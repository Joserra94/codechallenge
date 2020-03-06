package code.challenge.jrfigueira.generator;

import code.challenge.jrfigueira.model.TransactionEntity;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;

import java.io.Serializable;
import org.hibernate.type.Type;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

public class TransactionRefGenerator implements IdentifierGenerator, Configurable {

    private String reference;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if(obj.getClass() == TransactionEntity.class){
            reference = ((TransactionEntity) obj).getReference();
        }
        if(reference == null || reference.isEmpty()){
            String query = String.format("select %s from %s",session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(), obj.getClass().getSimpleName());
            Stream<String> ids = session.createQuery(query).stream();
            do {
                Random rand = new Random();
                reference = "" + rand.nextInt(100000);
                reference += (char) (rand.nextInt(26) + 65);
            } while(ids.anyMatch(id -> id.equals(reference)));
        }
        return reference;
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
    }
}