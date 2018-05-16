package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 冒泡排序
 * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>  
 * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>  
 * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>  
 * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>  
 *时间复杂度：平均O(n^2) 最少O(n) 最多O(n^2)
 *空间复杂度 O(1)
 *稳定 
 */
public class BubbleSort {
	 public static void main(String[] args) {    
	        BubbleSort  obj=new BubbleSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.bubbleSort(a);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本14.572秒
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
	   public void bubbleSort(int a[]){  
		   int temp; // 记录临时中间值   
		    int size = a.length; // 数组大小   
		    for (int i = 0; i < size - 1; i++) {   
		        for (int j = i + 1; j < size; j++) {   
		            if (a[i] < a[j]) { // 交换两数的位置   
		                temp = a[i];   
		                a[i] = a[j];   
		                a[j] = temp;   
		            }   
		        }   
		    }   
	    }  
}
