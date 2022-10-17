package script;

import connection.ConnectionMySql;
import constant.CustomFunction;
import constant.StatusData;
import constant.StatusFileLog;
import constant.StringConstant;
import entity.control.FileLog;
import entity.weather.Weather;
import repository.FileLogRepository;
import repository.WeatherRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class script3 {
    public static void main(String[] args) throws SQLException {
        //      Connect control database to get data config
        System.out.println("Connecting control database........");
        ConnectionMySql controlDatabase = new ConnectionMySql(StringConstant.CONTROL_DATABASE);
        Connection controlConnection = controlDatabase.getConnection();

        //      Connect staging database to upload data
        System.out.println("Connecting staging database.......");
        ConnectionMySql stagingDatabase = new ConnectionMySql(StringConstant.STAGING_DATABASE);
        Connection stagingConnection = stagingDatabase.getConnection();

        //      Connect staging database to upload data
        System.out.println("Connecting warehouse database.......");
        ConnectionMySql warehouseDatabase = new ConnectionMySql(StringConstant.WAREHOUSE_DATABASE);
        Connection warehouseConnection = warehouseDatabase.getConnection();

        //      Initialization Repository
        FileLogRepository fileLogRepository = new FileLogRepository(controlConnection);
        WeatherRepository weatherRepository = new WeatherRepository(warehouseConnection);

        //      Create Timer
        String[] split = CustomFunction.createTimer();
        String date = split[0];
        String time = split[1];

        //      Check if there are any file have been crawled data and status = ER today(date now) ?
        List<FileLog> listFileLog = fileLogRepository.findAllByDateAndStatus(date, StatusFileLog.SU);
        FileLog newFileLog = null;
        for (FileLog fileLog : listFileLog) {
            if (CustomFunction.equalsHour(fileLog.getTime(), time)) {
                newFileLog = fileLog;
            }
        }
//      Create temp table
        CustomFunction.createTempTable(stagingConnection);

//      Get data from temp table
        List<Weather> weathersStaging = CustomFunction.findAllDataTempTable(stagingConnection);
//      Get data have status is UPDATED from fact table in warehouse database
        List<Weather> weathersWarehouse = weatherRepository.findAllByStatus(StatusData.UPDATED);
        System.out.println(weathersStaging.size() + " size");

        for (Weather weatherStaging: weathersStaging) {
            Weather weatherWarehouse = weatherRepository.findByNaturalKeyInList(weathersWarehouse, weatherStaging.getNaturalKey());
            if(weatherWarehouse == null) {
//                If null then insert new data
                weatherStaging.setStatus(StatusData.UPDATED);
                weatherStaging.setExpiredDate(StringConstant.DATE_DEFAULT);
                weatherRepository.save(weatherStaging);
            } else {
//              Check duplicate data
//                If duplicate then do nothing
//                If not insert new data and update status old data
                if(!weatherStaging.equals(weatherWarehouse)) {
                    weatherWarehouse.setStatus(StatusData.DELETED);
                    weatherWarehouse.setExpiredDate(date);
                    weatherRepository.save(weatherWarehouse);

                    weatherStaging.setStatus(StatusData.UPDATED);
                    weatherStaging.setExpiredDate(StringConstant.DATE_DEFAULT);
                    weatherRepository.save(weatherStaging);
                }
            }

        }
        CustomFunction.truncateDataInFactTableStagingDatabase(stagingConnection);
        CustomFunction.dropTempTable(stagingConnection);


    }
}
