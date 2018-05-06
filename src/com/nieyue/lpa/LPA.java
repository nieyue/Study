package com.nieyue.lpa;  
  
import java.util.Arrays;  
import java.util.HashMap;  
import java.util.Map;  
/**
 * 标签传播算法
 * @author Administrator
 *
 */
public class LPA {  
	//标准 差
    public static float sigma = 1;
    //标记数
    public static int tag_num = 2;  
      
    public static void main(String[] args) {  
         // System.err.println(Math.exp(2));
         // System.err.println(Math.pow(Math.E, 2));
          //System.err.println(Math.exp(2)==Math.pow(Math.E, 2));
        //初始化未标注数据，需要被传播的数据
    	float[][] data = {  
         /*       {1,1},  
                {1,2},  
                {2,1},  
                {2,2},  
                {4,4},  
                {6,6},  
                {6,7},  
                {7,6},  
                {7,7} */ 
    			
    			{1,1},
    			{5,5},
    			{2,2}
        };  
         /**
          * 初始化标注数据
          */
        Map<Integer, Integer> tag_map = new HashMap<Integer, Integer>();  
        tag_map.put(1, 1);  
        tag_map.put(5, 5);  
         /**
          * 初始化概率矩阵 
          */
        float[][] weight = new float[data.length][data.length];  
          
        for(int i = 0; i < weight.length; i++) {  
            float sum = 0f;  
            for(int j = 0; j < weight[i].length; j++) {  
            	//计算权重，weight[i][j]=exp(-欧式距离/方差)
                weight[i][j] = (float) Math.exp( - Math.pow(distance(data[i], data[j]),2) / Math.pow(sigma, 2));  
                sum += weight[i][j];  
              //  System.err.println(weight[i][j]);
            }  
            //归一化处理
           for(int j = 0; j < weight[i].length; j++) {  
                weight[i][j] /= sum;  
            } 
        }  
          
        System.out.println("=============1");  
        for(int i = 0; i < weight.length; i++) {  
            System.out.println(Arrays.toString(weight[i]));  
        }  
        System.out.println("=============2");  
         /**
          * 初始化标注矩阵 
          */
        float[][] tag_matrix = new float[data.length][tag_num];  
        for(int i = 0; i < tag_matrix.length; i++) {  
            if(tag_map.get(i) != null) {  
                tag_matrix[i][tag_map.get(i)] = 1;  
            } else {  
                float sum = 0;  
                for(int j = 0; j < tag_matrix[i].length; j++) {  
                    tag_matrix[i][j] = (float) Math.random();  
                    sum += tag_matrix[i][j];  
                }  
                for(int j = 0; j < tag_matrix[i].length; j++) {  
                    tag_matrix[i][j] /= sum;  
                }  
            }  
        }  
          //t[i][j]=(w[i][j]*t[k][j])求和 ,k=1
        for(int it = 0; it < 100; it++) {  
            for(int i = 0; i < tag_matrix.length; i++) {  
                if(tag_map.get(i) != null) {  
                    continue;  
                }  
                float all_sum = 0;  
                for(int j = 0; j < tag_matrix[i].length; j++) {  
                    float sum = 0;  
                    for(int k = 0; k < weight.length; k++) {  
                        sum += weight[i][k] * tag_matrix[k][j];  
                    }  
                    tag_matrix[i][j] = sum;  
                    all_sum += sum;  
                }  
                for(int j = 0; j < tag_matrix[i].length; j++) {  
                    tag_matrix[i][j] /= all_sum;  
                }  
            }  
            System.out.println("=============3");  
            for(int i = 0; i < tag_matrix.length; i++) {  
                System.out.println(Arrays.toString(tag_matrix[i]));  
            }  
            System.out.println("=============4");  
        }  
    }  
    /**
     *  两个样本之间的欧式距离 dis = sqrt((x1-x2)^+(y1-y2)^)
     * @param a
     * @param b
     * @return
     */
    public static float distance(float[] a, float[] b) {  
        float dis = 0;  
//        for(int i = 0; i < a.length; i++) { 
//            dis = (float) Math.pow(b[i] - a[i], 2);  
//        }  
        for (int i = 0; i < a.length; i++) {  
        	float temp = (float)Math.pow((b[i] - a[i]), 2);  
            dis += temp;  
        }  
        dis = (float) Math.sqrt(dis); 
        return dis;  
    }  
}  