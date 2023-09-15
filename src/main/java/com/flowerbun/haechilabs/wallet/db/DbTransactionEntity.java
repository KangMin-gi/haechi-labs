package com.flowerbun.haechilabs.wallet.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.domain.Sort;

import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.domain.TxConfirmStatus;
import com.flowerbun.haechilabs.wallet.domain.TxStatus;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED)
public class DbTransactionEntity implements Tx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;
    private Long blockNumber;
    private String toAddress;
    private String fromAddress;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private BalanceUnit unit;
    @Enumerated(EnumType.STRING)
    private TxStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    private TxConfirmStatus confirmStatus;

    public static Sort defaultSort() {
        return Sort.by("id").descending();
    }

    public DbTransactionEntity(Tx tx) {
        this.blockNumber = tx.blockNumber() == null ? null : tx.blockNumber().longValue();
        this.hash = tx.hash();
        this.toAddress = tx.to();
        this.fromAddress = tx.from();
        WalletBalance walletBalance = tx.balance();
        this.unit = walletBalance.balanceUnit();
        this.balance = walletBalance.balance(this.unit);
        this.transactionStatus = tx.status();
        this.confirmStatus = tx.confirmStatus();
    }

    public void update(Tx tx) {
        this.transactionStatus = tx.status();
        this.confirmStatus = tx.confirmStatus();
    }

    @Override public String to() {
        return this.toAddress;
    }

    @Override public String hash() {
        return this.hash;
    }

    @Override public String from() {
        return this.fromAddress;
    }

    @Override public WalletBalance balance() {
        return new WalletBalance(this.balance, this.unit);
    }

    @Override public TxStatus status() {
        return this.transactionStatus;
    }

    @Override public BigInteger blockNumber() {
        return BigInteger.valueOf(this.blockNumber);
    }

    @Override public TxConfirmStatus confirmStatus() {
        return this.confirmStatus;
    }
}
