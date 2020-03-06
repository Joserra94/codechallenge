package code.challenge.jrfigueira.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionDto implements Serializable {

    private static final long serialVersionUID = -4715901356208926503L;

    //"reference":"12345A"  (optional): The transaction unique reference number in our system. If not present, the system will generate one.
    private String reference;

    //"account_iban":"ES9820385778983000760236" (mandatory): The IBAN number of the account where the transaction has happened.
    @NotNull
    private String account_iban;

    //"date":"2019-07-16T16:55:42.000Z",(optional): Date when the transaction took place
    private Date date;

    //"amount":193.38,(mandatory): If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account)
    @NotNull
    private float amount;

    //"fee":3.18,(optional): Fee that will be deducted from the amount, regardless on the amount being positive or negative.
    private float fee;

    //"description":"Restaurant payment" (optional): The description of the transaction
    private String description;
}
