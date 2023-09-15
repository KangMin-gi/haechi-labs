package com.flowerbun.haechilabs.wallet;

import java.math.BigInteger;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.db.DbBlockNumberCounter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockReader {

    private final DbBlockNumberCounter numberCounter;

    public Long lastNumber() {
        return this.numberCounter.lastNumber()
                .map(BigInteger::longValue)
                .orElse(BigInteger.ZERO.longValue());
    }
}
