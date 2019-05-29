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
                .to("smtp://smtp.evenx.ru:25?from=support-p@evenx.ru&to=PrilukovPA@evenx.ru&subject=DOWNLOAD");

        from("timer://runOnce?fixedRate=true&repeatCount=-1&delay=0&period=600000")
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader("Content-Type", simple("application/json"))
                .setHeader("Authorization", simple("Token eb6bec562e9197b4f57c5a2be84ca48492a08a86")) // Северный
                .to("jetty:https://planetahills.ru/distributors_api/v3/users/")
                .unmarshal().json(JsonLibrary.Jackson, Client[].class)
                .process(new DownloadProcessor());
    }
}