package com.servicelive.beans;

import java.util.HashSet;
import java.util.Set;
import com.servicelive.spellchecker.SpellCheckResultDto;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This is a bean class for storing all information of 
 * the providerResults
 * @author Shekhar Nirkhe
 *
 */

@XStreamAlias("spelling")
public class SpellResults {
	
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = "http://www.servicelive.com/namespaces/spellchecker spell.xsd";
	
	
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = "http://www.servicelive.com/namespaces/spellchecker";
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = "http://www.w3.org/2001/XMLSchema-instance";
	
	
	@XStreamAlias("correct")   
	@XStreamAsAttribute()   
	private boolean correct;
	 
	 @XStreamAlias("pageNum")   
	 @XStreamAsAttribute()   
	private Integer pageNum;
	 
	@XStreamImplicit(itemFieldName="word")
	private Set<String> words;
	 
	
	public SpellResults(SpellCheckResultDto dto) {
		this.correct = dto.isCorrect();		
		this.words = dto.getSuggestions();
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


	public boolean isCorrect() {
		return correct;
	}


	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public Set<String> getWords() {
		return words;
	}


	public void setWords(Set<String> words) {
		this.words = words;
	}
	
	
	public String getResponseXML() {					
		Class<?>[] responseClasses = new Class<?>[1];		
		responseClasses[0] = SpellResults.class;
		
		XStream xstream = new XStream(new DomDriver());
		//xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.processAnnotations(responseClasses);	
		
		
		//obj.setSchemaInstance(this.schemaInstance);
		//obj.setNamespace(this.namespace);
		//obj.setSchemaLocation(this.setSchemaLocation(schemaLocation));
		String xml  = (String) xstream.toXML(this);
        
//		if (xmlValidator.validateXML(xml, this.schemaLocationRes, this.responseXsd) == false) {
//			this.errorString = ResultsCode.INTERNAL_SERVER_ERROR.getMessage();
//			this.errorCode = ResultsCode.INTERNAL_SERVER_ERROR.getCode();
//			xml = getFailedResponseXML();
//		}
//        
//		logger.info("Exiting getResponseXML");		
		return xml;
	}
	
	
	public void addWord(String word) {
		if (words == null)
			words = new HashSet<String>();
		words.add(word);
	}

}
