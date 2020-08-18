package com.servicelive.esb.service;

import com.servicelive.esb.dto.JobCodeInfo;

public interface JobCodeMarginService {

	JobCodeInfo[] retrieveInfo(JobCodeInfo[] jobCodeInfos) throws Exception;

}
