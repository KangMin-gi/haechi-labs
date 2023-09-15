package com.flowerbun.haechilabs.wallet.domain;

import java.math.BigInteger;
import java.util.List;

public interface Block {

    BigInteger number();
    List<Tx> transactions();
}
