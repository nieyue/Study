package com.nieyue.sort;

import java.util.ArrayList;
import java.util.Collections;

/**选择排序
 * 堆排序
 * 由大到小排序
 *两个步骤：1，建堆  2，对顶与堆的最后一个元素交换位置
 **时间复杂度：平均O(nlog2n) 最少O(nlog2n) 最多O(nlog2n)
 *空间复杂度 O(1)
 *不稳定 
 */
public class HeapSort {

	public static void main(String[] args) {
		HeapSort  obj=new HeapSort();
		int[] a=obj.initArray(100000);
        System.out.println("初始值：");    
        obj.print(a);    
        long starttime=System.currentTimeMillis();
        obj.heapSort(a);    
        long endtime=System.currentTimeMillis();
        System.out.println("\n排序后：");    
        obj.print(a);    
        System.out.println("\n总耗时："+Double.valueOf(endtime-starttime)/1000+"s");
        //100000样本14.63秒

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
		 for (int i = (last- 1) / 2; i >= 0; i--) {  //找到最后一个叶子节点的双亲节点
	            // 保存当前正在判断的节点  
	            int parent = i;  
	            // 若当前节点的左子节点存在，即子节点存在
	            while (2 * parent + 1 <= last) {  
	                // biggerIndex总是记录较大节点的值,先赋值为当前判断节点的左子节点  
	                int bigger = 2 * parent + 1;//bigger指向左子节点  
	                if (bigger < last) { //说明存在右子节点
	                  
	                    if (data[bigger] > data[bigger+ 1]) { //右子节点>左子节点时
	                     
	                        bigger=bigger+1;  
	                    }  
	                }  
	                if (data[parent] > data[bigger]) {  //若双亲节点值大于子节点中最大的
	                    // 若当前节点值比子节点最大值小，则交换2者得值，交换后将biggerIndex值赋值给k  
	                    swap(data, parent, bigger);  
	                    parent = bigger;  
	                } else {  
	                    break;  
	                }  
	            }  
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
