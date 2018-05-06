package com.nieyue.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Combination {
	 public static void main(String[] args) {
	        String str = "dabc";
	        //List<String> cr = getCombinationResult(2, stringFilter(str));
	        List<String> cr = getCombinationResult(3, str);
	        for(String s: cr){
	            System.out.println(s);
	        }
	    }

	    /**
	     * 对字符串中元素进行重排序
	     * 此外还可以在该方法对元素进行去重等
	     * @param str  原字符串
	     * @return  目标字符串
	     */
	    public static String stringFilter(String str){
	        char[] c = str.toCharArray();
	        Arrays.sort(c);
	        return new String(c);
	    }

	    /**
	     * 得到组合结果
	     * @param num   从N个数中选取num个数
	     * @param str   包含Ng个元素的字符串
	     * @return  组合结果
	     */
	    public static List<String> getCombinationResult(int num, String str) {
	        List<String> result = new ArrayList<String>();
	        if (num == 1) {
	            for (char c : str.toCharArray()) {
	                result.add(String.valueOf(c));
	            }
	            return result;
	        }
	        if (num >= str.length()) {
	            result.add(str);
	            return result;
	        }
	        int strlen = str.length();
	        for (int i = 0; i < (strlen - num + 1); i++) {
	            List<String> cr = getCombinationResult(num - 1, str.substring(i + 1));//从i+1处直至字符串末尾
	            char c = str.charAt(i);//得到上面被去掉的字符，进行组合
	            for (String s : cr) {
	                result.add(c + s);
	            }
	        }
	        return result;
	    }
	    /**
	     * 得到组合结果
	     * @param num   从N个数中选取num个数
	     * @param str   包含Ng个元素的字符串
	     * @return  组合结果
	     */
	    public static List<List<List<Integer>>> getCombinationResult(int num, List<List<Integer>> str) {
	    	List<List<List<Integer>>>  result = new ArrayList<>();
	    	if (num <3) {
	    		throw new RuntimeException("k派系最少3个");
	    	}
	    	if (num >= str.size()) {
	    		result.add(str);
	    		return result;
	    	}
	    	int strlen = str.size();
	    	for (int i = 0; i < (strlen - num + 1); i++) {
	    		List<List<List<Integer>>> cr = getCombinationResult(num - 1, str.subList(i+1, str.size()));//从i+1处直至字符串末尾
	    		 List<Integer> c = str.get(i);//得到上面被去掉的列表，进行组合
	    		for (List<List<Integer>> s : cr) {
	    			//result.add(c + s);
	    			result.add( s);
	    		}
	    	}
	    	return result;
	    }
}
