package com.newco.marketplace.web.delegatesImpl.spn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.spn.SPNetworkBOImpl;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResults;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.CommMonitorRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderDocRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.dto.SPNLandingTableRowDTO;
import com.newco.marketplace.web.dto.SPNMemberManagerSearchResultsDTO;
import com.newco.marketplace.web.dto.SPNProviderProfileBuyerTable;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.SPNMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class SPNBuyerDelegateImpl implements ISPNBuyerDelegate {
	private static final Logger logger = Logger.getLogger(SPNBuyerDelegateImpl.class.getName());
	
	private ISelectProviderNetworkBO spnCreateUpdateBO;
	private IDocumentBO documentBO;
	private SPNetworkBOImpl spnetBO;

	
	public SPNBuyerDelegateImpl(ISelectProviderNetworkBO spnCreateUpdateBOArg, IDocumentBO documentBOArg){
		this.spnCreateUpdateBO = spnCreateUpdateBOArg;
		this.documentBO = documentBOArg;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#getNetworkList(java.lang.Integer)
	 */
	public List<SPNLandingTableRowDTO>  getNetworkList(Integer buyerId) {
		
		List<SPNLandingTableRowDTO> spnLandingTableDTOList = new ArrayList<SPNLandingTableRowDTO>();
		List<SPNSummaryVO> spnSummaryVOList = new ArrayList<SPNSummaryVO>();
		List<SPNMonitorVO> spnMonitorVOList = new ArrayList<SPNMonitorVO>();
		
		try{
			spnMonitorVOList = spnetBO.getSpnListForBuyer(buyerId);
			spnLandingTableDTOList = SPNMapper.convertspnMonitorVOToDTO(spnMonitorVOList);
			
		}catch(Exception e){
			logger.error("Error returned trying to get SPN list for buyerId from SelectProviderNetworkBOImpl:" + buyerId,e);
		}
		
		return spnLandingTableDTOList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#getAllNetworkList(java.lang.Integer)
	 */
	public List<SPNLandingTableRowDTO> getAllNetworkList(Integer buyerId) {
		
		List<SPNLandingTableRowDTO> spnLandingTableDTOList = new ArrayList<SPNLandingTableRowDTO>();
		List<SPNSummaryVO> spnSummaryVOList = new ArrayList<SPNSummaryVO>();
		List<SPNMonitorVO> spnMonitorVOList = new ArrayList<SPNMonitorVO>();
		Map<Long,Long> spnApprovedProviderCountMap = null;
		
		try{
			spnMonitorVOList = spnetBO.getSpnAllListForBuyer(buyerId);
			spnApprovedProviderCountMap = spnetBO.getSpnApprovedProviderCount();
			
			spnLandingTableDTOList = SPNMapper.convertAllspnMonitorVOToDTO(spnMonitorVOList,spnApprovedProviderCountMap);
			
		}catch(Exception e){
			logger.error("Error returned trying to get SPN list for buyerId from SelectProviderNetworkBOImpl:" + buyerId,e);
		}
		
		return spnLandingTableDTOList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#getNetworkListForSears(java.lang.Integer)
	 */
	public List<SPNLandingTableRowDTO>  getExistingNetworkListByBuyer(Integer buyerId) {
		
		List<SPNLandingTableRowDTO> spnLandingTableDTOList = new ArrayList<SPNLandingTableRowDTO>();
		List<SPNSummaryVO> spnSummaryVOList = new ArrayList<SPNSummaryVO>();
		
		try{
			spnSummaryVOList = spnCreateUpdateBO.getSPNByBuyerId(buyerId);
			spnLandingTableDTOList = SPNMapper.convertVOToDTOSpnLandingInfo(spnSummaryVOList);
		}catch(Exception e){
			logger.error("Error returned trying to get SPN list for buyerId from SelectProviderNetworkBOImpl:" + buyerId,e);
		}
		
		return spnLandingTableDTOList;
	}
	
    /*
     * get the select Provider Network Details for given SPN Id
     * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#getSPNDetails(java.lang.Integer)
     */
	public SPNBuilderFormDTO getSPNDetails(Integer spnId, List<SPNBuilderDocRowDTO> documentsDTOList) throws Exception  {
		
		SPNBuilderFormDTO networkDTO = new SPNBuilderFormDTO();
		try{
			networkDTO = SPNMapper.convertHeaderVOToDTO(spnCreateUpdateBO.getSPNBySPNId(spnId), documentsDTOList);
			// set Business name into DTO which is used for provider SPN campaign page
			setBuyerInfoForCampaign(networkDTO);
		}catch(Exception e){
			logger.error("Error returned trying to get SPN deatils for buyerId from SelectProviderNetworkBOImpl:" + spnId, e);
			throw e;
		}
		
		return networkDTO;
	}
	
	/*
	 * get Document List for the given Buyer 
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#getDocumentsList(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	
	public List<SPNBuilderDocRowDTO> getAllDocumentsList(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws Exception{
		
		List<SPNBuilderDocRowDTO> builderDocDTOList = new ArrayList<SPNBuilderDocRowDTO>();
		try{
			List<DocumentVO> documentVOList = documentBO.retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, categoryId, roleId, userId); 
			builderDocDTOList = SPNMapper.convertBuyerDocInfoVOToDTO(documentVOList);
			
		}catch(Exception e){
			logger.error("Error returned trying to get Document list for buyerId from DocumentBOImpl:" + buyerId, e);
			throw e;
		}
		
		
		return builderDocDTOList;
	}

	
	/*
	 * insert New select Provider Network
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#createSPN(com.newco.marketplace.web.dto.SPNBuilderFormDTO)
	 */
	public void createSPN(SPNBuilderFormDTO builderFormDto) throws Exception {
		try{
			SPNHeaderVO spnHeaderVO = SPNMapper.convertHeaderDTOToVO(builderFormDto);
			spnCreateUpdateBO.insertSPN(spnHeaderVO);
		}catch(Exception e){
			logger.error("Error returned trying to insert SPN in SelectProviderNetworkBOImpl:", e);
			throw e;
		}

	}
	
	/*
	 * update SPN
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#updateSPN(com.newco.marketplace.web.dto.SPNBuilderFormDTO)
	 */
	public void updateSPN(SPNBuilderFormDTO builderFormDto) throws Exception {
		try{
			SPNHeaderVO spnHeaderVO = SPNMapper.convertHeaderDTOToVO(builderFormDto);
		    spnCreateUpdateBO.updateSPN(spnHeaderVO);
		}catch(Exception e){
			logger.error("Error returned trying to update SPN in SelectProviderNetworkBOImpl:", e);
			throw e;
		}
		
	}
	
	/* 
	 * delete SPN
	 * @see com.newco.marketplace.web.delegates.ISPNBuyerDelegate#deleteSPN(java.lang.Integer)
	 */
	public void deleteSPN(Integer spnId) throws Exception {
		try{
			spnCreateUpdateBO.deleteSPN(spnId); 
		}catch(Exception e){
			logger.error("Error returned trying to update SPN in SelectProviderNetworkBOImpl:", e);
			throw e;
		}
		
	}
	
	private void setBuyerInfoForCampaign(SPNBuilderFormDTO networkDTO) throws Exception{
		String buyerBusinessName = "Business Name";
		try{
			buyerBusinessName  = spnCreateUpdateBO.getBuyerBusinessName(networkDTO.getBuyerId());
			networkDTO.setBusinessName(buyerBusinessName);
		}catch(Exception e){
			logger.error("Error returned trying to get Business name in SelectProviderNetworkBOImpl: "+ networkDTO.getBuyerId(), e );
			throw e;
		}
		
	}
	
	public DocumentVO getLogoDoc(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws Exception
	{
		DocumentVO docVO = null;
		try{
			List docList = documentBO.retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, categoryId, roleId, userId);
			if(docList!= null && docList.size()>0){
				docVO = (DocumentVO)docList.get(0);				
			}
		}catch(Exception e){
			logger.error("Error returned trying to get Logo file name in SelectProviderNetworkBOImpl: "+ buyerId, e );
			throw e;
		}
		
		return docVO;
	}
	
	
	public String getLogoFileName(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws Exception{
		String logoFileName = null;
		try{
			List docList = documentBO.retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, categoryId, roleId, userId);
			if(docList!= null && docList.size()>0){
				DocumentVO docVo = (DocumentVO)docList.get(0);
				logoFileName = docVo.getFileName();
			}
		}catch(Exception e){
			logger.error("Error returned trying to get Logo file name in SelectProviderNetworkBOImpl: "+ buyerId, e );
			throw e;
		}
		return logoFileName;
	}
	
	public List<SPNBuilderDocRowDTO> getSpnRelatedDocDTOList(Integer spnId) throws Exception{
		
		 List<SPNBuilderDocRowDTO>  selectedDocList = new ArrayList<SPNBuilderDocRowDTO>();
		 try{
			 SPNHeaderVO headerVO = spnCreateUpdateBO.getSPNBySPNId(spnId);
			 List<DocumentVO> spnRelatedDocs=null;
			 if(headerVO != null){
				 spnRelatedDocs = headerVO.getSpnRelatedDocumentIds();			 
				 selectedDocList = SPNMapper.convertSpnRelatedDocsVOToDTO(spnRelatedDocs);
			 }
		 }catch(Exception e){
			 logger.error("Error returned trying to get DTO list of  spnRelatedDocs in SelectProviderNetworkBOImpl: "+ spnId, e );
				throw e;
		 }
		 return selectedDocList;
	}
	
	public void updateStatusByProviderResponse(String response, Integer spnNetworkId)throws Exception{
		try{
			SPNNetworkResourceVO networkResourceVO = new SPNNetworkResourceVO();
			networkResourceVO.setSpnNetworkId(spnNetworkId);
			if(Constants.SPN.INTERESTED_STR.equals(response)){
				networkResourceVO.setApplicationDate(new Date());
				networkResourceVO.setSpnStatusId(Constants.SPN.STATUS.APPLICANT);
			}
			if(Constants.SPN.NOT_INTERESTED_STR.equals(response)){
				networkResourceVO.setApplicationDate(null);
				networkResourceVO.setSpnStatusId(Constants.SPN.STATUS.NOT_INTERESTED);
			}
			
			spnCreateUpdateBO.updateStatusByProviderResponse(networkResourceVO);
			
		}catch(Exception e){
			logger.error("Error returned trying to update provider response to ProviderNetwork in SelectProviderNetworkBOImpl: "+ spnNetworkId, e );
			throw e;
		}
	}
	
	public void createNewSPNCampaign(SPNCampaignVO campaign) throws Exception {
		try{
			if(campaign.getMarketId() != null &&
				campaign.getMarketId().intValue() == -1){
				campaign.setMarketId(null);
				campaign.setAllMarkets(1);
			}
		    spnCreateUpdateBO.createNewSPNCampaign(campaign);
		}catch(Exception e){
			logger.error("Error returned trying to create SPN in SelectProviderNetworkBOImpl:", e);
			throw e;
		}
	}
	
	public void deleteSPNCampaign(Integer campaignId) throws Exception {
		try{
		    spnCreateUpdateBO.deleteSPNCampaign(campaignId);
		}catch(Exception e){
			logger.error("Error returned trying to delete SPN Campaign in SelectProviderNetworkBOImpl:", e);
			throw e;
		}
	}

	public List<SPNHeaderVO> loadAllActiveSPNetwork() throws Exception {
		List<SPNHeaderVO>  spns = null;
		 try{
			 spns = spnCreateUpdateBO.getAllSPNS();
		 }catch(Exception e){
			 logger.error("Error retriving SPN Networks:", e );
				throw e;
		 }
		 return spns;
	}
	
	public List<SPNCampaignVO> fetchActiveCampaigns( Integer spnId ) throws Exception {
		List<SPNCampaignVO>  spncs = null;
		 try{
			 spncs = spnCreateUpdateBO.loadAllSPNCampaigns(spnId);
		 }catch(Exception e){
			 logger.error("Error retriving SPN Campaigns:", e );
				throw e;
		 }
		 return spncs;
	}
	
		
	public ProcessResponse insertSPNDocument(DocumentVO documentVO) throws Exception{

		ProcessResponse pr = new ProcessResponse();

		try{
			pr = documentBO.insertSPNDocument(documentVO);
		}catch(Exception e)
		{
			logger.info("Error inserting SO Document, ignoring exception",e);
		}
		return pr;			
	}

	
	public List<CommMonitorRowDTO> loadInviteSPNNetwork(Integer vendorResourceId, Integer companyId) throws Exception{
		List<CommMonitorRowDTO> dtoList =  new ArrayList<CommMonitorRowDTO>();
		
		
		List<SPNHeaderVO> spnHeaderList = spnCreateUpdateBO.loadAllSpnNetworkInvites(vendorResourceId, companyId);
		dtoList = SPNMapper.convertSPNHeaderListToCommRowList(spnHeaderList);
		
		
		return dtoList;
	}
	
	/**
	 * Retrieve Provider Complete Profile SPN Info
	 * @param service Pro Id
	 * @return List<SPNProviderProfileBuyerTable>	
	 */
	public List<SPNProviderProfileBuyerTable> getProviderProfileBuyers(Integer providerId) throws Exception{
		List<SPNProviderProfileBuyerTable> spnProProfileBuyerList = new ArrayList<SPNProviderProfileBuyerTable>();
		List<SPNProviderProfileBuyerVO> spnProProfileVOList = new ArrayList<SPNProviderProfileBuyerVO>();
		try{
			spnProProfileVOList = spnCreateUpdateBO.getProviderProfileBuyers(providerId);
			spnProProfileBuyerList =  SPNMapper.convertProProfileBuyerListVOToDTO(spnProProfileVOList);
			spnProProfileBuyerList = setBuyersLogoDocument(spnProProfileBuyerList);

		}catch(Exception e){
			logger.info("Error retreiving Provider SPNs List for Complete Profile for provider Id "+ providerId, e);
			throw e;
		}
		return spnProProfileBuyerList;
	}
	
	/**
	 * iterate thru buyers list and retrieves respective log doc
	 * @param spnProProfileBuyerList
	 * @return
	 * @throws Exception
	 * TODO refactoring required, need to get logo doc info while getting buyer info only, DAO need to be changed.
	 */
	private List<SPNProviderProfileBuyerTable> setBuyersLogoDocument(List<SPNProviderProfileBuyerTable> spnProProfileBuyerList ) throws Exception{
		List<SPNProviderProfileBuyerTable> spnProProfileBuyerListTemp = new ArrayList<SPNProviderProfileBuyerTable>();
		try{
			for(SPNProviderProfileBuyerTable proSPNBuyerTable: spnProProfileBuyerList){
				Integer buyerId = proSPNBuyerTable.getCompanyID();
				DocumentVO doc = getLogoDoc(buyerId, Constants.DocumentTypes.BUYER, 
				        OrderConstants.BUYER_ROLEID, -1);
				if(doc!= null){
					proSPNBuyerTable.setBlobBytes(doc.getBlobBytes());
					proSPNBuyerTable.setLogoFileName(doc.getFileName());					
				}
				spnProProfileBuyerListTemp.add(proSPNBuyerTable);
			}
			
		}catch(Exception e){
			logger.info("Error retreiving buyer's logo doc for Complete Profile", e);
			throw e;
		}
		return spnProProfileBuyerListTemp;
		
	}

	public SPNMemberManagerSearchResultsDTO getSPNMembers(SPNMemberManagerSearchResultsDTO pageDTO, 
														  CriteriaMap criteriaMap) throws Exception {
		SPNMemberSearchResults searchResults = spnCreateUpdateBO.getSPNResources(criteriaMap);
		CriteriaHandlerFacility.getInstance()
							   .updatePagingCriteria(OrderConstants.PAGING_CRITERIA_KEY, 
									   				 CriteriaHandlerFacility.getInstance().getPagingCriteria(), 
									   				 searchResults.getPaginationVo());
		pageDTO.setResult(searchResults);
		return pageDTO;
	}

	
	public void approveSelectedMembers(List<Integer> selectedIds) throws Exception {
		spnCreateUpdateBO.approveMembers(selectedIds);
		
	}

	public void removeSelectedMembers(List<Integer> selectedIds ) throws Exception {
		spnCreateUpdateBO.removeMembers(selectedIds);
		
	}

	/**
	 * @return the spnetBO
	 */
	public SPNetworkBOImpl getSpnetBO() {
		return spnetBO;
	}

	/**
	 * @param spnetBO the spnetBO to set
	 */
	public void setSpnetBO(SPNetworkBOImpl spnetBO) {
		this.spnetBO = spnetBO;
	}
	
	
	

}
