package constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeCustom {

    public static String[] createTimer() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timer = formatter.format(localDateTime);
        return timer.split(" ");
    }
}
