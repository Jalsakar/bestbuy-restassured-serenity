package com.bestbuy.utils;

import java.util.Random;

public class TestUtils {
    public static String getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        return Integer.toString(randomInt);
    }

    public static String getRandomName(){
        String saltChars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder name = new StringBuilder();
        Random random = new Random();
        while (name.length() < 10) {
            int index = (int) (random.nextFloat() * saltChars.length());
            name.append(saltChars.charAt(index));
        }
        String saltStr = (name.toString());
        return saltStr;
    }

    /**
     * This method will generate random string
     */
    public static String getRandomString() {
        int length = 7;
        StringBuilder sb = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
