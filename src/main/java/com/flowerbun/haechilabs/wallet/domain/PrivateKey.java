package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;

import com.flowerbun.haechilabs.util.TwoWayEncrypted;

public class PrivateKey {

    private final String encrypted;
    private final BigInteger origin;

    public PrivateKey(TwoWayEncrypted twoWayEncrypted) {
        this.encrypted = twoWayEncrypted.getEncrypted();
        this.origin = new BigInteger(twoWayEncrypted.getOrigin(), 16);
    }

    public String get() {
        return String.valueOf(this.origin);
    }

    public BigInteger origin() {
        return this.origin;
    }

    public String encrypted() {
        return this.encrypted;
    }
}
