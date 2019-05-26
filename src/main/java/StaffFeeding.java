import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Реализует управление активацией вет. врачей по программе Хиллс Стафф Фидинг.
 */
class StaffFeeding {

    private static final Logger log = LoggerFactory.getLogger("ru.evenx.logback");

    public static void main(String[] args) {
        StaffFeeding m = new StaffFeeding();
        m.run();
    }

    private void run() {
        Main main = new Main();
        main.addRouteBuilder(new DownloadRoute());
        main.addRouteBuilder(new UnloadRoute());
        try {
            main.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
