package com.newco.marketplace.web.delegatesImpl.provider;



import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ITeamCredentialBO;
import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.web.delegates.provider.ITeamCredentialDelegate;
import com.newco.marketplace.web.dto.provider.TeamCredentialsDto;
import com.newco.marketplace.web.utils.TeamCredentialMapper;

/**
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class TeamCredentialDelegateImpl implements ITeamCredentialDelegate {

	private ITeamCredentialBO iTeamCredentilaBOImpl;
	private TeamCredentialMapper teamCredentialMemberMapper;
	private static final Logger localLogger = Logger
			.getLogger(TeamCredentialDelegateImpl.class.getName());

	/**
	 * @param businessinfoBO
	 */
	public TeamCredentialDelegateImpl(ITeamCredentialBO iTeamCredentilaBOImpl,
				TeamCredentialMapper teamCredentialMemberMapper) {
		this.iTeamCredentilaBOImpl=iTeamCredentilaBOImpl;
		this.teamCredentialMemberMapper=teamCredentialMemberMapper;

	}

	/***************************************************************************
	 * 
	 * @param teamCredentialMapper
	 * @return
	 */
	public TeamCredentialsDto getCredentialList(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsVO teamCredentialsVO = null;
		VendorResource vendorResource = null;
		try {
			vendorResource = new VendorResource();
			vendorResource = (VendorResource) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, vendorResource);
			vendorResource = iTeamCredentilaBOImpl
					.queryResourceById(vendorResource);
			if (vendorResource != null) {
					if(vendorResource.getNoCredInd()!=null){
						teamCredentialsDto.setPassCredentials(vendorResource
								.getNoCredInd().booleanValue());
					}else{
						teamCredentialsDto.setPassCredentials(false);
					}
				
			}
			teamCredentialsVO = new TeamCredentialsVO();
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
			teamCredentialsDto.setCredentialsList(iTeamCredentilaBOImpl
					.getCredListByResourceId(teamCredentialsVO));
			teamCredentialsVO = iTeamCredentilaBOImpl.getResourceName(teamCredentialsVO);
			teamCredentialsDto.setResourceName(teamCredentialsVO.getResourceName());
			// get the Actity Status

		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}

	public void saveNoCred(TeamCredentialsDto teamCredentialsDto)
			throws DelegateException {
		VendorResource vendorResource = null;
		try {
			vendorResource = new VendorResource();
			vendorResource = (VendorResource) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, vendorResource);
			iTeamCredentilaBOImpl.updateResource(vendorResource);
			// Update the activity Table From Here by calling activity Delegate
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.saveNoCred]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.saveNoCred]"
							+ ex.getMessage());
		}

	}

	/***************************************************************************
	 * 
	 * @param teamCredentialMapper
	 * @return
	 */
	public TeamCredentialsDto getCredentialDetails(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsVO teamCredentialsVO = null;
		try {
			teamCredentialsVO = new TeamCredentialsVO();
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
			if(teamCredentialsVO.getResourceCredId()>0){
				teamCredentialsVO = iTeamCredentilaBOImpl.getCredById(teamCredentialsVO);
			}
			// Convert the Vo Values Into DTo to display it in the front end
			teamCredentialsDto = teamCredentialMemberMapper.convertVOtoDTO(
					teamCredentialsVO, teamCredentialsDto);

			// Load All the List necessary to load in the page
			teamCredentialsDto.setCredentialTypeList(iTeamCredentilaBOImpl
					.getTypeList());
			teamCredentialsDto = getCatListByTypeId(teamCredentialsDto);
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
		}
         return teamCredentialsDto;
	}

	/**
	 * 
	 * @param teamCredentialMapper
	 * @return
	 */
	public TeamCredentialsDto commitCredentialData(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsVO teamCredentialsVO = null;
		try {
			teamCredentialsVO = new TeamCredentialsVO();
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);

			// Resource Cred Id is not zero then Insert otherwise Update
			if (teamCredentialsVO.getResourceCredId() != 0) {
				iTeamCredentilaBOImpl.updateCredentials(teamCredentialsVO);
			} else {
				teamCredentialsVO = iTeamCredentilaBOImpl
				.insertCredentials(teamCredentialsVO);
			}
			
			teamCredentialsDto = (TeamCredentialsDto) teamCredentialMemberMapper
										.convertVOtoDTO(teamCredentialsVO, teamCredentialsDto);
			//Mapper for VO to DTO
			
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.commitCredentialData]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.commitCredentialData]"
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}
	
	/**
	 * 
	 * @param teamCredentialMapper
	 * @return
	 */
	public TeamCredentialsDto deleteCredentialData(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsVO teamCredentialsVO = null;
		try {
			teamCredentialsVO = new TeamCredentialsVO();
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
				teamCredentialsVO = iTeamCredentilaBOImpl.removeCredentials(teamCredentialsVO);
			// Do we need to update activity table ?????
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.deleteCredentialData]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.deleteCredentialData]"
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}

	
	
	/**
	 * 
	 * @param teamCredentialMapper
	 * @return
	 */
	public TeamCredentialsDto getCatListByTypeId(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsLookupVO teamCredentialsLookupVO;
		try {
			teamCredentialsLookupVO = new TeamCredentialsLookupVO();
			teamCredentialsLookupVO = (TeamCredentialsLookupVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsLookupVO);
			teamCredentialsDto.setCredentialTypeList(iTeamCredentilaBOImpl.getTypeList());
			teamCredentialsDto.setCredentialCatList(iTeamCredentilaBOImpl
					.getCatListByTypeId(teamCredentialsLookupVO));
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		}catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.getCatListByTypeId]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.getCatListByTypeId]"
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}
	
	public TeamCredentialsDto removeDocumentDetails(
									TeamCredentialsDto teamCredentialsDto) throws DelegateException
	{
		TeamCredentialsVO teamCredentialsVO = new TeamCredentialsVO();
		try {
			System.out.println("--------------Inside removeDocumentDetails------Delegate");
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper.
													convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
			iTeamCredentilaBOImpl.removeDocument(teamCredentialsVO);
			System.out.println("End--------------Inside removeDocumentDetails------Delegate");
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.removeDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.removeDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}
	
	public TeamCredentialsDto viewDocumentDetails(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException
	{
		TeamCredentialsVO teamCredentialsVO = new TeamCredentialsVO();
		try {
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper.
											convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
			
			teamCredentialsVO=iTeamCredentilaBOImpl.viewDocument(teamCredentialsVO);
			
			teamCredentialsDto = teamCredentialMemberMapper.convertVOtoDTO4Display(teamCredentialsVO, teamCredentialsDto);
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.viewDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.viewDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		}
		return teamCredentialsDto;
	}
	
	public TeamCredentialsDto loadListValues(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException {
		TeamCredentialsVO teamCredentialsVO = null;
		try {
			teamCredentialsVO = new TeamCredentialsVO();
			teamCredentialsVO = (TeamCredentialsVO) teamCredentialMemberMapper
					.convertDTOtoVO(teamCredentialsDto, teamCredentialsVO);
			// Load All the List necessary to load in the page
			teamCredentialsDto.setCredentialTypeList(iTeamCredentilaBOImpl
					.getTypeList());
			teamCredentialsDto = getCatListByTypeId(teamCredentialsDto);
		} catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at TeamCredentialDelegateImpl.getCredentialList]"
							+ ex.getMessage());
		}

		return teamCredentialsDto;
	}
	
}
/*
 * Maintenance History
 * $Log: TeamCredentialDelegateImpl.java,v $
 * Revision 1.12  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.12.1  2008/04/23 11:41:42  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:45  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/02/11 21:31:07  mhaye05
 * removed statesList attributes
 *
 */