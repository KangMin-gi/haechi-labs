package com.flowerbun.haechilabs.wallet.db;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    Optional<WalletEntity> findByAddress(String address);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT we FROM WalletEntity we where we.address = :address")
    Optional<WalletEntity> findByAddressWithPessimisticLock(String address);
    Optional<WalletEntity> findByPrivateKey(String privateKey);

}
