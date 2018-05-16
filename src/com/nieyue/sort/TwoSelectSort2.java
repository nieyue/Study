package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 直接选择排序改进，二元选择排序
 *简单选择排序，每趟循环只能确定一个元素排序后的定位。
 *我们可以考虑改进为每趟循环确定两个元素（当前趟最大和最小记录）的位置,从而减少排序所需的循环次数。
 *改进后对n个数据进行排序，最多只需进行[n/2]趟循环即可。
 *时间复杂度：平均O(n^2) 最少O(n^2) 最多O(n^2)
 *空间复杂度 O(1)
 *不稳定 
 */
public class TwoSelectSort2 {

	public static void main(String[] args) {
		TwoSelectSort2  obj=new TwoSelectSort2();
		int[] a=obj.initArray(100000);
        System.out.println("初始值：");    
        obj.print(a);    
        long starttime=System.currentTimeMillis();
        obj.selectSort(a,a.length);    
        long endtime=System.currentTimeMillis();
        System.out.println("\n排序后：");    
        obj.print(a);    
        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
        //100000个样本，波动：2.794s --  3.21s
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
	public void selectSort(int r[],int n) {  
	    int i ,j , min ,max, tmp;  
	    for (i=1 ;i <= n/2;i++) {    
	        // 做不超过n/2趟选择排序   
	        min = i; max = i ; //分别记录最大和最小关键字记录位置  
	        for (j= i+1; j<= n-i; j++) {  
	            if (r[j] > r[max]) {   
	                max = j ; continue ;   
	            }    
	            if (r[j]< r[min]) {   
	                min = j ;   
	            }     
	      }    
	      //该交换操作还可分情况讨论以提高效率  
	      tmp = r[i-1]; r[i-1] = r[min]; r[min] = tmp;  
	      tmp = r[n-i]; r[n-i] = r[max]; r[max] = tmp;   
	  
	    }   
	} 
}
