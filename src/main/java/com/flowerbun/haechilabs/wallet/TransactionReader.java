package com.flowerbun.haechilabs.wallet;

import java.util.List;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.db.DBTransactionReader;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.dto.TransactionReaderDto.Request;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionReader {

    private final DBTransactionReader reader;

    public List<Tx> txList(Request request) {
        if (request.hasHashParam()) {
            return this.reader.list(request.getStartHash(),
                    request.getEndHash(),
                    request.getLimit());
        }

        return this.reader.list(request.getLimit());
    }

}
