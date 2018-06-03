package com.ashstr.common.redis.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author keven
 * @date 2018-06-03 上午9:46
 * @Description
 */
public class Md5Utils {

    /**
     * Md5 加密
     *
     * @param str
     * @return
     */
    public static String Md5(String str) {

        String md5Result = null;

        if(StringUtils.isBlank(str)) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            md5Result = buf.toString();

        } catch (NoSuchAlgorithmException e) {
           e.getMessage();
        }
        return md5Result;
    }

}
