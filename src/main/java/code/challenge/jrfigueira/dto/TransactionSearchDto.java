package code.challenge.jrfigueira.dto;

import code.challenge.jrfigueira.enums.SortEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TransactionSearchDto  implements Serializable {

    @NotNull
    private String account_iban;

    private SortEnum sorting;
}
