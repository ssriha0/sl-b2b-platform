package com.newco.marketplace.web.dto.ajax;

import com.newco.marketplace.web.dto.AbstractAjaxResultsDTO;
import com.newco.marketplace.web.utils.Ajaxable;

public class AjaxResultsDTO extends AbstractAjaxResultsDTO {
	
	private static final long serialVersionUID = -5397289674139141824L;
	private StringBuffer sb = new StringBuffer();
	
	public String toXml() {
		getBuffer().append("<message_result>");
		getBuffer().append("<message>").append(getResultMessage()).append("</message>");
		getBuffer().append("<pass_fail>").append(getActionState()).append("</pass_fail>");
		getBuffer().append("<addtional_01>").append(getAddtionalInfo1()).append("</addtional_01>");
		getBuffer().append("<addtional_02>").append(getAddtionalInfo2()).append("</addtional_02>");
		getBuffer().append("<addtional_03>").append(getAddtionalInfo3()).append("</addtional_03>");
		getBuffer().append("<addtional_04>").append(getAddtionalInfo4()).append("</addtional_04>");
		getBuffer().append("<addtional_05>").append(getAddtionalInfo5()).append("</addtional_05>");
		getBuffer().append("<addtional_06>").append(getAddtionalInfo6()).append("</addtional_06>");
		getBuffer().append("<addtional_07>").append(getAddtionalInfo7()).append("</addtional_07>");
		getBuffer().append("<addtional_08>").append(getAddtionalInfo8()).append("</addtional_08>");
		getBuffer().append("<addtional_09>").append(getAddtionalInfo9()).append("</addtional_09>");
		getBuffer().append("<addtional_10>").append(getAddtionalInfo10()).append("</addtional_10>");
		getBuffer().append("<addtional_11>").append(getAddtionalInfo11()).append("</addtional_11>"); 
		getBuffer().append("<addtional_12>").append(getAddtionalInfo12()).append("</addtional_12>");
		getBuffer().append("<addtional_13>").append(getAddtionalInfo13()).append("</addtional_13>");
		
		//SL-21233: Document Retrieval Code Starts
		
		getBuffer().append("<addtional_14>").append(getAddtionalInfo14()).append("</addtional_14>");
		getBuffer().append("<addtional_15>").append(getAddtionalInfo15()).append("</addtional_15>");
		
		//SL-21233: Document Retrieval Code Ends
		
		getBuffer().append("</message_result>");
		String out = getBuffer().toString();
		return getBuffer().toString(); 
	}
	
	protected String toNestedXml() {
		getBuffer().append("<message_result>");
		getBuffer().append("<message>").append(getResultMessage()).append("</message>");
		getBuffer().append("<pass_fail>").append(getActionState()).append("</pass_fail>");
		getBuffer().append("<addtional_01>").append(getAddtionalInfo1()).append("</addtional_01>");
		getBuffer().append("<addtional_02>").append(getAddtionalInfo2()).append("</addtional_02>");
		getBuffer().append("<addtional_03>").append(getAddtionalInfo3()).append("</addtional_03>");
		getBuffer().append("<addtional_04>").append(getAddtionalInfo4()).append("</addtional_04>");
		getBuffer().append("<container>");
		String out = getBuffer().toString();
		return getBuffer().toString();
	}
	
	public String toXmlDeepCopy() {
		StringBuffer sb = new StringBuffer();
		sb.append(toNestedXml());
		if(isContainsNestedAjaxableItems()){
			for(Ajaxable ajax : get_ajaxAbleItems()) {
				sb.append(ajax.toXmlDeepCopy());
			}
		}
		sb.append("</container>");
		sb.append("</message_result>");
		return sb.toString().trim();
	}

	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return sb;
	}
}
