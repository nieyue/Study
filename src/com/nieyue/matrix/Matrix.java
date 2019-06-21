package com.nieyue.matrix;

import java.util.Scanner;

/**
 * 矩阵
 * @author Administrator
 *
 */
public class Matrix {
	/** 
	 * 矩阵乘法 
	 * a点乘b，当矩阵a的列数x与矩阵b的行数y相等时可进行相乘 
	 * a乘b得到的新矩阵c，c的行数y等于a的行数，c的列数x等于b的列数 
	 * Created by Queena on 2017/8/19. 
	 */  
	public static double[][] multiplyMatrix(double[][] a,double[][] b){
	if(a[0].length != b.length) {
	        return null;
	 }
	 double[][] c=new double[a.length][b[0].length];
	        for(int i=0;i<a.length;i++) {
	            for(int j=0;j<b[0].length;j++) {
	              for(int k=0;k<a[0].length;k++) {            
	            c[i][j] += a[i][k] * b[k][j]; 
	           } 

	         }

	       }
	return c;
	}

    /** 
     * 打印一个矩阵
     * @param type类型 1输入矩阵，2结果指针
     */
	public static void printMatrix(int type,double[][] c) {
		if (c != null) {
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < c[0].length; j++) {
					if(type==1) {
						System.out.print("  "+c[i][j]); // 保留1位小数;
					}else if(type==2) {
						System.err.print("  "+ c[i][j]); // 保留1位小数;						
					}else {
						System.out.print("  "+c[i][j]); // 保留1位小数;												
					}
				}
				if(type==1) {
					System.out.println();
				}else if(type==2) {
					System.err.println();						
				}else {
					System.out.println();												
				}
			}
		} else {
			System.out.println("无效");
		}
		System.out.println();

	}
	/**
	 * 输入输出
	 * @param args
	 * @throws InterruptedException
	 */
	public static void print() throws InterruptedException {
		Scanner input=new Scanner(System.in);
    	System.out.println("输入第一个矩阵的行数：");
    	int m=input.nextInt();
    	System.out.println("输入第一个矩阵的列数：");
    	int n=input.nextInt();
    	System.out.println("输入第二个矩阵的列数：");
    	int k=input.nextInt();
    	     double [][]a=new double[m][n];  //定义一个m*n的矩阵
    	     double [][]b=new double[n][k];  //定义一个n*k的矩阵 
    	     System.out.println("输入连续的数构成第一个矩阵:");
    	     for(int i=0;i<m;i++) {
    	    	 for(int j=0;j<n;j++) {
    	    		 a[i][j]=input.nextDouble();    	    		 
    	    	 }
    	     }
    	     System.out.println("第一个矩阵构建成功:");
    	     printMatrix(1,a);    //打印第一个矩阵
    	     System.out.println("输入连续的数构成第二个矩阵:");
    	     for(int i=0;i<n;i++) {
    	    	 for(int j=0;j<k;j++) {
    	    		 b[i][j]=input.nextDouble();    	    		 
    	    	 }
    	     }
    	     System.out.println("第二个矩阵构建成功:");
    	     printMatrix(1,b);    //打印第二个矩阵
    	     //乘法
    	     double [][]c=multiplyMatrix(a, b);
    	     Thread.sleep(1000);
    	     System.err.println("第一个矩阵和第二个矩阵的乘积结果:");
    	     printMatrix(2,c);    //打印第一个矩阵和第二个矩阵的乘积
    	     input.close(); 
	}
	/**
	 * 乘积链
	 * @throws InterruptedException
	 */
	public static void printChain() throws InterruptedException {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入矩阵的个数：");
		int number=input.nextInt();//矩阵个数
		if(number<2) {
			throw new RuntimeException("矩阵个数小于2");
		}
		double [][][] all=new double[number][][];//所有矩阵
		int tempn=0;//缓存上一个列数
		for (int z = 0; z <number; z++) {
			int m=0;
		if(z==0) {
			System.out.println("输入第"+(z+1)+"个矩阵的行数：");
			m=input.nextInt();			
		}else {
			m=tempn;			
		}
		System.out.println("输入第"+(z+1)+"个矩阵的列数：");
		int n=input.nextInt();
		tempn=n;
		double [][]a=new double[m][n];  //定义一个m*n的矩阵
		System.out.println("输入连续的数构成第"+(z+1)+"个矩阵:");
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(input.hasNextFloat()){ 
				a[i][j]=input.nextDouble();
				}else {
					throw new RuntimeException("请输入正确的数");
				}
			}
		}
		System.out.println("第"+(z+1)+"个矩阵构建成功:");
		printMatrix(1,a);    //打印第z+1个矩阵
		all[z]=a;//把第z个矩阵放入all中
		}
		
		Thread.sleep(1000);
		double temp[][]=new double[][]{};
		for (int i = 0; i <all.length; i++) {
			if(i==0) {
				System.err.println("第"+(i+1)+"个矩阵和第"+(i+2)+"个矩阵的乘积结果:");
				temp=multiplyMatrix(all[i], all[i+1]);
				printMatrix(2,temp);    //打印
			}else if(i>1){
				System.err.println("结果矩阵和第"+(i+1)+"个矩阵的乘积结果:");
				temp=multiplyMatrix(temp, all[i]);
				printMatrix(2,temp);    //打印
			}
		}
		input.close(); 
	}
    public static void main(String[] args) throws InterruptedException {
    	printChain();
	}
	    
}
