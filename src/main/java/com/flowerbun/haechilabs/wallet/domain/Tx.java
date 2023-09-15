package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;

public interface Tx {

    String to();
    String hash();
    String from();
    WalletBalance balance();

    TxStatus status();

    BigInteger blockNumber();
    TxConfirmStatus confirmStatus();
}
