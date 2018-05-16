package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 插入排序
 * 直接插入排序
 * <ul> 
 * <li>从第一个元素开始，该元素可以认为已经被排序</li>  
 * <li>取出下一个元素，在已经排序的元素序列中从后向前扫描</li>  
 * <li>如果该元素（已排序）大于新元素，将该元素移到下一位置</li>  
 * <li>重复步骤3，直到找到已排序的元素小于或者等于新元素的位置</li>  
 * <li>将新元素插入到该位置中</li>  
 * <li>重复步骤2</li>  
 * </ul>  
 *时间复杂度：平均O(n^2) 最少O(n) 最多O(n^2)
 *空间复杂度 O(1)
 *稳定 
 */
public class InsertSort {
	 public static void main(String[] args) {    
	        InsertSort  obj=new InsertSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.insertSort(a);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本2.809秒
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
	    /**
	     * 插入排序数组
	     * @param a
	     */
	    public void insertSort(int[] a) {    
	        for(int i=1;i<a.length;i++){//从头部第一个当做已经排好序的，把后面的一个一个的插到已经排好的列表中去。    
	                int j;    
	                int x=a[i];//x为待插入元素    
	                for( j=i;  j>0 && x<a[j-1];j--){//通过循环，逐个后移一位找到要插入的位置。    
	                    a[j]=a[j-1];    
	                }    
	                a[j]=x;//插入    
	        }    
	            
	    }  
}
