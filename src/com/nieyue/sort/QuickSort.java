package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 快速排序
 	1）选择一个基准元素,通常选择第一个元素或者最后一个元素,
	2）通过一趟排序讲待排序的记录分割成独立的两部分，其中一部分记录的元素值均比基准元素值小。另一部分记录的 元素值比基准值大。
	3）此时基准元素在其排好序后的正确位置
	4）然后分别对这两部分记录用同样的方法继续进行排序，直到整个序列有序。
 * @author 聂跃
 *时间复杂度：平均O(nlog2n) 最少O(nlog2n) 最多O(n^2)
 *空间复杂度 O(nlog2n)
 *不稳定
 */
public class QuickSort {
	 public static void main(String[] args) {    
	        QuickSort  obj=new QuickSort();  
	        int[] a=obj.initArray(1000000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.quickSort(a,0,a.length-1);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本0.016秒
	        //100000样本0.126秒
	    }
	 /**
	  * 初始数据
	  * @param size 大小
	  * @return
	  */
	 	public int[] initArray(int size){
	 		int a[] =new int[size];
	 		ArrayList<Integer> al = new ArrayList<Integer>();
	 		for (int i = 0; i < size; i++) {
	 			al.add(i+1);
			}
	 		Collections.shuffle(al );
	 		for (int i = 0; i < al.size(); i++) {
	 			a[i]=al.get(i);
	 		}
	 		return a;
	 	}
	    /**
	     * 打印数组
	     * @param a
	     */
	    public void print(int a[]){
	    	int size=a.length>100?100:a.length;
	        for(int i=0;i<size;i++){    
	            System.out.print(a[i]+" ");    
	        }    
	    } 
	    public  void quickSort(int[] a,int start, int end) {  
	    	 if (start < end) {   
	    	        int base = a[start]; // 选定的基准值（第一个数值作为基准值）   
	    	        int temp; // 记录临时中间值   
	    	        int i = start, j = end;   
	    	        do {   
	    	            while ((a[i] < base) && (i < end))   
	    	                i++;   
	    	            while ((a[j] > base) && (j > start))   
	    	                j--;   
	    	            if (i <= j) {   
	    	                temp = a[i];   
	    	                a[i] = a[j];   
	    	                a[j] = temp;   
	    	                i++;   
	    	                j--;   
	    	            }   
	    	        } while (i <= j);   
	    	        if (start < j)   
	    	            quickSort(a, start, j);   
	    	        if (end > i)   
	    	            quickSort(a, i, end);   
	    	    }    
	    }  
	   
}
