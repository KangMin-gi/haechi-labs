package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigInteger;

import com.flowerbun.haechilabs.util.TwoWayEncrypted;
import com.flowerbun.haechilabs.wallet.domain.PrivateKey;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import org.web3j.crypto.Credentials;

public class EthWallet implements Wallet {

    private final String address;
    private final Credentials credentials;

    public EthWallet(Credentials credentials) {
        this.address = credentials.getAddress();
        this.credentials = credentials;
    }

    @Override
    public String address() {
        return this.address;
    }

    public PrivateKey privateKey() {
        BigInteger privateKey = credentials.getEcKeyPair()
                .getPrivateKey();
        return new PrivateKey(TwoWayEncrypted.ofOrigin(privateKey));
    }
}
