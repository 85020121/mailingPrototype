package com.hesong.mailEngine.pop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.log4j.Logger;

import com.hesong.factories.PropertiesFactory;
import com.hesong.mail.model.Email;
import com.hesong.mail.model.Mail;
import com.hesong.mailEngine.tools.RegExp;
import com.sun.mail.pop3.POP3Folder;

public class POP3 {

    public static String INBOX = "INBOX";
    public static String POP3 = "pop3";
    public static String TEXT_HTML_CONTENT = "text/html";
    public static String TEXT_PLAIN_CONTENT = "text/plain";
    public static String MULTIPART = "multipart/*";
    public static String OCTET_STREAM = "application/octet-stream";

    public static Logger log = Logger.getLogger(POP3.getClass());

    static SimpleDateFormat sdf_receive = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
    static SimpleDateFormat sdf_today = new SimpleDateFormat("yyyy-MM-dd");


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
            log.info("Close inbox.");
            inbox.close(true);
        }
        if (s != null) {
            log.info("Close store.");
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
            ArrayList<String> uidList) throws MessagingException, IOException {

        Store store = POP3connection(email);
        POP3Folder inbox = getPOP3Inbox(store);
        
        log.info("Inbox count: "+getInboxCount(inbox));

        // All messages in Inbox
        Message[] messages = inbox.getMessages();

        List<Mail> mails = new ArrayList<Mail>();

        for (int i = 0; i < messages.length; i++) {
            log.info("*************************** POP3 Begin ***************************");
            Message msg = messages[i];
            String uid = inbox.getUID(msg);
            if (uidList.contains(uid))
                continue; // Message already downloaded, jump
            uidList.add(uid);
            Mail mail = new Mail();
            mail.setUid(uid);

            mail.setSender(RegExp.emailAdressMatcher(msg.getFrom()[0]
                    .toString()));
            mail.setReceiver(email.getAccount());
            mail.setSubject(msg.getSubject());
            mail.setSentDate(msg.getSentDate());
            mail.setSize(msg.getSize());
            
            log.info("From: "+mail.getSender());
            log.info("To: "+mail.getReceiver());
            log.info("Subject: "+mail.getSubject());
            log.info("Date: "+sdf_receive.format(mail.getSentDate()));
            log.info("Size: "+mail.getSize());

            Object content = msg.getContent();
            if (content instanceof MimeMultipart) {
                parseMultipartByMimeType((MimeMultipart) content, mail);
            }

            mails.add(mail);

        }
        
        log.info("*************************** POP3 End ***************************");        

        closePOP3connection(store, inbox);
        return mails;
    }

    public static void parseMultipartByMimeType(Multipart multipart, Mail mail)
            throws MessagingException, IOException {
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType(TEXT_HTML_CONTENT)) {
                // Save text/html content
                mail.setContent((String) bodyPart.getContent());
                log.info("Content: "+mail.getContent());
            } else if (bodyPart.isMimeType(TEXT_PLAIN_CONTENT)) {
                // TO DO
            } else if (bodyPart.isMimeType(MULTIPART)) {
                parseMultipartByMimeType((Multipart) bodyPart.getContent(),
                        mail);
            } else if (bodyPart.isMimeType(OCTET_STREAM)) {
                // TO DO
                String disposition = bodyPart.getDisposition();
                if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
                    String fileName = bodyPart.getFileName();
                    InputStream is = bodyPart.getInputStream();
                    String dirName = String.format(
                            "D:\\Attachment_for_Client\\%s\\%s\\%s\\%s_%s\\",sdf_today.format(new Date()),
                            mail.getReceiver(), mail.getSender(),
                            sdf_receive.format(mail.getSentDate()), mail.getUid());
                    new File(dirName).mkdirs();
                    log.info("Attachment dir = " + dirName);
                    copy(is, new FileOutputStream(dirName + fileName));
                }
            }
        }

    }

    /**
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
     * 
     * @param is
     * @param os
     * @throws IOException
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
        }
        if (os != null)
            os.close();
        if (is != null)
            is.close();
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
