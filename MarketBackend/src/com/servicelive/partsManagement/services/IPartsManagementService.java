package com.servicelive.partsManagement.services;

import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;







/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * for parts management
 *
 */
public interface IPartsManagementService {
	/**
	 * 
	 * @param partVO
	 */
	public void savePartsDetails(InvoicePartsVO partVO);

	/**
	 * @param invoicePartVO
	 * @param soId
	 * @param userName
	 */
	public abstract void editPartDetails(InvoicePartsVO invoicePartVO, String soId, String userName);

	/**
	 * @param invoicePartId
	 * @return
	 */
	public abstract InvoicePartsVO loadPartDetails(Integer invoicePartId);

	/**
	 * @param invoicePartIds
	 * @return
	 */
	public abstract InvoiceDetailsVO loadInvoiceDetails(
			List<Integer> invoicePartIds);

	/**
	 * @param invoiceNum
	 * @param soId 
	 * @return
	 */
	public abstract InvoiceDetailsVO loadInvoiceDetailsForInvoiceNum(
			String invoiceNum, String soId);

	/**
	 * @param invoiceDetailsVO
	 * @param soId
	 * @param userName
	 */
	public void saveInvoiceDetails(InvoiceDetailsVO invoiceDetailsVO,
			String soId, String userName,Map<Integer , List<Integer>>newDocumentMap);
	/**
	 * @param soId 
	 * @param invoiceId
	 * @return
	 */
	public abstract void deletePartDetails(Integer partInvoiceId, String soId);

	/**
	 * @param documentIdList
	 * @return
	 */
	public abstract Integer getInvoiceIdCountForDocId(List<Integer> documentIdList);

	/**
	 * @param documentIdList
	 * @param soId
	 */
	public abstract void documentHardDelete(List<Integer> documentIdList, String soId);

	/**
	 * @param invoicePartId
	 */
	public abstract void deletePartDocumentForPartInvoiceId(Integer invoicePartId);

	/**
	 * @param documentId
	 */
	public abstract void deletePartDocumentForDocumentId(Integer documentId);

	/**
	 * @param invoiceDetailsVO
	 * @param soId
	 * @param userName
	 */
	public void updatePartStatus(List<InvoicePartsVO> invoiceDetailsVO,String userName, String soId);
	/**
	 * @param partInvoiceIdList
	 * @param documentId
	 */
	public void deleteSelectedPartDocument(List<Integer> partInvoiceIdList,Integer documentId);

	/**
	 * Method to fetch sum of retail price
	 * @param soId
	 * @return
	 */
	public Double getRetailPriceTotal(String soId,Integer partInvoiceId);

	/**
	 * Method to get the auto adjudication indicator
	 * @param soId
	 * @return
	 */
	public Boolean  getAutoAdjudicationInd();

	public String   getInvoicePartInd(String soId);



	
	/**
	 * @param invoicePartVOlist
	 */
	public void saveInvoicePartDocument(
			List<InvoicePartsVO> invoicePartVOlist);
	
	/**
	 * 
	 * @param invoicePartsVOs
	 * @return
	 */
	public Map<Integer , List<Integer>>  getDBDocumentList (InvoiceDetailsVO invoiceDetailsVO,
			String soId, String userName);
	
	public String  getConstantValueFromDB(String appkey) throws Exception;

	public void saveInvoicePartPricingModel(String soID,String pricingModel);
	
	public String getInvoicePartsPricingModel(String soId);
	/**
	 * R15_2 : SL-20555:method to hit the LIS API after forming the url
	 * @param partNum
	 * @param soId 
	 * @return
	 * @throws Exception
	 */

	public List<InvoicePartsVO> searchParts(String partNum, String soId) throws Exception;
}
