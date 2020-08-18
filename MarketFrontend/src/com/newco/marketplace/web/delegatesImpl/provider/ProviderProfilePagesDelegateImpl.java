/**
 *
 */
package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.business.iBusiness.provider.ISkillAssignBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.dto.provider.ProviderInfoPagesDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;

/**
 * @author hoza
 *
 */
public class ProviderProfilePagesDelegateImpl implements
		IProviderProfilePagesDelegate {
	private IProviderInfoPagesBO providerInfoPagesBO;

	private ISkillAssignBO skillAssingBO;
	
	private ISPNetworkBO spnetworkBO;

	private static final Logger localLogger = Logger.getLogger(ProviderProfilePagesDelegateImpl.class.getName());

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate#getPublicProfile(com.newco.marketplace.web.dto.provider.PublicProfileDto)
	 */
	public ProviderInfoPagesDto getPublicProfile(int resourceId) throws DelegateException {
		ProviderInfoPagesDto dataDto = new ProviderInfoPagesDto();
		if ( resourceId <= 0  ) {
			//This is the scenario where some funky thing happened. We gotta have NON NEGATIVE resource id in order to continue
			//TODO throwing Exception for now.. find out the better way to handle this sceanrio
			localLogger.error("Delegate could not get the Resource ID from the Action...Trak back there");
			throw new DelegateException("Could not find resource id or resource id OR resource ID is negative");
		}

		if(this.providerInfoPagesBO == null ) {
			localLogger.error("Delegate could not get required BOs to start.. looks like its a configuration issue. check the Spring Context for this");
			throw new DelegateException("Delegate could not get required BOs to start.");
		}
		//Start building DTO now
		try {
			PublicProfileVO responseVO = this.providerInfoPagesBO.getProviderDetails(new Integer(resourceId));
			dataDto.setProviderPublicInfo(responseVO);
			dataDto.setCompanyPublicInfo(responseVO.getParentVendor());
			dataDto.setZipData(responseVO.getProviderLatitueLongi());
			dataDto.setSkillsInfo(getResourceSkillMap_Optimized(resourceId,responseVO.getResourceSkillList()));
		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while retrieving Provider Details...Track back there", e);
			throw new DelegateException("Error occurred while retrieving Provider Details : " +e);
		}
		return dataDto;
	}
	
	public ProviderInfoPagesDto getPublicFirmProfile(int vendorId)
		throws DelegateException {
		ProviderInfoPagesDto dataDto = new ProviderInfoPagesDto();
		try {
			PublicProfileVO responseVO = this.providerInfoPagesBO.getProviderFirmDetails(new Integer(vendorId));
			dataDto.setCompanyPublicInfo(responseVO.getParentVendor());
		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while retrieving Provider Details...Track back there", e);
			throw new DelegateException("Error occurred while retrieving Provider Details : " +e);
		}
		return dataDto;
	}

	private Map <Integer,ResourceSkillAssignDto> getResourceSkillMap(int resourceId,List<SkillNodeVO> skilltree) throws DelegateException{
		Map<Integer,ResourceSkillAssignDto> skillInfoMap = new HashMap();
		for(SkillNodeVO vo : skilltree){
			Integer nodeId = vo.getNodeId();
			ResourceSkillAssignDto dto = getSkills(resourceId,nodeId.intValue());
			skillInfoMap.put(nodeId, dto);
		}
		return skillInfoMap;
	}

	private Map <Integer,ResourceSkillAssignDto> getResourceSkillMap_Optimized(int resourceId,List<SkillNodeVO> rootNodes) throws DelegateException{
		Map<Integer,ResourceSkillAssignDto> skillInfoMap = new HashMap();
		List<Integer> rootNodeIds = new ArrayList<Integer>();
		Map<Integer,List<CheckedSkillsVO>> checkSkillMap = new HashMap<Integer,List<CheckedSkillsVO>>();


		for(SkillNodeVO  rootVo :rootNodes ){
			rootNodeIds.add(rootVo.getNodeId());
		}




		Map<Integer, List<SkillNodeVO>> skillMap = getSkillTree(rootNodeIds);
		List<CheckedSkillsVO> checkedServices = getAllCheckedSkillsService( resourceId);

		for(CheckedSkillsVO  cvo : checkedServices ) {
			if(cvo.getRootNodeId()> 0 ){
				 if(!checkSkillMap.containsKey(cvo.getRootNodeId())){
					 checkSkillMap.put(cvo.getRootNodeId(), new ArrayList<CheckedSkillsVO>());
				 }
				 checkSkillMap.get(cvo.getRootNodeId()).add(cvo);
			}
		}



		for(Integer id :skillMap.keySet() ){
			ResourceSkillAssignDto skillAssignInfo = new ResourceSkillAssignDto();
			skillAssignInfo.setSkillTreeList((ArrayList) skillMap.get(id));
			//before aligin grab first node and grab its Service Types as it is same as root node
			if(skillMap.get(id).size() > 0 ){
				List <SkillNodeVO> skills = skillMap.get(id);
				List<ServiceTypesVO> list = skills.get(0).getServiceTypes();
				skillAssignInfo.setServiceTypes((ArrayList)list);
			}
			alignAvailableSkills((ArrayList) skillMap.get(id),checkSkillMap.get(id));
			skillInfoMap.put(id, skillAssignInfo);

		}




		return skillInfoMap;
	}




	/* List <SkillNodeVO> subskillList = (List <SkillNodeVO>) rootNodeMap.get(rootNodeId).get(rootNodeId);
	  for(SkillNodeVO  vo1 : subskillList){
		  vo1.getServiceTypes().add(arg0)
	  }*/


	public List<SkillNodeVO> getSkillsForResource(int resourceId) throws DelegateException{
		List<SkillNodeVO> skilllist = new ArrayList();
		try {
			 skilllist  = skillAssingBO.getSkillsForResource(new Integer(resourceId));

		} catch (BusinessServiceException e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
		//	throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
		//	throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		return skilllist;
	}
	public Map<Integer, List<SkillNodeVO>> getSkillTree(List<Integer> rootNodeIds) throws DelegateException {
		Map<Integer, List<SkillNodeVO>> skillMap = new HashMap<Integer, List<SkillNodeVO>>();
		try {
			skillMap  = skillAssingBO.getSkillTree(rootNodeIds);

		} catch (BusinessServiceException e) {
			localLogger.error("Delegate could not retrive info ", e);
			//throw new DelegateException("Delegate could not retrive info ");
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info ", e);
			//throw new DelegateException("Delegate could not retrive info ");
		}
		return skillMap;
	}
	public List<CheckedSkillsVO> getAllCheckedSkillsService(int resourceId) throws DelegateException {
		List<CheckedSkillsVO> skilllist = new ArrayList();
		try {
			 skilllist  = skillAssingBO.getAllCheckedSkills(new Integer(resourceId));

		} catch (BusinessServiceException e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
			//throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
			//throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		return skilllist;
	}

	public List<ServiceTypesVO> getServiceTypeTree(List<Integer> rootNodeIds) throws DelegateException {
		List<ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
		try {
			serviceTypes  = skillAssingBO.getServiceTypeTree(rootNodeIds);

		} catch (BusinessServiceException e) {
			localLogger.error("Delegate could not retrive info ", e);
			//throw new DelegateException("Delegate could not retrive info ",e);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info ", e);
			//throw new DelegateException("Delegate could not retrive info ",e);
		}
		return serviceTypes;
	}
	/**
	 * This method returns the L
	 * @param resourceid
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public ResourceSkillAssignDto getSkills(int resourceId, int rootNodeId) throws DelegateException {
		localLogger.info("----------getSkills() in ProviderProfilePagesDelegateImpl  method starts-------");
		ResourceSkillAssignDto skillAssignInfo = new ResourceSkillAssignDto();
		SkillAssignVO requestRootNodeVO = new SkillAssignVO();
		requestRootNodeVO.setNodeId(rootNodeId);


		ArrayList<ServiceTypesVO> serviceTypes;
		ArrayList<SkillNodeVO> skillNodeTree;
		try {
			SkillAssignResponse sr = skillAssingBO.getSkills(requestRootNodeVO);
			serviceTypes = sr.getServiceList();
			skillNodeTree = sr.getSkillTreeList();
			// get the checked node ids/serviceTypes for my resource
			ArrayList<CheckedSkillsVO> checkedSkills = new ArrayList<CheckedSkillsVO>();
			checkedSkills = getCheckedSkills(resourceId, rootNodeId);
			alignAvailableSkills(skillNodeTree, checkedSkills);
			//checkCheckAll(skillNodeTree,serviceTypes);
			//put stuff to info object
			skillAssignInfo.setSkillTreeList(skillNodeTree);
			skillAssignInfo.setServiceTypes(serviceTypes);
		} catch (BusinessServiceException e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
			throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for resource id ="+resourceId, e);
			throw new DelegateException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		localLogger.info(".getSkills() method ends");
		return skillAssignInfo;
	}

	/**
	 *
	 * @param skillNodes
	 * @param checked
	 */
	private void alignAvailableSkills(List<SkillNodeVO> skillNodes, List<CheckedSkillsVO> checked) {
		localLogger	.info("ResourceSkillAssignAction.alignAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO) skillNodes.get(i);
			//skill.setActive(false);
			if (checked != null)
			{
				for (int j = 0; j < checked.size(); j++) {
					CheckedSkillsVO chkd = (CheckedSkillsVO) checked.get(j);
					if (skill.getNodeId() == chkd.getNodeId()) {
						skill.setActive(true);
						checkParentSkillNode((int)skill.getParentNodeId(),skillNodes);
						ArrayList<ServiceTypesVO> serviceTypes = skill.getServiceTypes();
						for (int k = 0; k < serviceTypes.size(); k++) {
							ServiceTypesVO servType = serviceTypes.get(k);
							if (servType.getServiceTypeId() == chkd.getServiceTypeId()) {
								servType.setActive(true);
							}
							/*else {
								servType.setActive(false);
							}*/
						}
					}
				}
			}
		}
		localLogger	.info("ResourceSkillAssignAction.alignAvailableSkills() method ends");
	}

	//Why dont they use Map.... at least I can get freaking constant time search resutl...
	private void checkParentSkillNode(int parentnodeid,List<SkillNodeVO> skillNodes){
		for(SkillNodeVO vo : skillNodes){
			if(parentnodeid == vo.getNodeId()){
				vo.setActive(true);
				vo.setOverriderSkillType(true);

				return;
			}
		}
	}
	private void checkCheckAll(ArrayList<SkillNodeVO> skillNodes,	ArrayList<ServiceTypesVO> serviceType) throws Exception {
		localLogger.info("ProviderProfilePagesDelegateImpl.checkCheckAll() method starts");
		boolean checkFlag = true;
		if (skillNodes == null || skillNodes.size() <= 0 || serviceType == null
				|| serviceType.size() <= 0)
			return;

		int loopSize = serviceType.size();
		if (loopSize <= 0)
			return;

		for (int loop1 = 0; loop1 < loopSize; loop1++) {
			checkFlag = true;
			for (int i = 0; i < skillNodes.size(); i++) {
				SkillNodeVO skill = (SkillNodeVO) skillNodes.get(i);

				ArrayList<ServiceTypesVO> serviceTypes = skill.getServiceTypes();
				if (serviceTypes != null && serviceTypes.size() > 0) {
					ServiceTypesVO serviceTypesVO = serviceTypes.get(loop1);
					if (serviceTypesVO == null)
						continue;

					if (!serviceTypesVO.isActive()) {
						checkFlag = false;
						break;
					}
				}
			}

			if (checkFlag) {
				ServiceTypesVO serTypeVO = serviceType.get(loop1);
				if (serTypeVO != null)
					serTypeVO.setActive(true);
			}
		}

		localLogger.info("ProviderProfilePagesDelegateImpl.checkCheckAll() method ends");
	}
	private ArrayList<CheckedSkillsVO> getCheckedSkills(long resourceId,
			int rootNodeId) throws Exception {
		localLogger.info("ProviderProfilePagesDelegateImpl.getCheckedSkills() method starts");
		ArrayList<CheckedSkillsVO> checkedSkills = null;
		CheckedSkillsVO checkedSkillsVO = new CheckedSkillsVO();
		checkedSkillsVO.setResourceId(resourceId);
		checkedSkillsVO.setRootNodeId(rootNodeId);
		try {
			checkedSkills = skillAssingBO.getCheckedSkills(checkedSkillsVO);
		} catch (Exception e) {
			localLogger.error("Error in getCheckedSkills.  Not able to get checked skills from ProviderProfilePagesDelegateImpl.", e);
			throw e;
		}
		localLogger.info("ProviderProfilePagesDelegateImpl.getCheckedSkills() method ends");
		return checkedSkills;
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate#getPrimaryPicture(int)
	 */
	public ProviderDocumentVO getPrimaryPicture(int resourceId)
			throws DelegateException {
		ProviderDocumentVO doc = new ProviderDocumentVO();
		try {
			doc = this.providerInfoPagesBO.getProviderPrimaryPhoto((new Integer(resourceId)));

		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while retrieving Provider Picture...Trak back there", e);
			throw new DelegateException("Error occurred while retrieving Provider Picture : " +e);
		}
		return doc;
	}
     
     public VendorDocumentVO getFirmLogo(int vendorId)
			throws DelegateException {
		VendorDocumentVO doc = new VendorDocumentVO();
		try {
			doc = this.providerInfoPagesBO.getFirmLogo((new Integer(vendorId)));

		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while retrieving getPrimaryLogo...Trak back there", e);
			throw new DelegateException("Error occurred while retrieving getPrimaryLogo : " +e);
		}
		return doc;
	}
     
	public ISkillAssignBO getSkillAssingBO() {
		return skillAssingBO;
	}

	public void setSkillAssingBO(ISkillAssignBO skillAssingBO) {
		this.skillAssingBO = skillAssingBO;
	}


	public IProviderInfoPagesBO getProviderInfoPagesBO() {
		return providerInfoPagesBO;
	}

	public void setProviderInfoPagesBO(IProviderInfoPagesBO providerInfoPagesBO) {
		this.providerInfoPagesBO = providerInfoPagesBO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate#uploadResourcePicture(com.newco.marketplace.dto.vo.provider.ProviderDocumentVO)
	 */
	public ProcessResponse uploadResourcePicture(ProviderDocumentVO providerdocument)  throws DelegateException {
		ProcessResponse pr = new ProcessResponse();
		try {
			pr = this.providerInfoPagesBO.uploadResourcePicture(providerdocument);

		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while uploading Provider Picture...Trak back there", e);
			throw new DelegateException("Error occurred while uploading Provider Picture : " +e);
		}
		return pr;
	}
	
		// uploading the Firm Logo
	public ProcessResponse uploadFirmLogo(VendorDocumentVO vendorDocument) throws DelegateException{
		ProcessResponse pr = new ProcessResponse();
		try {
			pr = this.providerInfoPagesBO.uploadFirmLogo(vendorDocument);

		} catch (BusinessServiceException e) {
			localLogger.error("Error occurred while uploading Firm logo...Trak back there", e);
			throw new DelegateException("Error occurred while uploading Firm logo : " +e);
		}
		return pr;
	}

	public Boolean isSPFirmNetworkTabViewable(int buyerId, int providerFirmId) throws DelegateException {
		Boolean isViewable = false;
		try{
			isViewable = this.spnetworkBO.isSPFirmNetworkTabViewable(buyerId, providerFirmId);
		}catch( com.newco.marketplace.exception.core.BusinessServiceException e){
			throw new DelegateException("Error occurred in isSPFirmNetworkTabViewable due to : " +e.getMessage());
		}
		return isViewable;
	}
	
	public Boolean isProviderNetworkTabViewable(int buyerId, int providerId) throws DelegateException {
		Boolean isViewable = false;
		try{
			isViewable = this.spnetworkBO.isProviderNetworkTabViewable(buyerId, providerId);
		}catch( com.newco.marketplace.exception.core.BusinessServiceException e){
			throw new DelegateException("Error occurred in isProviderNetworkTabViewable due to : " +e.getMessage());
		}
		return isViewable;
	}

	public ISPNetworkBO getSpnetworkBO() {
		return spnetworkBO;
	}

	public void setSpnetworkBO(ISPNetworkBO spnetworkBO) {
		this.spnetworkBO = spnetworkBO;
	}


}
