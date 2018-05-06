package com.nieyue.graph;

import java.util.Scanner;

/**
 * 图矩阵
 * @author Administrator
 *
 */
public class GraphMatrix {
	static final int MaxNum=20;         //图的最大顶点数
	static final int MaxValue=65535;    //最大值
	int GType;     //图的类型（0:无向图  1：有向图）
    int VertexNum;//顶点数量
    int EdgeNum;   //边的数量
    char[] Vertex=new char[MaxNum]; //保存顶点信息
    int[][] EdgeWeight=new int[MaxNum][MaxNum];//保存权
    /**
     * 创建图
     * @param gm
     */
    public static void creatGraph(GraphMatrix gm){
        int i,j,k;
        int weight;         //权
        char startV,endV;   //起始，终止顶点
        Scanner input=new Scanner(System.in);
        System.out.println("输入图中各顶点信息:");
        for(i=0;i<gm.VertexNum;i++){
            System.out.println("第"+(i+1)+"个顶点");
            gm.Vertex[i]=(input.next().toCharArray())[0];//保存到顶点数组中
        }
        System.out.println("输入各边的顶点及权值：");
        for(k=0;k<gm.EdgeNum;k++){
            System.out.println("第"+(k+1)+"条边");
            System.out.println("边的起点为：");
            startV=input.next().charAt(0);
            System.out.println("边的终点为：");
            endV=input.next().charAt(0);
            System.out.println("边的权值为：");
            weight=input.nextInt();
            for(i=0;gm.Vertex[i]!=startV;i++);  //在顶点数组中查找起点位置
            for(j=0;gm.Vertex[j]!=endV;j++);    //在顶点数组中查找终点位置
            gm.EdgeWeight[i][j]=weight;
            if(gm.GType==0){
                gm.EdgeWeight[j][i]=weight;
            }
        }
    }
    /**
     * 清除图
     * @param gm
     */
    static void clearGragh(GraphMatrix gm){
        for(int i=0;i<gm.VertexNum;i++)
            for(int j=0;j<gm.VertexNum;j++)
                gm.EdgeWeight[i][j]=gm.MaxValue;        //清空矩阵
    }
    /**
     * 显示图
     * @param gm
     */
    static void outGraph(GraphMatrix gm){
        for(int i=0;i<gm.VertexNum;i++){
            System.out.printf("\t%c",gm.Vertex[i]); //第一行输出顶点信息
        }
        System.out.println();
        for(int i=0;i<gm.VertexNum;i++){
            System.out.printf("%c",gm.Vertex[i]);
            for(int j=0;j<gm.VertexNum;j++){
                if(gm.EdgeWeight[i][j]==gm.MaxValue){
                    System.out.printf("\tZ");
                }else{
                    System.out.printf("\t"+gm.EdgeWeight[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
		creatGraph(new GraphMatrix());
	}
}
