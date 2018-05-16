package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**选择排序
 * 堆排序（改进） 
 * 由大到小排序 
 *时间复杂度：平均O(nlog2n) 最少O(nlog2n) 最多O(nlog2n)
 *空间复杂度 O(1)
 *不稳定 
 */  
public class BetterHeapSort {  
  
    public static void main(String[] args) {  
        BetterHeapSort  obj=new BetterHeapSort();  
        int[] a=obj.initArray(100000);
        System.out.println("初始值：");    
        obj.print(a);    
        long starttime=System.currentTimeMillis();
        obj.heapSort(a);    
        long endtime=System.currentTimeMillis();
        System.out.println("\n排序后：");    
        obj.print(a);    
        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
        //100000样本10.046秒 
  
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
		public void heapSort(int[] a){
			for(int i=0;i<a.length;i++){
				 createLittleHeap(a,a.length-1-i);//创建堆,创建的是小顶堆。每次循环完，二叉树的根节点都是最小值，所以与此时的未排好部分最后一个值交换位置  
		         swap(a, 0, a.length - 1 - i);//与最后一个值交换位置，最小值找到了位置 
			}
		}
    /* 
     * 创建小顶堆：双亲节点小于子节点的值。从叶子节点开始，直到根节点。这样建立的堆定位最小值 
     */  
    private void createLittleHeap(int[] data, int last) {  
    	for(int i=last;i>0;i--){
    		int parent=(i-1)/2;//当前节点的双亲节点
    		if(data[parent]> data[i])
    		swap(data, parent, i);   
    	}
    }  
     public  void swap(int[] data, int i, int j) {    
            if (i == j) {    
                return;    
            }    
            data[i] = data[i] + data[j];    
            data[j] = data[i] - data[j];    
            data[i] = data[i] - data[j];    
        }    
}  
