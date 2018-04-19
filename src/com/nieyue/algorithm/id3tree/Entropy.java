package com.nieyue.algorithm.id3tree;
/**
 * 熵
 * @author Administrator
 *
 */
public class Entropy {
	//信息熵
    public static double getEntropy(int x, int total)
    {
        if (x == 0)
        {
            return 0;
        }
        double x_pi = getShang(x,total);
        return -(x_pi*Logs(x_pi));
    }

    public static double Logs(double y)
    {
        return Math.log(y) / Math.log(2);
    }
    
    
    public static double getShang(int x, int total)
    {
        return x * Double.parseDouble("1.0") / total;
    }
    public static void main(String[] args) {
		System.out.println(Math.log1p(8));
		System.out.println(Math.log10(100));//2
		System.out.println(Math.log(Math.E));//1
	}
}
