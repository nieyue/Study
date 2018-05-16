package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 选择排序
 * 直接选择排序
 * <li>在未排序序列中找到最小元素，存放到排序序列的起始位置</li>  
 * <li>再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。</li>  
 * <li>以此类推，直到所有元素均排序完毕。</li> 
 *时间复杂度：平均O(n^2) 最少O(n^2) 最多O(n^2)
 *空间复杂度 O(1)
 *不稳定 
 */
public class SimpleSelectSort {

	public static void main(String[] args) {
		SimpleSelectSort  obj=new SimpleSelectSort();
		int[] a=obj.initArray(100000);
        System.out.println("初始值：");    
        obj.print(a);    
        long starttime=System.currentTimeMillis();
        obj.selectSort(a);    
        long endtime=System.currentTimeMillis();
        System.out.println("\n排序后：");    
        obj.print(a);    
        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
        //100000个样本2.622s
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
	private void selectSort(int[] a) {
		  int size = a.length, temp;   
		    for (int i = 0; i < size; i++) {   
		        int k = i;   
		        for (int j = size - 1; j >i; j--)  {   
		            if (a[j] < a[k])  k = j;   
		        }   
		        temp = a[i];   
		        a[i] = a[k];   
		        a[k] = temp;   
		    } 
	}
}
