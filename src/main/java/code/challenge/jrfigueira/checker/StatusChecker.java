package code.challenge.jrfigueira.checker;

import code.challenge.jrfigueira.dto.TransactionStatusResponse;
import code.challenge.jrfigueira.enums.ChannelEnum;
import code.challenge.jrfigueira.enums.StatusEnum;
import code.challenge.jrfigueira.model.TransactionEntity;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
public class StatusChecker {

    TransactionStatusResponse response;
    TransactionEntity transaction;
    ChannelEnum channel;

    public void generateResponse() {
        int daysDiff = compareDays();
        int channelType = channel.getChannelId();
        if(daysDiff < 0){
            if(channelType > 0){
                //C
                feeAndAmount(StatusEnum.SETTLED);
            }else{
                //B
                feeSubtracted(StatusEnum.SETTLED);
            }
        }else if(daysDiff == 0){
            if(channelType > 0){
                //E
                feeAndAmount(StatusEnum.PENDING);
            }else{
                //D
                feeSubtracted(StatusEnum.PENDING);
            }
        }else{
            if(channelType > 0){
                //H
                feeAndAmount(StatusEnum.FUTURE);
            }else if(channelType == 0){
                //G
                feeSubtracted(StatusEnum.PENDING);
            }else{
                //F
                feeSubtracted(StatusEnum.FUTURE);
            }
        }
    }

    private void feeSubtracted(StatusEnum status) {
        response.setStatus(status);
        response.setAmount(transaction.getAmount() - transaction.getFee());
    }

    private void feeAndAmount(StatusEnum status) {
        response.setStatus(status);
        response.setFee(transaction.getFee());
        response.setAmount(transaction.getAmount());
    }

    private int compareDays() {
        Calendar today = Calendar.getInstance();
        Calendar transactionDate = Calendar.getInstance();
        today.setTime(new Date());
        transactionDate.setTime(transaction.getDate());
        int currentDay = today.get(Calendar.DAY_OF_YEAR);
        int currentYear = today.get(Calendar.YEAR);
        int transactionDay = transactionDate.get(Calendar.DAY_OF_YEAR);
        int transactionYear = transactionDate.get(Calendar.YEAR);
        int result = 0;
        if(transactionYear < currentYear){
            result = -1;
        }else if(transactionYear > currentYear){
            result =  1;
        }else{
            if(transactionDay < currentDay){
                result = -1;
            }else if(transactionDay > currentDay){
                result =  1;
            }
        }
        return result;
    }
}
