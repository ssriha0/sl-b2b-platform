package com.newco.marketplace.utils.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.newco.marketplace.utils.constants.EmailAlertConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils {

	protected final ObjectMapper mapper = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	public JSONUtils() {
	}

	public <T> String toString(T obj) {
		try {
			ObjectWriter writer = mapper.writer();
			return writer.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		} 
		return null;
	}

	public <T> T toObject(Class<T> cls, String body) {
		try {
			ObjectReader reader = mapper.readerFor(cls);
			return reader.readValue(body);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public <T> List<T> toListObject(Class<T[]> cls, String jsonInput) {
		try {
			return Arrays.asList(mapper.readValue(jsonInput, cls));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public String toParameterMapJson(Map<String, String> PMap) {
		String jsonResp = null;
		try {
			// convert map to JSON String
			ObjectMapper mapperObj = new ObjectMapper();
			Map<String, Object> basePMap = new HashMap<>();
			String toEmails = PMap.get(EmailAlertConstants.EMAIL);
			if (null != toEmails && !"".equals(toEmails)) {
				PMap.remove(EmailAlertConstants.EMAIL);
			}
			basePMap.put(EmailAlertConstants.CTX_PAYLOAD, PMap);
			basePMap.put("email", toEmails);
			jsonResp = mapperObj.writeValueAsString(basePMap);			
		} catch (Exception e) {
			logger.error("Error in converting payload : " + e.getMessage(), e);
		}
		return jsonResp;
	}
}
