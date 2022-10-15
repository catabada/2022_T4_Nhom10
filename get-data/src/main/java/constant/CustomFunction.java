package constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomFunction {

    public static String[] createTimer() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timer = formatter.format(localDateTime);
        return timer.split(" ");
    }

    public static void uploadFileToDatabase(Connection connection, String fileName) {
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.UPLOAD_FILE_DATABASE);
            ps.setString(1, StringConstant.FOLDER_PATH_LOCAL + fileName);
            ps.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean equalsHour(String time, String anotherTime) {
        String hour = time.split(":")[0];
        String anotherHour = anotherTime.split(":")[0];
        return hour.equals(anotherHour);
    }

}
