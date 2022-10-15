package constant;

public class QUERY {

    public static final String UPLOAD_FILE_DATABASE = "LOAD DATA INFILE ? INTO TABLE fact " +
            "FIELDS TERMINATED BY  ',' " +
            "ENCLOSED BY ''" +
            "LINES TERMINATED BY '\n' ";
    public static class FILE_CONFIG {
        public static final String FIND_ALL = "select * from file_config";
        public static final String FIND_BY_ID = "select * from file_config where id = ?";
    }

    public static class FILE_LOG {
        public static final String FIND_ALL = "select * from file_log";
        public static final String FIND_BY_ID = "select * from file_log where id = ?";
        public static final String SAVE = "insert into file_log(file_config_id, file_name, file_date, time, author) values (?, ?, ?, ?, ?)";
        public static final String DELETE = "delete from file_log where id = ?";
        public static final String UPDATE = "update file_log set `file_config_id` = ?, `file_name` = ?, `file_date` = ?, `time` = ?, `author` = ?, `status` = ? where (`id` = ?)";
        public static final String FIND_ALL_BY_FILE_STATUS = "select * from file_log where status = ?";
        public static final String FIND_ALL_BY_DATE_AND_STATUS = "select * from file_log where file_date = ? and status = ?";
    }
}
