package com.nieyue.customprotocol;

import java.util.Enumeration;
import java.util.Properties;

/**
 * 运行环境工具类
 */
  
public class SystemUtils {  
      
    private static Properties properties = null;
      
    static {  
        properties = System.getProperties();  
    }  
      
    /** 
     * 返回格式化后的所有系统属性信息
     */
    public static String formatSystemProperties() {
        StringBuffer formatResult = new StringBuffer();  
        Enumeration<String> names = (Enumeration<String>) properties.propertyNames();
        while (names.hasMoreElements()) {  
            String name = names.nextElement();  
            formatResult.append(name).append("=")  
                .append(properties.getProperty(name)).append("\n");  
        }  
        int length = 0;  
        return (length = formatResult.length()) > 0 ?   
                formatResult.substring(0, length - 1) : "";  
    }  
      
    /** 
     * 返回格式化后的所有指定的系统属性信息
     * @param propertyKeys
     */
    public static String formatSystemProperties(String[] propertyKeys) {  
        StringBuffer formatResult = new StringBuffer();  
        if (propertyKeys != null && propertyKeys.length > 0) {  
            for (String key : propertyKeys)   
                formatResult.append(key).append("=")  
                    .append(properties.getProperty(key)).append("\n");  
        }  
        int length = 0;  
        return (length = formatResult.length()) > 0 ?   
                formatResult.substring(0, length - 1) : "";  
    }  
  
} 