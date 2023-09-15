package com.flowerbun.haechilabs.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.dto.TransactionReaderDto;
import com.flowerbun.haechilabs.wallet.dto.TransactionReaderDto.Request;
import lombok.Data;

public class TransactionDto {

    @Data
    public static class ListRequest {
        @JsonProperty("starting_after")
        private String startingAfter;
        @JsonProperty("ending_beofore")
        private String endingBefore;
        private Integer size;

        public TransactionReaderDto.Request toRequest() {
            return new Request(startingAfter, endingBefore, size);
        }
    }

    @Data
    public static class Response {
        private String hash;
        private String status;
        private String confirmedStatus;
        private Long blockConfirmation;

        Response(Tx tx, Long latestBlock) {
            this.hash = tx.hash();
            this.status = tx.status().name();
            this.confirmedStatus = tx.confirmStatus().name();
            this.blockConfirmation = Math.min(latestBlock - tx.blockNumber().longValue(), 12L);
        }
    }

    @Data
    public static class ListResponse {
        private List<Response> data;

        public ListResponse(List<Tx> list, Long latestBlockNumber) {
            this.data = list.stream()
                    .map(v -> new Response(v, latestBlockNumber))
                    .collect(Collectors.toList());
        }

    }
}
