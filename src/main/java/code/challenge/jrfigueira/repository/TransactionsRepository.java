package code.challenge.jrfigueira.repository;

import code.challenge.jrfigueira.model.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findByAccountIban(String iban, Sort sort);

    List<TransactionEntity> findByAccountIban(String iban);
}
