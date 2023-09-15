package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;

public interface GasLimitProvider {

    BigInteger gasLimit();
}
