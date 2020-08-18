package com.newco.marketplace.persistence.dao.partsManagement;

import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import java.util.List;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;



/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * for parts management
 */
public interface PartsManagementDao {

	/**
	 * @param soId
	 * @return trip Id
	 * @throws DataServiceException
	 * 
	 * this method returns the latest open trip's trip id
	 * 
	 */
	public abstract Integer getLatestOpenTrip(String soId) throws DataServiceException;

	/**
	 * @param invoiceId
	 * @return
	 * @throws DataServiceException
	 * 
	 * to fetch the available part details
	 * 
	 */
	public abstract InvoicePartsVO loadPartDetails(Integer invoicePartId)throws DataServiceException;

	/**
	 * @param invoicePartIds
	 * @return
	 * @throws DataServiceException
	 * 
	 * to load invoice details
	 */
	public abstract InvoiceDetailsVO loadInvoiceDetails(
			List<Integer> invoicePartIds)throws DataServiceException;

	/**
	 * @param invoiceNum
	 * @param soId
	 * @return
	 */
	public abstract InvoiceDetailsVO loadInvoiceDetailsForInvoiceNum(
			String invoiceNum, String soId)throws DataServiceException;
	/**
	 * 
	 * @param partsVo
	 * @throws DataServiceException
	 * to save part details
	 */
	public void savePartsDetails(InvoicePartsVO partsVo)throws DataServiceException;

	/**
	 * @param invoicePartVOlist
	 */
	public abstract void updateInvoicePartsDetails(
			List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException;

	/**
	 * @param invoicePartVOlist
	 * @throws DataServiceException
	 */
	public abstract void saveInvoicePartDocument(
			List<InvoicePartsVO> invoicePartVOlist)throws DataServiceException;
	/**
	 * @param partInvoiceId
	 * @return
	 */
	public abstract void deletePartLocnDetails(Integer partInvoiceId)throws DataServiceException;
	/**
	 * @param partInvoiceId
	 * @return docId
	 */
	public abstract List<Integer> getDocumentId(Integer partInvoiceId)throws DataServiceException;
	/**
	 * @param partInvoiceId
	 * @return 
	 */
	public abstract void deletePartDocumentDetails(Integer partInvoiceId)throws DataServiceException;
	/**
	 * @param partInvoiceId
	 * @return count
	 */
	public abstract Integer getInvoiceIdCountForDocId(List<Integer> documentIdList)throws DataServiceException;
	/**
	 * @param documentIdList
	 * @param soId 
	 * @return 
	 */
	public abstract void deleteSODoc(List<Integer> documentIdList, String soId)throws DataServiceException;
	/**
	 * @param documentIdList
	 * @return 
	 */
	public abstract void deleteDoc(List<Integer> documentIdList)throws DataServiceException;
	/**
	 * @param partInvoiceId
	 * @return 
	 */
	public abstract void deletePartInvoiceDetails(Integer partInvoiceId)throws DataServiceException;
	
	/**
	 * @param documentId
	 * @throws DataServiceException
	 */
	public abstract void deletePartDocumentForDocumentId(Integer documentId)throws DataServiceException;
	
	/**
	 * @param invoicePartVOlist
	 */
	public abstract void updatePartStatus(
			List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException;
	/**
	 * @param soId
	 * @return 
	 * @throws DataServiceException
	 */
	public abstract Double getRetailPriceTotal(String soId,Integer partInvoiceId)throws DataServiceException;
	/**
	 * @param partInvoiceIdList
	 * @param invoiceId 
	 */
	public abstract void deleteSelectedPartDocument(List<Integer> partInvoiceIdList, Integer invoiceId)throws DataServiceException;
	
	public void updateInvoiceParts(List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException;

	
}
