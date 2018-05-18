package com.nieyue.gn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
//--------------------------------------------------------------
//有连接的边，x和y为边的端点，value为权值
class v_side{
	int x;
	int y;
	double value;//这个value数组是用来存放计算过程中以某个源点出发对各边的介数
	double value2;//value2数组是用来存放最终所有的介数之和
	boolean remove_if;//在GN算法中决定去掉介数最大的边，这一项设置是否去掉，去掉这条边的时候对于此项设置为true

	v_side(int x,int y, double value){
		int x1 = ( (x< y) ? x:y);
		int y1 = ( (x> y) ? x:y);
		this.x = x1;//小的值给this.x
		this.y = y1;//大的值给this.y
		this.value = value;
		this.value2 = value;
		this.remove_if = false;//这条边是否在算法处理过程中被删除掉
	}

}
//--------------------------------------------------------------
/*这个类是用来存放每次分裂点隶属情况，*/
class point_belong{
	double Q;//记录这种分裂情况下的Q值。
	int []point_belong_to;//记录这种分裂情况下，每个点的属于哪个集团。例如点0属于集团2，则有point_belong_to[0]=2
	point_belong(int num, double Q){
		this.Q = Q;
		this.point_belong_to = new int[num];
	}
}
//--------------------------------------------------------------
/*这个类用来记录每次被删除的边*/
class s_remove_belong{
	int x;//边的点值x和y。
	int y;
	int belong_clique;//这条边是在分裂为几个集团的时候被删除掉的。比如v(i,j)是在分裂为两个集团时去除的,则x=i,y=j;belong_clique=2;

	s_remove_belong(int x, int y, int belong_clique){
		this.x = x;
		this.y = y;
		this.belong_clique = belong_clique;
	}
}
//--------------------------------------------------------------
public class test_all {
	public static int vertexNumber=0;//顶点数
	public static int[][] adjacencyList;//邻接表
	public static List<int[]> Edge_graph;//邻接表
	public static int[][] adjacencyMatrix;//邻接矩阵
	 /**
     * 邻接表转邻接矩阵 
     * @param adjacencyList 邻接表
     */
    public static void adjacencyListChangeadjacencyMatrix(int[][] adjacencyList,int vertexNumber) {
    	adjacencyMatrix=new int[vertexNumber][vertexNumber];
    	//邻接表转邻接矩阵
    	for(int i=0;i<adjacencyList.length;i++){
    		adjacencyMatrix[adjacencyList[i][0]-1][adjacencyList[i][1]-1]=1;
    		adjacencyMatrix[adjacencyList[i][1]-1][adjacencyList[i][0]-1]=1;
		}
    /*	System.out.println("邻接表转邻接矩阵 :");
    	for(int i=0;i<adjacencyMatrix.length;i++)  
    	    System.out.println(Arrays.toString(adjacencyMatrix[i]));*/
    }
	public static double getQ(HashMap<Integer,List<Integer>> Communitys,List<int[]> Edge_graph,int [][] Adjmartrix) {
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
	public static void main(String args[])throws IOException{
		long start=System.currentTimeMillis();
		int i,j;
		Vector<v_side> vec_relationship  = new Vector<v_side>();//记录有链接的边。
		int[] r_sign;//记录矩阵中行号为i的元素（边）在vec_relationship 中出现的首索引
		int max=0;//这个是max是点的个数
		
		
		String ecoding = "UTF-8";
		File file=null;
		file = new File("src/com/nieyue/gn/Karate.txt");
		file = new File("src/com/nieyue/gn/input4.txt");
		file = new File("src/com/nieyue/gn/Dolphins.txt");
		//file = new File("src/com/nieyue/gn/Football.txt");
		if(file.isFile() && file.exists() ){
			InputStreamReader read = new InputStreamReader( new FileInputStream(file),ecoding);
			BufferedReader bufferedreader = new BufferedReader(read);
			String Text_line = null;
			Text_line = bufferedreader.readLine();
			String[] max_str=Text_line.split("-");
			max=Integer.parseInt(max_str[0]);
			//存放边
			List<List<Integer>> ll=new ArrayList<>();
			while( ( Text_line = bufferedreader.readLine() ) != null ){
				String text = Text_line.replace(",", "");
				String[] version=text.split("-");
				
				for(int k = 0; k < version.length; k++ ){
					String[] version2 = version[k].split(":");
					int x1=Integer.parseInt(version2[0]);
					int x2=Integer.parseInt(version2[1]);
					v_side v_sd = new v_side(x1-1,x2-1,1);
					vec_relationship.add(v_sd);
					List<Integer> l=new ArrayList<>();
					l.add(x1);
					l.add(x2);
					ll.add(l);
				}
			}
			//放置adjacencyList
			adjacencyList=new int[ll.size()][];
			Edge_graph=new ArrayList<>();
			Set<Integer> set=new HashSet<Integer>();
			for (int k = 0; k < ll.size(); k++) {
				int[] a=new int[ll.get(k).size()];
				for (int k2 = 0; k2 < ll.get(k).size(); k2++) {
					a[k2]=ll.get(k).get(k2);
					set.add(ll.get(k).get(k2));
				}
				adjacencyList[k]=a;
				Edge_graph.add(a);
			}
			//顶点个数
			vertexNumber=set.size();
			read.close();
			bufferedreader.close();
		}
		
		r_sign = new int[max];//给r_sign设置数组大小
		//r_sign数组先统一赋值为-1
		for(i = 0; i < max; i++){
			r_sign[i] = -1;
		} 
		GN_use.v_order( vec_relationship, r_sign);//对vec_relationship中的内容进行排序整理,

		/*下面是GN算法的实现*/

		//当Q值最大时，Q_i存放数组result_temp[][]中对应的行号i，以便在输出结果的时候容易找到。		
		int Q_i=0;
		//result_temp_i是在存储各种Q值下分裂得到各个点的社团隶属情况要用到的行号下标变量，也就是数组result_temp[][]的行号下标，最后一次的分裂情况下的行号记录在result_temp_i中。
		int result_temp_i=0;
		//vec_result_temp数组每行下标0到max-1的是存放每个点隶属的集团号，存放每种Q 值下的分裂情况，每行对应一种情况，元素vec_result_temp.Q存放对应的Q值，		
		Vector< point_belong> vec_result_temp = new Vector< point_belong>(); 
		//vec_V_remove_belong数组用来存放每次去除介数最大的边的隶属情况，比如v(i,j)是在分裂为两个集团时去除的，则vec_V_remove_belong.x=i-1,vec_V_remove_belong.x=j-1，若是分裂为三个集团时去除的，则vec_V_remove_belong.belong_clique值为3
		Vector< s_remove_belong > vec_V_remove_belong = new Vector< s_remove_belong >();
		//若原始网络本身就是由几个孤立的社团构成的，original_community_alones+2是记录原始网络的孤立社团数目，
		//在vec_result_temp数组中，0到original_community_alones-1的行号记录的是原始网络中的社团检测情况。从行号为original_community_alones记录的是由于去除最大介数边的分裂情况。
		int original_community_alones=0;

		myGN GN_DEAL=new myGN(0);
		GN_DEAL.GN_deal(vec_relationship,r_sign,vec_result_temp, vec_V_remove_belong);//GN算法的入口函数
		original_community_alones=GN_DEAL.get_original_community_alones();
		Q_i=GN_DEAL.get_Q_i();

		result_temp_i=GN_DEAL.get_result_temp_i();
		System.out.println("");

		//如果原始网络时由几个孤立社团构成，要输出
		//若原始网络本来是由几个孤立的社团组成的，要弄清楚，所求的社团数目是original_community_alones+1，因为original_community_alones的下标是从1开始的
		int k3,out_temp=0;
		if(original_community_alones!=0)
		{
			System.out.printf("\n原始网络是由%d个孤立的社团构成的：\n",original_community_alones+1);
			for(j=0; j<original_community_alones+1; j++)
			{
				System.out.printf("\n集团%d：",(j+1));
				for(k3=0; k3<max; k3++)
				{
					out_temp = vec_result_temp.elementAt(original_community_alones-1).point_belong_to[k3];
					if(out_temp==j)
					{
						System.out.printf("%d  ",k3+1);
					}									
				}
			}	
		}
		else
		{
			System.out.println("\n原始网络为：");
			for(i=0; i<max; i++)
			{
				System.out.printf("%d  ",i+1);
			}
		}
		//存放社区
		HashMap<Integer,List<Integer>> Communitys=new HashMap<Integer,List<Integer>>();
		
		//最后把分裂的最佳情况输出，注意Q_i的下标是从0开始的。
		//注意分裂后的社团数就是Q_i+2,存放在变量final_community_num中。
		if(result_temp_i>original_community_alones){//通过result_temp_i>original_community_alones来判断原始网络是否经过GN算法分裂。
			int final_community_num=Q_i+2;

			System.out.println("\n\n\n\n经程序分析，原始网络极有可能分裂为"+final_community_num+"个集团：");

			for(j=0; j<final_community_num; j++)
			{
				System.out.printf("\n集团%d：",(j+1));
				//存放每个社区的list
				ArrayList<Integer> clist = new ArrayList<>();
			
				for(i=0; i<max; i++)
				{
					out_temp = (int)vec_result_temp.elementAt(Q_i).point_belong_to[i];
					if(out_temp==j)
					{
						clist.add(i+1);
						System.out.printf("%d  ",i+1);
					}

				}
				//存放每个社区
				Communitys.put((j+1), clist);
			}
			
			for(i=original_community_alones; i<=Q_i; i++)
			{//从数目为original_community_alones+1的原始网络下开始分裂，到分裂为Q_i+2个社团结束。
				//i是记录分裂情况的行号。i+2是分裂社团的个数
				if(i!=original_community_alones)
				{
					System.out.printf("\n\n再");
				}
				else{
					System.out.printf("\n\n在原始网络的基础上");
				}
				System.out.printf("去掉边(介数最大的边)：");
				for(int k1 = 0; k1 < vec_V_remove_belong.size(); k1++)
				{
					if(vec_V_remove_belong.elementAt(k1).belong_clique == i+2)
					{
						System.out.printf("v(%d,%d),",vec_V_remove_belong.elementAt(k1).x,vec_V_remove_belong.elementAt(k1).y);
					}
				}
			}	  
		}	
		System.out.printf("\n");
		long end=System.currentTimeMillis();
		//获取邻接矩阵
		adjacencyListChangeadjacencyMatrix(adjacencyList,vertexNumber);
		System.out.println("Q:"+ getQ( Communitys,Edge_graph,adjacencyMatrix));
		System.out.println("算法运行时间为："+Double.valueOf(end-start)/1000+"s");
	}
	
}
