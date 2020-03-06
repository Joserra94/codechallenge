package code.challenge.jrfigueira.service.impl;

import code.challenge.jrfigueira.checker.StatusChecker;
import code.challenge.jrfigueira.dto.*;
import code.challenge.jrfigueira.enums.ChannelEnum;
import code.challenge.jrfigueira.enums.SortEnum;
import code.challenge.jrfigueira.enums.StatusEnum;
import code.challenge.jrfigueira.exceptions.AccountException;
import code.challenge.jrfigueira.mapper.TransactionMapper;
import code.challenge.jrfigueira.model.AccountEntity;
import code.challenge.jrfigueira.model.TransactionEntity;
import code.challenge.jrfigueira.repository.AccountRepository;
import code.challenge.jrfigueira.repository.TransactionsRepository;
import code.challenge.jrfigueira.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private static final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CreateTransactionResponse createTransaction(TransactionDto transactionDto) throws AccountException {
        log.info("Transaction creation initiated");
        //First we obtain the associated account, in case there is no account or the balance will turn negative, we abort the operation
        AccountEntity account = validateAccount(transactionDto);
        TransactionEntity transactionEntity = transactionMapper.transactionDtoToEntity(transactionDto);
        transactionEntity.setAccount(account);
        transactionsRepository.save(transactionEntity);
        CreateTransactionResponse createTransactionResponse = new CreateTransactionResponse();
        createTransactionResponse.setStatus("OK");
        log.info("Transaction creation finished");
        return createTransactionResponse;
    }

    public AccountEntity validateAccount(TransactionDto transactionDto) throws AccountException {
        Optional<AccountEntity> account = accountRepository.findById(transactionDto.getAccount_iban());
        if(!account.isPresent()){
            log.error("Unknown account, it´s not possible to save the transaction");
            throw new AccountException("Unknown account, it´s not possible to save the transaction");
        }
        float balance = account.get().getBalance();
        if(balance - transactionDto.getFee() + transactionDto.getAmount() < 0) {
            log.error("The transaction can´t leave the account balance below 0");
            throw new AccountException("The transaction can´t leave the account balance below 0");
        }
        return account.get();
    }

    @Override
    public List<TransactionDto> searchTransactions(TransactionSearchDto transactionSearchDto) {
        log.info("Transaction search initiated");
        SortEnum sortEnum;
        Sort direction = null;
        Iterable<TransactionEntity> transactionsEntities;
        if ((sortEnum = transactionSearchDto.getSorting()) != null) {
            if (sortEnum.equals(SortEnum.ASCENDING)) {
                direction = Sort.by("AMOUNT").ascending();
            } else if (sortEnum.equals(SortEnum.DESCENDING)) {
                direction = Sort.by("AMOUNT").descending();
            }
            assert direction != null;
            transactionsEntities = transactionsRepository.findByAccountIban(transactionSearchDto.getAccount_iban(), direction);
        }else{
            transactionsEntities = transactionsRepository.findByAccountIban(transactionSearchDto.getAccount_iban());
        }
        List<TransactionDto> transactions = transactionMapper.transactionEntityListToDto(transactionsEntities);
        log.info("Transaction search finished");
        return transactions;
    }

    @Override
    public TransactionStatusResponse checkTransactionStatus(TransactionStatusDto transactionStatusDto) {
        log.info("Transaction status search initiated");
        TransactionStatusResponse response = new TransactionStatusResponse();
        response.setReference(transactionStatusDto.getReference());
        Optional<TransactionEntity> transaction = transactionsRepository.findById(transactionStatusDto.getReference());
        ChannelEnum channel = transactionStatusDto.getChannel();
        if(transaction.isPresent() && channel != null){
            StatusChecker statusChecker = new StatusChecker(response, transaction.get(), channel);
            statusChecker.generateResponse();
        }else{
            response.setStatus(StatusEnum.INVALID);
        }
        log.info("Transaction status search finished");
        return response;
    }


}
