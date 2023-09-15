package com.flowerbun.haechilabs.wallet.domain;

import lombok.Getter;

@Getter
public enum BalanceUnit {
    WEI(0),
    GWEI(9),
    ETH(18);

    BalanceUnit(Integer exp) {
        this.exp = exp;
    }

    private final Integer exp;
    private final Integer base = 10;
}
