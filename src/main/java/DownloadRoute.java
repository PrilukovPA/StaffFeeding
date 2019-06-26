import entities.Client;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Загрузка клиентов из сервиса в базу. Отправка письма разработчику в случае ошибки.
 */
class DownloadRoute extends RouteBuilder {

    public void configure() {

        onException(Throwable.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "${exception.message}")
                .transform().simple("${exception.message}")
                .to("smtp://" + StaffFeeding.settings.logmail.host + ":" + StaffFeeding.settings.logmail.port +
                        "?password=" + StaffFeeding.settings.logmail.pass +
                        "&username=" + StaffFeeding.settings.logmail.user +
                        "&from=" + StaffFeeding.settings.logmail.from +
                        "&to=" + StaffFeeding.settings.logmail.recipients.get(0) +
                        "&subject=" + StaffFeeding.settings.logmail.subject);

        from("timer://runOnce?fixedRate=true&repeatCount=-1&delay=0&period=600000")
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader("Content-Type", simple("application/json"))
                .setHeader("Authorization", simple("Token " + StaffFeeding.settings.gateway.key))
                .to("jetty:" + StaffFeeding.settings.gateway.url + "distributors_api/v3/users/")
                .unmarshal().json(JsonLibrary.Jackson, Client[].class)
                .process(new DownloadProcessor());
    }
}