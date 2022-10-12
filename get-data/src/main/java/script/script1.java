package script;

import connection.ConnectionMySql;
import constant.CustomFunction;
import constant.StatusFileLog;
import constant.StringConstant;
import dao.FileConfigDAO;
import dao.FileLogDAO;
import entity.control.FileConfig;
import entity.control.FileLog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class script1 {
    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();

//      Access control database to get data config
        ConnectionMySql controlDatabase = new ConnectionMySql(StringConstant.CONTROL_DATABASE, "root", "12345678");
        Connection connection = controlDatabase.getConnection();

//      Initialization DAO
        FileConfigDAO fileConfigDAO = new FileConfigDAO(connection);
        FileLogDAO fileLogDAO = new FileLogDAO(connection);

//      Check condition
        if (fileLogDAO.findAllByStatus(StatusFileLog.ES).isEmpty()) {
//          Get record from control database
            FileConfig fileConfig = fileConfigDAO.findById(1);

//          Create Timer
            String[] split = CustomFunction.createTimer();
            String date = split[0];
            String time = split[1];

//          Create File Log
            FileLog fileLog = new FileLog(fileConfig.getId(), date, time, StringConstant.AUTHOR);
            fileLogDAO.save(fileLog);

            System.out.printf("Starting crawl data from %s ..... \n", fileConfig.getSrcLoad());
            try {
                String fileName = CrawlData.run(fileConfig.getSrcLoad());
//              Crawl data successful
                fileLog.setFileName(fileName);
                fileLog.setStatus(StatusFileLog.ER);
                System.out.println("Crawl data successful");
            } catch (IOException e) {
//              Crawl data failed
                fileLog.setStatus(StatusFileLog.ERR);
                System.out.println("Craw data failed");
            }
//          Update file log
            fileLogDAO.save(fileLog);
        }


        long end = System.currentTimeMillis();
        System.out.println("Done: " + (end - start) + "ms");

    }


}
