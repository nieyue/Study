package com.nieyue.algorithm;

/**
 * 记时工具
 */
public class CountUtil {
    private static long start;
    private static long end;
    private static long count;
    public static long getStart(){
        start=System.currentTimeMillis();
        System.out.println("开始时间："+start);
        return start;
    }
    public static long getEnd(){
        end=System.currentTimeMillis();
        System.out.println("结束时间："+end);
        return end;
    }
    public static long getCount(){
        count=end-start;
        System.out.println("花费时间："+count);
        return count;
    }
}
