package com.hesong.mailEngine.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    static String NONE = "";
    static String FROM_ADDRESS_PARTTEN = "<(.+)>";

    /**
     * 
     * @param s
     *            包含了邮件发送者地址的字符串：.+<xxx@xxx.xxx>.+
     * @return 邮箱地址：xxx@xxx.xxx
     */
    public static String emailAdressMatcher(String s) {
        Pattern p = Pattern.compile(FROM_ADDRESS_PARTTEN);
        Matcher m = p.matcher(s);
        if (m.find())
            return m.group(1);
        return NONE;
    }

}
