package com.flowerbun.haechilabs.wallet.db;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DBTransactionReader {

    private final DbTransactionRepository repository;
    private final static Integer DEFAULT_LIMIT = 10;
    private final static Integer MAX_LIMIT = 100;

    public List<Tx> list() {
        return this.list(DEFAULT_LIMIT);
    }

    public List<Tx> list(Integer limit) {
        return this.repository.findAll(this.defaultRequest(limit))
                .stream()
                .map(v -> (Tx) v)
                .collect(Collectors.toList());
    }

    public List<Tx> list(String startHash, String endHash) {
        return this.list(startHash, endHash, DEFAULT_LIMIT);
    }

    public List<Tx> list(String startHash, String endHash, Integer limit) {
        Optional<DbTransactionEntity> start = this.repository.findByHash(startHash);
        Optional<DbTransactionEntity> end = this.repository.findByHash(endHash);
        Long startId = start.map(DbTransactionEntity::getId)
                .orElseThrow(() -> new CustomException("검색한 hash를 찾을 수 없습니다"));
        Long endId = end.map(DbTransactionEntity::getId)
                .orElseThrow(() -> new CustomException("검색한 hash를 찾을 수 없습니다"));

        return this.repository.findById(startId, endId, this.defaultRequest(limit))
                .stream()
                .map(v -> (Tx) v)
                .collect(Collectors.toList());
    }

    private PageRequest defaultRequest(Integer limit) {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        } else if (limit > MAX_LIMIT) {
            limit = MAX_LIMIT;
        }
        return PageRequest.of(0, limit, DbTransactionEntity.defaultSort());
    }


}
