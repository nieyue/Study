package com.nieyue.leetcode;

import java.util.LinkedList;

/**
 * 给定二叉树，求其最小深度。最小深度是从根节点到最近的叶节点沿最短路径的节点数。
 */
public class MinDepthBinaryTree {
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
            val=x;
        }
    }
    /**
     * 思路1：
     * 深度优先遍历（DFS）
     * 如果当前节点是空，则最小深度为 0，返回
     * 效率低
     */
    public int run(TreeNode root ){
        if(root==null){
            return 0;
        }
        int l=run(root.left);
        int r=run(root.right);
        if(l==0||r==0){
            return l+r+1;
        }
        return Math.min(l,r)+1;
    }

    /**
     * 思路2：
     * 广度优先遍历（BFS）
     * 找到第一个叶子结点就可以停止遍历
     * 效率高
     */
    public int run1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.addFirst(root);
        int start = 0;
        int end = 1;
        int depth = 1;
        while (!queue.isEmpty()) {
            TreeNode temp = queue.removeLast();
            start++;
            if (temp.left == null && temp.right == null) {
                return depth;
            }
            if (temp.left != null) {
                queue.addFirst(temp.left);
            }
            if (temp.right != null) {
                queue.addFirst(temp.right);
            }
            if (start == end) {
                depth++;
                start = 0;
                end = queue.size();
            }
        }

        return depth;
    }

    public static void main(String[] args) {

        MinDepthBinaryTree mdbt = new MinDepthBinaryTree();
        TreeNode tn = mdbt.new TreeNode(5);
        TreeNode tnleft = mdbt.new TreeNode(6);
        TreeNode tnleft2 = mdbt.new TreeNode(7);
        tnleft.left=tnleft2;
        tn.left=tnleft;
        TreeNode tnright = mdbt.new TreeNode(6);
        TreeNode tnright2 = mdbt.new TreeNode(7);
        tnright.right=tnright2;
        tn.right=tnright;
        int min = mdbt.run1(tn);
        System.out.println(min);
    }
}
