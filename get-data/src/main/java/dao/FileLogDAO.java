package dao;

import constant.QUERY;
import constant.StatusFileLog;
import entity.FileConfig;
import entity.FileLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileLogDAO implements BaseDAO<FileLog> {
    private Connection connection;

    public FileLogDAO(Connection connection) {
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
            PreparedStatement statement = connection.prepareStatement(QUERY.FILE_LOG.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
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
}
