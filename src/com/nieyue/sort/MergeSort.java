package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 归并排序
 * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。
 * 然后再把有序子序列合并为整体有序序列。
 *时间复杂度：平均O(nlog2n) 最少O(nlog2n) 最多O(nlog2n)
 *空间复杂度 O(n)
 *稳定 
 */
public class MergeSort {
	 public static void main(String[] args) {    
	        MergeSort  obj=new MergeSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.mergeSort(a);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本2.58秒
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
	    public  void mergeSort(int[] data) {    
	        sort(data, 0, data.length - 1);    
	    }    
	    
	    public  void sort(int[] data, int left, int right) {    
	        if (left >= right)    
	            return;    
	        // 找出中间索引    
	        int center = (left + right) / 2;    
	        // 对左边数组进行递归    
	        sort(data, left, center);    
	        // 对右边数组进行递归    
	        sort(data, center + 1, right);    
	        // 合并    
	        merge(data, left, center, right);    
	    }    
	    
	    /**  
	     * 将两个数组进行归并，归并前面2个数组已有序，归并后依然有序  
	     *   
	     * @param data  
	     *            数组对象  
	     * @param left  
	     *            左数组的第一个元素的索引  
	     * @param center  
	     *            左数组的最后一个元素的索引，center+1是右数组第一个元素的索引  
	     * @param right  
	     *            右数组最后一个元素的索引  
	     */    
	    public  void merge(int[] data, int left, int center, int right) {    
	        // 临时数组    
	        int[] tmpArr = new int[data.length];    
	        // 右数组第一个元素索引    
	        int mid = center + 1;    
	        // third 记录临时数组的索引    
	        int third = left;    
	        // 缓存左数组第一个元素的索引    
	        int tmp = left;    
	        while (left <= center && mid <= right) {    
	            // 从两个数组中取出最小的放入临时数组    
	            if (data[left] <= data[mid]) {    
	                tmpArr[third++] = data[left++];    
	            } else {    
	                tmpArr[third++] = data[mid++];    
	            }    
	        }    
	        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）    
	        while (mid <= right) {    
	            tmpArr[third++] = data[mid++];    
	        }    
	        while (left <= center) {    
	            tmpArr[third++] = data[left++];    
	        }    
	        // 将临时数组中的内容拷贝回原数组中    
	        // （原left-right范围的内容被复制回原数组）    
	        while (tmp <= right) {    
	            data[tmp] = tmpArr[tmp++];    
	        }    
	    }     
}
