import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Реализует управление активацией вет. врачей по программе Хиллс Стафф Фидинг.
 */
class StaffFeeding {

    private static final Logger log = LoggerFactory.getLogger("ru.evenx.logback");

    public static Settings settings;

    public static void main(String[] args) throws Exception {
        StaffFeeding m = new StaffFeeding();
        m.run(args);
    }

    private void loadSettings(String fileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Settings.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        settings = (Settings) unmarshaller.unmarshal(new File(fileName));
        System.setProperty("dbUrl", settings.db.url);
        System.setProperty("dbUsername", settings.db.username);
        System.setProperty("dbPassword", settings.db.password);
    }

    private void run(String[] args) throws Exception {
        loadSettings(args[0]);
        Main main = new Main();
        main.addRouteBuilder(new DownloadRoute());
        main.addRouteBuilder(new UnloadRoute());
        main.run();
    }
}
