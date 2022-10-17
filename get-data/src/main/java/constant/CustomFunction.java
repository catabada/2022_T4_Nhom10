package constant;


import entity.control.FileLog;
import entity.weather.Weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomFunction {

    public static String[] createTimer() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timer = formatter.format(localDateTime);
        return timer.split(" ");
    }

    public static void uploadFileToFactTableStagingDatabase(Connection connection, String fileName) {
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.UPLOAD_FILE_DATABASE);
            ps.setString(1, StringConstant.FOLDER_PATH_LOCAL + fileName);
            ps.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void truncateDataInFactTableStagingDatabase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.TRUNCATE_DATABASE);
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

    public static void createTempTable(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.CREATE_TEMP_TABLE);
            if(ps.execute()) System.out.println("Created temp table");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void dropTempTable(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.DROP_TEMP_TABLE);
            if(ps.execute()) System.out.println("Created temp table");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static List<Weather> findAllDataTempTable(Connection connection) {
        List<Weather> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.FIND_ALL_DATA_TEMP_TABLE);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return list;
            while (rs.next()) {
                String naturalKey = rs.getString("natural_key");
                int dateId = rs.getInt("date_id");
                String time = rs.getString("time");
                int provinceId = rs.getInt("province_id");
                int temp = rs.getInt("temp");
                String cloudDescription = rs.getString("cloud_description");
                int minTemp = rs.getInt("min_temp");
                int maxTemp = rs.getInt("max_temp");
                double humidity = rs.getDouble("humidity");
                double vision = rs.getDouble("vision");
                double windSpd = rs.getDouble("wind_spd");
                double uvIndex = rs.getDouble("uv_index");
                String atmosphereQuality = rs.getString("atmosphere_quality");

               list.add(new Weather(naturalKey, dateId, time,provinceId,temp,cloudDescription,minTemp,maxTemp,humidity,vision,windSpd,uvIndex,atmosphereQuality));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

}
