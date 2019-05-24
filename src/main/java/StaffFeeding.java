import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
