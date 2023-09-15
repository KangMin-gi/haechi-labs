package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class WalletBalance implements Comparable<WalletBalance> {

    private BigDecimal balance;
    private BalanceUnit balanceUnit;

    public static final WalletBalance ZERO = new WalletBalance(BigDecimal.ZERO, BalanceUnit.WEI);

    public BigDecimal balance(BalanceUnit unit) {
        if (this.balanceUnit == unit) {
            return balance;
        }
        BigDecimal unitGap = new BigDecimal(unit.getBase()).pow(this.balanceUnit.getExp() - unit.getExp(),
                MathContext.DECIMAL128);
        return this.balance.multiply(unitGap);
    }

    @Override
    public int compareTo(@NotNull WalletBalance o) {
        if (this.balanceUnit == o.balanceUnit) {
            return this.balance.compareTo(o.balance);
        }
        return this.balance(balanceUnit).compareTo(o.balance);
    }

    public BalanceUnit balanceUnit() {
        return this.balanceUnit;
    }

    @Override
    public boolean equals(Object x) {
        if (!(x instanceof WalletBalance)) {
            return false;
        }
        WalletBalance other = (WalletBalance) x;

        if (this.balanceUnit == other.balanceUnit) {
            return this.balance.compareTo(other.balance) == 0;
        }

        return this.balance(other.balanceUnit).compareTo(other.balance) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(balance, balanceUnit);
    }
}
