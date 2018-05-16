package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 *希尔排序（缩小增量排序）
 *1.选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
 *2.按增量序列个数k，对序列进行k 趟排序；
 *3.每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
 *仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 *时间复杂度：平均O(n^1.3) 最少O(n) 最多O(n^2)
 *空间复杂度 O(1)
 *不稳定 
 */
public class ShellSort {

	public static void main(String[] args) {
		ShellSort  obj=new ShellSort();
		int[] a=obj.initArray(1000000);
        System.out.println("初始值：");    
        obj.print(a);    
        long starttime=System.currentTimeMillis();
        obj.shellSort(a);    
        long endtime=System.currentTimeMillis();
        System.out.println("\n排序后：");    
        obj.print(a);    
        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
        //100000样本0.018s
        //1000000样本0.186s

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
	private void shellSort(int[] a) {
		 int dk = a.length/2; 
		 while( dk >= 1  ){  
	        ShellInsertSort(a, dk);  
	        dk = dk/2;
		 }
	}
	private void ShellInsertSort(int[] a, int dk) {//类似插入排序，只是插入排序增量是1，这里增量是dk,把1换成dk就可以了
		for(int i=dk;i<a.length;i++){
			if(a[i]<a[i-dk]){
				int j;
				int x=a[i];//x为待插入元素
				a[i]=a[i-dk];
				for(j=i-dk;  j>=0 && x<a[j];j=j-dk){//通过循环，逐个后移一位找到要插入的位置。
					a[j+dk]=a[j];
				}
				a[j+dk]=x;//插入
			}
				
		}
		
	}
}
