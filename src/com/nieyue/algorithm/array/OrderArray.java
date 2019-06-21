package com.nieyue.algorithm.array;

import com.nieyue.algorithm.CountUtil;

import java.util.Random;

/**
 * 有序数组
 * 有序数组的优点：查找效率高
 * 有序数组的缺点：删除和插入慢，大小固定
 */
public class OrderArray {
	private int[] intArray;
	private int length = 0; // 数组元素个数
 
	// 构造方法，传入数组最大长度
	public OrderArray(int max) {
		intArray = new int[max];
	}
 	public int size(){
		return length;
	}
	// 用二分查找法定位某个元素，如果存在返回其下标，不存在则返回-1
	//迭代法
	public int find(int target) {
		int start = 0; // 搜索段最小元素的小标
		int end = length - 1; // 搜索段最大元素的下标
		int mid=(end+start)/2; // 当前检测元素的下标
 
		if (end < 0) { // 如果数组为空，直接返回-1
			return -1;
		}
		if(target==intArray[mid]){
			return mid;
		}
		while(start<=end){
			mid=(end+start)/2;
			if(target<intArray[mid]){
				end=mid-1;
			}else if(target>intArray[mid]){
				start=mid+1;
			}else{
				return mid;
			}
		}
		return -1;
	}
	// 用二分查找法定位某个元素，如果存在返回其下标，不存在则返回-1
	//递归法
	public int find2(int target,int start,int end) {
		int mid=(end+start)/2; // 当前检测元素的下标

		if (end < 0) { // 如果数组为空，直接返回-1
			return -1;
		}
		if(target==intArray[mid]){
			return mid;
		}
		if(start>=end){
			return -1;
		}else if(target<intArray[mid]){
			return find2(target,start,mid-1);
		}else if(target<intArray[mid]){
			return find2(target,mid+1,end);
		}
		return -1;
	}

	// 插入
	public void insert(int elem) {
		int location = 0;
 
		// 判断应插入位置的下标
		for (; location < length; location++) {
			if (intArray[location] > elem)
				break;
		}
		// System.out.println(location);
		// 将插入下标之后的所有元素后移一位
		for (int i = length; i > location; i--) {
			intArray[i] = intArray[i - 1];
		}
 
		// 插入元素
		intArray[location] = elem;
 
		length++;
	}
 
	// 删除某个指定的元素值，删除成功则返回true，否则返回false
	public boolean delete(int target) {
		int index = -1;
		if ((index = find(target)) != -1) {
			for (int i = index; i < length - 1; i++) {
				// 删除元素之后的所有元素前移一位
				intArray[i] = intArray[i + 1];
			}
			length--;
			return true;
		} else {
			return false;
		}
	}
 
	// 列出所有元素
	public void display() {
		for (int i = 0; i < length; i++) {
			System.out.print(intArray[i] + "\t");
		}
		System.out.println();
	}


	public static void main(String[] args) {
		CountUtil.getStart();
		OrderArray orderArray = new OrderArray(1000);

		/*orderArray.insert(3);
		orderArray.insert(4);
		orderArray.insert(6);
		orderArray.insert(8);*/
		for (int i = 0; i < 10; i++) {
			orderArray.insert(i);
			//orderArray.insert(new Random().nextInt(1000));
		}
		orderArray.display();
		//int i = orderArray.find(4);
		int i = orderArray.find2(4,0,orderArray.size()-1);
		System.out.println("在队列中的位置是" + i);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CountUtil.getEnd();
		CountUtil.getCount();
	}
}