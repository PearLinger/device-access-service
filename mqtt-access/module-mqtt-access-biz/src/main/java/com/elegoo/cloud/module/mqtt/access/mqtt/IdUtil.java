package com.elegoo.cloud.module.mqtt.access.mqtt;

import java.util.Random;
import java.util.UUID;

public class IdUtil {




    public static String fastUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    /**
     * <p>获取短的uuid(16位)</p>
     *
     * @return
     */
    public static String generateShortUuid() {
        return generateShortUuid(16);
    }

    /**
     * 生成14位uuid
     *
     * @return
     */
    public static String generateShortUuid14() {
        return generateShortUuid(14);
    }

    /**
     * 生成 n 位的 uuid
     *
     * @return
     */
    public static String generateShortUuid(int num) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < num; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    /**
     * 生成n位数的随机数字
     *
     * @param n
     * @return
     */
    public static String randomNumber(int n) {
        Random r = new Random();
        //分配一个空字符内存
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 50; i++) {
            System.out.println(generateShortUuid());
        }
    }

}
