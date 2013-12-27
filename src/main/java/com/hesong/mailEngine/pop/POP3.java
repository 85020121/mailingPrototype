package com.hesong.mailEngine.pop;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.net.ftp.FTPClient;

import com.hesong.factories.PropertiesFactory;
import com.hesong.mail.model.Email;
import com.hesong.mail.model.Mail;
import com.hesong.mailEngine.ftp.POP3FTPclient;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.mailEngine.tools.RegExp;
import com.sun.mail.pop3.POP3Folder;

public class POP3 {

    public static String INBOX = "INBOX";
    public static String POP3 = "pop3";
    public static String TEXT_HTML_CONTENT = "text/html";
    public static String TEXT_PLAIN_CONTENT = "text/plain";
    public static String MULTIPART = "multipart/*";
    public static String OCTET_STREAM = "application/octet-stream";

    public static SimpleDateFormat sdf_receive = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss");
    public static SimpleDateFormat sdf_today = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static Store POP3connection(Email e) throws MessagingException {

        Session session = Session.getDefaultInstance(PropertiesFactory
                .POP3Factory(e));
        Store store = session.getStore(POP3);
        store.connect(e.getAccount(), e.getPassword());
        return store;
    }

    public static POP3Folder getPOP3Inbox(Store s) throws MessagingException {
        POP3Folder inbox = (POP3Folder) s.getFolder(INBOX);
        inbox.open(POP3Folder.READ_WRITE);
        return inbox;
    }

    public static void closePOP3connection(Store s, POP3Folder inbox)
            throws MessagingException {
        if (inbox != null) {
            MailingLogger.log.info("Close inbox.");
            inbox.close(true);
        }
        if (s != null) {
            MailingLogger.log.info("Close store.");
            s.close();
        }
    }

    /**
     * 返回收件箱中邮件总数，并关闭回话
     * 
     * @param email
     *            邮件账户
     * @return 收件箱中邮件数量
     * @throws MessagingException
     */
    public static int getInboxCount(Email email) throws MessagingException {
        Store s = POP3connection(email);
        POP3Folder inbox = getPOP3Inbox(s);
        int count = inbox.getMessageCount();
        closePOP3connection(s, inbox);
        return count;
    }

    /**
     * 返回收件箱中邮件总数，不断开会话
     * 
     * @param inbox
     *            POP3收件箱
     * @return 收件箱中邮件数量
     * @throws MessagingException
     */
    public static int getInboxCount(POP3Folder inbox) throws MessagingException {

        return inbox.getMessageCount();
    }

    public static List<Mail> getInboxMessages(Email email,
            List<String> uidList, FTPClient ftp) throws MessagingException, IOException {

        Store store = POP3connection(email);
        POP3Folder inbox = getPOP3Inbox(store);

        MailingLogger.log.info("Inbox count: " + getInboxCount(inbox));

        // All messages in Inbox
        Message[] messages = inbox.getMessages();

        List<Mail> mails = new ArrayList<Mail>();

        for (int i = 0; i < messages.length; i++) {
            MailingLogger.log
                    .info("*************************** POP3 Begin ***************************");
            Message msg = messages[i];
            String uid = inbox.getUID(msg);
            if (uidList.contains(uid))
                continue; // Message already downloaded, jump
            uidList.add(uid);
            Mail mail = new Mail();
            mail.setUid(uid);
            mail.setUnitID("1");
            mail.setSender(RegExp.emailAdressMatcher(msg.getFrom()[0]
                    .toString()));
            mail.setReceiver(email.getAccount());
            // log.info("SUBJECT IS:"+msg.getSubject());
            mail.setSubject(msg.getSubject());
            mail.setSentDate(msg.getSentDate());
            mail.setSize(msg.getSize());

            MailingLogger.log.info("From: " + mail.getSender());
            MailingLogger.log.info("To: " + mail.getReceiver());
            MailingLogger.log.info("Subject: " + mail.getSubject());
            MailingLogger.log.info("Date: "
                    + sdf_receive.format(mail.getSentDate()));
            MailingLogger.log.info("Size: " + mail.getSize());

            Object content = msg.getContent();
            if (content instanceof MimeMultipart) {
                parseMultipartByMimeType((MimeMultipart) content, mail, ftp);
            }

            mails.add(mail);

        }

        MailingLogger.log
                .info("*************************** POP3 End ***************************");

        closePOP3connection(store, inbox);
        return mails;
    }

    public static void parseMultipartByMimeType(Multipart multipart, Mail mail, FTPClient ftp)
            throws MessagingException, IOException {
        MailingLogger.log.info("MULTIPART COUNT = " + multipart.getCount());
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            MailingLogger.log.info("MIMETYPE IS :" + bodyPart.getContentType()
                    + " in part" + i);
            MailingLogger.log.info("DESCRIPTION: " + bodyPart.getDescription());
            String disposition = bodyPart.getDisposition();
            MailingLogger.log.info("DISPOSITION: " + disposition);
            if (disposition != null
                    && disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
                MailingLogger.log.info("This is a attachment");

                String fileName = decodeText(bodyPart.getFileName());
                InputStream is = bodyPart.getInputStream();
                
                String ftpDirName = String.format(
                        "/Attachment_for_Client/%s/%s/%s/%s/",
                        sdf_today.format(new Date()), mail.getReceiver(),
                        mail.getSender(),
                        sdf_receive.format(mail.getSentDate()));

                // FTP UPLOAD
                if (POP3FTPclient.uploadFile(ftp, ftpDirName, fileName, is)) {
                    MailingLogger.log
                            .info("FFFFFFFFFFFFFTTTTTTTTTTTTTTTTPPPPPPPPPPPPP SUCCESS");
                }
                
//                String dirName = String.format(
//                        "D:\\Attachment_for_Client\\%s\\%s\\%s\\%s\\",
//                        sdf_today.format(new Date()), mail.getReceiver(),
//                        mail.getSender(),
//                        sdf_receive.format(mail.getSentDate()));
//                File folder = new File(dirName);
//
//                if (folder.exists() || folder.mkdirs()) {
//                    MailingLogger.log.info("Attachment dir = " + dirName);
//                    AttachmentPuller.copy(is, new FileOutputStream(dirName
//                            + fileName));
//                } else {
//                    MailingLogger.log.info("Make dir failed: " + dirName);
//                }
            } else if (bodyPart.isMimeType(TEXT_HTML_CONTENT)) {
                // Save text/html content
                mail.setContent((String) bodyPart.getContent());
                MailingLogger.log.info("Content: " + mail.getContent());
            } else if (bodyPart.isMimeType(TEXT_PLAIN_CONTENT)) {
                // TO DO

            } else if (bodyPart.isMimeType(MULTIPART)) {
                parseMultipartByMimeType((Multipart) bodyPart.getContent(),
                        mail, ftp);
            }
        }

    }

    protected static String decodeText(String text)
            throws UnsupportedEncodingException {
        if (text == null)
            return null;
        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
            text = MimeUtility.decodeText(text);
        else
            text = new String(text.getBytes("ISO8859_1"));
        return text;
    }

}
