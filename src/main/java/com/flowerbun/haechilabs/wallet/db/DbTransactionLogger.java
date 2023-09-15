package com.flowerbun.haechilabs.wallet.db;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.wallet.domain.Block;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.domain.TxConfirmStatus;
import com.flowerbun.haechilabs.wallet.domain.TxLogger;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbTransactionLogger implements TxLogger {

    private final DbTransactionRepository transactionRepository;

    @Override public void write(Tx tx) {
        DbTransactionEntity dbTransactionEntity = this.transactionRepository.findByHash(tx.hash())
                .orElseGet(() -> new DbTransactionEntity(tx));
        dbTransactionEntity.update(tx);

        this.transactionRepository.save(dbTransactionEntity);
    }

    @Transactional
    public void confirmBlockUpdate(Block block) {
        this.transactionRepository.confirmAllTransaction(TxConfirmStatus.CONFIRM, block.number().longValue());
    }
}
