package code.challenge.jrfigueira.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
@Data
public class TransactionEntity {

    //Attribute used to ensure that no transaction is created more than once
    @Transient
    private boolean update;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",
            strategy = "code.challenge.jrfigueira.generator.TransactionRefGenerator")
    @Column(name = "REF", length = 6, nullable = false)
    private String reference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TR_DATE")
    private Date date;

    @Column(name = "AMOUNT", nullable = false)
    private float amount;

    @Column(name = "FEE")
    private float fee;

    @Column(name = "DESCR", length = 120)
    private String description;

    @ManyToOne
    @JoinColumn(name="iban")
    @ToString.Exclude
    private AccountEntity account;

}
