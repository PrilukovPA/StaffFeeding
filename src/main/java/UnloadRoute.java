import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Выгрузка данных об активных клиентах на сервис
 */
class UnloadRoute extends RouteBuilder {
    @Override
    public void configure() {

        onException(Throwable.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "${exception.message}")
                .transform().simple("${exception.message}")
                .to("smtp://smtp.evenx.ru:25?from=support-p@evenx.ru&to=PrilukovPA@evenx.ru&subject=UNLOAD");

        from("timer://runOnce?fixedRate=true&repeatCount=-1&delay=0&period=600000")
                .process(new UnloadProcessor())
                .split(body())
                .process(new TransformPocessor())
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, simple("PATCH"))
                .setHeader("Content-Type", simple("application/json"))
                .setHeader("Authorization", simple("Token eb6bec562e9197b4f57c5a2be84ca48492a08a86")) // Северный
                .toD("jetty:https://planetahills.ru/distributors_api/v3/users/${headers.clientId}/");
    }
}
