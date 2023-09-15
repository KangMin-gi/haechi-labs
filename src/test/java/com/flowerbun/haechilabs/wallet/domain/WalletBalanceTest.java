package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigDecimal;

import static com.flowerbun.haechilabs.wallet.domain.BalanceUnit.ETH;
import static com.flowerbun.haechilabs.wallet.domain.BalanceUnit.GWEI;
import static com.flowerbun.haechilabs.wallet.domain.BalanceUnit.WEI;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WalletBalanceTest {

    @Test
    public void inZeroUnitChangeTest() {
        WalletBalance wei = new WalletBalance(new BigDecimal(0.0), WEI);
        WalletBalance gwei = new WalletBalance(new BigDecimal(0.0), GWEI);
        WalletBalance eth = new WalletBalance(new BigDecimal(0.0), ETH);

        assertThat(wei)
                .isEqualTo(gwei)
                .isEqualTo(eth);

        assertThat(wei.balance(WEI).compareTo(gwei.balance(GWEI)))
                .isEqualTo(0);
        assertThat(wei.balance(GWEI).compareTo(gwei.balance(ETH)))
                .isEqualTo(0);
        assertThat(wei.balance(WEI).compareTo(gwei.balance(WEI)))
                .isEqualTo(0);
    }

    @Test
    public void converter() {
        WalletBalance wei = new WalletBalance(new BigDecimal("1102910293710293711"), WEI);
        WalletBalance gwei = new WalletBalance(new BigDecimal("1102910293.710293711"), GWEI);
        WalletBalance eth = new WalletBalance(new BigDecimal("1.102910293710293711"), ETH);

        assertThat(wei)
                .isEqualTo(gwei)
                .isEqualTo(eth);

    }

    @Test
    @DisplayName("부동 소수점이 달라도, WalletBalance는 같은 값이라고 판단한다.")
    public void otherConverter() {
        WalletBalance wei = new WalletBalance(new BigDecimal("1102910293710293000"), WEI);
        WalletBalance gwei = new WalletBalance(new BigDecimal("1102910293.7102930"), GWEI);
        WalletBalance eth = new WalletBalance(new BigDecimal("1.102910293710293"), ETH);

        boolean equals = wei.equals(gwei);
        boolean equals1 = gwei.equals(eth);
        boolean equals2 = eth.equals(wei);

        assertThat(true)
                .isEqualTo(equals1)
                .isEqualTo(equals2)
                .isEqualTo(equals);

    }

    @Test
    @DisplayName("전체 변환 테스트")
    public void convertEquals() {
        WalletBalance wei = new WalletBalance(new BigDecimal("12345000000000"), WEI);
        WalletBalance gwei = new WalletBalance(new BigDecimal("12345"), GWEI);
        WalletBalance eth = new WalletBalance(new BigDecimal("0.000012345"), ETH);

        assertThat(wei)
                .isEqualTo(gwei)
                .isEqualTo(eth);

        assertThat(this.compareToSameUnit(wei, gwei, WEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(wei, gwei, GWEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(wei, gwei, ETH)).isEqualTo(0);

        assertThat(this.compareToSameUnit(gwei, eth, WEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(gwei, eth, GWEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(gwei, eth, ETH)).isEqualTo(0);

        assertThat(this.compareToSameUnit(wei, eth, WEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(wei, eth, GWEI)).isEqualTo(0);
        assertThat(this.compareToSameUnit(wei, eth, ETH)).isEqualTo(0);

    }

    private int compareToSameUnit(WalletBalance a, WalletBalance b, BalanceUnit unit) {
        return a.balance(unit).compareTo(b.balance(unit));
    }
}