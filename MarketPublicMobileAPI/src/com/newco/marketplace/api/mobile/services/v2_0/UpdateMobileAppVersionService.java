package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.UpdateMobileAppVerReq;
import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0.UpdateMobileAppVersionRequest;
import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0.UpdateMobileAppVersionResponse;

import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.AppVersionDataList;

@APIRequestClass(UpdateMobileAppVersionRequest.class)
@APIResponseClass(UpdateMobileAppVersionResponse.class)
public class UpdateMobileAppVersionService extends BaseService {

	/**
	 * Constructor
	 */

	public UpdateMobileAppVersionService() {
		 super();
	}

	private Logger logger = Logger.getLogger(AuthenticateMobileVersionService.class);
	private IAuthenticateUserBO authenticateUserBO;
	int unsuccessLoginInd = 0;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		UpdateMobileAppVersionRequest updateMobileAppVersionRequest= (UpdateMobileAppVersionRequest)apiVO.getRequestFromPostPut();
		UpdateMobileAppVersionResponse updateMobileAppVersionResponse = new UpdateMobileAppVersionResponse();
		List<Result> successMsg = new ArrayList<Result>();
		Results results = new Results();	
		
		try {
			//step one: validating base app version and latest app version in the request
			String baseAppVersion = updateMobileAppVersionRequest.getBaseAppVersion();
			String latestAppVersion = updateMobileAppVersionRequest.getLatestAppVersion();						
			int baseAppVersionInt = Integer.parseInt( baseAppVersion.replaceAll( "\\.", "" ) );
			int latestAppVersionInt = Integer.parseInt( latestAppVersion.replaceAll( "\\.", "" ) );
			if((StringUtils.isNotBlank(baseAppVersion) && baseAppVersion.length() > 5) 
					||(StringUtils.isNotBlank(latestAppVersion) && latestAppVersion.length() > 5) 
					|| (baseAppVersionInt>999)|| (latestAppVersionInt>999) || (latestAppVersionInt< baseAppVersionInt)){
				successMsg=addSuccess(successMsg, ResultsCode.INVALID_APP_VERSION_FOR_UPDATE.getCode(),
						ResultsCode.INVALID_APP_VERSION_FOR_UPDATE.getMessage());
				results.setResult(successMsg);
				updateMobileAppVersionResponse.setResults(results);
				return updateMobileAppVersionResponse;
			}
			//step two: validating whether request device os is matching with DB entries
			AppVersionDataList appVersionDataList = authenticateUserBO.fetchMobileAppVersions();
			boolean validAppOS=false;
			UpdateMobileAppVerReq updateMobileAppVerReq = new UpdateMobileAppVerReq();
			for (AppVersionData appVersionData : appVersionDataList.getAppVersionDataList()){
				if(appVersionData.getDeviceOS().equalsIgnoreCase(updateMobileAppVersionRequest.getDeviceOS())){
					validAppOS=true;
					updateMobileAppVerReq.setId(appVersionData.getId());
				}
			}
			if(!validAppOS){
				successMsg=addSuccess(successMsg, ResultsCode.INVALID_APP_OS_FOR_UPDATE.getCode(),
						ResultsCode.INVALID_APP_OS_FOR_UPDATE.getMessage());
				results.setResult(successMsg);
			}else{
				//step three: updating base app version and latest app version in DB from request	
				updateMobileAppVerReq.setModifiedBy("API");
				updateMobileAppVerReq.setBaseAppVersion(updateMobileAppVersionRequest.getBaseAppVersion());
				updateMobileAppVerReq.setLatestAppVersion(updateMobileAppVersionRequest.getLatestAppVersion());
				authenticateUserBO.updateMobileAppVersions(updateMobileAppVerReq);
				successMsg=addSuccess(successMsg, ResultsCode.APP_VERSION_UPDATE_SUCCESS.getCode(),
						ResultsCode.APP_VERSION_UPDATE_SUCCESS.getMessage());
				results.setResult(successMsg);
			}
			updateMobileAppVersionResponse.setResults(results);		
		} catch (Exception e1) {
			logger.error("UpdateMobileAppVersionService-->execute()--> Exception--> "
					+ e1.getMessage(), e1);
			updateMobileAppVersionResponse.setResults(Results.getError(
					ResultsCode.APP_VERSION_UPDATE_FAILURE.getMessage(),
					ResultsCode.APP_VERSION_UPDATE_FAILURE.getCode()));
			return updateMobileAppVersionResponse;
		}		
		return updateMobileAppVersionResponse;
	}
	
	private List<Result> addSuccess(List<Result> sucesses, String code,
			String message) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		sucesses.add(result);
		return sucesses;
	}

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

}
