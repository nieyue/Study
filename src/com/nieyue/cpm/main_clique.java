package com.nieyue.cpm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class main_clique 
{
	static int vertexNumber=0;//顶点数量,默认为0
	public static Set<Integer> adjacencyVertex=new HashSet<Integer>();//获取所有的顶点
	public static List<List<Integer>> adjacencyList;//边
	public static List<List<Integer>> adjacencyMatrix;//邻接矩阵
	
	public static void main(String args[])throws IOException{
     long start=System.currentTimeMillis();
		int relation_members[];//下三角 存放关系矩阵
		int member_num  = 0; //网络中点的个数
		try{
			String encording="UTF8";
			File my_input_file=null;
			 //my_input_file  = new File("src/com/nieyue/cpm/Karate.txt");
			 my_input_file  = new File("src/com/nieyue/cpm/Karate2.txt");
			// my_input_file  = new File("src/com/nieyue/cpm/Dolphins.txt");
			// my_input_file  = new File("src/com/nieyue/cpm/polBooks.txt");
			// my_input_file  = new File("src/com/nieyue/cpm/Football.txt");

			if(my_input_file.exists() && my_input_file.isFile() ){//file.isFile() && file.exists() 
				InputStreamReader read_file = new InputStreamReader( new FileInputStream(my_input_file), encording);
				BufferedReader bufferreader = new BufferedReader(read_file);
				String text_line = null;
				String text = null;

				if((text_line = bufferreader.readLine()) == null ){
					System.out.println("网络中点的个数读取为空");
				} 
				adjacencyList=new ArrayList<>();
				Set<Integer> set=new HashSet<>();
				while( (text_line=bufferreader.readLine()) != null ){
					String[] textlines = text_line.split(" ");
					List<Integer> li=new ArrayList<>();
					int point_x  = Integer.parseInt(textlines[0].trim());
					int point_y = Integer.parseInt(textlines[1].trim());
					set.add(point_x);
					set.add(point_y);
					li.add(point_x);
					li.add(point_y);
					adjacencyList.add(li);
				}
				bufferreader.close();
				read_file.close();
				//member_num  = Integer.parseInt(text_line);//先读取网络中点的个数;
				member_num  = set.size();//先读取网络中点的个数;
				relation_members = new int[(member_num-1)*member_num/2];//申请空间，下三角，存放关系矩阵，压缩存放
				boolean isZero=false;//数据中默认没有0
				loop:for (int i = 0; i < adjacencyList.size(); i++) {
				if(adjacencyList.get(i).get(0)==0||adjacencyList.get(i).get(1)==0) {
					isZero=true;
					break loop;
				}	
				}
				if(isZero) {
					for (int i = 0; i < adjacencyList.size(); i++) {
						int a0=adjacencyList.get(i).get(0)+1;
						int a1=adjacencyList.get(i).get(1)+1;
						adjacencyList.get(i).clear();
						adjacencyList.get(i).add(a0);
						adjacencyList.get(i).add(a1);
						}	
				}
				
				for (int i = 0; i < adjacencyList.size(); i++) {
					int point_x = adjacencyList.get(i).get(0);
					int point_y = adjacencyList.get(i).get(1);
					int x=( (point_x > point_y) ? point_x:point_y )-1;
					int y=( (point_x < point_y) ? point_x:point_y )-1;//x是行值，y是列值.文本中的数据下标是从1开始的，而程序中则是从0开始，注意此点
					/* 下面是根据行值x和列值y来定位数据在下三角矩阵中的位置*/
					int position_xy = (x-1)*x/2+y;
					relation_members[position_xy] = 1;
				}
					/*text = text_line.replace(",", "");
					String[] version = text.split("-");
					//System.err.println(version.length);
					for(int i = 0; i < version.length; i++ ){
						String[] version_2 = version[i].split(":");
						int point_x = Integer.parseInt(version_2[0]);
						int point_y = Integer.parseInt(version_2[1]);
						int x=( (point_x > point_y) ? point_x:point_y )-1;
						int y=( (point_x < point_y) ? point_x:point_y )-1;//x是行值，y是列值.文本中的数据下标是从1开始的，而程序中则是从0开始，注意此点
						 下面是根据行值x和列值y来定位数据在下三角矩阵中的位置
						int position_xy = (x-1)*x/2+y;
						relation_members[position_xy] = 1;
					}*/

				

				/* 下面开始进行算法*/
				clique_child clique_c = new clique_child( relation_members, member_num);
				clique_c.traceback_find_clique();
				Vector< Vector<Integer> >  vector_s = clique_c.get_vector_result();//vector_s 中得到所有的派系
                System.out.printf("找出的派系如下：");
				for(int i = 0; i < vector_s.size(); i++){
					int s = vector_s.elementAt(i).size();
					System.out.printf("\n%d派系：",s );
					for(int j = 0; j < vector_s.elementAt(i).size(); j++){
						System.out.printf("%d  ", vector_s.elementAt(i).elementAt(j).intValue());
					}
				}

				int k = 3;
				calculate_clique_clique_overlap_matrix  c_c_matrx_deal = new calculate_clique_clique_overlap_matrix(vector_s,k);//生成派系社团重叠矩阵		
				
				int[] k_clique_matrix = c_c_matrx_deal.get_k_clique_overlap_matrix(k);//得到派系社团重叠矩阵
				Vector< Vector<Integer> > vector_k_clique = c_c_matrx_deal.get_vector_k_clique();
				Vector< Vector<Integer> > vector_final_k_clique = c_c_matrx_deal.get_vector_final_k_clique();
				System.out.println();
				System.out.printf("%d派系社团情况如下：",k);
				for(int ii = 0; ii < vector_k_clique.size(); ii++){//定位到某个k派系社团
					int number=0;
					System.out.printf("\n%d派系社团%d为: ",k,ii);
					
					for(int j = 0; j < vector_k_clique.elementAt(ii).size(); j++){//定位到某个k派系社团中的某个派系
						int num = vector_k_clique.elementAt(ii).elementAt(j).intValue();//这个派系在vector_s中的编号
						int size_max = vector_s.elementAt(num).size();
						
						System.out.printf("\n%d派系:  ",size_max);
						for(int s = 0; s < size_max; s++,number++){
							int point = vector_s.elementAt(num).elementAt(s).intValue();
							System.out.printf("%d ",point);
						}
					}
					System.out.println();
					System.out.printf("社团%d共有点的个数为%d: ",ii,number);
				}
				
				System.out.println();
				System.out.println("共有："+vector_k_clique.size()+" 个社团");
				System.out.println();
				//double EQ=0 ;
				//double EQ_temp=0;
				//int m=0;
			/*	for(int i=0;i<vector_k_clique.size();i++)
				{
					for(int j = 0; j < vector_k_clique.elementAt(i).size(); j++)
					{//定位到某个k派系社团中的某个派系
						int num = vector_k_clique.elementAt(ii).elementAt(j).intValue();//这个派系在vector_s中的编号
						int size_max = vector_s.elementAt(num).size();
						for(int s = 0; s < size_max; s++){
							int point = vector_s.elementAt(num).elementAt(s).intValue();
					 		
						}
					}
				}*/
				//计算EQ
				double EQ = 0,
				EQ_temp=0;
				//m表示总边数
				int m=adjacencyList.size();
				for(int ii = 0; ii < vector_final_k_clique.size(); ii++){//定位到某个k派系社团
					Vector<Integer> ell = vector_final_k_clique.elementAt(ii);
					for (int i = 0; i < ell.size(); i++) {
						Map<Integer,Integer> tempqv=new HashMap<Integer,Integer>();
						if(tempqv.get(ell.get(i))==null){
							tempqv.put(ell.get(i), 1);					
						}else{
							tempqv.put(ell.get(i),tempqv.get(ell.get(i))+1);					
						}
						//表示节点v所属社区的数目
						double Qv = Double.valueOf(tempqv.get(ell.get(i)))*3;
						//节点v的度
						ArrayList<Integer> Kvl = new ArrayList<Integer>();
						Kvl.add(ell.get(i));
						double Kv = Double.valueOf(getEdgeByVertexs(Kvl));
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
							ArrayList<Integer> Kwl = new ArrayList<Integer>();
							Kwl.add(ell.get(j));
							Kw = Double.valueOf(getEdgeByVertexs(Kwl));
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
				System.out.println("EQ值:"+EQ);
			}
			else{
				System.out.println("找不到指定的文件");
			}
		}
		catch(Exception e){
			System.out.println("读取数据错误");
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();
		//System.out.println("算法运行时间为："+(end-start)+"ms");
		 System.err.println("花费的时间："+Double.valueOf(end-start)/1000+"s");

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
	    	for (int i = 0; i < adjacencyList.size(); i++) {
	    		ArrayList<Integer> a=new ArrayList<Integer>();
	    		for (int j = 0; j < adjacencyList.get(i).size(); j++) {
	    			a.add(adjacencyList.get(i).get(j));
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
	     *根据顶点获取邻接点
	     *@param vertex 顶点 从1开始
	     */
	    public static List<Integer> getAdjacencyVertexByVertexs(int vertex) {
	    	List<Integer> list=new ArrayList<>();
	    	List<List<Integer>> tempadjacencyList=new ArrayList<>();
	    	for (int i = 0; i < adjacencyList.size(); i++) {
	    		ArrayList<Integer> a=new ArrayList<Integer>();
	    		for (int j = 0; j < adjacencyList.get(i).size(); j++) {
	    			if( adjacencyList.get(i).get(j)==vertex){//如果相等，就是对应的边
	    				a.add(adjacencyList.get(i).get(j));
	    			}
	    		}
	    		if(a.size()>0){    			
	    		tempadjacencyList.add(adjacencyList.get(i));
	    		}
	    	}
	    	//循环去除自身
	    	for (int i = 0; i < tempadjacencyList.size(); i++) {
	    		for (int j = 0; j < tempadjacencyList.get(i).size(); j++) {
	    			if(tempadjacencyList.get(i).get(j)!=vertex&& !list.contains(tempadjacencyList.get(i).get(j))){
	    				list.add(tempadjacencyList.get(i).get(j));
	    			}
	    		}
			}
	    	return list;
	    }
}
