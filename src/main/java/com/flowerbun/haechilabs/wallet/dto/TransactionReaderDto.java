package com.flowerbun.haechilabs.wallet.dto;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionReaderDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {
        private String startHash;
        private String endHash;
        private Integer limit = null;

        public boolean hasHashParam() {
            if (ObjectUtils.isEmpty(startHash) || ObjectUtils.isEmpty(endHash)) {
                return false;
            }
            return true;
        }

    }
}
