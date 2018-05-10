package com.nieyue.c45;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecisionTree {  
    
    InfoGainRatio infoGainRatio = new InfoGainRatio();  
      
    public TreeNode createDecisionTree(List<String> attribute, List<ArrayList<String>> dataset) {  
        TreeNode tree = new TreeNode();  
          
        //check if it is pure  
        if(DataSetUtil.isPure(DataSetUtil.getTarget(dataset))) {  
            tree.setLeaf(true);  
            tree.setTargetValue(DataSetUtil.getTarget(dataset).get(0));  
            return tree;  
        }  
        //choose the best attribute  
        int bestAttr = getBestAttribute(attribute, dataset);  
        //create a decision tree  
        tree.setAttribute(attribute.get(bestAttr));  
        tree.setLeaf(false);  
        List<String> attrValueList = DataSetUtil.getAttributeValueOfUnique(bestAttr, dataset);      
        List<String> subAttribute = new ArrayList<String>();  
        subAttribute.addAll(attribute);  
        subAttribute.remove(bestAttr);  
        for(String attrValue : attrValueList) {  
            //更新数据集dataset  
            List<ArrayList<String>> subDataSet = DataSetUtil.getSubDataSetByAttribute(dataset, bestAttr, attrValue);  
            //递归构建子树  
            TreeNode childTree = createDecisionTree(subAttribute, subDataSet);  
            tree.addAttributeValue(attrValue);  
            tree.addChild(childTree);  
        }  
  
        return tree;  
    }  
      
    /** 
     * 选出最优属性 
     * @param attribute 
     * @param dataset 
     * @return 
     */  
    public int getBestAttribute(List<String> attribute, List<ArrayList<String>> dataset) {  
        //calculate the gainRatio of each attribute, choose the max  
        int bestAttr = 0;  
        double maxGainRatio = 0;  
          
        for(int i = 0; i < attribute.size(); i++) {  
            double thisGainRatio = infoGainRatio.getGainRatio(i, dataset);  
            if(thisGainRatio > maxGainRatio) {  
                maxGainRatio = thisGainRatio;  
                bestAttr = i;  
            }  
        }  
          
        System.out.println("最好的属性是:" + attribute.get(bestAttr));  
        return bestAttr;  
    }  
  
      
    public static void main(String args[]) {  
        //eg 1  
        String attr = "age income student credit_rating";  
        String[] set = new String[12];  
        set[0] = "youth high no fair no";  
        set[1] = "youth high no excellent no";  
        set[2] = "middle_aged high no fair yes";  
        set[3] = "senior low yes fair yes";  
        set[4] = "senior low yes excellent no";  
        set[5] = "middle_aged low yes excellent yes";  
        set[6] = "youth medium no fair no";  
        set[7] = "youth low yes fair yes";  
        set[8] = "senior medium yes fair yes";  
        set[9] = "youth medium yes excellent yes";  
        set[10] = "middle_aged high yes fair yes";  
        set[11] = "senior medium no excellent no";  
  
        List<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();  
        List<String> attribute = Arrays.asList(attr.split(" "));  
        for(int i = 0; i < set.length; i++) {  
            String[] s = set[i].split(" ");  
            ArrayList<String> list = new ArrayList<String>();  
            for(int j = 0; j < s.length; j++) {  
                list.add(s[j]);  
            }  
            dataset.add(list);  
        }  
          
        DecisionTree dt = new DecisionTree();  
        TreeNode tree = dt.createDecisionTree(attribute, dataset);  
        tree.print("");  
    }  
  
}