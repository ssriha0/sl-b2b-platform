package com.newco.marketplace.util.buyerFileUploadTool;

import java.util.List;
import java.util.Map;

public interface IBuyerFileUploadToolParser {
	
	public List<Map<String, String>> parseBuyerXLSFile(byte[] bytes) throws Exception;

}
