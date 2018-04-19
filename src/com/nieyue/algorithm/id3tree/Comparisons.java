package com.nieyue.algorithm.id3tree;

import java.util.Comparator;
/**
 * 自定义比较器
 * @author Administrator
 *
 */
public class Comparisons implements Comparator{

	@Override
	public int compare(Object a, Object b) {
		// TODO 自动生成的方法存根
		String str1 = (String)a;
        String str2 = (String)b;
		return str1.compareTo(str2);
	}

}
