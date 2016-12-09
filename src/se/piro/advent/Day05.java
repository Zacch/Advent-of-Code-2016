package se.piro.advent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Rolf Staflin 2016-12-06 22:07
 */
public class Day05 {

    private static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Day05 day05 = new Day05();
        day05.go();
    }

    String input = "uqwqemis";
    String password = "........";

    public void go() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int i = 0;
        while (password.contains(".")) {
            test(input + Integer.toString(i++));
        }
        print("Done! The password is " + password);
    }

    public boolean test(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    byte[] bytesOfMessage = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfMessage);
        if (theDigest[0] == 0 && theDigest[1] == 0 && (theDigest[2] & 0xf0) == 0) {
            int position = theDigest[2] & 0x0f;
            if (position > 7 || password.charAt(position) != '.') {
                print(" False positive for " + data + ". position = " + position + ", password = " + password );
                return false;
            }
            String letter = Integer.toHexString((theDigest[3] & 0xf0) >> 4);
            print("String " + data + " matches! Position " + position + ", letter " + letter);
            password = password.substring(0, position) + letter + password.substring(position + 1);
            print(password);
            return true;
        };
        return false;
    }
}
