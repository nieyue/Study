package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 桶排序/基数排序(Radix Sort)
	把数据分组，放在一个个的桶中，然后对每个桶里面的在进行排序。
 *时间复杂度：平均O(d(r+n)) 最少O(d(n+rd)) 最多O(d(r+n));r代表关键字基数，d代表长度，n代表关键字个数
 *空间复杂度 O(rd+n)
 *稳定
 *
 */
public class RadixSort {
	 public static void main(String[] args) {    
	        RadixSort  obj=new RadixSort();  
	        int[] a=obj.initArray(100000);
	        System.out.println("初始值：");    
	        obj.print(a);    
	        long starttime=System.currentTimeMillis();
	        obj.radixSort(a,1000,10);    
	        long endtime=System.currentTimeMillis();
	        System.out.println("\n排序后：");    
	        obj.print(a);    
	        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
	        //100000样本0.016秒
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
	    public  void radixSort(int[] data, int radix, int d) {  
	        // 缓存数组  
	        int[] tmp = new int[data.length];  
	        // buckets用于记录待排序元素的信息  
	        // buckets数组定义了max-min个桶  
	        int[] buckets = new int[radix];  
	  
	        for (int i = 0, rate = 1; i < d; i++) {  
	  
	            // 重置count数组，开始统计下一个关键字  
	            Arrays.fill(buckets, 0);  
	            // 将data中的元素完全复制到tmp数组中  
	            System.arraycopy(data, 0, tmp, 0, data.length);  
	  
	            // 计算每个待排序数据的子关键字  
	            for (int j = 0; j < data.length; j++) {  
	                int subKey = (tmp[j] / rate) % radix;  
	                buckets[subKey]++;  
	            }  
	  
	            for (int j = 1; j < radix; j++) {  
	                buckets[j] = buckets[j] + buckets[j - 1];  
	            }  
	  
	            // 按子关键字对指定的数据进行排序  
	            for (int m = data.length - 1; m >= 0; m--) {  
	                int subKey = (tmp[m] / rate) % radix;  
	                data[--buckets[subKey]] = tmp[m];  
	            }  
	            rate *= radix;  
	        }  
	  
	    }
}
