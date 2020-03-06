package code.challenge.jrfigueira.mapper;

import code.challenge.jrfigueira.dto.TransactionDto;
import code.challenge.jrfigueira.model.TransactionEntity;
import org.mapstruct.*;

import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionEntity transactionDtoToEntity(TransactionDto transactionDto);

    TransactionDto transactionEntityToDto(TransactionEntity transactionEntity);

    default List<TransactionDto> transactionEntityListToDto(Iterable<TransactionEntity> transactionEntities){
        List<TransactionDto> result = new LinkedList<>();
        if(transactionEntities != null){
            for(TransactionEntity entity: transactionEntities){
                TransactionDto dto = transactionEntityToDto(entity);
                dto.setAccount_iban(entity.getAccount().getIban());
                result.add(dto);
            }
        }
        return result;
    }

}
