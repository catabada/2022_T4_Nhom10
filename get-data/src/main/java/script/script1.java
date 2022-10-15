package script;

import connection.ConnectionMySql;
import constant.CustomFunction;
import constant.StatusFileLog;
import constant.StringConstant;
import repository.FileConfigRepository;
import repository.FileLogRepository;
import entity.control.FileConfig;
import entity.control.FileLog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class script1 {
    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();

//      Access control database to get data config
        ConnectionMySql controlDatabase = new ConnectionMySql(StringConstant.CONTROL_DATABASE);
        Connection connection = controlDatabase.getConnection();

//      Initialization Repository
        FileConfigRepository fileConfigRepository = new FileConfigRepository(connection);
        FileLogRepository fileLogRepository = new FileLogRepository(connection);

//      Check condition
        if (fileLogRepository.findAllByStatus(StatusFileLog.ES).isEmpty()) {
//          Get record from control database
            FileConfig fileConfig = fileConfigRepository.findById(1);

//          Create Timer
            String[] split = CustomFunction.createTimer();
            String date = split[0];
            String time = split[1];

//          Create File Log
            FileLog fileLog = new FileLog(fileConfig.getId(), date, time, StringConstant.AUTHOR);
            fileLogRepository.save(fileLog);

            System.out.printf("Starting crawl data from %s ..... \n", fileConfig.getSrcLoad());
            try {
                String fileName = CrawlData.run(fileConfig.getSrcLoad());
//              Crawl data successful
                fileLog.setFileName(fileName);
                fileLog.setStatus(StatusFileLog.ER);
                System.out.println("Crawl data successful");

//              Access ftp server
//                System.out.println("Connect ftp server .....");
//                ConnectFTPServer connectFTPServer = new ConnectFTPServer(fileConfig.getIp(), fileConfig.getPort(), fileConfig.getUsername(), fileConfig.getPassword());
//                connectFTPServer.connect();
//                connectFTPServer.sendFileToFtpServer(fileLog.getFileName());
            } catch (IOException e) {
//              Crawl data failed
                System.err.println("Crawl data failed: " + e.getMessage());
                fileLog.setStatus(StatusFileLog.ERR);
                throw new RuntimeException(e);
            }

//          Update file log
            fileLogRepository.save(fileLog);
        }

        connection.close();

        long end = System.currentTimeMillis();
        System.out.println("Done: " + (end - start) + "ms");

    }


}
