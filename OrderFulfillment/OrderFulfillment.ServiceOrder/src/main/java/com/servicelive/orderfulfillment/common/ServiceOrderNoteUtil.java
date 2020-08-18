package com.servicelive.orderfulfillment.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.NumberTool;

import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class ServiceOrderNoteUtil {

	private Map<String, SONote> noteTypes;
    private Logger logger = Logger.getLogger(getClass());
	
	public SONote getNewNote(ServiceOrder so, String noteType, Map<String, Object> dataMap){
	    SONote note = getNewNote(noteType, dataMap);
        so.addNote(note);
        return note;
    }

    public SONote getNewNote(String noteType, Map<String, Object> dataMap){
		if(noteTypes.containsKey(noteType)){
            logger.debug("create note for the note type " + noteType);
			SONote note = noteTypes.get(noteType).copy();

			if (dataMap != null){
				//use velocity to change the description
				VelocityContext vContext = new VelocityContext();
				vContext.put("number", new NumberTool());
				// add all keys from the dataMap
				for (String key: dataMap.keySet()) {
					vContext.put(key, dataMap.get(key));
				}
				// can merge template now
				String message = evaluateVelocityTemplate(note.getNote(), vContext);
				note.setNote(message);
			}

            logger.debug("done creating the so note");
            return note;
		} else {
            throw new ServiceOrderException(String.format("Note type %s is described in configuration files", noteType));
        }
	}

	public void setNoteTypes(Map<String, SONote> noteTypes) {
		this.noteTypes = noteTypes;
	}
	private void failVelocityTemplateEvaluation(String template, Exception e) throws ServiceOrderException {
		throw new ServiceOrderException("Unable to evaluate velocity template " + template,e);
	}

	private String evaluateVelocityTemplate(String template, VelocityContext vContext) throws ServiceOrderException {
		StringWriter sw = new StringWriter();
		try {
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.evaluate(vContext, sw, "velocity template", template);
		} catch (ParseErrorException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (MethodInvocationException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (ResourceNotFoundException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (IOException e) {
			failVelocityTemplateEvaluation(template, e);
		}
		return sw.toString();
	}
	
	
}
