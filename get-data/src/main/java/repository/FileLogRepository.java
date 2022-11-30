package repository;

import constant.QUERY;
import constant.StatusFileLog;
import entity.control.FileLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileLogRepository implements BaseRepository<FileLog> {
    private Connection connection;

    public FileLogRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FileLog> findAll() {
        List<FileLog> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.FILE_LOG.FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int fileConfigId = rs.getInt("file_config_id");
                String fileName = rs.getString("file_name");
                String fileDate = rs.getString("file_date");
                String time = rs.getString("time");
                StatusFileLog status = StatusFileLog.valueOf(rs.getString("status"));
                String author = rs.getString("author");
                FileLog fileLog = new FileLog(id, fileConfigId, fileName, fileDate, time, status, author);
                list.add(fileLog);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public FileLog findById(int id) {
        try {
            CallableStatement cs = connection.prepareCall(QUERY.FILE_LOG.FIND_BY_ID);
            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                int fileConfigId = rs.getInt("file_config_id");
                String fileName = rs.getString("file_name");
                String fileDate = rs.getString("file_date");
                String time = rs.getString("time");
                StatusFileLog status = StatusFileLog.valueOf(rs.getString("status"));
                String author = rs.getString("author");
                return new FileLog(id, fileConfigId, fileName, fileDate, time, status, author);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public void save(FileLog fileLog) {
        try {
            PreparedStatement statement = connection.prepareStatement(fileLog.getId() == 0 ? QUERY.FILE_LOG.SAVE : QUERY.FILE_LOG.UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, fileLog.getFileConfigId());
            statement.setString(2, fileLog.getFileName());
            statement.setString(3, fileLog.getFileDate());
            statement.setString(4, fileLog.getTime());
            statement.setString(5, fileLog.getAuthor());
            if (fileLog.getId() != 0) {
                statement.setString(6, fileLog.getStatus().toString());
                statement.setInt(7, fileLog.getId());
            }
            statement.executeUpdate();
            if (fileLog.getId() == 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        fileLog.setId(generatedKeys.getInt(1));
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

    public List<FileLog> findAllByStatus(StatusFileLog status) {
        List<FileLog> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.FILE_LOG.FIND_ALL_BY_FILE_STATUS);
            statement.setString(1, status.toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return list;
            if (rs.next()) {
                int id = rs.getInt("id");
                int fileConfigId = rs.getInt("file_config_id");
                String fileName = rs.getString("file_name");
                String fileDate = rs.getString("file_date");
                String time = rs.getString("time");
                String author = rs.getString("author");
                list.add(new FileLog(id, fileConfigId, fileName, fileDate, time, status, author));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return list;
        }
        return list;
    }

    public List<FileLog> findAllByDateAndStatus(String date, StatusFileLog status) {
        List<FileLog> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.FILE_LOG.FIND_ALL_BY_DATE_AND_STATUS);
            statement.setString(1, date);
            statement.setString(2, status.toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return list;
            while (rs.next()) {
                int id = rs.getInt("id");
                int fileConfigId = rs.getInt("file_config_id");
                String fileName = rs.getString("file_name");
                String fileDate = rs.getString("file_date");
                String time = rs.getString("time");
                String author = rs.getString("author");
                list.add(new FileLog(id, fileConfigId, fileName, fileDate, time, status, author));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return list;
        }
        return list;
    }

}
