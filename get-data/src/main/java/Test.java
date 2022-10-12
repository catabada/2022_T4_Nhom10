import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("103.97.126.30");
        ftpClient.login("huuan@huuan.tk", "12345678");
        int code = ftpClient.getReplyCode();
        System.out.println(code);
    }
}
