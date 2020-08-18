package com.newco.batch.tier.performance;

import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.iBusiness.tier.performance.IDeletePerformanceHistoryService;

public class DeletePerformanceHistoryProcessor extends ABatchProcess{
	private static final Logger logger = Logger.getLogger(DeletePerformanceHistoryProcessor.class.getName());
	private IDeletePerformanceHistoryService deletePerformanceHistoryService;

	@Override
	public int execute() {
		deletePerformanceHistory ();
		return 0;
	}
	private void deletePerformanceHistory () {
		try{
			deletePerformanceHistoryService.deleteFirmPerfHistory();
			deletePerformanceHistoryService.deleteProviderPerfHistory();
			deletePerformanceHistoryService.deleteSPNCriteriaHistory();
			deletePerformanceHistoryService.deleteSPNFirmHistory();
			deletePerformanceHistoryService.deleteSPNHdrCriteriaHistory();
			deletePerformanceHistoryService.deleteSPNProviderHistory();
		}catch(Exception e){
			
		}
		
	}
	@Override
	public void setArgs(String[] args) {
		// do nothing
	}
	public IDeletePerformanceHistoryService getDeletePerformanceHistoryService() {
		return deletePerformanceHistoryService;
	}

	public void setDeletePerformanceHistoryService(
			IDeletePerformanceHistoryService deletePerformanceHistoryService) {
		this.deletePerformanceHistoryService = deletePerformanceHistoryService;
	}
}
