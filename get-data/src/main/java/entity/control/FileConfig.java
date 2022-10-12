package entity.control;

import java.io.Serializable;

public class FileConfig implements Serializable {
    private int id;
    private String srcName;
    private String srcLoad;
    private String ip;
    private int port;
    private String username;
    private String password;

    public FileConfig(int id, String srcName, String srcLoad, String ip, int port, String username, String password) {
        this.id = id;
        this.srcName = srcName;
        this.srcLoad = srcLoad;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public FileConfig() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getSrcLoad() {
        return srcLoad;
    }

    public void setSrcLoad(String srcLoad) {
        this.srcLoad = srcLoad;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
