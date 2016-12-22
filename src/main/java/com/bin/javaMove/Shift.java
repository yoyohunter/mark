package com.bin.javaMove;

/**
 * Created by zhangbin on 16/11/18.
 */
public class Shift {
    public static void main(String[] args) {

        /*System.out.println("******************正数左移在低位补0*******************");
        int a = 1;
        a = a << 2;
        System.out.println(a);
        System.out.println(Integer.toBinaryString(a));

        System.out.println("******************正数右移在高位补0********************");
        a = 1;
        a = a >> 2;
        System.out.println(a);
        System.out.println(Integer.toBinaryString(a));
        System.out.println("******从上面结果可以看出：移位是不循环的*****");
        System.out.println("看看负数的移位:");
*/
        System.out.println("***********负数的右移操作高位补1**************");
        int i = -1;
        System.out.println(i + ":");
        System.out.println(Integer.toBinaryString(i));
        i = i >> 2;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println("**********负数的左移操作低位补0*****************");
        i = i << 2;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println("*************再看看>>>操作符*************");
        System.out.println("*************负数的>>>操作高位补0***************");
        i = -1;
        System.out.println(Integer.toBinaryString(i));
        i = i >>> 10;
        System.out.println(i + ":");
        System.out.println(Integer.toBinaryString(i));
        System.out.println("*************注意：没有<<<符号**************");

        System.out.println("**********byte类型移位时要强转换*************");
        byte k = 10;
        System.out.println(Integer.toBinaryString(k));
        k = (byte) ((byte) k >>> 2);
        System.out.println(Integer.toBinaryString(k));
    }
}
