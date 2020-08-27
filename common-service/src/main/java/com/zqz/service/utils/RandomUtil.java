package com.zqz.service.utils;

import java.util.Random;

public class RandomUtil {

    public static Random random = new Random();

    public static String getRandom(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean isChar = (random.nextInt(2) % 2 == 0);// 偶数输出字符，奇数输出数字
            if (isChar) { // 字符
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 65对应A，97对应a
                ret.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                ret.append(random.nextInt(10));// 生成[0, 10)之间的整数
            }
        }
        return ret.toString();
    }


    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /**
     * @Author: zqz
     * @Description: 获取指定长度的ASCII字符随机字符串
     * @Param: [length]
     * @Return: java.lang.String
     * @Date: 2:26 PM 2020/3/31
     */
    public static String getAsciiRandom(int length){
        String str = "";
        for (int i = 0; i < length; i++)
        {
            char c = (char) (int) (Math.random() * 26 + 97);
            str += c;
        }
        return str.trim();
    }

}
