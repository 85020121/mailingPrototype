package com.hesong.mailingPrototype;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.quartz.SchedulerException;

import com.hesong.mailEngine.ftp.factories.FTPConnectionFactory;
import com.hesong.quartz.runner.PullingMailRunner;

public class QuartzMain {

    public static void main(String[] args) {
        try {
            FTPConnectionFactory.initDefualtFTPclientConnection(args[0],
                    21, args[1], args[2]);
            FTPClient ftp = FTPConnectionFactory.getDefaultFTPConnection();
            // FTPClient ftp = FTPConnectionFactory.getFTPClientConnection(
            // "127.0.0.1", 21, "bowen", "waiwai");

            new PullingMailRunner().task(ftp);

        } catch (IOException | SchedulerException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

}
