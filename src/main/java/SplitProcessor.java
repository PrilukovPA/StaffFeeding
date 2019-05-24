import entities.Client;
import entities.ClientActivator;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

class SplitProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        Client client = exchange.getIn().getBody(Client.class);
        ClientActivator clientActivator = new ClientActivator(client.getActivatedByDistributor());
        exchange.getIn().setHeader("clientId", client.getId());
        exchange.getIn().setBody(clientActivator);
    }
}


