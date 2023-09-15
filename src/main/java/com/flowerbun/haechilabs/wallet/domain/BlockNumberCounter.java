package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;
import java.util.Optional;

public interface BlockNumberCounter {

    Optional<BigInteger> lastNumber();
    void addCount(Block block);
}
