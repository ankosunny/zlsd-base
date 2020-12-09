package com.zhilingsd.base.common.utils.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    //树节点ID
    private String nodeId;
    //父节点ID
    private String parentNodeId;
    //节点在树中的排序号(预留)
    private int orderNum;
    //父节点
    private TreeNode parent;
    //当前节点的二子节点
    private List<TreeNode> children = new ArrayList<TreeNode>();
    //当前节点的子孙节点
    private List<TreeNode> allChildren = new ArrayList<TreeNode>();

    public void addChild(TreeNode treeNode){
        this.children.add(treeNode);
    }
    public void removeChild(TreeNode treeNode){
        this.children.remove(treeNode);
    }
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public String getParentNodeId() {
        return parentNodeId;
    }
    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    public TreeNode getParent() {
        return parent;
    }
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    public List<TreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public List<TreeNode> getAllChildren() {
        if(this.allChildren.isEmpty()){
            for(TreeNode treeNode : this.children){
                this.allChildren.add(treeNode);
                this.allChildren.addAll(treeNode.getAllChildren());
            }
        }
        return this.allChildren;
    }
}