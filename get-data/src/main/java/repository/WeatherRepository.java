package repository;

import constant.QUERY;
import constant.StatusData;
import constant.StatusFileLog;
import entity.control.FileLog;
import entity.weather.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepository implements BaseRepository<Weather> {
    private Connection connection;

    public WeatherRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Weather> findAll() {
        List<Weather> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.WEATHER.FIND_ALL);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return list;
            while (rs.next()) {
                int id = rs.getInt("id");
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
                StatusData status = StatusData.valueOf(rs.getString("status"));
                String expireDate = rs.getString("expire_date");

                list.add(new Weather(id, naturalKey, dateId, time, provinceId, temp, cloudDescription, minTemp, maxTemp, humidity, vision, windSpd, uvIndex, atmosphereQuality, status, expireDate));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Weather> findAllByStatus(StatusData status) {
        List<Weather> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.WEATHER.FIND_ALL_BY_STATUS);
            ps.setString(1, status.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return list;
            while (rs.next()) {
                int id = rs.getInt("id");
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
                String expireDate = rs.getString("expired_date");
                list.add(new Weather(id, naturalKey, dateId, time, provinceId, temp, cloudDescription, minTemp, maxTemp, humidity, vision, windSpd, uvIndex, atmosphereQuality, status, expireDate));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Weather findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.WEATHER.FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
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
                StatusData status = StatusData.valueOf(rs.getString("status"));
                String expireDate = rs.getString("expire_date");

                return new Weather(id, naturalKey, dateId, time, provinceId, temp, cloudDescription, minTemp, maxTemp, humidity, vision, windSpd, uvIndex, atmosphereQuality, status, expireDate);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public void save(Weather weather) {
        try {
            PreparedStatement statement = connection.prepareStatement(weather.getId() == 0 ? QUERY.WEATHER.SAVE : QUERY.WEATHER.UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, weather.getNaturalKey());
            statement.setInt(2, weather.getDateId());
            statement.setString(3, weather.getTime());
            statement.setInt(4, weather.getProvinceId());
            statement.setInt(5, weather.getTemp());
            statement.setString(6, weather.getCloudDescription());
            statement.setInt(7, weather.getMinTemp());
            statement.setInt(8, weather.getMaxTemp());
            statement.setDouble(9, weather.getHumidity());
            statement.setDouble(10, weather.getVision());
            statement.setDouble(11, weather.getWindSpd());
            statement.setDouble(12, weather.getUvIndex());
            statement.setString(13, weather.getAtmosphereQuality());
            statement.setString(14, weather.getStatus().toString());
            statement.setString(15, weather.getExpiredDate());
            if (weather.getId() != 0) {
                statement.setInt(16, weather.getId());
            }
            statement.executeUpdate();
            if (weather.getId() == 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        weather.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating fileLog failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

    }

    //    public Weather findByNaturalKey(String naturalKey) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(QUERY.WEATHER.FIND_BY_NATURAL_KEY);
//            statement.setString(1, naturalKey);
//            ResultSet rs = statement.executeQuery();
//            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
//            if (rs.next()) {
//                int id = rs.getInt("id");
//                int dateId = rs.getInt("date_id");
//                String time = rs.getString("time");
//                int provinceId = rs.getInt("province_id");
//                int temp = rs.getInt("temp");
//                String cloudDescription = rs.getString("cloud_description");
//                int minTemp = rs.getInt("min_temp");
//                int maxTemp = rs.getInt("max_temp");
//                double humidity = rs.getDouble("humidity");
//                double vision = rs.getDouble("vision");
//                double windSpd = rs.getDouble("wind_spd");
//                double uvIndex = rs.getDouble("uv_index");
//                String atmosphereQuality = rs.getString("atmosphere_quality");
//
//                return new Weather(id, naturalKey, dateId, time, provinceId, temp, cloudDescription, minTemp, maxTemp, humidity, vision, windSpd, uvIndex, atmosphereQuality);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//        return null;
//    }
    public Weather findByNaturalKeyInList(List<Weather> list, String naturalKey) {
        for (Weather weather : list) {
            if (weather.getNaturalKey().equals(naturalKey)) return weather;
        }
        return null;
    }
}
