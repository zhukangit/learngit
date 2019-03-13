package test;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String s = new String("aa999");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        String cv = convertMD5(s);
        System.out.println("解密的：" + convertMD5(cv));
//        md5Test();
//        test1();
//        List list1 = new ArrayList();
//        list1.add("1111");
//        list1.add("2222");
//        list1.add("3333");
//        for (int i = 0; i < 8000; i++) {
//            list1.add(i);
//        }
//
//        List list2 = new ArrayList();
//        list2.add("3333");
//        list2.add("4444");
//        list2.add("5555");
//
//        List l3 = new ArrayList();
//        long s1 = System.currentTimeMillis();
//        l3.addAll(list1);
//        System.out.println(System.currentTimeMillis() - s1);
//        List l4 = new ArrayList();
//        long s2 = System.currentTimeMillis();
//        for (int i = 0; i < list1.size(); i++) {
//            l4.add(list1.get(i));
//        }
//        System.out.println(System.currentTimeMillis() - s2);

//        //并集
////        list1.addAll(list2);
//        //交集s
////        list1.retainAll(list2);
//        //差集
//        list1.removeAll(list2);
//        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);
//        System.out.println(list1);
    }

    public static void md5Test() {//加密后的字符串
        String encodeStr = DigestUtils.md5Hex("aa999");
        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);
    }
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    public static void test1() {
        List list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");

        list1.addAll(list2);

        list1.clear();
        System.out.println(list2.size());
    }
}
