package com.newco.marketplace.search.solr.dto;

import org.apache.solr.client.solrj.beans.Field;

public class ProviderSkill {	
	public final static int MAIN_CATEGORY = 0; // root
	public final static int CATEGORY = 1;      // level 1
	public final static int SUB_CATEGORY = 2;  //  level 2	
	
    @Field
	String skill;

    @Field
    String text;
	
	@Field
	int id;
	
    @Field("root")
	int rootId;
    
	@Field("parent")
    int parentId;
	
	@Field
	float score;
	
	@Field
	int level;
	
	@Field("rootflag")
	int rootflag;
	

	public ProviderSkill() {
		// intentionally blank
	}
	
	@Override
	public String toString() {
		StringBuilder bld =  new StringBuilder();
		String format = "|%1$-10d|%2$-50s|%3$-10.4f|%4$-10d|%5$-10d|%6$-4b";
  	    String s = String.format(format, Integer.valueOf(id), skill, Float.valueOf(score), Integer.valueOf(parentId), Integer.valueOf(rootId), Boolean.valueOf(isIAmRoot()));
		//bld.append(String.format("%3d", id ) + "\t" + String.format("%15$s", skill.trim()) + "\t" + String.format("%8f", score) + "\t\t\t" + parentId + "\t\t\t" + rootId + "\t\t\t" + iAmRoot);
		bld.append(s);
  	    return bld.toString();
	}

	public String getSkill() {
		if (skill != null)
			return skill.trim();
		return skill;
	}

	public void setSkill(String skill) {
		if (skill != null)
			skill = skill.trim();
		this.skill = skill;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isIAmRoot() {
		if (rootflag == 0) {
			return false;
		}
		return true;
	}

	public int getRootflag() {
		return rootflag;
	}

	public void setRootflag(int rootflag) {
		this.rootflag = rootflag;
	}
	
	public int getSkillLevel() {
		if (this.isIAmRoot())
			return MAIN_CATEGORY;
		else if (this.getRootId() == this.getParentId()) 
			return CATEGORY;

		return SUB_CATEGORY;
	}

}
