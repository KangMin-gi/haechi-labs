package com.flowerbun.haechilabs.wallet.db;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.wallet.domain.Block;
import com.flowerbun.haechilabs.wallet.domain.BlockNumberCounter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbBlockNumberCounter implements BlockNumberCounter {

    private final BlockCounterRepository blockCounterRepository;

    @Override
    public Optional<BigInteger> lastNumber() {
        Optional<BlockCounterEntity> lastBlock = this.blockCounterRepository.findTopByOrderByBlockNumberDesc();
        return lastBlock.map(BlockCounterEntity::blockNumber);
    }

    @Override
    @Transactional
    public void addCount(Block block) {
        BlockCounterEntity blockCounterEntity = BlockCounterEntity.of(block);
        this.blockCounterRepository.save(blockCounterEntity);
    }
}
