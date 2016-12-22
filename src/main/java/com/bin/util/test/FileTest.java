package com.bin.util.test;

import javax.servlet.ServletContext;
import java.net.URL;

/**
 * Created by zhangbin on 16/10/27.
 */
public class FileTest {

    public static void main(String[] args){
        URL blank_path=null;
        blank_path=FileTest.class.getResource("");
        System.out.println(blank_path+"--------空的路径----------");

        blank_path=FileTest. class .getResource("/");
        System.out.println(blank_path + "--------空的路径----------");

        blank_path=FileTest. class.getClassLoader() .getResource("/");
        System.out.println(blank_path + "--------genlujing----------");
        FileTest. class .getResource("/");
        System.out.println(blank_path + "--------空的路径----------");

        //我推荐使用Thread.currentThread().getContextClassLoader().getResource("" )
        // 来得到当前的classpath的绝对路径的URI表示法。

        blank_path=ClassLoader.getSystemResource( "" );
        System.out.println(blank_path);
        blank_path=Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(blank_path);

        //ServletContext.getRealPath("/");

        //而JDK提供的ClassLoader类，它的 getResource(String name),
        // getResourceAsStream(String name)等方法，
        // 使用相对于当前项目的 classpath的相对路径来查找资源。
    }
}
