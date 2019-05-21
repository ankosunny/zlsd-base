package com.zhilingsd.base.common.utils.tree;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class TreeUtil{
    private HashMap<String, TreeNode> treeNodesMap = new HashMap<String, TreeNode>();
    private List<TreeNode> treeNodesList = new ArrayList<TreeNode>();

    private TreeUtil(List<? extends TreeNode> list){
        initTreeNodeMap(list);
        initTreeNodeList();
    }

    public static synchronized String getTreeJson(List<? extends TreeNode> list){
        List<TreeNode> treeList=new TreeUtil(list).getRoot();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
        filter.getExcludes().add("nodeId");
        filter.getExcludes().add("nodeName");
        return JSONObject.toJSONString(treeList, filter);
    }

    public static JSONArray getTreeDataForJson(List<? extends TreeNode> list){
            String s=getTreeJson(list);
            return  JSONArray.parseArray(s);
    }

    public static synchronized String getTreeDeleteEmptyChild(List<? extends TreeNode> list){
        List<TreeNode> treeList=new TreeUtil(list).getRoot();
        deleteEmptyChild(treeList);
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
        filter.getExcludes().add("nodeId");
        filter.getExcludes().add("nodeName");
        return JSONObject.toJSONString(treeList, filter);
    }

    public static JSONArray getTreeDataForJsonDeleteEmptyChild(List<? extends TreeNode> list){
        String s=getTreeDeleteEmptyChild(list);
        return  JSONArray.parseArray(s);
    }

    private static List<TreeNode> deleteEmptyChild(List<TreeNode> treeList){
        for (TreeNode node:treeList) {
            if(node.getChildren().size()==0){
                node.setChildren(null);
            }else {
                node.setChildren(deleteEmptyChild(node.getChildren()));
            }
        }
        return treeList;
    }

    private void initTreeNodeMap(List<? extends TreeNode> list){
        TreeNode treeNode = null;
        for(TreeNode item : list){
            treeNodesMap.put(item.getNodeId(), item);
        }

        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode parentTreeNode = null;
        while(iter.hasNext()){
            treeNode = iter.next();
            if(StringUtils.isBlank(treeNode.getParentNodeId())){
                continue;
            }

            parentTreeNode = treeNodesMap.get(treeNode.getParentNodeId());
            if(parentTreeNode != null){
                treeNode.setParent(parentTreeNode);
                parentTreeNode.addChild(treeNode);
            }
        }
    }

    private void initTreeNodeList(){
        if(treeNodesList.size() > 0){
            return;
        }
        if(treeNodesMap.size() == 0){
            return;
        }
        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode treeNode = null;
        while(iter.hasNext()){
            treeNode = iter.next();
            if(treeNode.getParent() == null){
                this.treeNodesList.add(treeNode);
                this.treeNodesList.addAll(treeNode.getAllChildren());
            }
        }
    }

    private List<TreeNode> getTree() {
        return this.treeNodesList;
    }

    private List<TreeNode> getRoot() {
        List<TreeNode> rootList = new ArrayList<TreeNode>();
        if (this.treeNodesList.size() > 0) {
            for (TreeNode node : treeNodesList) {
                if (node.getParent() == null)
                    rootList.add(node);
            }
        }
        return rootList;
    }
}