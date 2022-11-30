package repository;

import constant.QUERY;
import entity.control.FileConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileConfigRepository implements BaseRepository<FileConfig> {
    private Connection connection;

    public FileConfigRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FileConfig> findAll() {
        List<FileConfig> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(QUERY.FILE_CONFIG.FIND_ALL);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String srcName = rs.getString("srcName");
                String srcLoad = rs.getString("srcLoad");
                String ip = rs.getString("ip");
                int port = rs.getInt("port");
                String username = rs.getString("username");
                String password = rs.getString("password");
                FileConfig fileConfig = new FileConfig(id, srcName, srcLoad, ip, port, username, password);
                list.add(fileConfig);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public FileConfig findById(int id) {
        try {
            CallableStatement cs = connection.prepareCall(QUERY.FILE_CONFIG.FIND_BY_ID);
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String srcName = rs.getString("src_name");
                String srcLoad = rs.getString("src_load");
                String ip = rs.getString("ip");
                int port = rs.getInt("port");
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new FileConfig(id, srcName, srcLoad, ip, port, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public void save(FileConfig fileConfig) {
    }

    @Override
    public void delete(int id) {

    }
}
