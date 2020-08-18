package com.newco.marketplace.search.solr.dto;

import org.apache.solr.client.solrj.beans.Field;

public class SkillTreeDto {

	public final static int ERROR = -1;	 
	public final static int MAIN_CATEGORY = ProviderSkill.MAIN_CATEGORY;
	public final static int CATEGORY = ProviderSkill.CATEGORY;
	public final static int SUB_CATEGORY = ProviderSkill.SUB_CATEGORY;
	public final static int AMBIGUOUS = 3;
	public final static int MULTI_CHOISE = 4;
	public final static int WRONG_SPELLING = 5;
	
	public static final float MAX_SCORE_DIFF = 2.0f;
	public static final int MAX_DISAMBIGUATION_RESULT = 8;
	
	@Field("NodeId")
	private int nodeid;
	
	@Field("NodeName")
	private String nodename;
	
	@Field("ParentId")
	private int parentid;
	
	@Field("ParentName")
	private String parentname;
	
	@Field
	float score;
	
	@Field("Levell")
	private int level;
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public SkillTreeDto() {
		// intentionally blank
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int node_id) {
		this.nodeid = node_id;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String node_name) {
		this.nodename = node_name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parent_id) {
		this.parentid = parent_id;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParent_name(String parent_name) {
		this.parentname = parent_name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
