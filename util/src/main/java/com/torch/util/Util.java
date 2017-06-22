package com.torch.util;

import com.google.common.base.Strings;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {
    private static final int HASH_ITERATIONS = 1000;
    public static final String EMPTY_STRING = "";
    private static final int HASH_KEY_LENGTH = 192;
    private static final int DEFAULT_SALT_SIZE = 32;

    public static boolean isEmpty(String source) {
        return (source == null || "".equals(source)) ? true : false;
    }

    private static String generateSalt() {
        Random r = new SecureRandom();
        byte[] saltBinary = new byte[DEFAULT_SALT_SIZE];
        r.nextBytes(saltBinary);
        return Base64.encodeBase64String(saltBinary);
    }

    private static String hashPasswordAddingSalt(String password, byte[] salt) {
        if (isEmpty(password)) {
            return EMPTY_STRING;
        }
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, HASH_ITERATIONS, HASH_KEY_LENGTH)
            );
            return Base64.encodeBase64String(key.getEncoded());
        } catch (Exception e) {
            return EMPTY_STRING;
        }
    }

    public static String hashPasswordAddingSalt(String password) {
        byte[] salt = generateSalt().getBytes();
        return Base64.encodeBase64String(salt) + '$' + hashPasswordAddingSalt(password, salt);
    }

    public static boolean isValidPassword(String password, String hashedPassword) {
        String[] saltAndPass = hashedPassword.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hashPasswordAddingSalt(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    public static Util shaPasswordEncoder() {
        return new Util();
    }

    public static boolean isPhoneFormatterError(String phone) {
        return Strings.isNullOrEmpty(phone);
    }

    public static Date formatterDateStringToDate(String dateString) {
        if(Strings.isNullOrEmpty(dateString)) return new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString.replaceAll("/","-"));
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatterDateToString(Date date) {
        if(date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String dateString = sdf.format(date);
        return dateString.trim();
    }

    public static String formatterDateStringToYYYYMMDD(String dateString) {
        if(Strings.isNullOrEmpty(dateString)) return formatterDateToString(new Date());
        String date = dateString.substring(0,10);
        return date;
    }

    public static boolean stringIsEquals(String strA, String strB) {
        if(strA == null) {
            strA = "";
        }
        if(strB == null) {
            strB = "";
        }
        return strA.equals(strB);
    }

    public static boolean stringIsNotEquals(String strA, String strB) {
        return !stringIsEquals(strA, strB);
    }

    public static boolean integerIsEquals(Integer inA, Integer inB) {
        if(inA == null) {
            inA = 0;
        }
        if(inB == null) {
            inB = 0;
        }
        return inA.equals(inB);
    }

    public static boolean integerIsNotEquals(Integer inA, Integer inB) {
        return !integerIsEquals(inA, inB);
    }

    public static boolean doubleIsEquals(Double dbA, Double dbB) {
        if(dbA == null) {
            dbA = 0d;
        }
        if(dbB == null) {
            dbB = 0d;
        }
        return dbA.equals(dbB);
    }

    public static boolean doubleIsNotEquals(Double dbA, Double dbB) {
        return !doubleIsEquals(dbA, dbB);
    }

    public static boolean shortIsEquals(Short inA, Short inB) {
        if(inA == null) {
            inA = 0;
        }
        if(inB == null) {
            inB = 0;
        }
        return inA == inB;
    }

    public static boolean shortIsNotEquals(Short inA, Short inB) {
        return !shortIsEquals(inA, inB);
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value == 0) ? true : false;
    }

}
