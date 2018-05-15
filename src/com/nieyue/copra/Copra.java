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
import java.util.Map.Entry;
import java.util.Set;


/**
 * 改进的copra
 * @author Administrator
 *
 */
public class Copra {
	static int vertexNumber=0;//顶点数量,默认为0
	public static Set<Integer> adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
	public static Set<Integer> kVertex=new HashSet<Integer>();//完全子图所有节点
	public static int[][] adjacencyList;//邻接表
	public static int[][] adjacencyMatrix;//邻接矩阵
	public static HashMap<Integer,List<Integer>> vertexTag=new HashMap<>();//每个图与剩余节点对应 标签 集合
	public static HashMap<Integer,Boolean> vertexIsOver=new HashMap<>();//顶点稳定
	public static HashMap<Integer,Double> tagInfluence=new HashMap<>();//每个标签的影响值
	public static Map<Integer,List<Integer>> group=new HashMap<>();//社区
	public static int iteratorNumber=0;//迭代次数
	public static long starttime=0;//开始时间
	public static long endtime=0;//结束时间
    public static void main(String[] args) {
     starttime=System.currentTimeMillis();
    // System.out.println("k2".replace("r", "").replace("k", ""));
    //1-1.初始化节点数据 （ 邻接表）
  adjacencyList=new int[][] {
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
    		{7,9},
    		{9,10},
    		{10,11},
    		{11,12},
    		{12,10},
    		};
    //从文件中读取数据
    adjacencyList=getFileData("src/com/nieyue/copra/Karate.txt");
    //adjacencyList=getFileData("src/com/nieyue/copra/Karate2.txt");
    //adjacencyList=getFileData("src/com/nieyue/copra/Dolphins.txt");
    //adjacencyList=getFileData("src/com/nieyue/copra/polBooks.txt");
   // adjacencyList=getFileData("src/com/nieyue/copra/Football.txt");
     //1-2，获取所有顶点等初始化
     getAdjacencyVertex(adjacencyList);
    //1-3，邻接表转邻接矩阵
    adjacencyListChangeadjacencyMatrix(adjacencyList);
    // System.err.println(getEdgeByVertex(9));
   //2-1， 获取所有不重复极大完全子图的列表 
    ArrayList<ArrayList<Integer>> karray=getMaxK(adjacencyVertex);
    //3-1，排序后的完全子图 ,降序
    int[][] orderkarray = sort(karray);
    //4-1，获取剩余节点
    int[] remainderVertex = getRemainderVertex(adjacencyList,orderkarray);
    //4-2，获取每一个标签影响值，为每个不重复极大派系和剩余节点分配一个唯一的标签
    assignTag(orderkarray,remainderVertex);
	//5-1，迭代发现社区，输出EQ值
	discoveryCommunity(vertexTag);
    endtime=System.currentTimeMillis();
   long costtime=endtime-starttime;
   System.out.println("");
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
     * 获取所有的顶点
     * @param adjacencyList 邻接表
     */
    public static Set<Integer> getAdjacencyVertex(int[][] adjacencyList ){
    	//判断节点是否从0开始，如果是转化为1开始
    	boolean isfromzero=false;
    	loop:
    		for (int i = 0; i < adjacencyList.length; i++) {
    		for (int j = 0; j < adjacencyList[i].length; j++) {
    			if(adjacencyList[i][j]==0) {
    				isfromzero=true;
    				break loop;
    			}
    		}
    	}
    	if(isfromzero) {
    		for (int i = 0; i < adjacencyList.length; i++) {
        		for (int j = 0; j < adjacencyList[i].length; j++) {
        			adjacencyList[i][j]+=1;
        		}
        	}
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
     * 获取所有不重复极大完全子图的列表 
     * @param adjacencyVertex 所有顶点
     */
    public static ArrayList<ArrayList<Integer>> getMaxK(Set<Integer> adjacencyVertex ){
    	//所有顶点
    	ArrayList<Integer> adv = new ArrayList<Integer>(adjacencyVertex);
    	//存放最大完全子图
    	ArrayList<ArrayList<Integer>> klist=new ArrayList<>();
    	int advwl=adv.size();
    	for (int i = 0; i < advwl; i++) {
    		//存放每一个最大完全子图
    		ArrayList<Integer> list=new ArrayList<>();
    		int advl=adv.size();
	    	for (int j = i; j < advl; j++) {
	    		boolean b=true;//默认都有连接
	    		//判断当前顶点加入完全图之后是否仍是一个完全图
	    		if(j==i){
	    			list.add(adv.get(j));    			
	    		}
	    		if(j>=i+1){
	    			for (int z = 0; z < list.size(); z++) {
	    				//判断该顶点和团中顶点是否都有连接,只要有一个没连接，则不是
						if(!twoVertex(adv.get(j),list.get(z))){
							b=false;
							break;
						}
					}
	    			if(b){
	    				list.add(adv.get(j));    			
	    			}
	    		}
			}
	    	//大于等于3个才是完全子图
	    	if(list.size()>=3){
	    		klist.add(list);
	    		//去掉重复点
	    		adv.removeAll(list);
	    	}
    	}
    	if(klist.size()<=0||klist.get(0).size()<3){
    		//endtime=System.currentTimeMillis();
    		//long costtime=endtime-starttime;
    	   // System.err.println("花费的时间："+Double.valueOf(costtime)/1000+"s");
    		//throw new RuntimeException("没有k派系");
    		System.err.println("没有k派系,单个节点传播");
    		return klist;
    	}
   	  System.out.println("k派系节点不重复的完全子图：");
   	    for (int i = 0; i < klist.size(); i++) {
   	    	System.out.print("k派系完全子图,第"+(i+1)+"个： ");
   	    	for (int j = 0; j < klist.get(i).size(); j++) {
   	    		System.out.print(" "+klist.get(i).get(j));
   	    	}
   	    	System.out.println("");
   		}
    	return  klist;
    }
  /**
   * 判断两个顶点是否是边存在邻接表中
   * @param vertex1
   * @param vertex2
   * @return
   */
    public static boolean  twoVertex(int vertex1,int vertex2)  
    {  boolean b=false;
    	if(vertex1==vertex2){
    		return b;
    	}
    for (int i = 0; i < adjacencyList.length; i++) {
			if((adjacencyList[i][0]==vertex1&&adjacencyList[i][1]==vertex2)
				||adjacencyList[i][1]==vertex1&&adjacencyList[i][0]==vertex2){
				b=true;
				break;
			}
	}
       return b;
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
     *根据顶点获取邻接点
     *@param vertex 顶点 从1开始
     */
    public static List<Integer> getAdjacencyVertexByVertexs(int vertex) {
    	List<Integer> list=new ArrayList<>();
    	List<int[]> tempadjacencyList=new ArrayList<int[]>();
    	for (int i = 0; i < adjacencyList.length; i++) {
    		ArrayList<Integer> a=new ArrayList<Integer>();
    		for (int j = 0; j < adjacencyList[i].length; j++) {
    			if( adjacencyList[i][j]==vertex){//如果相等，就是对应的边
    				a.add(adjacencyList[i][j]);
    			}
    		}
    		if(a.size()>0){    			
    		tempadjacencyList.add(adjacencyList[i]);
    		}
    	}
    	//循环去除自身
    	for (int i = 0; i < tempadjacencyList.size(); i++) {
    		for (int j = 0; j < tempadjacencyList.get(i).length; j++) {
    			if(tempadjacencyList.get(i)[j]!=vertex&& !list.contains(tempadjacencyList.get(i)[j])){
    				list.add(tempadjacencyList.get(i)[j]);
    			}
    		}
		}
    	return list;
    }
    /**
     *根据多个顶点获取包含自身的多个邻接点
     *@param keyVertex 需要去掉的自身点
     *@param vertexs所有需要查找的顶点
     */
    public static List<Integer> getAdjacencyVertexsByVertexs(Integer keyVertex,List<Integer> vertexs) {
    	List<Integer> list=new ArrayList<>();
    	List<int[]> tempadjacencyList=new ArrayList<int[]>();
    	for (int i = 0; i < adjacencyList.length; i++) {
    		ArrayList<Integer> a=new ArrayList<Integer>();
    		for (int j = 0; j < adjacencyList[i].length; j++) {
    			for (int j2 = 0; j2 < vertexs.size(); j2++) {
    			if( adjacencyList[i][j]==vertexs.get(j2)){//如果相等，就是对应的边
    				a.add(adjacencyList[i][j]);
    			}
    			}
    		}
    		if(a.size()>0){    			
    			tempadjacencyList.add(adjacencyList[i]);
    		}
    	}
    	//循环去除自身
    	for (int i = 0; i < tempadjacencyList.size(); i++) {
    		for (int j = 0; j < tempadjacencyList.get(i).length; j++) {
    			for (int j2 = 0; j2 < vertexs.size(); j2++) {
	    			if(tempadjacencyList.get(i)[j]!=vertexs.get(j2)
	    					&& !list.contains(tempadjacencyList.get(i)[j])){
	    				list.add(tempadjacencyList.get(i)[j]);
	    			}
    			}
    		}
		}
    	/*System.out.println("-----------------");
    	vertexs.forEach(System.out::print);
    	System.out.println("");
    	list.forEach(System.err::print);
    	System.out.println("");
    	System.out.println(keyVertex);
    	System.out.println("-----------------");*/
    	return list;
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
     *对所发现的完全子图按综合度排序 降序
     *每个图的每个顶点的边相加
     *@param karray完全子图
     */
    public static int[][] sort(ArrayList<ArrayList<Integer>> karray) {
    	if(karray.size()<=0) {
    		return new int[][] {};
    	}
    	//排序数组； 行为完全子图下表；列为综合度数
    	int[][] temp=new int[karray.size()][1];
    	int[][] teampkarray=new int[karray.size()][];
    	//每个图的每个顶点的边相加
    	for(int i = 0; i < karray.size(); i++){
    		List<Integer> list=new ArrayList<>();
    		int[] subteampkarray=new int[karray.get(i).size()];
    		for (int j = 0; j < karray.get(i).size(); j++) {
    			list.add(karray.get(i).get(j));
    			subteampkarray[j]=karray.get(i).get(j);
    		}
    		teampkarray[i]=subteampkarray;
    		temp[i][0]=getEdgeByVertexs(list);
    		System.out.println("k派系完全子图,第"+(i+1)+"个的综合度："+temp[i][0]);
    	}
    	//排序过了的k派系
    	//int[][] orderkarray=new int[karray.length][karray[0].length];
    	int[][] orderkarray=teampkarray;
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
    		//打印
    	    for (int i = 0; i < orderkarray.length; i++) {
    	    	System.out.print("排序后的k派系完全子图,第"+(i+1)+"个： ");
    	    	for (int j = 0; j < orderkarray[i].length; j++) {
    	    		kVertex.add(orderkarray[i][j]);
    	    		System.out.print(" "+orderkarray[i][j]);
    	    	}
    	    	System.out.println("");
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
    	Set<Integer> settotal=new HashSet<>(adjacencyVertex);;
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
     *为每个Clique和剩余节点分配一个唯一的标签 
     *@param orderkarray 完全子图
     *@param remainderVertex 剩余节点
     */
    public static void assignTag(int[][] orderkarray,int[] remainderVertex) {
    	//把完全子图和剩余节点分配一个唯一标签。
    	//key 为图或者节点 ，value为标签
    	int aaa=0;
    	for (int i = 0; i < orderkarray.length; i++) {
    		for (int j = 0; j < orderkarray[i].length; j++) {
    		List<Integer> list=new ArrayList<>();
    		aaa++;
    		list.add(aaa);
    		vertexTag.put(aaa,list);
    		//设置标签影响值
    		tagInfluence.put(aaa,getTagInfluence(aaa));
    		}
		}
    	if(orderkarray.length<=0) {
    		for (int i = 1; i <= remainderVertex.length; i++) {
    			List<Integer> list=new ArrayList<>();
    			list.add(i);
    			vertexTag.put(i,list);
    			//设置标签影响值
    			tagInfluence.put(i,getTagInfluence(i));
    		}
    	}else {
			for (int i = kVertex.size()+1; i <= remainderVertex.length+kVertex.size(); i++) {
				List<Integer> list=new ArrayList<>();
				list.add(i);
				vertexTag.put(i,list);
				//设置标签影响值
				tagInfluence.put(i,getTagInfluence(i));
			}
    	}
    	//打印每个标签的影响值
        //System.out.println( getTagInfluence(2));
    	for (Map.Entry<Integer, Double> entry : tagInfluence.entrySet()) {
    		System.out.println(vertexTag.get(entry.getKey())+"标签"+entry.getKey()+"的影响值："+entry.getValue());
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
    	for (Map.Entry<Integer, List<Integer>> entry : vertexTag.entrySet()) {
    		//entry.getKey(), entry.getValue();
    		for (int i = 0; i < entry.getValue().size(); i++) {
    			if(entry.getValue().get(i).equals(l)){
    				cl++;//标签l的总个数
    				Integer id = entry.getKey();
    				vertexIdList.add(id);
    				wl+=getVertexByEdgeWeigth(id);//获取标签l的顶点总权重
    			}				
			}
		}
    	dl=getEdgeByVertexs(vertexIdList);
    	Double ml=Math.pow(Math.E,-dl);
    	influence=wl/cl+(1-ml);
    	return influence;
    }
    
    /**
     * 发现社区
     * 
     */
    public static void discoveryCommunity(Map<Integer,List<Integer>> tempVertexTag){
    	//临时的
    	int tempVertexIsOverSize=vertexIsOver.size();
   	//遍历vertexTag每个图与剩余节点对应 标签 集合
    for (Map.Entry<Integer, List<Integer>> entry : tempVertexTag.entrySet()) {
    		//List<Integer> vertexList=new ArrayList<>();
    		Set<Integer> vertexSet=new HashSet<>();
    		//获取当前节点的所有邻接点
    		List<Integer> avl = getAdjacencyVertexByVertexs(entry.getKey());
    		//获取多个顶点的不包含自身的多个邻接点
    		//List<Integer> aavl = getAdjacencyVertexsByVertexs(entry.getKey(),avl);
    		//从所有邻接点中选出最大的影响值,默认自身的影响值最大
    		Double maxInfluence=tagInfluence.get(entry.getValue().get(0));
    		//最大的标签
    		Integer maxValue=entry.getValue().get(0);
    		//找到自身节点最大的影响值和标签
    		for (int i = 0; i < entry.getValue().size(); i++) {
    			if(tagInfluence.get(entry.getValue().get(i))>=maxInfluence){
					maxInfluence=tagInfluence.get(entry.getValue().get(i));
					maxValue=entry.getValue().get(i);//缓存更新标签
				}
			}
    		int noupdatenumber=1;//默认1，表示当前节点标签与邻接点标签相等
    		int isequals=0;//默认0，表示当前邻接点与邻接点的邻接点不相等,1代表相同社区，2++代表完全子图
    		
    		//当前节点的所有邻接点的所有标签的影响值 分组，
			Map<Integer,Double> currentVertexTagInfluences=new HashMap<>();
			//遍历所有邻接点
			for (int i = 0; i < avl.size(); i++) {
				Integer avle = avl.get(i);
				 List<Integer> vtte = vertexTag.get(avle);
				int vttes=vtte.size();
				//遍历所有邻接点的所有标签，
				for (int j = 0; j <vttes ; j++) {
					if(currentVertexTagInfluences.get(vertexTag.get(avl.get(i)).get(j))==null){
						//第一次存
						currentVertexTagInfluences.put(vertexTag.get(avl.get(i)).get(j), tagInfluence.get(vertexTag.get(avl.get(i)).get(j)));
						//currentVertexTagInfluences.put(vertexTag.get(avl.get(i)).get(j), 1.0);
					}else{
						currentVertexTagInfluences.put(vertexTag.get(avl.get(i)).get(j), currentVertexTagInfluences.get(vertexTag.get(avl.get(i)).get(j))+tagInfluence.get(vertexTag.get(avl.get(i)).get(j)));
						//currentVertexTagInfluences.put(vertexTag.get(avl.get(i)).get(j), currentVertexTagInfluences.get(vertexTag.get(avl.get(i)).get(j))+1);
						
					}
				}
			}
			//排序，默认最大的key为第一个
			Integer maxctif=vertexTag.get(avl.get(0)).get(0);
			for (Entry<Integer, Double> cvti : currentVertexTagInfluences.entrySet()) {
				if(cvti.getValue()>currentVertexTagInfluences.get(vertexTag.get(avl.get(0)).get(0))){
					maxctif=cvti.getKey();
				}
			}
			//avl.stream().forEach(System.err::print);
			//System.out.println(currentVertexTagInfluences);
			//遍历所有邻接点
    		for (int i = 0; i < avl.size(); i++) {
    			List<Integer> aaavl= getAdjacencyVertexByVertexs(avl.get(i));
    			//获取当前邻接点的所有邻接点
    			for (int j = 0; j < aaavl.size(); j++) {
    				for (int z = 0; z< avl.size(); z++) {
	    				if(avl.get(z).equals(aaavl.get(j))) {
	    					isequals++;
	    					//大于等于1,不需再运算
	    					if(isequals>=1){
	    						break;        					
	    					}
	    				}
    				}
    			}
    			//如果当前标签集合不包含邻接点的标签集合，需要遍历
    			if(!entry.getValue().containsAll(vertexTag.get(avl.get(i)))){
    				noupdatenumber=0;
    			}
    			
    			//遍历当前节点的邻接点的所有标签，
    			for (int j = 0; j < vertexTag.get(avl.get(i)).size(); j++) {
    				//0，不相等返回自身的
    				if(isequals==0){
    					//vertexSet=new HashSet<>();
    					vertexSet.addAll(entry.getValue());
    					//vertexList=entry.getValue();    					
    				}
    				//
    				if( vertexTag.get(avl.get(i)).get(j)==maxctif
    						//&& currentVertexTagInfluences.get(vertexTag.get(avl.get(i)).get(j))>maxInfluence
    						){
    					//if(isequals>=2 && tagInfluence.get(vertexTag.get(avl.get(i)).get(j))>=maxInfluence){
    					maxInfluence=tagInfluence.get(vertexTag.get(avl.get(i)).get(j));
    					maxValue=vertexTag.get(avl.get(i)).get(j);//缓存更新标签
    					//entry.getValue().clear();
    					//vertexList=new ArrayList<>();
    					//vertexList.add(maxValue);
    					vertexSet=new HashSet<>();
    					vertexSet.add(maxValue);
    					//System.err.println(maxValue);
    				}else
    				//1代表相同社区，增加标签
    				if(isequals==1){
    					maxInfluence=tagInfluence.get(vertexTag.get(avl.get(i)).get(j));
    					//maxValue=vertexTag.get(avl.get(i)).get(j);//增加标签
    					if(!vertexSet.contains(vertexTag.get(avl.get(i)).get(j))){
    						//vertexList=entry.getValue();
    						//vertexList.add(vertexTag.get(avl.get(i)).get(j));
    						//entry.getValue().add(vertexTag.get(avl.get(i)).get(j));
    						//System.err.println(vertexTag.get(avl.get(i)).get(j));
    						vertexSet=new HashSet<>();
    						vertexSet.addAll(entry.getValue());
    						vertexSet.add(vertexTag.get(avl.get(i)).get(j));
    					}
    				}
    				
				}
			}
    		//最后更新标签，
    		entry.setValue(new ArrayList<Integer>(vertexSet));
    		if(noupdatenumber>=1){
    			//tempVertexTag.remove(entry.getKey());
    			vertexIsOver.put(entry.getKey(), true);
    		}
		}
    	iteratorNumber++;//迭代次数增加
    	//非空迭代
    	//if(iteratorNumber<=2){
    		if(tempVertexIsOverSize!=vertexIsOver.size()||tempVertexIsOverSize==0){
    		//System.err.println(tempVertexTag.size());
    		discoveryCommunity(tempVertexTag);    		
    		//System.out.println(iteratorNumber);
    	}else{
    		//System.out.println(vertexIsOver.size());
    		System.out.println("迭代次数："+iteratorNumber);
    		System.out.println("迭代前的标签：");
    		for (Map.Entry<Integer, Double> ti : tagInfluence.entrySet()) {
    			System.out.print(ti.getKey()+" ");
    		}
    		System.out.println("");
    		System.out.println("迭代后的标签：");
    		for (Map.Entry<Integer, List<Integer>> entry : vertexTag.entrySet()) {
        		//entry.getKey(), entry.getValue();
    			for (int j = 0; j < entry.getValue().size(); j++) {
    				System.out.print(entry.getValue().get(j)+" ");
    				//System.out.println("key="+entry.getKey()+"---tag="+entry.getValue());					
				}
    		}
    		group=new HashMap<>();
    		Set<Integer> set=new HashSet<>();
    		//找到所有的迭代后存留的标签
    		for (Map.Entry<Integer, List<Integer>> entry : vertexTag.entrySet()) {
    			for (int j = 0; j < entry.getValue().size(); j++) {
    				set.add(entry.getValue().get(j));
    				//System.out.println("key="+entry.getKey()+"---tag="+entry.getValue());					
				}
    		}
    		//根据迭代后存留的标签获取每一个节点
    		for (Integer s:set) {
    			List<Integer> ll=new ArrayList<>();
    			for (Map.Entry<Integer, List<Integer>> entry : vertexTag.entrySet()) {
    				for (int j = 0; j < entry.getValue().size(); j++) {
	    				if(s==entry.getValue().get(j)){
	    					ll.add(entry.getKey());
	    				}
    				}
    			}
    			group.put(s, ll);
			}
    		//循环打印标签节点
    		for (Map.Entry<Integer, List<Integer>> el : group.entrySet()) {
    			System.out.println("");
    			System.out.print("标签为"+el.getKey()+"的节点序号：");
    			for (int j = 0; j < el.getValue().size(); j++) {
					System.out.print(el.getValue().get(j)+" ");
				}
    		}
    		System.out.println("");
    		System.out.println("EQ值:"+getEQ());
    		
    	}
    }
 
    /**
     * 获取EQ
     * 
     */
    public static Double getEQ(){
    	//计算EQ
		double EQ = 0,
		EQ_temp=0;
		//m表示总边数
		int m=adjacencyList.length;
		for (Map.Entry<Integer, List<Integer>> el : group.entrySet()) {
			List<Integer> ell = el.getValue();
			Map<Integer,Integer> tempqv=new HashMap<Integer,Integer>();
			for (int i = 0; i < ell.size(); i++) {
				if(tempqv.get(ell.get(i))==null){
					tempqv.put(ell.get(i), 1);					
				}else{
					tempqv.put(ell.get(i),tempqv.get(ell.get(i))+1);					
				}
				//表示节点v所属社区的数目
				double Qv = Double.valueOf(tempqv.get(ell.get(i)));
				//节点v的度
				double Kv = Double.valueOf(getEdgeByVertex(ell.get(i)));
				//表示节点w所属社区的数目
				double Qw = 0;
				//节点w的度
				double Kw = 0;
				//A为邻接矩阵
				double Avw= 0;
				Map<Integer,Integer> tempqw=new HashMap<Integer,Integer>();
				for(int j=0;j<ell.size();j++){
					if(tempqw.get(ell.get(j))==null){
						tempqw.put(ell.get(j), 1);					
					}else{
						tempqw.put(ell.get(j),tempqw.get(ell.get(j))+1);					
					}
					Qw = Double.valueOf(tempqw.get(ell.get(j)));
					Kw = Double.valueOf(getEdgeByVertex(el.getValue().get(j)));
					//如果u与v存在一条连边
					if(getAdjacencyVertexByVertexs(ell.get(i)).contains(ell.get(j))){
						Avw=1.0;
					}else{
						Avw=0;						
					}
					//Avw=1.0;
					EQ_temp = EQ_temp+(Avw-(Kv*Kw)/(Double.valueOf(2*m)))/(Qv*Qw);
				}
			}
		}
		EQ=EQ_temp/(2*m);
    	return EQ;
    }
}
