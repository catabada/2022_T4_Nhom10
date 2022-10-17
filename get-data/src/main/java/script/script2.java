package script;

import connection.ConnectionMySql;
import constant.CustomFunction;
import constant.StatusFileLog;
import constant.StringConstant;
import repository.FileConfigRepository;
import repository.FileLogRepository;
import entity.control.FileLog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class script2 {
    public static void main(String[] args) throws SQLException {
//      Connect control database to get data config
        System.out.println("Connecting control database.......");
        ConnectionMySql controlDatabase = new ConnectionMySql(StringConstant.CONTROL_DATABASE);
        Connection controlConnection = controlDatabase.getConnection();

//      Connect staging database to upload data
        System.out.println("Connecting staging database.......");
        ConnectionMySql stagingDatabase = new ConnectionMySql(StringConstant.STAGING_DATABASE);
        Connection stagingConnection = stagingDatabase.getConnection();

//      Initialization Repository
        FileLogRepository fileLogRepository = new FileLogRepository(controlConnection);

//      Create Timer
        String[] split = CustomFunction.createTimer();
        String date = split[0];
        String time = split[1];

//      Check if there are any file have been crawled data and status = ER today(date now) ?
        List<FileLog> listFileLog = fileLogRepository.findAllByDateAndStatus(date, StatusFileLog.ER);
        FileLog newFileLog = null;
        for (FileLog fileLog : listFileLog) {
            if (CustomFunction.equalsHour(fileLog.getTime(), time)) {
                newFileLog = fileLog;
            }
        }
        if (newFileLog == null) {
//            Run script 1 again or stop
        } else {
//          Retrieve file from ftp or local
            CustomFunction.uploadFileToFactTableStagingDatabase(stagingConnection, newFileLog.getFileName());
            newFileLog.setStatus(StatusFileLog.SU);
            fileLogRepository.save(newFileLog);
        }

    }
}
