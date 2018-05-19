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
import java.util.Random;
import java.util.Set;


/**
 * Copra
 * @author Administrator
 *
 */
public class Copra {
	public static int v=2;//默认的重叠数
	public static int vertexNumber=0;//顶点数量,默认为0
	public static Set<Integer> adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
	public static int[][] adjacencyList;//邻接表
	public static int[][] adjacencyMatrix;//邻接矩阵
	public static HashMap<Integer,Map<Integer,Double>> vertexTag=new HashMap<>();//每个顶点对应 标签 集合
	public static HashMap<Integer,Boolean> vertexIsOver=new HashMap<>();//顶点稳定
	public static Map<Integer,List<Integer>> group=new HashMap<>();//社区
	public static int iteratorNumber=0;//迭代次数
	public static long starttime=0;//开始时间
	public static long endtime=0;//结束时间
    public static void main(String[] args) {
     starttime=System.currentTimeMillis();
    // System.out.println("k2".replace("r", "").replace("k", ""));
    //1-1.初始化节点数据 （ 邻接表）
/*  adjacencyList=new int[][] {
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
   adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate2.txt");
    //adjacencyList=getFileData("src/com/nieyue/bettercopra/Dolphins.txt");
   // adjacencyList=getFileData("src/com/nieyue/bettercopra/polBooks.txt");
   // adjacencyList=getFileData("src/com/nieyue/bettercopra/Football.txt");
     //1-2，获取所有顶点等初始化
     getAdjacencyVertex(adjacencyList);
    //1-3，邻接表转邻接矩阵
    adjacencyListChangeadjacencyMatrix(adjacencyList);
    // System.err.println(getEdgeByVertex(9));
   //2-1，为每一个顶点分配一个唯一的标签
    assignTag();
	//3-1，迭代发现社区，输出EQ值
	discoveryCommunity(vertexTag);*/
	
	double number=0;	
	for (int i = 0; i <10; i++) {
		number+=avg();
		}
		System.out.println("EQ值:"+number/10);
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
    	 // adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate.txt");//0.31
    	  //adjacencyList=getFileData("src/com/nieyue/bettercopra/Karate2.txt");//0.45
    	  // adjacencyList=getFileData("src/com/nieyue/bettercopra/Dolphins.txt");//0.412
    	   // adjacencyList=getFileData("src/com/nieyue/bettercopra/polBooks.txt");//0.438
    	    adjacencyList=getFileData("src/com/nieyue/bettercopra/Football.txt");//0.56
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
    			// double number = getQ(group, Edge_graph, adjacencyMatrix);
    			 double number = getEQ();
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
    	/*System.out.println("邻接表转邻接矩阵 :");
    	for(int i=0;i<adjacencyMatrix.length;i++)  
    	    System.out.println(Arrays.toString(adjacencyMatrix[i]));*/
    }
    /**
     *获取邻接点标签从属系数最大的标签
     *@param vertexs 顶点的所有邻接点
     *@param selfVertex 自身顶点
     */
    public static Map<Integer,Double> getTagByVertexs(List<Integer> vertexs,Integer selfVertex) {
    	//存放标签-从属系数之和
    	List<Map<Integer, Double>> listmap = new ArrayList<>(); 
    	//存放自身的标签map
    	listmap.add(vertexTag.get(selfVertex));
    	
    	//存放邻接点的标签map
    	for (int i = 0; i < vertexs.size(); i++) {
    		listmap.add(vertexTag.get(vertexs.get(i)));
    	}
    	//存放所有邻接点的标签，计算从属系数之和，
    	Map<Integer, Double> map = new HashMap<Integer, Double>(); 
    	for (Map<Integer, Double> m : listmap) {
    		Iterator<Map.Entry<Integer, Double>> mit = m.entrySet().iterator(); 
    		while (mit.hasNext()) {
    			Entry<Integer, Double> mentry = mit.next();
    			if (map.containsKey(mentry.getKey())) {  
    				//新map中存的从属系数+当前的从属系数
    				map.put(mentry.getKey(), map.get(mentry.getKey()) + mentry.getValue());  
    			} else {  
    				map.put(mentry.getKey(), mentry.getValue());  
    			} 
    		}
		}
 
    	Map<Integer,Double> resultMap=new HashMap<>();
        Iterator<Map.Entry<Integer, Double>> it = map.entrySet().iterator();  
        //默认第一个最大
        Entry<Integer, Double> tempentry = it.next();
        resultMap.put(tempentry.getKey(), tempentry.getValue());
        while (it.hasNext()) { 
            Entry<Integer, Double> entry = it.next(); 
            //邻接点从属系数和最大的选出来
            if (entry.getValue() >tempentry.getValue()) { 
            	//if(resultMap.size()>=2) {            		
            	resultMap.remove(tempentry.getKey());
            	resultMap.put(entry.getKey(),entry.getValue());
                tempentry.setValue(entry.getValue());
            	//}else {
            	//	resultMap.put(entry.getKey(),entry.getValue());            		
            	//}
            } else if(entry.getValue() .equals(tempentry.getValue())) {
            	resultMap.put(entry.getKey(),entry.getValue());
            } 
        }  
        //随机取
        //小于0返回自身的
        if(resultMap.size()<=0) {
        	return vertexTag.get(selfVertex);
        }else if(resultMap.size()==1){
        	//只有一个,从属系数为1
        	resultMap.entrySet().iterator().next().setValue(1.0);
        	return resultMap;
        }else {
        	//从map中选2个
        	Integer[] keys = resultMap.keySet().toArray(new Integer[0]);  
        	Random random = new Random();  
        	int number1 = random.nextInt(keys.length);
        	Integer randomKey1 = keys[number1];  
        	int number2 = random.nextInt(keys.length);
        	if(number2==number1) {
        		if(number2<keys.length-1) {
        			number2++;
        		}else {
        			number2--;        			
        		}
        	}
        	Integer randomKey2 = keys[number2]; 
        	Double result1 = resultMap.get(randomKey1);
        	Double result2 = resultMap.get(randomKey2);
        	Map<Integer,Double> resultMap2=new HashMap<>();
        	resultMap2.put(randomKey1, result1);
        	resultMap2.put(randomKey2, result2);
        	Iterator<Entry<Integer, Double>> iit = resultMap2.entrySet().iterator();
        	double bsum=0.0;
        	while (iit.hasNext()) {
				Entry<Integer, Double> entry2 = iit.next();
				//计算所有从属系数之和
				bsum+=entry2.getValue();
			}
        	Iterator<Entry<Integer, Double>> iit2 = resultMap2.entrySet().iterator();
        	//需要删除的
        	Map<Integer,Double> resultMap3=new HashMap<>();
        	while (iit2.hasNext()) {
        		Entry<Integer, Double> entry3 = iit2.next();
        		//为每个标签分配新的从属系数,使其和为1
        		entry3.setValue(entry3.getValue()/bsum);
        		if(entry3.getValue()<(1.0/resultMap2.size())) {
        			resultMap3.put(entry3.getKey(),entry3.getValue());
        		}
        	}
        	Iterator<Entry<Integer, Double>> iit3 = resultMap3.entrySet().iterator();
        	while (iit3.hasNext()) {
        		Entry<Integer, Double> entry4 = iit3.next();
        		resultMap2.remove(entry4.getKey());
        	}
        	return resultMap2;
        }
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
     *为每个节点分配一个唯一的标签 
     */
    public static void assignTag() {
    	//为顶点分配一个唯一标签。
    	//key 为节点 ，value为标签和从属系数
    	for (int av: adjacencyVertex) {
    		Map<Integer,Double> hm = new HashMap<>();
    		hm.put(av, 1.0);//新的标签结构（c，b）,c为所属标签，即是社区，b为从属系数  0<=b<=1;,初始化为1.
    		//设置标签
    		vertexTag.put(av,hm);
		}
    }
  
   
    /**
     * 发现社区
     * 
     */
    public static void discoveryCommunity(Map<Integer,Map<Integer,Double>> tempVertexTag){
   	//遍历vertexTag每个图与剩余节点对应 标签 集合
    for (Map.Entry<Integer, Map<Integer,Double>> entry : tempVertexTag.entrySet()) {
    		//获取当前节点的所有邻接点
    		List<Integer> avl = getAdjacencyVertexByVertexs(entry.getKey());
    		//获取邻接点标签从属系数最大的标签
    		Map<Integer,Double> maxTagMap = getTagByVertexs(avl,entry.getKey());
    		//获取多个顶点的不包含自身的多个邻接点
    		//List<Integer> aavl = getAdjacencyVertexsByVertexs(entry.getKey(),avl);
    		int noupdatenumber=1;//默认1，表示当前节点标签与邻接点标签相等
    		//如果当前标签集合不包含邻接点的最大标签集合，需要遍历
    		Iterator<Map.Entry<Integer, Double>> mtme = maxTagMap.entrySet().iterator(); 
    		while (mtme.hasNext()) {
    			if(!entry.getValue().containsKey(mtme.next().getKey())){
    				noupdatenumber=0;
    			}    			
    		}
    		//最后更新标签，
    		entry.setValue(maxTagMap);
    		if(noupdatenumber>=1){
    			vertexIsOver.put(entry.getKey(), true);
    		}
		}
    	iteratorNumber++;//迭代次数增加
    	//非空迭代,不相等代表还没迭代完
    	if(vertexIsOver.size()!=vertexTag.size()){
    		discoveryCommunity(tempVertexTag);    		
    	}else{
    		//System.out.println(vertexIsOver.size());
    		System.out.println("迭代次数："+iteratorNumber);
    		System.out.println("迭代前的标签：");
    		for (Map.Entry<Integer, Map<Integer,Double>> entry : vertexTag.entrySet()) {
    				System.out.print(entry.getKey()+" ");
    		}
    		System.out.println("");
    		System.out.println("迭代后的标签：");
    		for (Map.Entry<Integer, Map<Integer,Double>> entry : vertexTag.entrySet()) {
        		//entry.getKey(), entry.getValue();
    			Iterator<Map.Entry<Integer, Double>> mit = entry.getValue().entrySet().iterator(); 
        		while (mit.hasNext()) {
        			Entry<Integer, Double> e = mit.next();
        			System.out.print(e.getKey()+" ");
        			System.err.print(e.getValue()+" ");
        		}
    		}
    		group=new HashMap<>();
    		Set<Integer> set=new HashSet<>();
    		//找到所有的迭代后存留的标签
    		for (Map.Entry<Integer, Map<Integer,Double>> entry : vertexTag.entrySet()) {
    			Iterator<Map.Entry<Integer, Double>> mit = entry.getValue().entrySet().iterator(); 
        		while (mit.hasNext()) {
        			Entry<Integer, Double> e = mit.next();
        			set.add(e.getKey());
        		}
    		}
    		//根据迭代后存留的标签获取每一个节点
    		for (Integer s:set) {
    			List<Integer> ll=new ArrayList<>();
    			for (Map.Entry<Integer, Map<Integer,Double>> entry : vertexTag.entrySet()) {
    				Iterator<Map.Entry<Integer, Double>> mit = entry.getValue().entrySet().iterator(); 
            		while (mit.hasNext()) {
            			Entry<Integer, Double> e = mit.next();
            			if(s==e.getKey()) {
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
    		List<int[]> Edge_graph=new ArrayList<>();
    		for (int i = 0; i < adjacencyList.length; i++) {
				int[] a=new int[2];
				a[0]=adjacencyList[i][0];
				a[1]=adjacencyList[i][1];
				Edge_graph.add(a);
			}
    		
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
			for (int i = 0; i < ell.size(); i++) {
				Map<Integer,Integer> tempqv=new HashMap<Integer,Integer>();
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
				for(int j=0;j<ell.size();j++){
					Map<Integer,Integer> tempqw=new HashMap<Integer,Integer>();
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
