package com.flowerbun.haechilabs.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EncryptTest {

    @Test
    public void encDecTest() {
        String origin = "hidmalsid";
        String encrypted = Encrypt.twoWay(origin);
        String dec = Encrypt.twoWayDecrypt(encrypted);
        assertThat(origin).isEqualTo(dec);
    }
}