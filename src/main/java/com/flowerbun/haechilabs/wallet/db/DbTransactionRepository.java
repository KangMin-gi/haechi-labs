package com.flowerbun.haechilabs.wallet.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flowerbun.haechilabs.wallet.domain.TxConfirmStatus;

public interface DbTransactionRepository extends JpaRepository<DbTransactionEntity, Long> {

    Optional<DbTransactionEntity> findByHash(String hash);

    @Modifying
    @Query("update DbTransactionEntity dte set dte.confirmStatus = :confirmStatus WHERE dte.blockNumber = :blockNumber")
    void confirmAllTransaction(TxConfirmStatus confirmStatus, Long blockNumber);

    @Query("    "
            + "SELECT tx  "
            + "  FROM DbTransactionEntity tx"
            + " WHERE tx.toAddress = :address or tx.fromAddress = :address"
            + " ")
    List<DbTransactionEntity> findByAddress(String address, Pageable pageable);

    @Query("  "
            + "  SELECT tx"
            + "    FROM DbTransactionEntity tx"
            + "   WHERE tx.id between :startId and :endId "
            + "   ")
    List<DbTransactionEntity> findById(Long startId, Long endId, Pageable pageable);
}
