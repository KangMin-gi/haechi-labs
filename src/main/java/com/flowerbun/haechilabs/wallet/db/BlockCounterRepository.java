package com.flowerbun.haechilabs.wallet.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockCounterRepository extends JpaRepository<BlockCounterEntity, Long> {

    Optional<BlockCounterEntity> findTopByOrderByBlockNumberDesc();
}
