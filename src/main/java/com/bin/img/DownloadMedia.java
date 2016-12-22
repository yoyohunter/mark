package com.bin.img;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * Created by zhangbin on 16/10/26.
 */
public class DownloadMedia {

    /**
     * 从服务器中下载图片
     *
     * @return
     */
    @RequestMapping(value = "/download")
    public void downloadMedia(HttpServletResponse response, HttpServletRequest request) {
        
    }
}
