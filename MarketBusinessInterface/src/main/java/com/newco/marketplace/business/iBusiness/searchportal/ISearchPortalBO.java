/**
 *
 */
package com.newco.marketplace.business.iBusiness.searchportal;

import java.util.List;

import com.newco.marketplace.vo.apiSearch.FirmResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponseData;
import com.newco.marketplace.vo.apiSearch.SearchProviderRequestCriteria;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO;
import com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO;
import com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO;

/**
 * @author hoza
 *
 */
public interface ISearchPortalBO {
	public List<SearchPortalBuyerVO> searchBuyer(SearchPortalBuyerVO filter ) throws  Exception;
	public List<SearchPortalProviderFirmVO> searchProviderFirm(SearchPortalProviderFirmVO filter) throws Exception;
	public List<SearchPortalServiceProviderVO> searchServiceProvider(SearchPortalServiceProviderVO filter) throws Exception;
	/* Search provider firms based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<FirmResponseData>
	 */
	public List<FirmResponseData> searchProviderFirmFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception;
	/* Search service provider based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<ProviderResponseData>
	 */
	public List<ProviderResponseData> searchServiceProviderFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception;
	public boolean updateServiceProviderName (String fName,String mName,String lName,String resouceId) throws Exception;  //SL-18330
	public boolean insertServiceProviderName_Audit(String oldName,String newName,String resouceId,String type,String modified_by) throws Exception; //SL-18330
	
	public GeneralInfoVO getProviderDetails(GeneralInfoVO provider) throws Exception;
	public Integer getBackgroundCheckIdWithSameContact(GeneralInfoVO provider) throws Exception;
	public Integer getBackgroundCheckInfo(Integer bcCheckId)throws Exception;
	public void updateBackGroundCheckDetails(String resourceId, String ssn) throws Exception;
	public void updateBGCheck_details(String resourceId, Integer bgCheckId, Integer bgCheckStateId) throws Exception;
	public Integer getBGCheckId(String resourceId) throws Exception;
	public GeneralInfoVO getProviderDetailsWithSameContact(GeneralInfoVO pro)throws Exception;
	public void updateBGStateInfo(Integer bgCheckId, Integer bgCheckStateId)throws Exception;
}
