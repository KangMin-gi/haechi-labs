package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;

public interface GasPriceProvider {

    BigInteger gasPrice();
}
