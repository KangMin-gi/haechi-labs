package com.flowerbun.haechilabs.wallet.domain;

public interface Wallet {

    String address();

    PrivateKey privateKey();
}
