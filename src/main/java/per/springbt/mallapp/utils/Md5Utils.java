package per.springbt.mallapp.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import per.springbt.mallapp.common.Constant;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public static String getMDStr(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        return Base64.encodeBase64String(md5.digest((strValue+Constant.SALT).getBytes(StandardCharsets.UTF_8))) ;

    }

}