package com.newco.marketplace.persistence.daoImpl.partsManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.SoTripVo;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.partsManagement.PartsManagementDao;
import com.newco.marketplace.vo.mobile.DocIdVO;
import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.PartCoverage;
import com.newco.marketplace.api.mobile.beans.sodetails.PartSource;



/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * for parts management 
 */
public class PartsManagementDaoImpl extends ABaseImplDao implements PartsManagementDao {
  
	/**
	 * @param soPartsVo
	 * @throws DataServiceException
	 * this method save the part details provided by user in db
	 */
	public void savePartsDetails(InvoicePartsVO invoicePartVO) throws DataServiceException {
			try {
				if(null!=invoicePartVO){
			     insert("addPartsDetails.insert", invoicePartVO);
				}
		} catch (Exception e) {
			logger.error("Exception in savePartsDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public Double getRetailPriceTotal(String soId,Integer partInvoiceId) throws DataServiceException {
		
		Double retailPriceSum = null;
		Map<String,String> retailMap=new HashMap<String, String>();
		retailMap.put("soId",soId);
		retailMap.put("partStatus","Installed");
		retailMap.put("partInvoiceId",partInvoiceId.toString());
		try{
			retailPriceSum = (Double) queryForObject("getRetailPriceSum.query",retailMap);
		}
		catch(Exception e){
			logger.error("Exception in validateRetailPrice"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return retailPriceSum;
	}

	/**
	 * @param soId
	 * @return trip Id
	 * @throws DataServiceException
	 * 
	 * this method returns the latest open trip's trip id
	 * 
	 */
	public Integer getLatestOpenTrip(String soId) throws DataServiceException{
		SoTripVo latestOpenTripVo=null;
		Integer latestTripId = null;
		try{
			latestOpenTripVo =  (SoTripVo) queryForObject("getLatestOpenTrip.query",soId);
		}catch (Exception e) {
			logger.error("Exception in validating Trip Number"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		if(null != latestOpenTripVo){
			latestTripId = latestOpenTripVo.getTripId();
		}
		return latestTripId;
	}

	/**
	 * @param invoiceId
	 * @return
	 * @throws DataServiceException
	 * 
	 * to fetch the available part details
	 * 
	*/
	public InvoicePartsVO loadPartDetails(Integer invoicePartId)
			throws DataServiceException {
			
		InvoicePartsVO invoicePartsVO = null;
		try{
			if(invoicePartId!=null){
				List<Integer> invoicePartIds = new ArrayList<Integer>();
				List<InvoicePartsVO> invoicePartsVOs = new ArrayList<InvoicePartsVO>();
				invoicePartIds.add(invoicePartId);
				invoicePartsVOs =  (List<InvoicePartsVO>) queryForList("getPartDetails.query",invoicePartIds);
				if(null != invoicePartsVOs && !invoicePartsVOs.isEmpty()){
					invoicePartsVO = invoicePartsVOs.get(0);
				}
				List<PartSource> sourceTypes = (List<PartSource>)queryForList("fetchInvoicePartsSource.query");
				List<LookupVO> partStatusTypes = (List<LookupVO>)queryForList("fetchPartStatusTypes.query");
				invoicePartsVO.setSourceTypes(sourceTypes);
				invoicePartsVO.setPartStatusTypes(partStatusTypes);
			}
		}catch (Exception e) {
			logger.error("Exception in loadPartDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoicePartsVO;
	}

	/**
	 * @param invoicePartIds
	 * @return
	 * @throws DataServiceException
	 * 
	 * to load invoice details
	 */
	public InvoiceDetailsVO loadInvoiceDetails(List<Integer> invoicePartIds)
			throws DataServiceException {
		
	List<InvoicePartsVO> invoicePartsVOs = null;
	InvoiceDetailsVO invoiceDetails = new InvoiceDetailsVO();
	try{
		invoicePartsVOs = (List<InvoicePartsVO>)queryForList("getPartDetails.query",invoicePartIds);
		List<PartSource> sourceTypes = (List<PartSource>)queryForList("fetchInvoicePartsSource.query");
		List<PartCoverage> coverageTypes = (List<PartCoverage>)queryForList("fetchInvoicePartsCoverage.query");
		List<LookupVO> partStatusTypes = (List<LookupVO>)queryForList("fetchPartStatusTypes.query");
		invoiceDetails.setInvoicePartsVOs(invoicePartsVOs);
		invoiceDetails.setPartSourceTypes(sourceTypes);
		invoiceDetails.setPartStatusTypes(partStatusTypes);
		
	}catch (Exception e) {
		logger.error("Exception in loadInvoiceDetails"+ e.getMessage());
		throw new DataServiceException(e.getMessage());
	}
	return invoiceDetails;
}

	/**
	 * @param invoiceNum
	 * @param soId
	 * @return
	 */
	public InvoiceDetailsVO loadInvoiceDetailsForInvoiceNum(String invoiceNum,
			String soId) throws DataServiceException {
		
	List<InvoicePartsVO> invoicePartsVOs = new ArrayList<InvoicePartsVO>();
	InvoiceDetailsVO invoiceDetails = new InvoiceDetailsVO();
	
	try{
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("invoiceNum", invoiceNum);
		params.put("soId", soId);
		invoicePartsVOs =  (List<InvoicePartsVO>) queryForList("getInvoiceDetails.query",params);
		List<PartSource> sourceTypes = (List<PartSource>)queryForList("fetchInvoicePartsSource.query");
		List<PartCoverage> coverageTypes = (List<PartCoverage>)queryForList("fetchInvoicePartsCoverage.query");
		List<LookupVO> partStatusTypes = (List<LookupVO>)queryForList("fetchPartStatusTypes.query");
		invoiceDetails.setInvoicePartsVOs(invoicePartsVOs);
		invoiceDetails.setPartSourceTypes(sourceTypes);
		invoiceDetails.setPartStatusTypes(partStatusTypes);

	}catch (Exception e) {
		logger.error("Exception in loadInvoiceDetailsForInvoiceNum"+ e.getMessage());
		throw new DataServiceException(e.getMessage());
	}
	return invoiceDetails;
}
	/**
	 * @param partInvoiceId
	 * @return
	 */
	public void deletePartLocnDetails(Integer partInvoiceId)throws DataServiceException {
		
		try{
			delete("deletePartLcnDetails.delete",partInvoiceId);
		}
		catch(Exception e){
			logger.error("Exception in deletePartLocnDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param partInvoiceId
	 * @return docId
	 */
	public List<Integer> getDocumentId(Integer partInvoiceId)throws DataServiceException {
		List<Integer> docIdList = new ArrayList<Integer>();
		try{
			docIdList = (List<Integer>) queryForList("getDocIdForInvoiceId.query",partInvoiceId);
		}
		catch(Exception e){
			logger.error("Exception in getDocumentId"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return docIdList;
	}
	/**
	 * @param partInvoiceId
	 * @return
	 */
	public void deletePartDocumentDetails(Integer partInvoiceId)throws DataServiceException {
		
		try{
			delete("deletePartDocumentDetails.delete",partInvoiceId);
		}
		catch(Exception e){
			logger.error("Exception in deletePartDocumentDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param docId
	 * @return count
	 */
	public Integer getInvoiceIdCountForDocId(List<Integer> docIdList)throws DataServiceException {
		Integer invoiceIdCount = null;
		try{
			invoiceIdCount = (Integer) queryForObject("getInvoiceIdCountForDocId.query",docIdList);
		}
		catch(Exception e){
			logger.error("Exception in getInvoiceIdCountForDocId"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoiceIdCount;
	}
	/**
	 * @param docId
	 * @return
	 */
	public void deleteSODoc(List<Integer> documentIdList,String soId)throws DataServiceException {
		Map parameterMap = new HashMap();
		try{
			parameterMap.put("documentIdList",documentIdList);
			parameterMap.put("soId",soId);
			delete("deleteSODocDetails.delete",parameterMap);
		}
		catch(Exception e){
			logger.error("Exception in deleteSODoc"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param docId
	 * @return
	 */
	public void deleteDoc(List<Integer> documentIdList)throws DataServiceException {
		
		try{
			delete("deleteDocDetails.delete",documentIdList);
		}
		catch(Exception e){
			logger.error("Exception in deleteDoc"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param partInvoiceId
	 * @return
	 */
	public void deletePartInvoiceDetails(Integer partInvoiceId)throws DataServiceException {
		
		try{
			delete("deletePartInvoiceDetails.delete",partInvoiceId);
		}
		catch(Exception e){
			logger.error("Exception in deletePartInvoiceDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public void deletePartDocumentForDocumentId(Integer documentId)throws DataServiceException {
		try{
			delete("deletePartInvoiceForDocumentId.delete",documentId);
		}
		catch(Exception e){
			logger.error("Exception in deletePartInvoiceDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}


	/**
	 * @param invoicePartVOlist
	 * 
	 * to batch update invoice details
	 * 
	 * 
	 */
	public void updateInvoicePartsDetails(List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException{
		try {
			if(null!=invoicePartVOlist){
		       batchUpdate("updateInvoicePartsDetails.update", invoicePartVOlist);
		       }
		} catch (Exception e) {
			logger.error("Exception in updateInvoicePartsDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public void updateInvoiceParts(List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException{
		try {
			if(null!=invoicePartVOlist){
				
				for(InvoicePartsVO invoicePartsVO: invoicePartVOlist){
					update("updateInvoicePartsDetails.update", invoicePartsVO);
				}
			}    
		} catch (Exception e) {
			logger.error("Exception in updateInvoicePartsDetails"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	/**
	 * @param invoicePartVOlist
	 * @throws DataServiceException
	 * 
	 * to batch update invoice document details
	 */
	public void saveInvoicePartDocument(List<InvoicePartsVO> invoicePartVOlist)
			throws DataServiceException {
		List<Integer> docIdList=null;
		List<DocIdVO> docVoList=new ArrayList<DocIdVO>();		
		try{
			if(null!=invoicePartVOlist && !invoicePartVOlist.isEmpty()){
				for(InvoicePartsVO invoicePart : invoicePartVOlist){
					if(null!=invoicePart && null!=invoicePart.getDocumentIdList() && invoicePart.getDocumentIdList().size()!=0){
						docIdList=invoicePart.getDocumentIdList();
						//mapping into vo for inserting as list 
						for(Integer docId:docIdList){
							DocIdVO docIdVO=new DocIdVO();
							docIdVO.setDocId(docId);
							docIdVO.setInvoiceId(invoicePart.getInvoiceId());
							docVoList.add(docIdVO);
						}
					}
				}
			}
			batchInsert("invoicePartDocument.insert", docVoList);				
			
		}catch (Exception e) {
			logger.error("Exception in saveInvoicePartDocument"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	
	/**
	 * @param invoicePartVOlist
	 * 
	 * to batch update part Status
	 * 
	 * 
	 */
	public void updatePartStatus(List<InvoicePartsVO> invoicePartVOlist) throws DataServiceException{
		try {
			if(null!=invoicePartVOlist){
		       batchUpdate("updateInvoicePartsStatus.update", invoicePartVOlist);
		       }
		} catch (Exception e) {
			logger.error("Exception in updatePartStatus"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	} 
	/**
	 * @param documentId
	 * @param invoiceId
	 * 
	*/
	public void deleteSelectedPartDocument(List<Integer> partInvoiceIdList, Integer documentId) throws DataServiceException {
		Map parameterMap = new HashMap();
		try{
			parameterMap.put("documentId",documentId);
			parameterMap.put("invoiceIdList",partInvoiceIdList);
			delete("deleteSelectedPartDocument.delete",parameterMap);
		}
		catch(Exception e){
			logger.error("Exception in deleteSelectedPartDocument"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	

	


}