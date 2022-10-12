package connection;

import constant.StringConstant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMySql {
    private String username;
    private String password;
    private String database;

    public ConnectionMySql(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {

        String url = StringConstant.URL.concat(database);
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection != null) {
            System.out.println("Access successful control database");
        } else {
            System.out.println("Access failed control database");
        }
        return connection;
    }
}
