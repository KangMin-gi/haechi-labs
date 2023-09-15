package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigInteger;

import com.flowerbun.haechilabs.wallet.domain.SendTx;
import com.flowerbun.haechilabs.wallet.domain.TxConfirmStatus;
import com.flowerbun.haechilabs.wallet.domain.TxStatus;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class EthSendTransaction implements SendTx {

    private String to;
    private String from;
    private String hash;
    private WalletBalance balance;

    private BigInteger blockNumber;
    private TxConfirmStatus confirmStatus;

    EthSendTransaction(TransactionReceipt receipt, WalletBalance walletBalance) {
        this.to = receipt.getTo();
        this.from = receipt.getFrom();
        this.hash = receipt.getTransactionHash();
        this.balance = walletBalance;
        this.blockNumber = receipt.getBlockNumber();
        this.confirmStatus = blockNumber == null ? TxConfirmStatus.PENDING : TxConfirmStatus.MINED;

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
        return this.balance;
    }

    @Override public TxStatus status() {
        return TxStatus.UNKNOWN;
    }

    @Override public BigInteger blockNumber() {
        return this.blockNumber;
    }

    @Override public TxConfirmStatus confirmStatus() {
        return this.confirmStatus;
    }
}
