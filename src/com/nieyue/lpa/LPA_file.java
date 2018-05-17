package com.nieyue.lpa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LPA_file {
	static int Vertex = 105; //解决N个节点的LPA算法	
	static int [][] Adjmartrix=new int[Vertex][Vertex];//图的邻接矩阵形式
	static List<int[]> Edge_graph = null;//图中所有的边
	static int[] Lable_t=new int[Vertex];//存放各个点的标签,t时刻，采用异步更新，更新过的lable与未更新的lable同时记录Lable_t中
	static int[] Lable_t_1=new int[Vertex];//存放各个点的标签,t-1时刻，用于判断划分是否结束
	static int[] Importance_sorting=new int[Vertex];//存放各个点的重要度，即
	static int[] Vertex_neighbour_lable=new int[Vertex];//统计v的邻接节点中标签的个数，下标为标签，值为标签为该值的节点个数
	static int MaxIteration = 50; //循环阈值
	
	static Map<Integer, List<Integer>> Communitys = null;//将标签作为键，节点序号列表作为值，保存到Map中

	/**********************数据初始化 从文件中读取图的内容*************************/
 	public static void Init_Graph(String filename){//
		File file = new File(filename);
    	String ecoding = "gbk";
		if(file.isFile() && file.exists() ){
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			Edge_graph = new ArrayList<int[]>();
			try {
				inputStreamReader = new InputStreamReader( new FileInputStream(file),ecoding);
				bufferedReader = new BufferedReader(inputStreamReader);
				String inputLine = null;
//				inputLine = bufferedReader.readLine(); //先默认处理7个节点的情况
//				String[] snodeNum=inputLine.split("-");
//				int nodeNum=Integer.parseInt(snodeNum[0]);
				//用二维数组表示原始图，需要注意的是，顶点序号减一才能使二维数组的下标与边的顶点的序号对应，
				while( ( inputLine = bufferedReader.readLine() ) != null ){
					String[] edge=inputLine.split("-");
					//从输入数据构造以二维数组形式表示的图
					for(int k = 0; k < edge.length; k++ ){
						String[] edgePoint = edge[k].split(":");
						int x1=Integer.parseInt(edgePoint[0]);
						int x2=Integer.parseInt(edgePoint[1]);
						Adjmartrix[x1-1][x2-1]=1;
						Adjmartrix[x2-1][x1-1]=1;
						int[] edgepoints = {x1,x2};
						Edge_graph.add(edgepoints);
					}
				}
				for(int i=0;i<Vertex;i++){//对每个节点进行赋值标签，初始化为下标值
					Lable_t[i]=i;
					Importance_sorting[i]=0;
					Lable_t_1[i]=0;
					Vertex_neighbour_lable[i]=0;
				}				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}finally {
				try {
					bufferedReader.close();
					inputStreamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
 	
	/**********************对重要度进行排序,重要度即为节点的度数*************************/
	public static void Initing_Degree_Sorting(List<int[]> Edge){//对每个节点进行赋值标签，初始化为下标值
		int[][] Degree_array_temp=new int[Vertex][2];
		int max=-1;
		int temp=0;
		int[] visited=new int[Vertex];
		for(int i=0;i<Vertex;i++){
			Degree_array_temp[i][0]=0;
			Degree_array_temp[i][1]=i;
			visited[i]=0;
		}
		int x[] = null;
		for(int i=0;i<Edge.size();i++){
			x = Edge.get(i);
			Degree_array_temp[x[0]-1][0]=Degree_array_temp[x[0]-1][0]+1;
			Degree_array_temp[x[1]-1][0]=Degree_array_temp[x[1]-1][0]+1;
		}
		for(int i=0;i<Degree_array_temp.length;i++){
			for(int j=0;j<Degree_array_temp.length;j++){
				if(max<Degree_array_temp[j][0]&&visited[j]==0){
					max=Degree_array_temp[j][0];
					temp=j;
				}	
			}
			max=-1;
			visited[temp]=1;
			Importance_sorting[i]=temp;
		}
	}
	
	
	/**********************标签的选择更新策略*************************/
	public static void Lable_Spread(){
		for(int i=0;i<Vertex;i++)//记录节点的当前标签，用于判断循环是否结束
			Lable_t_1[i]=Lable_t[i];	
		Lable_Update();
		//System.out.println("Lable_Spread:");
	}
	/**********************标签更新*************************/
	public static void Lable_Update(){
		for(int i=0;i<Vertex;i++){
			if(Is_isolated_vertex(Importance_sorting[i])){
				continue;//为节省时间，孤立节点直接跳过
			}
			else{//从节点temp的邻居节点中选一个节点，用该节点的标签作为temp的标签
				int temp=Importance_sorting[i];
				Lable_t[temp]=Lable_Select(temp);
			}		
		}
		//System.out.println("Lable_Update:");
	}
	/**********************选择标签：从节点v的邻居节点中选一个节点，该节点的标签用来更新节点v的标签*************************/
	public static int Lable_Select(int v){
		int vertex_lable_select = 0;
		Lable_count(v);	//统计v的邻接节点的标签及标签的个数，下标为标签，值为标签为该值的节点个数
		int max=-1;
		for(int i=0;i<Vertex;i++){
			//从节点v的邻居节点中，选出标签个数最大的标签，用来更新节点v的标签，若有相同个数的标签，则选择第一个标签。
			if(max<Vertex_neighbour_lable[Importance_sorting[i]]){
				max=Vertex_neighbour_lable[Importance_sorting[i]];
				vertex_lable_select=Importance_sorting[i];
				}
		}
		for(int i=0;i<Vertex;i++){//为下一次的Lable_count()做准备
			Vertex_neighbour_lable[i]=0;
		}
		//System.out.println("Lable_Select:");
		return vertex_lable_select;
	}
	/**********************对邻居节点标签个数进行初始化,采用异步更新*************************/
	//注意同步更新在处理二等分网络时，会引起震荡，无法收敛，转而采用异步更新
	public static void Lable_count(int v){
		//统计v的邻接节点中标签的个数，下标为标签，值为标签为该值的节点个数
		for(int i=0;i<Adjmartrix.length;i++){
			if(Adjmartrix[v][i]>0){//用当前时刻更新过的标签与未更新的标签对邻居节点标签个数进行初始化
				Vertex_neighbour_lable[Lable_t[i]]=Vertex_neighbour_lable[Lable_t[i]]+1;
			}
		}
		//System.out.println("Lable_count:");
	}
	/**********************判断是否是孤立节点*************************/
	public static boolean Is_isolated_vertex(int v){
		int temp=0;
		for(int i=0;i<Adjmartrix.length;i++){
			if(Adjmartrix[v][i]>0){//对邻居节点标签个数进行初始化
				temp++;
			}
		}
		//System.out.println("Is_isolated_vertex:");
		if(temp>0)
			return false;
		else
			return true;
	}
	
	
	/*总结划分结果,将社区标签作为键，社区内节点序号列表作为值，保存到Map中*/
	public static void Community_divided() {
		Communitys = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<Lable_t.length;i++){
			if(Communitys.get(Lable_t[i])==null){
				Communitys.put(Lable_t[i], new ArrayList<>());
				Communitys.get(Lable_t[i]).add(i);
			}else {
				Communitys.get(Lable_t[i]).add(i);
			}
		}
	}
	/*计算模块度,参考博客：https://blog.csdn.net/wangyibo0201/article/details/52048248?locationNum=2 中的定义一*/
	public static double getQ() {
		double q = 0;
		int communityNum = Communitys.size();
		int[][] communityMtr = new int[communityNum][communityNum];
		List<Integer> iList = null;
		List<Integer> jList = null;
		List<Integer> keyList = new ArrayList<>(Communitys.keySet());
		for (int i_index=0; i_index<keyList.size();i_index++ ) {
			iList = Communitys.get(keyList.get(i_index));
			for (int j_index=0;j_index<keyList.size();j_index++) {
				jList = Communitys.get(keyList.get(j_index));
				for(int i=0;i<iList.size();i++){
					for(int j=0;j<jList.size();j++){
						communityMtr[i_index][j_index] += Adjmartrix[iList.get(i)][jList.get(j)];
					}
				}
			}
		}
		float a=0,e=0;
		double a_2 = 0;
		for(int i=0;i<communityNum;i++){
			e += communityMtr[i][i];
			for(int j=0;j<communityNum;j++){
				if(i==j)
					continue;
				a += communityMtr[i][j];
			}
			a_2 += ((0.5*a/(float)Edge_graph.size())*(0.5*a/(float)Edge_graph.size()));
		}
	/*	System.out.println("e=" + e + " a=" + a);
		System.out.println("e=" + e + " a=" + a + " a_2=" + a_2);*/
		q = e/Edge_graph.size()*0.5 - a_2;
		return q;
	}
	
	public static void main(String[] args) {
		Long start=System.currentTimeMillis();
		int Iteration=0;
		/**********************初始化*************************/
		String filename = "src/com/nieyue/lpa/Karate.txt";
		//String filename = "src/com/nieyue/lpa/Dolphins.txt";
		//String filename = "src/com/nieyue/lpa/PolBooks.txt";
		//String filename = "src/com/nieyue/lpa/Football.txt";
		Init_Graph(filename);
		Initing_Degree_Sorting(Edge_graph);//对重要度进行排序，亦是随机序列
		
		
		/*****************对每一个节点，返回所有邻居中某标签数最多的标签值*****************/
		while(!Arrays.equals(Lable_t_1,Lable_t) && Iteration<MaxIteration){
			//由于同步更新在处理二等分网络时的循环震荡问题，这里控制循环次数
			Iteration++;
			Lable_Spread();
		}
		
		Community_divided();
		
		System.out.print("标签前的网络：");
		for(int i=0;i<Vertex;i++){
			System.out.print(i+" ");
		}
		System.out.print("\n");
		
		System.out.print("标签后的网络：");
		for(int i=0;i<Vertex;i++){
			System.out.print(Lable_t[i]+" ");
		}
		System.out.print("\n");
		int key = 0;
		List<Integer> value = null;
		for (Iterator<Integer> iterator = Communitys.keySet().iterator(); iterator.hasNext();) {
			key = (int) iterator.next();
			System.out.print("标签为" + key + "的网络中的节点序号为：");
			value = Communitys.get(key);
			for(int i=0;i<value.size();i++){
				System.out.print(value.get(i)+"\t");
			}
			System.out.print("\n");
		}
		System.out.println("划分后的模块度Q为：" + getQ());;
		System.out.println("Iteration:"+Iteration);
		Long end=System.currentTimeMillis();
		System.out.println("算法运行时间:"+(end-start)+"ms");
		
	}
}

