package se.piro.advent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rolf Staflin 2016-12-14 09:41
 */
public class Day14 extends Day {


    private static final String HEX_STRING = "0123456789abcdef";
    private static final char[] HEX_CHARS = HEX_STRING.toCharArray();

    private static final int KEY_LIMIT = 64;
    String salt;
    long index;

    ArrayList<LinkedList<Key>> waitingLists = new ArrayList<>();

    List<Key> keys = new ArrayList<>(64);

    MessageDigest messageDigest;

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Day14 day = new Day14("ahsbgdzn"); // "abc"
        day.go();
        print("------------------");
        Day14 part2 = new Day14("ahsbgdzn"); // "abc"
        part2.goPart2();
    }

    public Day14(String salt) throws NoSuchAlgorithmException {
        this.salt = salt;
        index = 0;
        messageDigest = MessageDigest.getInstance("MD5");
        for (int i = 0; i < 16; i++) {
            waitingLists.add(new LinkedList<>());
        }
    }

    private void go() throws UnsupportedEncodingException {
        while (keys.size() < KEY_LIMIT) {
            getKey(index);
            index++;
        }
    }

    private void goPart2() throws UnsupportedEncodingException {
        while (keys.size() < KEY_LIMIT) {
            getStretchedKey(index);
            index++;
        }
    }

    private Key getKey(long index) throws UnsupportedEncodingException {
        String message = salt + Long.toString(index);
        byte[] bytesOfMessage = message.getBytes("UTF-8");
        byte[] theDigest = messageDigest.digest(bytesOfMessage);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : theDigest) {
            stringBuilder.append(HEX_CHARS[(b & 0xf0) >> 4])
                    .append(HEX_CHARS[b & 0xf]);
        }

        return new Key(stringBuilder.toString(), index);
    }

    private Key getStretchedKey(long index) throws UnsupportedEncodingException {
        String message = salt + Long.toString(index);
        for (int i = 0; i < 2017; i++) {
            byte[] bytesOfMessage = message.getBytes("UTF-8");
            byte[] theDigest = messageDigest.digest(bytesOfMessage);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : theDigest) {
                stringBuilder.append(HEX_CHARS[(b & 0xf0) >> 4])
                        .append(HEX_CHARS[b & 0xf]);
            }
            message = stringBuilder.toString();
        }

        return new Key(message, index);
    }

    class Key {
        String key;
        long index;

        public Key(String key, long index) {
            this.key = key;
            this.index = index;

            int firstTripletPos = Integer.MAX_VALUE;
            char tripletChar = ' ';
            for (char c : HEX_CHARS) {
                String quintupletString = "" + c + c + c + c + c;
                if (key.contains(quintupletString)) {
                    LinkedList<Key> waitingList = waitingLists.get(HEX_STRING.indexOf(c));
                    for (Key waitingKey : waitingList) {
                        if (waitingKey.index > index - 1000 && !keys.contains(waitingKey)) {
                            keys.add(waitingKey);
                            if (keys.size() <= KEY_LIMIT) {
                                print("Key " + keys.size() + ": " + waitingKey + " at index " + waitingKey.index + " (added because of key " + index + " = " + key + ")");
                            }
                        }
                    }
                }

                String tripletString = "" + c + c + c;
                int position = key.indexOf(tripletString);
                if (position >= 0 && position < firstTripletPos) {
                    tripletChar = c;
                    firstTripletPos = position;
                }
            }
            if (tripletChar != ' ') {
                LinkedList<Key> waitingList = waitingLists.get(HEX_STRING.indexOf(tripletChar));
                waitingList.add(this);
            }
        }

        @Override
        public String toString() {
            return key;
        }
    }
}