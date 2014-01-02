package com.hesong.mailEngine.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.hesong.mailEngine.ftp.factories.FTPConnectionFactory;
import com.hesong.mailEngine.tools.AttachmentPuller;
import com.hesong.mailEngine.tools.MailingLogger;

public class POP3FTPclient {

    public static void disconnect(FTPClient ftp) throws IOException {
        ftp.logout();
        ftp.disconnect();
    }

    public static boolean mkdir(FTPClient ftp, String path) throws IOException {
        if (!ftp.changeWorkingDirectory(path)) {
            if (FTPReply.isPositiveCompletion(ftp.mkd(path))) {
                ftp.changeWorkingDirectory(path);
            } else {
                MailingLogger.log.info("Make dir failed with path: " + path);
                return false;
            }
        }

        MailingLogger.log.info("Destination directory: "
                + ftp.printWorkingDirectory());
        return true;
    }

    /**
     * 
     * @param ftp
     *            FTP链接
     * @param path
     *            目标文件夹路径
     * @param filename
     *            待上传文件名
     * @param input
     *            输入流
     * @return True：上传成功， False：失败
     * @throws IOException
     */
    public static boolean uploadFile(FTPClient ftp, String path,
            String filename, InputStream input) throws IOException {
        if (!mkdir(ftp, path)) {
            return false;
        }
        OutputStream output = ftp.storeFileStream(filename);
        AttachmentPuller.copy(input, output);

        if (!ftp.completePendingCommand()) {
            MailingLogger.log.info("File transfer failed.");
            // TO DO
            // ftp.logout();
            // ftp.disconnect();
            // System.exit(1);
        }
        return true;
    }

    public static boolean uploadFile(String url, int port, String username,
            String password, String path, String filename, InputStream input)
            throws IOException {
        FTPClient ftp = FTPConnectionFactory.getFTPClientConnection(url, port,
                username, password);
        if (ftp == null)
            return false;
        boolean success = uploadFile(ftp, path, filename, input);
        disconnect(ftp);
        return success;
    }

}
