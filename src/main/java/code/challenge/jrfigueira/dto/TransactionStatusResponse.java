package code.challenge.jrfigueira.dto;

import code.challenge.jrfigueira.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionStatusResponse  implements Serializable {

    //"reference":"12345A", The transaction reference number
    private String reference;

    //"status":"PENDING", The status of the transaction. It can be any of these values: PENDING, SETTLED, FUTURE,
    private StatusEnum status;

    //"amount":193.38, the amount of the transaction
    private float amount;

    //"fee":3.18 The fee applied to the transaction
    private float fee;
}
