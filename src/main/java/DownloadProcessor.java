import entities.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hibernate.Session;

import java.util.stream.Stream;

/**
 * Загрузка клиентов в базу
 */
class DownloadProcessor implements Processor {

    public void process(Exchange exchange) {

        Client[] response = exchange.getIn().getBody(Client[].class);
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        // Добавим только те записи, которых нет в базе
        Stream.of(response).forEach(client -> {
            if (session.get(Client.class, client.getId()) == null)
                session.saveOrUpdate(client);
        });
        session.getTransaction().commit();
        session.close();
    }
}
