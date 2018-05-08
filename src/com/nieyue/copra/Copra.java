package com.nieyue.copra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 自定义copra
 * @author Administrator
 *
 */
public class Copra {
	static int k=3;//k派系数
	static int vertexNumber=0;//顶点数量,默认为0
	public static Set<Integer> adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
	public static Set<Integer> kVertex=new HashSet<Integer>();//完全子图所有节点
	public static int[][] adjacencyList;//邻接表
	public static int[][] adjacencyMatrix;//邻接矩阵
	public static HashMap<Integer,Integer> vertexTag=new HashMap<>();//每个图与剩余节点对应 标签 集合
	public static HashMap<Integer,Double> tagInfluence=new HashMap<>();//每个标签的影响值
	public static long starttime=0;//开始时间
	public static long endtime=0;//结束时间
    public static void main(String[] args) {
     starttime=System.currentTimeMillis();
    // System.out.println("k2".replace("r", "").replace("k", ""));
    //1-1.初始化节点数据 （ 邻接表）
  /* adjacencyList=new int[][] {
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
    		};*/
    //从文件中读取数据
     adjacencyList=getFileData("src/com/nieyue/copra/Karate.txt");
     //1-2，获取所有顶点等初始化
     getAdjacencyVertex(adjacencyList);
    //1-3，邻接表转邻接矩阵
    adjacencyListChangeadjacencyMatrix(adjacencyList);
    // System.err.println(getEdgeByVertex(9));
   //2-1，获取完全子图的邻接表组合
    ArrayList<ArrayList<Integer>> kList=complie(adjacencyVertex);
    //2-1，获取完全子图
    int[][] karray = getK(kList);
    for (int i = 0; i < karray.length; i++) {
    	System.out.print("k派系完全子图,第"+(i+1)+"个： ");
    	for (int j = 0; j < karray[i].length; j++) {
    		System.out.print(" "+karray[i][j]);
    	}
    	System.out.println("");
	}
    //3，排序后的完全子图 ,降序
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
    //5-1，计算每个标签的影响值
    //System.out.println( getTagInfluence(2));
	for (Map.Entry<Integer, Double> entry : tagInfluence.entrySet()) {
		System.out.println("标签"+entry.getKey()+"的影响值："+entry.getValue());
	}
    endtime=System.currentTimeMillis();
   long costtime=endtime-starttime;
    System.err.println("花费的时间："+Double.valueOf(costtime)/1000+"s");
	}
    
    /**
     * 读取文件数据
     * 
     */
    public static int[][] getFileData(String path){
    	int[][] filedata;
    	List<List<Integer>> list=new ArrayList<>();
    	File file=new File(path);
    	if(file.exists() && file.isFile() ){ 
			InputStreamReader isr;
			try {
				isr = new InputStreamReader( new FileInputStream(file), "UTF-8");
				BufferedReader bufferreader = new BufferedReader(isr);
				String text_line = null;

				if((text_line = bufferreader.readLine()) == null ){
					System.out.println("网络中点的个数读取为空");
				} 
				while( (text_line=bufferreader.readLine()) != null ){
					//text_line=text_line.replaceAll("\\s*", "");
					String[] textlines = text_line.split(" ");
				/*	List<String> tls = new ArrayList<>();
					for (int i = 0; i < textlines.length; i++) {
						if(!textlines[i].isEmpty()){
							tls.add(textlines[i]);
						}
					}
					System.out.println(tls.size());
					int point1 = Integer.parseInt(tls.get(0).trim());
					int point2 = Integer.parseInt(tls.get(1).trim());*/
					int point1 = Integer.parseInt(textlines[0].trim());
					int point2 = Integer.parseInt(textlines[1].trim());
					List<Integer> l=new ArrayList<>();
					l.add(point1);
					l.add(point2);
					list.add(l);
				}
				bufferreader.close();
				isr.close();
				filedata=new int[list.size()][list.get(0).size()];
				for (int i = 0; i < filedata.length; i++) {
					for (int j = 0; j < filedata[0].length; j++) {
						filedata[i][j]=list.get(i).get(j);
					}
				}
				return filedata;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    		throw new RuntimeException("读取文件错误");
    	
    }
    /**
     * 根据k派系组合获取k派系
     *@param klist k派系组合
     * @return k派系
     */
    public static int[][] getK(ArrayList<ArrayList<Integer>> klist){
    	if(klist.size()<=0||klist.get(0).size()<3){
    		endtime=System.currentTimeMillis();
    		long costtime=endtime-starttime;
    	    System.err.println("花费的时间："+Double.valueOf(costtime)/1000+"s");
    		throw new RuntimeException("没有k派系");
    	}
    	int[][] karray=new int[klist.size()][klist.get(0).size()];
    	 for (int i = 0; i < klist.size(); i++) {
    	    	for (int j = 0; j < klist.get(i).size(); j++) {
    	    			karray[i][j]=klist.get(i).get(j);
    	    	}
    		}
    	return karray;
    }
    /**
     * 获取所有的顶点
     * @param adjacencyList 邻接表
     */
    public static Set<Integer> getAdjacencyVertex(int[][] adjacencyList ){
    	if(adjacencyList.length<k){
    		throw new RuntimeException("邻接表长度不够");
    	}
    	//获取所有顶点
    	for (int i = 0; i < adjacencyList.length; i++) {
			for (int j = 0; j < adjacencyList[i].length; j++) {
				adjacencyVertex.add(adjacencyList[i][j]);
			}
		}
    	//顶点个数初始化
    	vertexNumber=adjacencyVertex.size();
    	
    	return  adjacencyVertex;
    }
    /**
     * 获取完全子图的邻接表组合
     * @param adjacencyVertex 所有顶点
     */
    public static ArrayList<ArrayList<Integer>> complie(Set<Integer> adjacencyVertex ){
    	if(adjacencyList.length<k){
    		throw new RuntimeException("邻接表长度不够");
    	}
    	int[] av=new int[adjacencyVertex.size()];
    	ArrayList<Integer> adv = new ArrayList<Integer>(adjacencyVertex);
    	for (int i = 0; i < adv.size(); i++) {
			av[i]=adv.get(i);
		}
    	//获取所有的组合
    	ArrayList<ArrayList<Integer>> tempcomplieAdjacencyList = combination(av,av.length,k,new ArrayList<Integer>());

    	//提取k派系的组合 判断规则，n个顶点有n*(n-1)/2条边。如：3个顶点3条边，即只能有3个顶点才是完全子图
    	ArrayList<ArrayList<Integer>> klist=new ArrayList<>();
    	for (int j = 0; j < tempcomplieAdjacencyList.size(); j++) {
    		Set<Integer> set=new HashSet<>();
    		for (int j2 = 0; j2 < tempcomplieAdjacencyList.get(j).size(); j2++) {
    				 set.add(tempcomplieAdjacencyList.get(j).get(j2));
    		}
    		//如果n个顶点有n*(n-1)/2条边，则是k派系，即完全子图
    		if(set.size()==k && isCompleteSubgraph(new ArrayList<Integer>(set))){
    			klist.add(tempcomplieAdjacencyList.get(j));
    			kVertex.addAll(set);
    		}
		}
    	return  klist;
    }
  //组合数，通过返回值返回结果  
    public static ArrayList<ArrayList<Integer>>  combination(int[] A,int n,int m,ArrayList<Integer> t)  
    {  
        ArrayList<ArrayList<Integer>> res=new ArrayList<>();  
        if(m==0)  
        {  
            ArrayList<Integer> temp=new ArrayList<>(t);  
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
    	adjacencyMatrix=new int[vertexNumber][vertexNumber];
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
     *根据顶点获取边数，即度数
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
     *根据多个顶点获取所有边数
     *@param vertex 顶点 从1开始
     */
    public static int getEdgeByVertexs(List<Integer> vertexs) {
    	int edge=0;
    	if(vertexs.size()<1){
    		throw new RuntimeException("数组越界");
    	}
    	List<ArrayList<Integer>> tempadjacencyList=new ArrayList<ArrayList<Integer>>();
    	for (int i = 0; i < adjacencyList.length; i++) {
    		ArrayList<Integer> a=new ArrayList<Integer>();
    		for (int j = 0; j < adjacencyList[i].length; j++) {
    			a.add(adjacencyList[i][j]);
    		}
    		tempadjacencyList.add(a);
    	}
    	int tempadjacencyListSize=tempadjacencyList.size();
    	loop2:for (int i = 0; i < tempadjacencyListSize; i++) {
    		for (int j = 0; j < tempadjacencyList.get(i).size(); j++) {
    			for (int j2 = 0; j2 < vertexs.size(); j2++) {
	    			if( tempadjacencyList.get(i).get(j)==vertexs.get(j2)){//如果相等，就是对应的边
	    				edge+=1;
	    				tempadjacencyList.remove(tempadjacencyList.get(i));
	    				tempadjacencyListSize--;
	    				i--;
	    				continue loop2;
	    			}
    			}
    		}
    	}
    	return edge;
    }
    /**
     *判断是否完全子图，根据多个顶点获取自身边数 如:V(1,2,4),则获取1和2,1和4,2和4三条边是否都存在
     *@param vertex 顶点 从1开始
     */
    public static boolean isCompleteSubgraph(List<Integer> vertexs) {
    	boolean isCS=false;//默认
    	if(vertexs.size()<1){
    		throw new RuntimeException("数组越界");
    	}
    	int[] vts=new int[vertexs.size()];
    	for (int i = 0; i < vts.length; i++) {
    		vts[i]=vertexs.get(i);
    		}
    	//顶点组合 的所有边
    	ArrayList<ArrayList<Integer>> combination = combination(vts, vertexs.size(), 2, new ArrayList<Integer>());
    	List<ArrayList<Integer>> tempadjacencyList=new ArrayList<ArrayList<Integer>>();
    	for (int i = 0; i < adjacencyList.length; i++) {
    		ArrayList<Integer> a=new ArrayList<Integer>();
    		for (int j = 0; j < adjacencyList[i].length; j++) {
    			a.add(adjacencyList[i][j]);
    		}
    		tempadjacencyList.add(a);
    	}
    	isCS=tempadjacencyList.containsAll(combination);
    /*	int tempadjacencyListSize=tempadjacencyList.size();
    	for (int i = 0; i < tempadjacencyListSize; i++) {
    		int[] temp=new int[tempadjacencyList.get(i).size()];
    		for (int j = 0; j < tempadjacencyList.get(i).size(); j++) {
    			temp[j]=tempadjacencyList.get(i).get(j);
    		}
    		for (int j2 = 0; j2 < vertexs.size(); j2++) {
    		}
    	}*/
    	return isCS;
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
    		List<Integer> list=new ArrayList<>();
    		for (int j = 0; j < karray[i].length; j++) {
    			list.add(karray[i][j]);
    		}
    		temp[i][0]=getEdgeByVertexs(list);
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
    	//总结点
    	Set<Integer> settotal=adjacencyVertex;
    	//完全子图节点
    	Set<Integer> setk=kVertex;
    	settotal.removeAll(setk);
    	//剩余
    	ArrayList<Integer> remainderVertexList = new ArrayList<Integer>(settotal);
    	//获取剩余节点
    	remainderVertex=new int[remainderVertexList.size()];
    	for (int i = 0; i < remainderVertexList.size(); i++) {
    		remainderVertex[i]=remainderVertexList.get(i);
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
    	//key 为图或者节点 ，value为标签
    	for (int i = 1; i <= orderkarray.length; i++) {
    		vertexTag.put(i,i);
    		//设置标签影响值
    		tagInfluence.put(i,getTagInfluence(i));
		}
    	for (int i = orderkarray.length+1; i <= remainderVertex.length+orderkarray.length; i++) {
    		vertexTag.put(i,i);
    		//设置标签影响值
    		tagInfluence.put(i,getTagInfluence(i));
    	}
    }
    /**
     *  两个样本之间的欧式距离 dis = sqrt((x1-x2)^+(y1-y2)^)
     * @param a
     * @param b
     * @return
     */
    public static Double distance(int[] a, int[] b) {  
    	Double dis = 0.0;  
        for (int i = 0; i < a.length; i++) {  
        	Double temp = (Double)Math.pow((b[i] - a[i]), 2);  
            dis += temp;  
        }  
        dis = (Double) Math.sqrt(dis); 
        return dis;  
    } 
    public static Double distance(int a, int b) {  
    	Double dis = 0.0;  
    	Double temp = (Double)Math.pow((b - a), 2);  
    		dis += temp;  
    	dis = (Double) Math.sqrt(dis); 
    	return dis;  
    } 
    /**
     *每一个顶点所在边的权重值
     */
    public static Double getVertexByEdgeWeigth(Integer vertexId) {
    	//vertexId顶点的所有边,shamap中存放边的两个点
    	List<HashMap<Integer,Integer>> edgeList=new ArrayList<>();
    	for (int i = 0; i < adjacencyList.length; i++) {
				if(vertexId==adjacencyList[i][0]
						||vertexId==adjacencyList[i][1]){
					HashMap<Integer,Integer> hm=new HashMap<>();
					hm.put(adjacencyList[i][0], adjacencyList[i][1]);
					edgeList.add(hm);
				}
		}
    	//计算权重
    	Double sum = 0.0;  
    	List<Double> weights=new ArrayList<Double>();
    	//所有的顶点对应的标签遍历
    	for (int i = 0; i < edgeList.size(); i++) {
	    	for (Map.Entry<Integer, Integer> entry : edgeList.get(i).entrySet()) {
	    		//计算权重，weight[i][j]=exp(-欧式距离/方差)
	    		Double w=(Double) Math.exp( - Math.pow(distance(entry.getKey(), entry.getValue()),2) / Math.pow(1, 2));
	    		weights.add(w);
	    		sum += w; 
	    	}
    	}
         //归一化处理
    	/*for(int j = 0; j < weights.size(); j++) {  
    		weights.set(j, weights.get(j)/sum);  
    	} */
    	return sum;
    }
    /**
     *为x（图或者节点）邻接点的每一个标签l计算影响值
     */
    public static Double getTagInfluence(Integer l) {
    	Double influence=0.0;
    	Double wl = 0.0;//每一个标签的顶点所在边的权重值
    	Integer cl = 0;//标签l的总个数
    	Integer dl = 0;//所有标签为l的顶点度之和
    	//所有标签l的顶点
    	List<Integer> vertexIdList=new ArrayList<>();
    	//所有的顶点对应的标签遍历
    	for (Map.Entry<Integer, Integer> entry : vertexTag.entrySet()) {
    		//entry.getKey(), entry.getValue();
    		if(entry.getValue().equals(l)){
    			 cl++;//标签l的总个数
    			 Integer id = entry.getKey();
    			 vertexIdList.add(id);
    			 wl+=getVertexByEdgeWeigth(id);//获取标签l的顶点总权重
    		}
		}
    	dl=getEdgeByVertexs(vertexIdList);
    	Double ml=Math.pow(Math.E,-dl);
    	influence=wl/cl+(1-ml);
    	return influence;
    }
 
    /**
     * 获取EQ
     * 
     */
    public static Double getEQ(){
    	//去掉
    	String[] groupCluarray=new String[4];
    	//计算EQ
		double EQ = 0,EQ_temp=0;int m3=0;
		for(int i = 0,n=groupCluarray.length;i<n;i++){
			String[] grouppoint = groupCluarray[i].split(",");//获取第I个社区全部的点
			m3 = grouppoint.length;
			
			for(int iii=0;iii<m3-1;iii++){
				
				//表示节点v所属社区的数目
				//double Qv = Double.valueOf(map3.get(grouppoint[iii]).toString());
				//节点v的度
				//double Kv = Double.valueOf(map.get(grouppoint[iii]).toString().split(";")[0]);
				//表示节点w所属社区的数目
				double Qw = 0;
				//节点w的度
				double Kw = 0;
				//A为邻接矩阵
				double Avw= 0;
				
				for(int j=iii+1;j<m3;j++){
				/*	Qw = Double.valueOf(map3.get(grouppoint[j]).toString());
					Kw = Double.valueOf(map.get(grouppoint[j]).toString().split(";")[0]);
					String grouplink = map.get(grouppoint[iii]).toString().split(";")[1];
					
					if(grouplink.contains(",-"+grouppoint[j]+",")&&grouplink.contains(","+grouppoint[j]+"-,")){
						Avw=2.0;//社团C内双向边的数目和
					}else if(grouplink.contains(",-"+grouppoint[j]+",")||grouplink.contains(","+grouppoint[j]+"-,")){
						Avw=1.0;//社团C内单向边的数目和
					}*/
					//m表示总边数
					//EQ_temp = EQ_temp+(Avw-(Kv*Kw)/(Double.valueOf(2*m)))/(Qv*Qw);
				}
			}
		}
    	return EQ;
    }
}
