package connection;

import constant.StringConstant;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class ConnectFTPServer {
    private FTPClient ftpClient;
    private String ip;
    private int port;
    private String username;
    private String password;

    private static ConnectFTPServer instance;

    public static ConnectFTPServer getInstance(String ip, int port, String username, String password) {
        if (instance == null) {
            instance = new ConnectFTPServer(ip, port, username, password);
        }
        return instance;
    }

    private ConnectFTPServer(String ip, int port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        this.ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip, port);
            boolean isLogin = ftpClient.login(username, password);
            if (isLogin) System.out.println("Login success");
            else System.out.println("Login failed");
            System.out.println("Connect ftp success");
        } catch (IOException e) {
            System.err.println("Connect ftp failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void reconnect() throws IOException {
        this.ftpClient.logout();
        this.ftpClient.disconnect();
    }

    public void sendFileToFtpServer(String fileName) {
        try {

            this.ftpClient.enterLocalPassiveMode();

            this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(StringConstant.FOLDER_PATH_LOCAL + "/" + fileName);

            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Start uploading first file");
            boolean done = this.ftpClient.storeFile(fileName, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("The first file is uploaded using FTP successfully.");
            }
            System.out.println(done);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                if (this.ftpClient.isConnected())
                    reconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void downloadFileFromFtpServer(String fileName) {
        try {

            this.ftpClient.enterLocalPassiveMode();

            this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(StringConstant.FOLDER_PATH_LOCAL + "/" + fileName);

            OutputStream outputStream = new FileOutputStream(localFile);

            System.out.println("Start downloading first file");
            boolean done = this.ftpClient.retrieveFile(fileName, outputStream);
            outputStream.close();

            if (done) {
                System.out.println("The first file is downloaded using FTP successfully.");
            }
            System.out.println(done);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                if (this.ftpClient.isConnected())
                    reconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        ConnectFTPServer connectFTPServer = new ConnectFTPServer("103.97.126.30", 21, "huuan@huuan.tk", "12345678");
        connectFTPServer.connect();
        connectFTPServer.downloadFileFromFtpServer("1668668301436.csv");

//        System.out.println(new File("E:\\Study\\Java\\weather-data\\1665665628178.csv").exists());

    }
}
