package constant;

public class QUERY {

    public static final String UPLOAD_FILE_DATABASE = "LOAD DATA INFILE ? INTO TABLE fact " +
            "FIELDS TERMINATED BY  ',' " +
            "ENCLOSED BY ''" +
            "LINES TERMINATED BY '\n' ";
    public static final String TRUNCATE_DATABASE = "Truncate fact";

    public static final String CREATE_TEMP_TABLE = "CREATE TEMPORARY TABLE `temp` (\n" +
            "  `natural_key` varchar(255) DEFAULT NULL,\n" +
            "  `date_time_id` int  DEFAULT 0,\n" +
            "  `time` varchar(255) DEFAULT NULL,\n" +
            "  `province_id` int DEFAULT 0,\n" +
            "  `temp` float DEFAULT NULL,\n" +
            "  `cloud_description` nvarchar(255) DEFAULT 'Chưa rõ',\n" +
            "  `min_temp` int  DEFAULT -9999,\n" +
            "  `max_temp` int DEFAULT -9999,\n" +
            "  `humidity` double DEFAULT -9999,\n" +
            "  `vision` double DEFAULT -9999,\n" +
            "  `wind_spd` double DEFAULT -9999,\n" +
            "  `uv_index` double DEFAULT -9999,\n" +
            "  `atmosphere_quality` nvarchar(255) DEFAULT 'Chưa rõ'\n" +
            ") as select natural_key,d.id as date_time_id,time,p.id as province_id,temp,cloud_description,min_temp,max_temp,humidity,vision,wind_spd,uv_index,atmosphere_quality \n" +
            "from fact f inner join date_dim d\n" +
            "\t\t\ton f.date = d.date\n" +
            "            inner join province_dim p\n" +
            "            on f.province = p.name;";
    public static final String DROP_TEMP_TABLE = "drop table temp";
    public static final String FIND_ALL_DATA_TEMP_TABLE = "select * from temp";

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

    public static class WEATHER {
        public static final String FIND_ALL = "select * from fact";
        public static final String FIND_ALL_BY_STATUS = "select * from fact where status = ?";
        public static final String FIND_BY_ID = "select * from fact where id = ?";
        public static final String FIND_BY_NATURAL_KEY= "select * from fact where natural_key = ?";
        public static final String DELETE = "delete from file_log where id = ?";
        public static final String SAVE = "INSERT INTO `data-warehouse`.fact\n" +
                "(natural_key,\n" +
                "date_time_id,\n" +
                "time,\n" +
                "province_id,\n" +
                "temp,\n" +
                "cloud_description,\n" +
                "min_temp,\n" +
                "max_temp,\n" +
                "humidity,\n" +
                "vision,\n" +
                "wind_spd,\n" +
                "uv_index,\n" +
                "atmosphere_quality,\n" +
                "status,\n" +
                "expired_date)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        public static final String UPDATE = "UPDATE `data-warehouse`.fact\n" +
                "SET natural_key = ?,\n" +
                "date_time_id = ?,\n" +
                "time = ?,\n" +
                "province_id = ?,\n" +
                "temp = ?,\n" +
                "cloud_description = ?,\n" +
                "min_temp = ?,\n" +
                "max_temp = ?,\n" +
                "humidity = ?,\n" +
                "vision = ?,\n" +
                "wind_spd = ?,\n" +
                "uv_index = ?,\n" +
                "atmosphere_quality = ?,\n" +
                "status = ?,\n" +
                "expired_date = ?\n" +
                "WHERE id = ?;";
    }
}
