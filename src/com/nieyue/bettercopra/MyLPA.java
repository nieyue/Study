package com.nieyue.bettercopra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * mylpa自己写的lpa
 * @author Administrator
 *
 */
public class MyLPA {
	static int vertexNumber=0;//顶点数量,默认为0
	public static Set<Integer> adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
	public static int[][] adjacencyList;//邻接表
	public static int[][] adjacencyMatrix;//邻接矩阵
	public static HashMap<Integer,List<Integer>> vertexTag=new HashMap<>();//每个顶点对应 标签 集合
	public static HashMap<Integer,Boolean> vertexIsOver=new HashMap<>();//顶点稳定
	public static Map<Integer,List<Integer>> group=new HashMap<>();//社区
	public static int iteratorNumber=0;//迭代次数
	public static long starttime=0;//开始时间
	public static long endtime=0;//结束时间
    public static void main(String[] args) {
     starttime=System.currentTimeMillis();
    // System.out.println("k2".replace("r", "").replace("k", ""));
    //1-1.初始化节点数据 （ 邻接表）
  /*adjacencyList=new int[][] {
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
    //adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate.txt");
  //adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate2.txt");
    adjacencyList=getFileData("src/com/nieyue/bettercopra/Dolphins.txt");
   // adjacencyList=getFileData("src/com/nieyue/bettercopra/polBooks.txt");
    //adjacencyList=getFileData("src/com/nieyue/bettercopra/Football.txt");
     //1-2，获取所有顶点等初始化
     getAdjacencyVertex(adjacencyList);
    //1-3，邻接表转邻接矩阵
    adjacencyListChangeadjacencyMatrix(adjacencyList);
    // System.err.println(getEdgeByVertex(9));
   //2-1，为每一个顶点分配一个唯一的标签
    assignTag();
	//3-1，迭代发现社区，输出Q值
	discoveryCommunity(vertexTag);
	
	List<int[]> Edge_graph=new ArrayList<>();
	for (int i = 0; i < adjacencyList.length; i++) {
		int[] a=new int[2];
		a[0]=adjacencyList[i][0];
		a[1]=adjacencyList[i][1];
		Edge_graph.add(a);
	}
	System.out.println("Q值:"+getQ(group, Edge_graph, adjacencyMatrix));*/
	double number=0;	
	for (int i = 0; i <1000; i++) {
		number+=avg();
		}
		System.out.println("Q值:"+number/1000);
    endtime=System.currentTimeMillis();
   long costtime=endtime-starttime;
   System.out.println("");
    System.err.println("花费的时间："+Double.valueOf(costtime)/1000+"s");
	}
    public static double avg(){
    	vertexNumber=0;//顶点数量,默认为0
    	adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
    	adjacencyList=null;//邻接表
    	adjacencyMatrix=null;//邻接矩阵
    	vertexTag=new HashMap<>();//每个顶点对应 标签 集合
    	vertexIsOver=new HashMap<>();//顶点稳定
    	group=new HashMap<>();//社区
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
    	   adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate.txt");
    	  //adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate2.txt");
    	   // adjacencyList=getFileData("src/com/nieyue/bettercopra/Dolphins.txt");
    	   // adjacencyList=getFileData("src/com/nieyue/bettercopra/polBooks.txt");
    	   // adjacencyList=getFileData("src/com/nieyue/bettercopra/Football.txt");
    	     //1-2，获取所有顶点等初始化
    	     getAdjacencyVertex(adjacencyList);
    	    //1-3，邻接表转邻接矩阵
    	    adjacencyListChangeadjacencyMatrix(adjacencyList);
    	    // System.err.println(getEdgeByVertex(9));
    	   //2-1，为每一个顶点分配一个唯一的标签
    	    assignTag();
    		//3-1，迭代发现社区，输出Q值
    		List<int[]> Edge_graph=new ArrayList<>();
    		for (int i = 0; i < adjacencyList.length; i++) {
    			int[] a=new int[2];
    			a[0]=adjacencyList[i][0];
    			a[1]=adjacencyList[i][1];
    			Edge_graph.add(a);
    		}
    			vertexIsOver.clear();
    			iteratorNumber=0;
    			discoveryCommunity(vertexTag);
    			 double number = getQ(group, Edge_graph, adjacencyMatrix);
    			return number;
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
    	/*System.out.println("邻接表转邻接矩阵 :");
    	for(int i=0;i<adjacencyMatrix.length;i++)  
    	    System.out.println(Arrays.toString(adjacencyMatrix[i]));*/
    }
    /**
     *获取邻接点标签次数最多的标签
     *@param vertexs 顶点的所有邻接点
     *@param selfVertex 自身顶点
     */
    public static Integer getTagByVertexs(List<Integer> vertexs,Integer selfVertex) {
    	//存放所有邻接点的标签，含重复的，统计哪个次数最大，
    	List<Integer> list=new ArrayList<>();
    	list.addAll(vertexTag.get(selfVertex));
    	for (int i = 0; i < vertexs.size(); i++) {
    		list.addAll(vertexTag.get(vertexs.get(i)));
    	}
    	//存放标签-标签数
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
    	for (Integer obj:list) {
    		 if (map.containsKey(obj)) {  
                 map.put(obj, map.get(obj).intValue() + 1);  
             } else {  
                 map.put(obj, 1);  
             } 
		}
    	List<Integer> list2=new ArrayList<>();
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();  
        //默认第一个最大
        Entry<Integer, Integer> tempentry = it.next();
        list2.add(tempentry.getKey());
        while (it.hasNext()) { 
            Entry<Integer, Integer> entry = it.next();  
            if (entry.getValue() >tempentry.getValue()) { 
            	list2.remove(tempentry.getKey());
                list2.add(entry.getKey());
                tempentry.setValue(entry.getValue());
            } else if(entry.getValue() ==tempentry.getValue()) {
            	list2.add(entry.getKey());
            } 
        }  
       /* System.out.println("list2");
        for (int i = 0; i < list2.size(); i++) {
			System.out.print(list2.get(i)+"-");
			
		}*/
        //随机取一个
        Integer result=list2.get((int)(Math.random()*list2.size()));
    	return result;
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
     *为节点分配一个唯一的标签 
     */
    public static void assignTag() {
    	//为顶点分配一个唯一标签。
    	//key 为节点 ，value为标签
    	for (int av: adjacencyVertex) {
    		ArrayList<Integer> ll = new ArrayList<Integer>();
    		ll.add(av);
    		//设置标签
    		vertexTag.put(av,ll);
		}
    }
    
    /**
     * 发现社区
     * 
     */
    public static void discoveryCommunity(Map<Integer,List<Integer>> tempVertexTag){
   	//遍历vertexTag每个图与剩余节点对应 标签 集合
    for (Map.Entry<Integer, List<Integer>> entry : tempVertexTag.entrySet()) {
    		//List<Integer> vertexList=new ArrayList<>();
    		Set<Integer> vertexSet=new HashSet<>();
    		//获取当前节点的所有邻接点
    		List<Integer> avl = getAdjacencyVertexByVertexs(entry.getKey());
    		//获取邻接点标签次数最多的标签
    		Integer maxTag = getTagByVertexs(avl,entry.getKey());
    		int noupdatenumber=1;//默认1，表示当前节点标签与邻接点标签相等
			//遍历所有邻接点
    		/*for (int i = 0; i < avl.size(); i++) {
    			//如果当前标签集合不包含邻接点的标签集合，需要遍历
    			if(!entry.getValue().containsAll(vertexTag.get(avl.get(i)))){
    				noupdatenumber=0;
    			}
    		}*/
    		//如果当前标签集合不包含邻接点的最大标签集合，需要遍历
    		if(!entry.getValue().contains(maxTag)){
    			noupdatenumber=0;
    		}
    		vertexSet.add(maxTag);
    		//最后更新标签，
    		entry.setValue(new ArrayList<Integer>(vertexSet));
    		if(noupdatenumber==1){
    			vertexIsOver.put(entry.getKey(), true);
    		}
		}
    	iteratorNumber++;//迭代次数增加
    	//非空迭代
    	if(vertexIsOver.size()!=vertexTag.size()){
    		//if(tempVertexIsOverSize!=vertexIsOver.size()||tempVertexIsOverSize==0){
    		discoveryCommunity(tempVertexTag);    		
    	}else{
    		System.out.println("迭代次数："+iteratorNumber);
    		System.out.println("迭代前的标签：");
    		for (Map.Entry<Integer, List<Integer>> entry : vertexTag.entrySet()) {
    				System.out.print(entry.getKey()+" ");
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
    		
    		
    	}
    }
 
	public static double getQ(Map<Integer, List<Integer>> group2,List<int[]> Edge_graph,int [][] Adjmartrix) {
		double q = 0;
		int communityNum = group2.size();
		int[][] communityMtr = new int[communityNum][communityNum];
		List<Integer> iList = null;
		List<Integer> jList = null;
		List<Integer> keyList = new ArrayList<>(group2.keySet());
		for (int i_index=0; i_index<keyList.size();i_index++ ) {
			iList = group2.get(keyList.get(i_index));
			for (int j_index=0;j_index<keyList.size();j_index++) {
				jList = group2.get(keyList.get(j_index));
				for(int i=0;i<iList.size()-1;i++){
					for(int j=0;j<jList.size();j++){
						int i4=iList.get(i);
						int j4=jList.get(j);
						communityMtr[i_index][j_index] += Adjmartrix[i4-1][j4-1];
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
}
