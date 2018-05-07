package com.nieyue.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Combination {
	 public static void main(String[] args) {
	        //String str = "dabc";
	        //List<String> cr = getCombinationResult(2, stringFilter(str));
	        /*List<String> cr = getCombinationResult(3, str);
	        for(String s: cr){
	            System.out.println(s);
	        }*/
	       /* int[][] data = {{1,2,3}, {2,3,4}, {5,2,3}, {1,8,9}, {2,3,4}};
	        combine(data, 3);*/
	        int[] str=new int[]{1,2,3,4,5};  
	          
	        ArrayList<Integer> t=new ArrayList<Integer>();  
	        ArrayList<ArrayList<Integer>> res=new ArrayList<ArrayList<Integer>>();  
	        //求组合数  
	        System.out.println(combination(str,5,4,t));  
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
	    /**
	     * 二维数组混乱排列
	     * @param data
	     * @param n
	     */
	    public static void combine(int[][] data, int n){
	        if (n > data.length) {n = data.length;}
	        int[] idx = new int[data.length];
	        for (int i=0; i<data.length; i++) {
	            if (i<data.length-n) {idx[i]=0;}
	            else {idx[i]=1;}
	        }
	        List<String> list = new ArrayList<String>();
	        while (idx[0] < 2) {
	            int cnt=0, dig=0;
	            int[] gp = new int[n];
	            for (int i=0; i<idx.length; i++) {
	                if (idx[i] == 1) {
	                    if (i<n) {dig++;}
	                    if (cnt<n) {gp[cnt] = i;}
	                    cnt++;
	                }
	            }
	            if (n == cnt) {
	                int[] subIdx = new int[n];
	                Arrays.fill(subIdx, 0);
	                while (subIdx[0] < data[gp[0]].length) {
	                    StringBuilder sb = new StringBuilder();
	                    for (int i=0; i<n; i++) {
	                        sb.append(data[gp[i]][data[gp[i]].length-subIdx[i]-1]);
	                    }
	                    list.add(0, sb.toString());
	 
	                    subIdx[n-1]++;
	                    for (int i=n-1; i>0; i--) {
	                        if (subIdx[i] == data[gp[i]].length) {
	                            subIdx[i] = 0;
	                            subIdx[i-1]++;
	                        } else {
	                            break;
	                        }
	                    }
	                }
	            }
	            if (dig == n) {break;}
	 
	            idx[data.length-1]++;
	            for (int i=data.length-1; i>0; i--) {
	                if (idx[i] == 2) {
	                    idx[i] = 0;
	                    idx[i-1]++;
	                } else {
	                    break;
	                }
	           }
	            
	        }
	     
	        //system.out.println("n个字符串长度的结果"+？？？？)
	        int count = 0;
	        for (String s : list) {
	            System.out.printf("%s,", s);
	            count++;
	            if (count%10 == 0) {System.out.println();}
	       }
	       System.out.printf("\ncount=%d\n", count);
	    }
	  //组合数，通过返回值返回结果  
	    public static ArrayList<ArrayList<Integer>>  combination(int[] A,int n,int m,ArrayList<Integer> t)  
	    {  
	        ArrayList<ArrayList<Integer>> res=new ArrayList<ArrayList<Integer>>();  
	        if(m==0)  
	        {  
	            ArrayList<Integer> temp=new ArrayList<>(t);  
	            res.add(temp);  
	            return res;  
	        }  
	        else  
	        {  
//	          for(int i=n-1;i>=m-1;i--)  
//	          {  
//	              t.add(A[i]);  
//	              Combination(A,i,m-1,t,res);  
//	              t.remove(t.size()-1);  
//	          }  
	              
	            for(int i=A.length-n;i<=A.length-m;i++)  
	            {  
	                t.add(A[i]);  
	                res.addAll(combination(A,A.length-i-1,m-1,t));  
	                t.remove(t.size()-1);  
	            }  
	            return res;  
	        }  
	    }  
	    
}
