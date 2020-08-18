package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.web.dto.CommMonitorRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderDocRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.dto.SPNLandingTableRowDTO;
import com.newco.marketplace.web.dto.SPNMemberManagerSearchResultsDTO;
import com.newco.marketplace.web.dto.SPNProviderProfileBuyerTable;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ISPNBuyerDelegate {
	
	// List of networks for given buyer
	public List<SPNLandingTableRowDTO>  getNetworkList(Integer buyerId);
	
	public List<SPNLandingTableRowDTO> getAllNetworkList(Integer buyerId);
	
	public SPNBuilderFormDTO getSPNDetails(Integer spnId, List<SPNBuilderDocRowDTO> documentsDTOList) throws Exception;
	
	public List<SPNBuilderDocRowDTO> getAllDocumentsList(Integer buyerId, Integer categoryId, Integer roleId, Integer userId)
	               throws Exception;
	
	public void updateSPN(SPNBuilderFormDTO builderFormDto) throws Exception;
	
	public void createSPN(SPNBuilderFormDTO builderFormDto) throws Exception;
	
	public void deleteSPN(Integer spnId) throws Exception;
	
	public String getLogoFileName(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws Exception;
	
	public List<SPNBuilderDocRowDTO> getSpnRelatedDocDTOList(Integer spnId) throws Exception;
	
	public void updateStatusByProviderResponse(String response, Integer spnNetworkId)throws Exception;
	
	public List<SPNHeaderVO> loadAllActiveSPNetwork() throws Exception;
	
	public List<SPNCampaignVO> fetchActiveCampaigns( Integer spnId ) throws Exception; 
	
	public void createNewSPNCampaign(SPNCampaignVO campaign) throws Exception;
	
	public void deleteSPNCampaign(Integer campaignId) throws Exception;
	
	public ProcessResponse insertSPNDocument(DocumentVO documentVO) throws Exception;
	
	public List<CommMonitorRowDTO> loadInviteSPNNetwork(Integer vendorResourceId, Integer companyId) throws Exception;

	public DocumentVO getLogoDoc(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws Exception;
	
	// Provider Profile Page
	/* Retrieve Provider Complete Profile SPN Info	 */
	public List<SPNProviderProfileBuyerTable> getProviderProfileBuyers(Integer providerId) throws Exception;

	public SPNMemberManagerSearchResultsDTO getSPNMembers(SPNMemberManagerSearchResultsDTO pageDTO, CriteriaMap map) throws Exception; 
	
	public void  approveSelectedMembers(List<Integer> selectedIds)  throws Exception;
	
	public void  removeSelectedMembers(List<Integer> selectedIds)  throws Exception;
	/**
	 * get the list of old spn for given buyer
	 * @param buyerId
	 * @return
	 */
	public List<SPNLandingTableRowDTO>  getExistingNetworkListByBuyer(Integer buyerId);
		
}
