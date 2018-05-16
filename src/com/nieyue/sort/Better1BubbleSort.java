package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 改进1冒泡排序
 * 设置一标志性变量pos,用于记录每趟排序中最后一次进行交换的位置。
 * 由于pos位置之后的记录均已交换到位,故在进行下一趟排序时只要扫描到pos位置即可
 * @author 聂跃
 *时间复杂度：平均O(n^2) 最少O(n) 最多O(n^2)
 *空间复杂度 O(1)
 *稳定 
 */
public class Better1BubbleSort {
	 public static void main(String[] args) {    
	        Better1BubbleSort  obj=new Better1BubbleSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.bubble1Sort(a,a.length);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本15.789秒
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
	   public void bubble1Sort(int a[], int n){  
		   int i= n -1;  //初始时,最后位置保持不变  
		    while ( i> 0) {   
		        int pos= 0; //每趟开始时,无记录交换  
		        for (int j= 0; j< i; j++)  
		            if (a[j]> a[j+1]) {  
		                pos= j; //记录交换的位置   
		                int tmp = a[j]; a[j]=a[j+1];a[j+1]=tmp;  
		            }   
		        i= pos; //为下一趟排序作准备  
		     }   
	    }  
}
