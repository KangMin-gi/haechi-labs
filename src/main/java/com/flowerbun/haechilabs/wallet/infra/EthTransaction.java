package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.domain.TxConfirmStatus;
import com.flowerbun.haechilabs.wallet.domain.TxStatus;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import org.web3j.protocol.core.methods.response.Transaction;

public class EthTransaction implements Tx {

    private final String hash;
    private final String to;
    private final String from;
    private final BigInteger price;
    private final BalanceUnit unit;
    private final BigInteger blockNumber;
    private final TxConfirmStatus confirmStatus;

    private TxStatus status = TxStatus.FAIL;

    public static EthTransaction ofConfirm(Transaction transaction) {
        return new EthTransaction(transaction, TxConfirmStatus.CONFIRM);
    }

    public static EthTransaction ofPending(Transaction transaction) {
        return new EthTransaction(transaction, TxConfirmStatus.PENDING);
    }

    public static EthTransaction ofMined(Transaction transaction) {
        return new EthTransaction(transaction, TxConfirmStatus.MINED);
    }

    public EthTransaction(Transaction transaction, TxConfirmStatus confirmStatus) {
        this.hash = transaction.getHash();
        this.to = transaction.getTo();
        this.from = transaction.getFrom();
        this.price = transaction.getValue();
        this.blockNumber = transaction.getBlockNumber();
        this.unit = BalanceUnit.WEI;
        this.confirmStatus = confirmStatus;
    }

    @Override public String to() {
        return this.to;
    }

    @Override public String hash() {
        return this.hash;
    }

    @Override public String from() {
        return this.from;
    }

    @Override public WalletBalance balance() {
        BigDecimal price = new BigDecimal(this.price);
        return new WalletBalance(price, this.unit);
    }

    @Override public TxStatus status() {
        return this.status;
    }

    @Override public BigInteger blockNumber() {
        return this.blockNumber;
    }

    @Override public TxConfirmStatus confirmStatus() {
        return this.confirmStatus;
    }

    public void success() {
        this.status = TxStatus.SUCCESS;
    }

    public void unknown() {
        this.status = TxStatus.UNKNOWN;
    }

    public void fail() {
        this.status = TxStatus.FAIL;
    }


}
