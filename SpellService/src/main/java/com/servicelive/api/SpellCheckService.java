package com.servicelive.api;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import com.servicelive.beans.SpellResults;
import com.servicelive.spellchecker.CheckSpell;
import com.servicelive.spellchecker.SpellCheckResultDto;



/**
 * 
 * @authorShekhar Nirkhe
 * @since 08/25/2009
 * @version 1.0
 */

// 
@Path("")
public class SpellCheckService {
	private Logger logger = Logger.getLogger(SpellCheckService.class);
	final private MediaType mediaType = MediaType.parse("text/xml");
	
	// Required for retrieving attributes from Get URL
	@Resource
	private HttpServletRequest httpRequest;
	
	
	@GET
	@Path("/check/{word}")
	public Response checkSpell(@PathParam("word") String word) {
		logger.info("Entering SpellChecker Service with word :" + word);
		String countStr = httpRequest.getParameter("count");
		int count = 1;
		try {
			if (countStr != null) {
				try {
					count = Integer.parseInt(countStr);
				}catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
			SpellResults ss = null;
			if (word != null && word.trim().length() > 0) {
				word = word.replaceAll("\\+", " ");
				SpellCheckResultDto r = CheckSpell.getInstance().checkSpell(word, count);		
				ss = new SpellResults(r);
			} else {
				SpellCheckResultDto r = new SpellCheckResultDto(true);
				ss = new SpellResults(r);
			}

			String xml = ss.getResponseXML();
			return Response.ok(xml, mediaType).build();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.ok("Internal Server Error", mediaType).build();
	}

}
