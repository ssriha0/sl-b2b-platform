package com.newco.marketplace.api.provider.utils.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.provider.beans.authenticateUser.v2_0.LocationDetail;
import com.newco.marketplace.api.provider.beans.authenticateUser.v2_0.Locations;
import com.newco.marketplace.api.provider.beans.authenticateUser.v2_0.LoginProviderResponse;
import com.newco.marketplace.api.provider.beans.authenticateUser.v2_0.UserProviderDetails;
import com.newco.marketplace.api.provider.constants.ProviderAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.mobile.FirmDetailsVO;
import com.newco.marketplace.vo.mobile.LocationResponseData;
import com.newco.marketplace.vo.mobile.UserProfileData;

public class AuthenticateUserMapper {

	private Logger logger = Logger.getLogger(AuthenticateUserMapper.class);

	private IAuthenticateUserBO authenticateUserBO;

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

	public UserProviderDetails populateUserResponse(UserProfileData userProfileData) {
		UserProviderDetails userDetails = null;

		if (null != userProfileData) {
			userDetails = new UserProviderDetails();

			if (!StringUtils.isBlank(userProfileData.getFirstName())) {
				userDetails.setFirstName(userProfileData.getFirstName());
			}
			if (!StringUtils.isBlank(userProfileData.getLastName())) {
				userDetails.setLastName(userProfileData.getLastName());
			}
			if (!StringUtils.isBlank(userProfileData.getPhoneNo())) {
				userDetails.setPhoneNo(formatPhoneNumber(userProfileData.getPhoneNo()));
			}
			if (!StringUtils.isBlank(userProfileData.getEmail())) {
				userDetails.setEmail(userProfileData.getEmail());
			}

			if (null != userProfileData.getListOflocations() && userProfileData.getListOflocations().size() > 0) {
				List<LocationDetail> locationList = new ArrayList<LocationDetail>();
				Locations locations = new Locations();
				for (LocationResponseData locationData : userProfileData.getListOflocations()) {
					LocationDetail locationDetails = new LocationDetail();

					locationDetails.setLocnType(locationData.getLocType());
					locationDetails.setStreetAddress1(locationData.getStreet1());
					locationDetails.setStreetAddress2(locationData.getStreet2());
					locationDetails.setAptNo(locationData.getAptNo());
					locationDetails.setCity(locationData.getCity());
					locationDetails.setState(locationData.getState());
					locationDetails.setZip(locationData.getZip());
					locationDetails.setCountry(locationData.getCountry());
					locationList.add(locationDetails);
				}
				locations.setLocation(locationList);
				userDetails.setLocations(locations);
			}

			if (!StringUtils.isBlank(userProfileData.getCompanyName())) {
				userDetails.setFirmName(userProfileData.getCompanyName());
			}
			if (null != (userProfileData.getGeneratedPwdInd()) && 1 == userProfileData.getGeneratedPwdInd()) {
				userDetails.setTemporaryPassword(true);
			} else {
				userDetails.setTemporaryPassword(false);
			}
			if (null != userProfileData.getFirmId()) {
				userDetails.setFirmId(userProfileData.getFirmId());
			}
			if (null != userProfileData.getResourceId()) {
				userDetails.setProviderId(userProfileData.getResourceId());
			}

		}
		return userDetails;
	}

	private String formatPhoneNumber(String number) {
		String formattedNum = StringUtils.EMPTY;
		;
		if (null != number) {
			formattedNum = UIUtils.formatPhoneNumber(number.replaceAll("-", ""));
		}
		return formattedNum;
	}

	public UserProfileData populateDataForUser(LoginProviderResponse loginProviderResponse,
			UserProfileData userProfileData) throws Exception {

		if (userProfileData.getRoleId() == ProviderAPIConstant.API_PROVIDER_ROLE_ID) {
			List<LocationResponseData> locationList = new ArrayList<LocationResponseData>();
			locationList = authenticateUserBO.getLocationDetails(userProfileData.getContactId());
			logger.info("Location List : " + locationList.size());
			if (locationList.size() > 0) {
				userProfileData.setListOflocations(locationList);
			}
			FirmDetailsVO firmDetailsVO = authenticateUserBO.getFirmDetails(userProfileData.getContactId());

			if (null != firmDetailsVO) {
				if (null != firmDetailsVO.getVendorId()) {
					userProfileData.setFirmId(firmDetailsVO.getVendorId());
				}
				if (!StringUtils.isBlank(firmDetailsVO.getBusinessName())) {
					userProfileData.setCompanyName(firmDetailsVO.getBusinessName());
				}
				if (null != firmDetailsVO.getResourceId()) {
					userProfileData.setResourceId(firmDetailsVO.getResourceId());
				}
			}
		}
		return userProfileData;
	}

	public UserProviderDetails populateUserResponseV2(UserProfileData userProfileData) {
		UserProviderDetails userDetails = null;

		if (null != userProfileData) {
			userDetails = new UserProviderDetails();

			if (!StringUtils.isBlank(userProfileData.getFirstName())) {
				userDetails.setFirstName(userProfileData.getFirstName());
			}
			if (!StringUtils.isBlank(userProfileData.getLastName())) {
				userDetails.setLastName(userProfileData.getLastName());
			}
			if (!StringUtils.isBlank(userProfileData.getPhoneNo())) {
				userDetails.setPhoneNo(formatPhoneNumber(userProfileData.getPhoneNo()));
			}
			if (!StringUtils.isBlank(userProfileData.getEmail())) {
				userDetails.setEmail(userProfileData.getEmail());
			}

			if (null != userProfileData.getListOflocations() && userProfileData.getListOflocations().size() > 0) {
				List<LocationDetail> locationList = new ArrayList<LocationDetail>();
				Locations locations = new Locations();
				for (LocationResponseData locationData : userProfileData.getListOflocations()) {
					LocationDetail locationDetails = new LocationDetail();

					locationDetails.setLocnType(locationData.getLocType());
					locationDetails.setStreetAddress1(locationData.getStreet1());
					locationDetails.setStreetAddress2(locationData.getStreet2());
					locationDetails.setAptNo(locationData.getAptNo());
					locationDetails.setCity(locationData.getCity());
					locationDetails.setState(locationData.getState());
					locationDetails.setZip(locationData.getZip());
					locationDetails.setCountry(locationData.getCountry());
					locationList.add(locationDetails);
				}
				locations.setLocation(locationList);
				userDetails.setLocations(locations);
			}

			if (!StringUtils.isBlank(userProfileData.getCompanyName())) {
				userDetails.setFirmName(userProfileData.getCompanyName());
			}
			if (null != (userProfileData.getGeneratedPwdInd()) && 1 == userProfileData.getGeneratedPwdInd()) {
				userDetails.setTemporaryPassword(true);
			} else {
				userDetails.setTemporaryPassword(false);
			}
			if (null != userProfileData.getFirmId()) {
				userDetails.setFirmId(userProfileData.getFirmId());
			}
			if (null != userProfileData.getResourceId()) {
				userDetails.setProviderId(userProfileData.getResourceId());
			}
		}
		return userDetails;
	}

}
