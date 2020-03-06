package code.challenge.jrfigueira.controller;

import code.challenge.jrfigueira.dto.*;
import code.challenge.jrfigueira.exceptions.AccountException;
import code.challenge.jrfigueira.service.TransactionsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction/v1")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    //It is IMPORTANT to note that a transaction that leaves the total account balance bellow 0 is not allowed.
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Registers a new transaction", response = HttpStatus.class)
    public ResponseEntity createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        try{
            return new ResponseEntity<>(transactionsService.createTransaction(transactionDto), HttpStatus.CREATED);
        }catch (AccountException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping("/search")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Search for transactions", response = HttpStatus.class)
    public ResponseEntity<List<TransactionDto>> searchTransactions(@RequestBody @Valid TransactionSearchDto transactionSearchDto) {
        return new ResponseEntity<>(transactionsService.searchTransactions(transactionSearchDto), HttpStatus.FOUND);
    }

    @GetMapping("/status")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Search for a transaction status", response = HttpStatus.class)
    public ResponseEntity<TransactionStatusResponse> checkTransactionStatus(@RequestBody @Valid TransactionStatusDto transactionStatusDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionsService.checkTransactionStatus(transactionStatusDto));
    }



}
