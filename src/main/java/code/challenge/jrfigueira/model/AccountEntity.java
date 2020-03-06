package code.challenge.jrfigueira.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNTS")
@Data
public class AccountEntity {

    @Id
    @Column(name = "IBAN", length = 34, nullable = false)
    private String iban;

    @Column(name = "BALANCE")
    private float balance;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionEntities;

}
