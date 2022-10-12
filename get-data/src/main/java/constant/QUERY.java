package constant;

public class QUERY {
    public static class FILE_CONFIG {
        public static String FIND_ALL = "select * from file_config";
        public static String FIND_BY_ID = "select * from file_config where id = ?";
    }

    public static class FILE_LOG {
        public static String FIND_ALL = "select * from file_log";
        public static String FIND_BY_ID = "select * from file_log where id = ?";
        public static String SAVE = "insert into file_log(file_config_id, file_name, file_date, time, author) values (?, ?, ?, ?, ?)";
        public static String DELETE = "delete from file_log where id = ?";
        public static final String UPDATE = "update file_log set `file_config_id` = ?, `file_name` = ?, `file_date` = ?, `time` = ?, `author` = ?, `status` = ? where (`id` = ?)";
        public static String FIND_ALL_BY_FILE_STATUS = "select * from file_log where status = ?";
    }
}
