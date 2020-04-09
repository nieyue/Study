package com.nieyue.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 无序数组
 * 无序数组的优点：插入快，如果知道下标，可以很快的存取
 * 无序数组的缺点：查找慢，删除慢，大小固定。
 */
public class Array<T> {
    private Object [] array;
    private int length = 0;       //数组元素个数


    //构造方法，传入数组最大长度
    public Array(int max){
        array = new Object [max];
    }



    public int size(){
        return length;
    }
    //检测数组是否包含某个元素，如果存在返回其下标，不存在则返回-1
    public int contains(T target){
        int index = -1;
        for(int i=0;i<length;i++){
            if(array[i].equals(target)){
                index = i;
                break;
            }
        }
        return index;
    }

    //插入
    public void insert(T elem) {
        array[length] = elem;
        length++;
    }
    //插入所有
    public void insertAll(T[] elems) {
        for (int i = 0; i < elems.length; i++) {
            array[length] = elems[i];
            length++;
        }
    }

    //删除某个指定的元素值，删除成功则返回true，否则返回false
    public boolean delete(T target){
        int index = -1;
        if((index = contains(target)) !=-1){
            for(int i=index;i<length-1;i++){
                //删除元素之后的所有元素前移一位
                array[i] =array[i+1];
            }
            length--;
            return true;
        }else{
            return false;
        }
    }

    //列出所有元素
    public void display(){
        for(int i=0;i<length;i++){
            System.out.print(array[i]+"\t");
        }
    }

    public static void main(String[] args) {
        Object[] in = new Object[]{23,23,22,33,44,55};
        String[] in2 = new String[]{"23","34"};
        Integer[] in3 = new Integer[]{11,22};
        Array<String > a = new Array< >(10);
        a.insertAll(in2);
        a.display();
        System.out.println();
        System.out.println(a.size());
    }
}