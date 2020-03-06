package code.challenge.jrfigueira.dto;

import code.challenge.jrfigueira.enums.ChannelEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TransactionStatusDto implements Serializable {

    //"reference":"12345A" (mandatory): The transaction reference number
    @NotNull
    private String reference;

    //"channel":"CLIENT" (optional): The type of the channel that is asking for the status. It can be any of these values: CLIENT, ATM, INTERNAL
    private ChannelEnum channel;
}
