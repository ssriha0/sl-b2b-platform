package com.newco.marketplace.web.delegates;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplateForSkuDTO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentForSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.LuServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.SkillTreeForSkuVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author madhup_chand
 *
 */
public interface  ISOWizardFetchDelegate
{
	
    public ContactLocationVO  getBuyerContLocDetails(int buyerId);
    public void setLockEditMode(String soId, Integer editIndex, SecurityContext securityContext);
    public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws BusinessServiceException;
    public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId) throws BusinessServiceException;
    public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd) throws BusinessServiceException;
    public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId,String buyerId,String stateCd) throws BusinessServiceException;
	public List<LookupVO> getStateCodes() throws DataServiceException;
	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException;
	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException;	
	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException;   
	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip) throws BusinessServiceException;
	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId);
	
	
	public ServiceOrder getServiceOrder(String soId );
	
	public ArrayList<DocumentVO> getDocumentsBySOID(DocumentVO documentVO, Integer userId) throws DataServiceException;
	
	public ArrayList<DocumentVO> getDocumentsMetaDataBySOID(DocumentVO documentVO, Integer userId,String docSource) throws DataServiceException;
	/**
	 * Loads the service order - scope of work DTO from the data store
	 * 
	 * @param soID
	 * @param i 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public HashMap<String, Object> getServiceOrderDTOs(String soID, int i) throws BusinessServiceException;
	
	
	public List<BuyerDocumentTypeVO> retrieveDocumentByBuyerIdAndSource(Integer buyerId,Integer source) throws BusinessServiceException;
	
	public CreateServiceOrderDescribeAndScheduleDTO loadDefaultLocation(Integer buyerID, Integer buyerResourceID);
	
	/**
	 * Loads the service order - creates Simple service Order DTOs from the data store
	 * 
	 * @param soID
	 * @param isSSO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public HashMap<String, Object> getSimpleServiceOrderDTOs(String soID) throws BusinessServiceException;
	
	
	/**
	 * Sets the service order edit indicator in the datastore.
	 * 
	 * @param soID
	 */
	public void setServiceOrderEditIndicator(String soID);

	/**
	 * Loads the service order - scope of work DTO from the data store
	 * 
	 * @param soID
	 * @return
	 * @throws BusinessServiceException 
	 */

	public ArrayList<SOWBrandingInfoDTO> retrieveBuyerDocumentsByBuyerIdAndCategory(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException;
	
	public SOWBrandingInfoDTO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException;
	
	public SODocumentDTO retrieveServiceOrderDocumentByDocumentId(Integer documentId)throws BusinessServiceException;
	
	public void applyBrandingLogo(String soId, Integer logoDocumentId)throws BusinessServiceException;

	public ArrayList<SOWProviderDTO> getRefinedProviderList(SOWProviderSearchDTO providerSearchDto,LocationVO serviceLocation, String soId);

	public ArrayList<SOWProviderDTO> getProviderList(ProviderSearchCriteriaVO searchCriteria, String soId);
	
    /**
	 * Get Contact and Location details for the Buyer Resource
	 * @param buyerId Buyer Id of the Buyer Resource
	 * @param buyerResId Buyer Resource Id
     */
    public ContactLocationVO getBuyerResContLocDetails(int buyerId, int buyerResId);
    
    public Boolean saveServiceOrderAsTemplate(Integer buyerId, ServiceOrderDTO serviceOrderDto);
	public String getValidDisplayExtensions(); 
	/**
	 * This method retrives a boolean value after checking if the Order can be routed.
	 * @param serviceOrderId
	 * @return boolean
	 */
	public boolean checkStatusForRoute(String serviceOrderId);
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContLocDetailsForSO(String soID) throws DelegateException;

	/**
	 * Get Contact and Location details for the Buyer Contact Id
	 * @param so_id
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContLocDetailsForContactId(Integer contactId) throws DelegateException;
	
	public ArrayList<LookupVO> getPartStatus() throws DataServiceException;

	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws DelegateException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DelegateException;
	/**
	 * This method retrieves category id for a buyerId.
	 * @param Integer buyerId
	 * @return List
	 * throws DelegateException
	 */
	public List<BuyerSkuCategoryVO>  fetchBuyerSkuCategories(Integer buyerId) throws DelegateException;
	/**
	 * This method retrieves sku name for a categoryid.
	 * @param Integer buyerId
	 * @return List
	 * throws DelegateException
	 */
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws DelegateException;
	/**
	 * This method retrieves sku details for a skuId.
	 * @param Integer skuId
	 * @return BuyerSkuTaskForSoVO
	 * throws DelegateException
	 */
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)throws DelegateException;
	/**
	 * This method retrieves bid price of the selected skuId.
	 * @param Integer skuId
	 * @return BuyerSkuTaskForSoVO
	 * throws DelegateException
	 */
	public Double fetchBidPriceBySkuId(Integer skuId)throws DelegateException;
	
	/**
	 * This method retrieves template details for a serviceTypeTemplateId for a particular sku id.
	 * @param Integer serviceTypeTemplateId
	 * @return LuServiceTypeTemplateVO
	 * throws DelegateException
	 */
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws DelegateException;
	/**
	 * This method retrieves skill tree details for a  particular node id.
	 * @param Integer nodeId
	 * @return SkillTreeForSkuVO
	 * throws DelegateException
	 */
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws DelegateException;
	/**
	 * This method retrieves template details for a  particular template Id.
	 * @param Integer templateId
	 * @return BuyerSOTemplateForSkuVO
	 * throws DelegateException
	 */
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws DelegateException;
	
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId) throws DelegateException;
	
	/**This method will fetch the document id for the given title uploaded by the buyer
	 * @param title
	 * @param buyerId
	 * @return
	 * @throws DelegateException
	 * @throws BusinessServiceException 
	 */
	public  Integer retrieveBuyerLogDocumentId(String title,Integer buyerId) throws  BusinessServiceException;
	 /**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param buyerId
	 * @param soId
	 * @return List<DocumentVO>
	 * @throws DelegateException
	 */
	public  List<DocumentVO> retrieveRefBuyerDocumentsByBuyerId(Integer buyerId,String soId) throws DelegateException;
	
	//to check whether buyer has permission to view prov docs
	public boolean getViewDocPermission(String userName);
	
	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getLogoDocumentId(String soId)throws  BusinessServiceException;
	
	
	//This method fetches the sku indicator from so_workflow_controls to identify type of service order
	//public Boolean checkSkuIndicatorValue(String soID);
}//end interface