package com.flowerbun.haechilabs.wallet.db;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.util.TwoWayEncrypted;
import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.PrivateKey;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletEntity implements Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String privateKey;
    private String address;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private BalanceUnit balanceUnit;
    private BigDecimal pendingWithdrawal;

    public static WalletEntity of(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.privateKey = wallet.privateKey().encrypted();
        walletEntity.address = wallet.address();
        walletEntity.balance = BigDecimal.ZERO;
        walletEntity.balanceUnit = BalanceUnit.WEI;
        walletEntity.pendingWithdrawal = BigDecimal.ZERO;
        return walletEntity;
    }

    public WalletBalance balance() {
        return new WalletBalance(this.balance, this.balanceUnit);
    }

    public void addBalance(WalletBalance added) {
        BigDecimal balance = added.balance(this.balanceUnit);
        this.balance = this.balance.add(balance);
    }

    public void withdrawal(WalletBalance withdrawal) {
        BigDecimal minusWithdrawBalance = withdrawal.balance(this.balanceUnit);
        this.balance = this.balance.subtract(minusWithdrawBalance);
        this.pendingWithdrawal = this.pendingWithdrawal.subtract(minusWithdrawBalance);
    }

    public void validWithdrawal(WalletBalance withdrawalBalance) {
        BigDecimal withdrawableBalance = this.balance.subtract(this.pendingWithdrawal);
        if (withdrawableBalance.compareTo(withdrawalBalance.balance(this.balanceUnit)) < 0) {
            throw new CustomException("출금할 수 없습니다. 출금 대기중이 아닌 보유 금액이 더 작습니다");
        }
    }

    public void tryWithdrawal(WalletBalance withdrawal) {
        this.pendingWithdrawal.add(withdrawal.balance(this.balanceUnit));
    }

    @Override public String address() {
        return this.address;
    }

    @Override public PrivateKey privateKey() {
        return new PrivateKey(TwoWayEncrypted.ofEncrypted(this.privateKey));
    }
}
