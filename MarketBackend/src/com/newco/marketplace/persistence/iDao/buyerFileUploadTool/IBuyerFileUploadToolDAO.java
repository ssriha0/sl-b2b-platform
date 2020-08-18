package com.newco.marketplace.persistence.iDao.buyerFileUploadTool;

import java.util.List;

import com.newco.marketplace.dto.vo.buyerUploadScheduler.BuyerRefTypeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditSuccessVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerFileUploadToolDAO {
	public int testquery(int test) ;
	public int generateTestId();
	public boolean getValidZipCode(String zip);
	public List getTheSkillTree();
	
	/**
	 * Retrieves list of files to be processed by Buyer File Upload Batch
	 * @return List<UploadFileVO>
	 */
	public List<UploadFileVO> getFileToCreateSo();
	
	public List getMainCategoryMap();
	public List getSubCatMap();
	public List getSkillMap();
	public List getAllFileEntries();
	public List getShippingInfo() ;
	public String getStateForZip(String zip) ;
	public String getUserEmail(int buyerId);
	
	/**
	 * Retrieves list of buyer reference fields
	 * @param buyerId
	 * @return
	 */
	public List<BuyerRefTypeVO> getBuyerReferences(int buyerId);
	
	public List getPartsSuppliedBy();
	public List getPhoneTypes();
	public List getPhoneClass();
	public int getBuyerContactId(int buyerId);
	public List getDocumentLookUp();
	public List getLocationLookUp() ;
	public List getDocIdValLookup(int buyerId);
	public List getLogoIdValLookup(int buyerId);
	public void markFileAsRead(String fileName);
	public String insertDocumentDetails(int docId, String soId);
	//public int selectDocId(String title, String descr) ;
	public int selectDocId(String title, int buyerId) ;
	public int selectLogoId(String title, int buyerId) ;
	
	/**
	 * In case of errors, the errored record is inserted into so_audit_error table
	 * @param uAEVO
	 */
	public void errorInsert(UploadAuditErrorVO uAEVO);
	
	public void successInsert(UploadAuditSuccessVO uASVO);
	
	// ESB functions
	public List<UploadFileVO> getFilesSentToEsb();
	public void markAsSentToESB(int fileId);
	public void markAsRetrievedFromESB(int fileId);
	
}
