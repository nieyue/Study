package com.nieyue.copra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义copra
 * @author Administrator
 *
 */
public class Copra {
	static int k=3;//k派系数
	static int VertexNumber=9;//顶点数量
	public static int[][] adjacencyMatrix=new int[VertexNumber][VertexNumber];//邻接矩阵
	public static int[][] tag=new int[VertexNumber][1];//每个顶点的标签集合
    public static void main(String[] args) {
    long starttime=System.currentTimeMillis();
    //1.初始化节点数据 （ 邻接表）
    int[][] adjacencyList= {
    		{1,2},
    		{1,3},
    		{1,4},
    		{2,3},
    		{3,4},
    		{4,5},
    		{4,6},
    		{5,6},
    		{5,7},
    		{5,8},
    		{6,7},
    		{6,8},
    		{7,8},
    		{7,9}
    		};
   //2-1，获取完全子图的邻接表组合
    ArrayList<int[][]> kList=complie(adjacencyList,3);
    //2-2，获取完全子图
    int[][] karray = getK(kList);
    for (int i = 0; i < karray.length; i++) {
    	System.out.print("k派系完全子图,第"+(i+1)+"个： ");
    	for (int j = 0; j < karray[i].length; j++) {
    		System.out.print(" "+karray[i][j]);
    	}
    	System.out.println("");
	}
    //3-1，邻接表转邻接矩阵
    adjacencyListChangeadjacencyMatrix(adjacencyList);
   // System.err.println(getEdgeByVertex(9));
    //3-2，排序后的完全子图 ,降序
    int[][] orderkarray = sort(karray);
    for (int i = 0; i < orderkarray.length; i++) {
    	System.out.print("排序后的k派系完全子图,第"+(i+1)+"个： ");
    	for (int j = 0; j < orderkarray[i].length; j++) {
    		System.out.print(" "+orderkarray[i][j]);
    	}
    	System.out.println("");
	}
    
    //4-1，获取剩余节点
    int[] remainderVertex = getRemainderVertex(adjacencyList,orderkarray);
    //4-2，为每个Clique和剩余节点分配一个唯一的标签
    assignTag(orderkarray,remainderVertex);
    long endtime=System.currentTimeMillis();
    long costtime=endtime-starttime;
    System.err.println("花费的时间："+Double.valueOf(costtime)/1000+"s");
	}
    
    
    
    /**
     * 根据k派系组合获取k派系
     *@param klist k派系组合
     * @return k派系
     */
    public static int[][] getK(ArrayList<int[][]> klist){
    	int[][] karray=new int[klist.size()][klist.get(0).length];
    	 for (int i = 0; i < klist.size(); i++) {
    		 Set<Integer> set=new HashSet<>();
    	    	for (int j = 0; j < klist.get(i).length; j++) {
    	    		for (int z = 0; z < klist.get(i)[j].length; z++) {
    	    			set.add(klist.get(i)[j][z]);
    	    		}
    	    	}
    	    	List<Integer> result = new ArrayList<>(set);
    	    	for (int j = 0; j < result.size(); j++) {
					karray[i][j]=result.get(j);
				}
    		}
    	return karray;
    }
    /**
     * 获取组合
     * @param adjacencyList 邻接表
     * @param number k系数   
     */
    public static ArrayList<int[][]> complie(int[][] adjacencyList ,int number){
    	int complieNumber=0;//组合后的k派系数量
    	if(adjacencyList.length<number){
    		throw new RuntimeException("邻接表长度不够");
    	}
    	long tempn=1;//临时n!
    	long tempm=1;//临时m!
    	long tempnm=1;//临时(n-m)!
    	for (int i = 1; i <=adjacencyList.length; i++) {
    		tempn*=i;
		}
    	for (int i = 1; i <=number; i++) {
    		tempm*=i;
    	}
    	for (int i = 1; i <=(adjacencyList.length-number); i++) {
    		tempnm*=i;
    	}
    	//组合的数目
    	complieNumber=(int) (tempn/tempm/tempnm);
    	//所有的组合初始化
    	int[][][] complieAdjacencyList=new int[complieNumber][number][adjacencyList[0].length];
    	//获取所有的组合
    	ArrayList<ArrayList<int[]>> tempcomplieAdjacencyList = combination(adjacencyList,adjacencyList.length,number,new ArrayList<int[]>());
    	//System.err.println(tempcomplieAdjacencyList.size());
    	//list转数组
    	for (int i = 0; i < tempcomplieAdjacencyList.size(); i++) {
			for (int j = 0; j < tempcomplieAdjacencyList.get(i).size(); j++) {
				complieAdjacencyList[i][j]=tempcomplieAdjacencyList.get(i).get(j);
				for (int z = 0; z < tempcomplieAdjacencyList.get(i).get(j).length; z++) {
				  //System.err.print(" "+complieAdjacencyList[i][j][z]);
				}
				//System.err.print(" ");
			}
			//System.err.println(" ");
		}
    	//提取k派系的组合 判断规则，n个顶点有n*(n-1)/2条边。如：3个顶点3条边，即只能有3个顶点才是完全子图
    	ArrayList<int[][]> klist=new ArrayList<>();
    	for (int i = 0; i < complieAdjacencyList.length; i++) {
    		Set<Integer> set=new HashSet<>();
			for (int j = 0; j < complieAdjacencyList[i].length; j++) {
				for (int j2 = 0; j2 < complieAdjacencyList[i][j].length; j2++) {
					//complieAdjacencyList[i][j]=adjacencyList[j];//把原来的组合放入组合数组
					 set.add(complieAdjacencyList[i][j][j2]);
					// System.err.print("  "+complieAdjacencyList[i][j][j2]);
				}
				//System.err.print(" ");
			}
			//System.err.println(" ");
			
			//System.err.println(set.size());
			//如果n个顶点有n*(n-1)/2条边，则是k派系，即完全子图
			if(set.size()==number*(number-1)/2){
				klist.add(complieAdjacencyList[i]);
			}
		}
    	return  klist;
    }
  //组合数，通过返回值返回结果  
    public static ArrayList<ArrayList<int[]>>  combination(int[][] A,int n,int m,ArrayList<int[]> t)  
    {  
        ArrayList<ArrayList<int[]>> res=new ArrayList<>();  
        if(m==0)  
        {  
            ArrayList<int[]> temp=new ArrayList<>(t);  
            res.add(temp);  
            return res;  
        }  
        else  
        {  
            for(int i=A.length-n;i<=A.length-m;i++)  
            {  
            	t.add(A[i]);  					
                res.addAll(combination(A,A.length-i-1,m-1,t));  
                t.remove(t.size()-1);  
            }  
            return res;  
        }  
    } 
    /**
     * 邻接表转邻接矩阵 
     * @param adjacencyList 邻接表
     */
    public static void adjacencyListChangeadjacencyMatrix(int[][] adjacencyList) {
    	//邻接表转邻接矩阵
    	for(int i=0;i<adjacencyList.length;i++){
    		adjacencyMatrix[adjacencyList[i][0]-1][adjacencyList[i][1]-1]=1;
    		adjacencyMatrix[adjacencyList[i][1]-1][adjacencyList[i][0]-1]=1;
		}
    	System.out.println("邻接表转邻接矩阵 :");
    	for(int i=0;i<adjacencyMatrix.length;i++)  
    	    System.out.println(Arrays.toString(adjacencyMatrix[i]));
    }
    /**
     *根据顶点获取边，即度数
     *@param vertex 顶点 从1开始
     */
    public static int getEdgeByVertex(int vertex) {
    	int edge=0;
    	if(vertex<1||vertex>adjacencyMatrix[0].length){
    		throw new RuntimeException("数组越界");
    	}
    	for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(vertex-1==j){
					edge+=adjacencyMatrix[i][j];
				}
			}
		}
    	return edge;
    }
    
    /**
     *对所发现的完全子图按综合度排序 降序
     *每个图的每个顶点的边相加
     *@param karray完全子图
     */
    public static int[][] sort(int[][] karray) {
    	//排序数组； 行为完全子图下表；列为综合度数
    	int[][] temp=new int[karray.length][1];
    	//每个图的每个顶点的边相加
    	for(int i = 0; i < karray.length; i++){
    		for (int j = 0; j < karray[i].length; j++) {
    			temp[i][0]+=getEdgeByVertex(karray[i][j]);
    		}
    		System.out.println("k派系完全子图,第"+(i+1)+"个的综合度："+temp[i][0]);
    	}
    	//排序过了的k派系
    	//int[][] orderkarray=new int[karray.length][karray[0].length];
    	int[][] orderkarray=karray;
    	int[] orderkarrayt=orderkarray[0];
    	
    	//根据综合度数排序
    		int[] t=temp[0];	
    		for (int i = 0; i < temp.length-1; i++) {
    			for (int j = 0; j < temp.length-1-i; j++) {
	    			if(temp[j][0]<temp[j + 1][0]){
	    				 t = temp[j];
	    				 temp[j] = temp[j + 1];
	    				 temp[j + 1] = t;
	    				 
	    				 orderkarrayt = orderkarray[j];
	    				 orderkarray[j] = orderkarray[j + 1];
	    				 orderkarray[j + 1] = orderkarrayt;
	    				 
	    			}
    			}
    		}
    	return orderkarray;
    }
    /**
     *获取剩余节点
     *@param adjacencyList 初始化数据节点
     *@param orderkarray 完全子图
     *@return remainderVertex 剩余节点
     */
    public static int[] getRemainderVertex(int[][] adjacencyList,int[][] orderkarray) {
    	int[] remainderVertex;
    	//总节点
    	Set<Integer> set=new HashSet<Integer>();
    	for (int i = 0; i < adjacencyList.length; i++) {
			for (int j = 0; j < adjacencyList[i].length; j++) {
				set.add(adjacencyList[i][j]);
			}
		}
    	//k派系节点
    	Set<Integer> kset=new HashSet<Integer>();
    	for (int i = 0; i < orderkarray.length; i++) {
    		for (int j = 0; j < orderkarray[i].length; j++) {
    			kset.add(orderkarray[i][j]);
    		}
    	}
    	List<Integer> list=new ArrayList<>(set);
    	List<Integer> klist=new ArrayList<>(kset);
    	//去掉相同的，留下孤立节点。
    	for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < klist.size(); j++) {
				if(list.get(i)==klist.get(j)){
					list.remove(i);
				}
			}
		}
    	//获取剩余节点
    	remainderVertex=new int[list.size()];
    	for (int i = 0; i < list.size(); i++) {
    		remainderVertex[i]=list.get(i);
    		//System.err.println(remainderVertex[i]);
		}
    	return remainderVertex;
    }
    /**
     *为每个Clique和剩余节点分配一个唯一的标签 ，这里我是把每个完全子图的所有节点标签设置一样。
     *@param orderkarray 完全子图
     *@param remainderVertex 剩余节点
     */
    public static void assignTag(int[][] orderkarray,int[] remainderVertex) {
    	//把完全子图和剩余节点分配一个唯一标签。
    	int[][] tagorderkarray=new int[orderkarray.length][orderkarray[0].length];
    	int[] tagremainderVertex=new int[remainderVertex.length];
    	
    }
 
    
}
