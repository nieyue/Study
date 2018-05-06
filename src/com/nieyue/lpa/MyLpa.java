package com.nieyue.lpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 自定义lpa
 * @author Administrator
 *
 */
public class MyLpa {
	static int VertexNumber=7;//顶点数量
	public static int[][] adjacencyMatrix=new int[VertexNumber][VertexNumber];//邻接矩阵
	public static int[][] tag=new int[VertexNumber][1];//每个顶点的标签集合
	public static int[] get_t=new int[VertexNumber];//存放各个点的标签,t时刻
	public static int[] get_t1=new int[VertexNumber];//存放各个点的标签,t-1时刻
    public static void main(String[] args) {
    //邻接表
    int[][] adjacencyList= {{1,2},{1,3},{2,3},{2,4}};
		MyLpa.init(adjacencyList);
		for(int i=0;i<adjacencyMatrix.length;i++)  
	    System.out.println(Arrays.toString(adjacencyMatrix[i]));
		setT();
		sort(tag);
	}
    /**
     * 初始化 
     * 1.邻接表转邻接矩阵
     * 2.为网络中的每个节点分配一个唯一的标签，对于节点x,有Gx(0)=x;
     */
    public static void init(int[][] adjacencyList) {
    	//邻接表转邻接矩阵
    	for(int i=0;i<adjacencyList.length;i++){
    		adjacencyMatrix[adjacencyList[i][0]-1][adjacencyList[i][1]-1]=1;
    		adjacencyMatrix[adjacencyList[i][1]-1][adjacencyList[i][0]-1]=1;
		}
    	//为网络中的每个节点分配一个唯一的标签，对于节点x,有Gx(0)=x;
    	/*for (int i = 0; i < tag.length; i++) {
			for (int j = 0; j < tag[i].length; j++) {
				tag[i][j]=i*tag.length+j+1;
				System.out.println(tag[i][j]);
			}
		}*/
    	for (int i = 0; i < tag.length; i++) {
    			tag[i][0]=i+1;
    			//System.out.println(tag[i][0]);
    	}
    }
    /**
     * 设置t=1 
     */
    public static void setT() {
    	//邻接表转邻接矩阵
    	for (int i = 0; i < get_t.length; i++) {
    		get_t[i]=1;
    	//System.out.println(adjacencyMatrix[i][j]);
    	}
    }
    /**
     *将网络中的节点随机排序，假设顺序为x.
     */
    public static void sort(int[][] tag) {
    	List<int[]> list=Arrays.asList(tag);
    	Collections.shuffle(list);
    	tag=(int[][]) list.toArray();
    	/*for(int i = 0; i < tag.length; i++){
   		 for (int j = 0; j < tag[i].length; j++) {
			System.err.println(tag[i][j]);	
			}
        }*/
    }
    /**
     *将网络中的节点随机排序，假设顺序为x.
     */
    public static void update() {
    	
    }
    
}
