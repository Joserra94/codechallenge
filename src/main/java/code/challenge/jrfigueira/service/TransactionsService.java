package code.challenge.jrfigueira.service;

import code.challenge.jrfigueira.dto.*;
import code.challenge.jrfigueira.exceptions.AccountException;

import java.util.List;

public interface TransactionsService {
    CreateTransactionResponse createTransaction(TransactionDto transactionDto) throws AccountException;

    List<TransactionDto> searchTransactions(TransactionSearchDto transactionSearchDto);

    TransactionStatusResponse checkTransactionStatus(TransactionStatusDto transactionStatusDto);
}
