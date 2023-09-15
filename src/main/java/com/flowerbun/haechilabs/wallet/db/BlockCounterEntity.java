package com.flowerbun.haechilabs.wallet.db;

import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.flowerbun.haechilabs.wallet.domain.Block;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockCounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long blockNumber;
    private LocalDateTime createDate;

    public static BlockCounterEntity of(Block read) {
        BigInteger number = read.number();
        BlockCounterEntity blockCounterEntity = new BlockCounterEntity();
        blockCounterEntity.blockNumber = number.longValue();
        blockCounterEntity.createDate = LocalDateTime.now();
        return blockCounterEntity;
    }

    public BigInteger blockNumber() {
        return BigInteger.valueOf(this.blockNumber);
    }
}
