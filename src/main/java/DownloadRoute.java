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
                .to("smtp://smtp.city-sales.ru:25?password=#log16ax&username=aliluev@city-sales.ru?from=aliluev@city-sales.ru&to=PrilukovPA@evenx.ru&subject=DOWNLOAD");

        from("timer://runOnce?fixedRate=true&repeatCount=-1&delay=0&period=600000")
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader("Content-Type", simple("application/json"))
                .setHeader("Authorization", simple("Token e760626877de220b5e270ee612643c1e1f18bf82")) // Самара
                .to("jetty:https://planetahills.ru/distributors_api/v3/users/")
                .unmarshal().json(JsonLibrary.Jackson, Client[].class)
                .process(new DownloadProcessor());
    }
}