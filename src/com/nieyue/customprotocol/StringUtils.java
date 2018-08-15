package com.nieyue.customprotocol;

/**
 * 字符串工具
 */
public class StringUtils {
      
    public static boolean isNullOrBlank(String str) {  
        return str == null || str.length() == 0;  
    }  
      
    public static boolean isNotNullOrBlank(String str) {  
        return !isNullOrBlank(str);  
    }  
  
} 