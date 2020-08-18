package com.servicelive.esb.service;

import org.apache.log4j.Logger;

import com.sears.hs.order.domain.StockNoClassify;
import com.sears.hs.order.domain.StockNoClassifyLookup;
import com.sears.hs.order.domain.request.StockNoClassifyRequest;
import com.sears.hs.order.domain.response.StockNoClassifyResponse;
import com.sears.hs.order.service.OrderService;
import com.servicelive.esb.dto.JobCodeInfo;

public class JobCodeMarginServiceImpl implements JobCodeMarginService {

	private OrderService orderService;
	@SuppressWarnings("unused")
	private int timeout;
	private Logger logger = Logger.getLogger(JobCodeMarginServiceImpl.class);

	public JobCodeInfo[] retrieveInfo(JobCodeInfo[] jobCodeInfos)
		throws Exception 
	{
		StockNoClassifyRequest stockNoClassifyRequest = new StockNoClassifyRequest();
		stockNoClassifyRequest.setClient("client");
		stockNoClassifyRequest.setPassword("password");
		int num_job_codes = jobCodeInfos.length;
		StockNoClassifyLookup[] stockNos = new StockNoClassifyLookup[num_job_codes];
		for (int i = 0; i < num_job_codes; i++) {
			stockNos[i] = new StockNoClassifyLookup(jobCodeInfos[i]
					.getJobCode(), jobCodeInfos[i].getSpecCode());
		}
		stockNoClassifyRequest.setStockNos(stockNos);
		stockNoClassifyRequest.setUser("user");
		stockNoClassifyRequest.setVersion("1.0");

		StockNoClassifyResponse stockNoClassifyResponse = orderService
				.lookup(stockNoClassifyRequest);

		StockNoClassify[] stockNoClassify = stockNoClassifyResponse
				.getStockNos();
		//TODO - If no response found, log an error
		if(stockNoClassify == null || num_job_codes > stockNoClassify.length) 
		{
			StringBuilder sb = new StringBuilder();
			for( JobCodeInfo j : jobCodeInfos )
			{
				sb.append( "specCode: "+j.getSpecCode()+" | stockNumber: "+j.getStockNumber()+" " );
			}
			throw new Exception("No response from SST for JobCodes: "+sb.toString());
		}
		
		for (int j = 0; j < num_job_codes; j++) {
			jobCodeInfos[j].setMarginRate(stockNoClassify[j].getMargin());
			jobCodeInfos[j].setLeadTimeDays(stockNoClassify[j]
					.getDispatchDaysOut());
			jobCodeInfos[j].setInclusionDesc(stockNoClassify[j].getInclDs());
			
			//Don't set StockNo from SST webservice as StockNumber as it contains a suffix (typically 'z')
			//jobCodeInfos[j].setStockNumber(stockNoClassify[j].getStockNo());
			jobCodeInfos[j].setStockNumber(jobCodeInfos[j].getJobCode());
			
			jobCodeInfos[j].setJobCodeDesc(stockNoClassify[j].getRegisterDs());
		}
		
		logger.info("****** JobCodeMarginService - SUCCESS ! *******");
		
		return jobCodeInfos;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
