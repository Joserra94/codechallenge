package code.challenge.jrfigueira.repository;

import code.challenge.jrfigueira.model.AccountEntity;
import code.challenge.jrfigueira.model.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    List<TransactionEntity> findTransactionEntitiesByIban(String iban, Sort sort);

    List<TransactionEntity> findTransactionEntitiesByIban(String iban);
}
