package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 改进2冒泡排序
 * 传统冒泡排序中每一趟排序操作只能找到一个最大值或最小值,
 * 我们考虑利用在每趟排序中进行正向和反向两遍冒泡的方法一次可以得到两个最终值(最大者和最小者) ,
 *  从而使排序趟数几乎减少了一半。
 * @author 聂跃
 *时间复杂度：平均O(n^2) 最少O(n) 最多O(n^2)
 *空间复杂度 O(1)
 *稳定
 */
public class Better2BubbleSort {
	 public static void main(String[] args) {    
	        Better2BubbleSort  obj=new Better2BubbleSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.bubble2Sort(a,a.length);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本11.227秒
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
	   public void bubble2Sort(int a[], int n){  
		   int low = 0;   
		    int high= n -1; //设置变量的初始值  
		    int tmp,j;  
		    while (low < high) {  
		        for (j= low; j< high; ++j) //正向冒泡,找到最大者  
		            if (a[j]> a[j+1]) {  
		                tmp = a[j]; a[j]=a[j+1];a[j+1]=tmp;  
		            }   
		        --high;                 //修改high值, 前移一位  
		        for ( j=high; j>low; --j) //反向冒泡,找到最小者  
		            if (a[j]<a[j-1]) {  
		                tmp = a[j]; a[j]=a[j-1];a[j-1]=tmp;  
		            }  
		        ++low;                  //修改low值,后移一位  
		    }    
	    }  
}
