package test;

import java.util.Base64;

public class TestConvert {
    // 将字母转换成数字
    public static void letterToNum(String input) {
        for (byte b : input.getBytes()) {
            System.out.print(b/* - 96*/);
        }
    }// 将数字转换成字母

    public static void numToLetter(String input) {
        for (byte b : input.getBytes()) {
            System.out.print((char) (b/* + 48*/));
        }
    }

    public static void main(String[] args) {
//        String i1 = "和";
//        String i2 = "123456";
//        letterToNum(i1);
//        System.out.println();
//        numToLetter(i2);
        enBase64();
    }

    public static void enBase64(){
        String before = "test";
        //加密
        String after = Base64.getEncoder().encodeToString(before.getBytes());
        System.out.println(after);
        //解密
        byte[] beforeB = Base64.getDecoder().decode(after);
        System.out.println(new String(beforeB));
    }
}
