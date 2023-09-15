package com.flowerbun.haechilabs.wallet;

import java.math.BigInteger;

import com.flowerbun.haechilabs.wallet.domain.GasLimitProvider;

public class ZeroGasLimitProvider implements GasLimitProvider {

    @Override public BigInteger gasLimit() {
        return BigInteger.ZERO;
    }
}
