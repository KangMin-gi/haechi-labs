package com.flowerbun.haechilabs.util;

import java.math.BigInteger;

import lombok.Getter;

@Getter
public class TwoWayEncrypted {

    private final String encrypted;
    private final String origin;

    public static TwoWayEncrypted ofEncrypted(String encrypted) {
        return new TwoWayEncrypted(encrypted, true);
    }

    public static TwoWayEncrypted ofOrigin(String origin) {
        return new TwoWayEncrypted(origin, false);
    }

    public static TwoWayEncrypted ofOrigin(BigInteger origin) {
        return new TwoWayEncrypted(String.valueOf(origin), false);
    }

    private TwoWayEncrypted(String str, boolean encrypted) {
        if (encrypted) {
            this.encrypted = str;
            this.origin = Encrypt.twoWayDecrypt(str);
        } else {
            this.encrypted = Encrypt.twoWay(str);
            this.origin = str;
        }
    }
}
