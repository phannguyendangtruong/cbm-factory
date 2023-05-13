package com.amitgroup.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class StringGenerator {

    private static final Random random = new Random();
    private static final char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] digits = "0123456789".toCharArray();

    public StringGenerator() {
    }

    public static String randomCharacters(int length) {
        char[] buf = new char[length];

        for(int idx = 0; idx < length; ++idx) {
        buf[idx] = chars[random.nextInt(chars.length)];
        }

        return new String(buf);
        }

    public static String genOtp() {
        return RandomStringUtils.random(6, false, true);
    }

    public static void main(String[] args) {
        char[] var4 = chars;
        int var3 = chars.length;

        for(int var2 = 0; var2 < var3; ++var2) {
            char s = var4[var2];
            System.out.print(s);
        }
    }

    public static String randomDigits(int length) {
        if (length <= 0) {
            return "";
        } else {
            char[] buf = new char[length];

            for(int idx = 0; idx < length; ++idx) {
                buf[idx] = digits[random.nextInt(digits.length)];
            }

            return new String(buf);
        }
    }

}
