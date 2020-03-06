package code.challenge.jrfigueira.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateTransactionResponse  implements Serializable {

    private String status;

    private int code;

    private String description;
}
