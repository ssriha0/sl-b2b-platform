package com.servicelive.routingrulesengine.services;

import java.io.File;
import java.util.List;

import com.servicelive.routingrulesengine.vo.RoutingRuleUploadRuleVO;

public interface RoutingRuleFileParser {
	public List<RoutingRuleUploadRuleVO> parseFile(File file) throws Exception;
}
