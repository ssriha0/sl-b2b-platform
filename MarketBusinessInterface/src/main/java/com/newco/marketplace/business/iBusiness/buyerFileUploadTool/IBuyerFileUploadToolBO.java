package com.newco.marketplace.business.iBusiness.buyerFileUploadTool;

public interface IBuyerFileUploadToolBO {
	
	/**
	 * Main BO method for Buyer File Upload Batch
	 */
	public void StartFileToSOProcessing();
	
	/*
	 * Method to move file upload batches from the integration DB to the so_upload_audit,
	 * so_upload_error and so_upload_success tables
	 */
	public void moveFileUploadDataFromIntegrationDbToSupplierDb();
	
}
