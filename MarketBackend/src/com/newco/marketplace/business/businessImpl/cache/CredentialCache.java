package com.newco.marketplace.business.businessImpl.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.iBusiness.provider.ITeamCredentialBO;
import com.newco.marketplace.business.iBusiness.provider.IVendorCredentialBO;
import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsLookupVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.utils.LmsCredUploadConstants;

public class CredentialCache {
	private final Map<String, Map<String, TeamCredentialsLookupVO>> resourceCredCache;
	private final Map<String, Map<String, VendorCredentialsLookupVO>> vendorCredCache;
	
	private ITeamCredentialBO teamCredBO;
	private IVendorCredentialBO vendorCredBO;
	
	private static Integer vendorClientBasedTypeId;
	private static Integer vendorSLBasedTypeId;
	private static Integer resourceClientBasedTypeId;
	private static Integer resourceSLBasedTypeId;
	
	
	public CredentialCache()  throws BusinessServiceException{
		resourceCredCache = new HashMap<String, Map<String,TeamCredentialsLookupVO>>();
		vendorCredCache = new HashMap<String, Map<String,VendorCredentialsLookupVO>>();
		
		resourceCredCache.put(LmsCredUploadConstants.CLIENT_BASED, new HashMap<String,TeamCredentialsLookupVO>());
		resourceCredCache.put(LmsCredUploadConstants.SL_BASED, new HashMap<String,TeamCredentialsLookupVO>());
		vendorCredCache.put(LmsCredUploadConstants.CLIENT_BASED, new HashMap<String,VendorCredentialsLookupVO>());
		vendorCredCache.put(LmsCredUploadConstants.SL_BASED, new HashMap<String,VendorCredentialsLookupVO>());
		// initialize();
	}
	
	private void initialize()  throws BusinessServiceException{
		initializeVendorCredentials();
		initializeResourceCredentials();
	}
	
	private void initializeVendorCredentials() throws BusinessServiceException{
		Map<String,VendorCredentialsLookupVO> luMapVenCredCategory = vendorCredCache.get(LmsCredUploadConstants.CLIENT_BASED);
		VendorCredentialsLookupVO vendorCredentialsLookupVO = new VendorCredentialsLookupVO();
		vendorCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.CLIENT_BASED);
		List<VendorCredentialsLookupVO> vendorCredLookUpVOList = vendorCredBO.getCatListByType(vendorCredentialsLookupVO);
		for (VendorCredentialsLookupVO tvo : vendorCredLookUpVOList) {
			luMapVenCredCategory.put(tvo.getCategory(), tvo);
		}
		luMapVenCredCategory = vendorCredCache.get(LmsCredUploadConstants.SL_BASED);
		vendorCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.SL_BASED);
		vendorCredLookUpVOList = vendorCredBO.getCatListByType(vendorCredentialsLookupVO);
		for (VendorCredentialsLookupVO tvo : vendorCredLookUpVOList) {
			luMapVenCredCategory.put(tvo.getCategory(), tvo);
		}
	}
	
	private void initializeResourceCredentials() throws BusinessServiceException{
		Map<String,TeamCredentialsLookupVO> luMapResCredCategory = resourceCredCache.get(LmsCredUploadConstants.CLIENT_BASED);
		TeamCredentialsLookupVO teamCredentialsLookupVO = new TeamCredentialsLookupVO();
		teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.CLIENT_BASED);
		List<TeamCredentialsLookupVO> teamCredLookUpVOList = teamCredBO.getCatListByType(teamCredentialsLookupVO);
		for (TeamCredentialsLookupVO tvo : teamCredLookUpVOList) {
			luMapResCredCategory.put(tvo.getCategory(), tvo);
		}
		luMapResCredCategory = resourceCredCache.get(LmsCredUploadConstants.SL_BASED);
		teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.SL_BASED);
		teamCredLookUpVOList = teamCredBO.getCatListByType(teamCredentialsLookupVO);
		for (TeamCredentialsLookupVO tvo : teamCredLookUpVOList) {
			luMapResCredCategory.put(tvo.getCategory(), tvo);
		}
	}
	
	public VendorCredentialsLookupVO getVendorCredType(String credType) throws BusinessServiceException{
		VendorCredentialsLookupVO vendorCredentialsLookupVO = new VendorCredentialsLookupVO();
		if(credType.equals(LmsCredUploadConstants.CLIENT_BASED)){
			if(vendorClientBasedTypeId == null){
				vendorCredentialsLookupVO = vendorCredBO.getVendorCredLookup(LmsCredUploadConstants.CLIENT_BASED);
				if(vendorCredentialsLookupVO == null){
					throw new BusinessServiceException("Can not find credential type in lu_vendor_catedential_type table for type: "+LmsCredUploadConstants.CLIENT_BASED);
				}
				vendorClientBasedTypeId = vendorCredentialsLookupVO.getTypeId();
			}
			vendorCredentialsLookupVO.setTypeId(vendorClientBasedTypeId);
			vendorCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.CLIENT_BASED);
		}else if(credType.equals(LmsCredUploadConstants.SL_BASED)){
			if(vendorSLBasedTypeId == null){
				vendorCredentialsLookupVO = vendorCredBO.getVendorCredLookup(LmsCredUploadConstants.SL_BASED);
				if(vendorCredentialsLookupVO == null){
					throw new BusinessServiceException("Can not find credential type in lu_vendor_catedential_type table for type: "+LmsCredUploadConstants.SL_BASED);
				}
				vendorSLBasedTypeId = vendorCredentialsLookupVO.getTypeId();
			}
			vendorCredentialsLookupVO.setTypeId(vendorSLBasedTypeId);
			vendorCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.SL_BASED);
		}
		return vendorCredentialsLookupVO;
	}
	
	public TeamCredentialsLookupVO getResCredType(String credType) throws BusinessServiceException{
		TeamCredentialsLookupVO teamCredentialsLookupVO = new TeamCredentialsLookupVO();
		if(credType.equals(LmsCredUploadConstants.CLIENT_BASED)){
			teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.CLIENT_BASED);
			if(resourceClientBasedTypeId == null){
				teamCredentialsLookupVO = teamCredBO.getTeamCredLookup(credType);
				if(teamCredentialsLookupVO == null){
					throw new BusinessServiceException("Can not find credential type in lu_resource_catedential_type table for type: "+LmsCredUploadConstants.CLIENT_BASED);
				}
				resourceClientBasedTypeId = teamCredentialsLookupVO.getTypeId();
			}
			teamCredentialsLookupVO.setTypeId(resourceClientBasedTypeId);
			teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.CLIENT_BASED);
		}else if(credType.equals(LmsCredUploadConstants.SL_BASED)){
			teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.SL_BASED);
			if(resourceSLBasedTypeId == null){
				teamCredentialsLookupVO = teamCredBO.getTeamCredLookup(credType);
				if(teamCredentialsLookupVO == null){
					throw new BusinessServiceException("Can not find credential type in lu_resource_catedential_type table for type: "+LmsCredUploadConstants.SL_BASED);
				}
				resourceSLBasedTypeId = teamCredentialsLookupVO.getTypeId();
			}
			teamCredentialsLookupVO.setTypeId(resourceSLBasedTypeId);
			teamCredentialsLookupVO.setTypeDesc(LmsCredUploadConstants.SL_BASED);
		}
		return teamCredentialsLookupVO;
	
	}
	
	
	public VendorCredentialsLookupVO getVendorCredentialCategoryId(String credType, String credCatName) throws BusinessServiceException{
		Map<String,VendorCredentialsLookupVO> luMapVenCredCategory = vendorCredCache.get(credType);
		VendorCredentialsLookupVO vendorCredLookUpVo = luMapVenCredCategory.get(credCatName);
		if (vendorCredLookUpVo == null){
			// vendorCredLookUpVo = getVenCredCategoryIdFromDB(credType,credCatName);
			initialize();
			vendorCredLookUpVo = luMapVenCredCategory.get(credCatName);
			/*if(vendorCredLookUpVo != null){
				vendorCredLookUpVo.ge
			//	luMapVenCredCategory.put(vendorCredLookUpVo.getCategory(), vendorCredLookUpVo);
			}*/
		}
		return vendorCredLookUpVo;
	}
	
	/*private VendorCredentialsLookupVO getVenCredCategoryIdFromDB(String credType, String credCatname){
		return vendorCredBO.getCredByCategoryName(credType,credCatname);
	}
	*/
	public TeamCredentialsLookupVO getResourceCredentialCategoryId(String credType, String credCatName) throws BusinessServiceException{
		Map<String,TeamCredentialsLookupVO> luResCredCategory = resourceCredCache.get(credType);
		TeamCredentialsLookupVO teamCredLookupVo = luResCredCategory.get(credCatName);
		if (teamCredLookupVo == null){
			initialize();
			teamCredLookupVo = luResCredCategory.get(credCatName);
			/*teamCredLookupVo = getResCredCategoryIdFromDB(credType,credCatName);
			if(teamCredLookupVo != null){
				luResCredCategory.put(teamCredLookupVo.getCategory(), teamCredLookupVo);
			}*/
		}
		return teamCredLookupVo;
	}
	
/*	private TeamCredentialsLookupVO getResCredCategoryIdFromDB(String credType, String credCatName){
		return teamCredBO.getCredByCategoryName(credType, credCatName);
	}*/

	public ITeamCredentialBO getTeamCredBO() {
		return teamCredBO;
	}

	public void setTeamCredBO(ITeamCredentialBO teamCredBO) {
		this.teamCredBO = teamCredBO;
	}

	public IVendorCredentialBO getVendorCredBO() {
		return vendorCredBO;
	}

	public void setVendorCredBO(IVendorCredentialBO vendorCredBO) {
		this.vendorCredBO = vendorCredBO;
	}
}
