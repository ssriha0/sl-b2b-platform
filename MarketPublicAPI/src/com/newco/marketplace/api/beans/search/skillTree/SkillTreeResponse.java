/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.skillTree;

import java.util.List;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * This is a bean class for storing response information for 
 * the skillTree Service
 * @author Infosys
 *
 */
@XSD(name="skillTreeResponse.xsd", path="/resources/schemas/search/")
@XStreamAlias("skillTreeResponse")
public class SkillTreeResponse implements IAPIResponse{
	
	 @XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("searchTerm")
	private String originalTerm;	
	
	@XStreamAlias("suggestedTerm")
	private String collation;

	@XStreamAlias("categories")
	private Categories categories;
	
	public SkillTreeResponse(Results results, Categories category) {
		this();
		this.results = results;
		this.categories = category;
	}
	
	public SkillTreeResponse(){
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public String getCollation() {
		return collation;
	}

	public void setCollation(String collation) {
		this.collation = collation;
	}

	public String getOriginalTerm() {
		return originalTerm;
	}

	public void setOriginalTerm(String originalTerm) {
		this.originalTerm = originalTerm;
	}

	
}
