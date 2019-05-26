import entities.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hibernate.Session;

import java.util.List;

/**
 * Выгрузка клиентов из базы в виде списка
 */
class UnloadProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Client> clients = session.createQuery("FROM hills_clients").getResultList();
        exchange.getIn().setBody(clients);
        session.close();
    }
}
