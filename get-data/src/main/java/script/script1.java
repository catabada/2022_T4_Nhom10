package script;

import connection.ConnectionMySql;
import constant.LocalTimeCustom;
import constant.StatusFileLog;
import constant.StringConstant;
import dao.FileConfigDAO;
import dao.FileLogDAO;
import entity.FileConfig;
import entity.FileLog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class script1 {
    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();

//        Access control database to get data config
        ConnectionMySql controlDatabase = new ConnectionMySql(StringConstant.CONTROL_DATABASE, "root", "12345678");
        Connection connection = controlDatabase.getConnection();

        FileConfigDAO fileConfigDAO = new FileConfigDAO(connection);
        FileConfig fileConfig = fileConfigDAO.findById(1);

//        Create Timer
        String[] split = LocalTimeCustom.createTimer();
        String date = split[0];
        String time = split[1];

//        Create File Log
        FileLogDAO fileLogDAO = new FileLogDAO(connection);
        FileLog fileLog = new FileLog(fileConfig.getId(), date, time, StringConstant.AUTHOR);
        fileLogDAO.save(fileLog);

        System.out.printf("Starting crawl data from %s ..... \n", fileConfig.getSrcLoad());
        try {
            String fileName = CrawlData.run(fileConfig.getSrcLoad());
//          Crawl data successful
            fileLog.setFileName(fileName);
            fileLog.setStatus(StatusFileLog.ER);
            System.out.println("Crawl data successful");
        } catch (IOException e) {
//          Crawl data failed
            fileLog.setStatus(StatusFileLog.ERR);
            System.out.println("Craw data failed");
        }
//        Update file log
        fileLogDAO.save(fileLog);

        long end = System.currentTimeMillis();
        System.out.println("Done: " + (end - start) + "ms");

    }


}
