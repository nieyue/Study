package com.nieyue.algorithm.id3tree;
/**
 * 树节点
 * @author Administrator
 *
 */
public class TreeNode {
	//父节点
	TreeNode parent;
	//指向父节点的属性
	String parentAttribute;
	String nodeName;
	String[] attributes;
	TreeNode[] childNodes;
}
