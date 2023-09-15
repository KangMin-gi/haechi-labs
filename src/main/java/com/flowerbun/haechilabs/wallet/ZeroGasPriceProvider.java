package com.flowerbun.haechilabs.wallet;

import java.math.BigInteger;
import org.springframework.stereotype.Component;

import com.flowerbun.haechilabs.wallet.domain.GasPriceProvider;

@Component
public class ZeroGasPriceProvider implements GasPriceProvider {

    @Override public BigInteger gasPrice() {
        return BigInteger.ZERO;
    }
}
