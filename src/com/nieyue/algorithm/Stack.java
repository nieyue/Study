package com.nieyue.algorithm;

/**
 * 栈
 */
public class Stack<T> {
    private int size;
    private int top;//栈顶元素下标
    private Object[] stackArray;//栈的容器
    public Stack(int size){
        stackArray=new Object[size];
        top=-1;//初始化栈，下标-1
        this.size=size;
    }
    //入栈
    public void push(T elem){
        stackArray[++top]=elem;
    }
    //出栈，删除栈顶，下标减一
    public Object pop(){
        return stackArray[top--];
    }
    //判空
    public boolean isEmpty(){
        return top==-1;
    }
    //判满
    public boolean isFull(){
        return top==size-1;
    }
    //查看栈顶，不删除
    public Object peek(){
        return stackArray[top];
    }

    public static void main(String[] args) {

    }
}
