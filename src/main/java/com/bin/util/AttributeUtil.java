package com.bin.util;


import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan on 15/9/5.
 * 对attribute 处理的工具类
 */
public class AttributeUtil {

    static final String SP = ";";
    static final String SSP = ":";

    static final String R_SP = "#3A";
    static final String R_SSP = "#3B";

    /**
     * 通过Map转换成String
     *
     * @param attrs
     * @return
     */
    public static final String toString(Map<String, String> attrs) {
        StringBuilder sb = new StringBuilder();
        if (null != attrs && !attrs.isEmpty()) {
            sb.append(SP);
            for (String key : attrs.keySet()) {
                String val = attrs.get(key);
                if (StringUtils.isNotEmpty(val)) {
                    sb.append(encode(key)).append(SSP).append(encode(val)).append(SP);
                }
            }
        }
        return sb.toString();
    }

    public static final String toString(String key, String val) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(val)) {
            sb.append(SP);
            sb.append(encode(key)).append(SSP).append(encode(val));
            sb.append(SP);
        }
        return sb.toString();
    }

    /**
     * 通过字符串解析成attributes
     *
     * @param str
     * @return
     */
    public static final Map<String, String> fromString(String str) {
        Map<String, String> attrs = new HashMap<String, String>();
        if (StringUtils.isNotBlank(str)) {
            String[] arr = str.split(SP);
            if (null != arr) {
                for (String kv : arr) {
                    if (StringUtils.isNotBlank(kv)) {
                        String[] ar = kv.split(SSP);
                        if (null != ar && ar.length == 2) {
                            String key = decode(ar[0]);
                            String val = decode(ar[1]);
                            if (StringUtils.isNotEmpty(val)) {
                                attrs.put(key, val);
                            }
                        }
                    }
                }
            }
        }
        return attrs;
    }

    private static String encode(String val) {
        return StringUtils.replace(StringUtils.replace(val, SP, R_SP), SSP, R_SSP);
    }

    private static String decode(String val) {
        return StringUtils.replace(StringUtils.replace(val, R_SP, SP), R_SSP, SSP);
    }


}
