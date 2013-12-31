package com.hesong.mailEngine.ftp.factories;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnectionFactory {

    public static String DefaultURL;
    public static String DefualtUsername;
    public static String DefualtPassword;
    public static int DefualtPort = 0;

    public static void initDefualtFTPclientConnection(String url, int port,
            String username, String password) throws IOException {
        DefaultURL = url;
        DefualtUsername = username;
        DefualtPassword = password;
        DefualtPort = port;
    }
    
    public static FTPClient getDefaultFTPConnection() throws IOException{
        if(DefualtPort > 0)
            return getFTPClientConnection(DefaultURL, DefualtPort, DefualtUsername, DefualtPassword);
        else
            return getFTPClientConnection(DefaultURL, DefualtUsername, DefualtPassword);

    }

    public static FTPClient getFTPClientConnection(String url, int port,
            String username, String password) throws IOException {
        FTPClient ftp = new FTPClient();
        int reply;
        ftp.connect(url, port);// 连接FTP服务器
        ftp.login(username, password);// 登录

        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null; // 连接建立失败
        }

        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        // ftp.enterLocalPassiveMode();

        return ftp;
    }

    public static FTPClient getFTPClientConnection(String url, String username,
            String password) throws IOException {
        FTPClient ftp = new FTPClient();
        int reply;
        ftp.connect(url);// 连接FTP服务器
        ftp.login(username, password);// 登录

        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null; // 连接建立失败
        }

        return ftp;
    }
}
