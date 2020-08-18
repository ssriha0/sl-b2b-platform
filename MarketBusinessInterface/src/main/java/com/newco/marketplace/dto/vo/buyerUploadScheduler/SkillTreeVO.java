package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class SkillTreeVO extends SerializableBaseVO{
private int node_id;
private int level;
private String node_name;
private int root_node_id;
private int parent_node;
private int root;
public int getNode_id() {
	return node_id;
}
public void setNode_id(int node_id) {
	this.node_id = node_id;
}
public int getLevel() {
	return level;
}
public void setLevel(int level) {
	this.level = level;
}
public String getNode_name() {
	return node_name;
}
public void setNode_name(String node_name) {
	this.node_name = node_name;
}
public int getRoot_node_id() {
	return root_node_id;
}
public void setRoot_node_id(int root_node_id) {
	this.root_node_id = root_node_id;
}
public int getParent_node() {
	return parent_node;
}
public void setParent_node(int parent_node) {
	this.parent_node = parent_node;
}
public int getRoot() {
	return root;
}
public void setRoot(int root) {
	this.root = root;
}
	
}
