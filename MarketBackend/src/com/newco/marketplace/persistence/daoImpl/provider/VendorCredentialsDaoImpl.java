package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.dto.vo.provider.VendorCredentialsLookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class VendorCredentialsDaoImpl extends ABaseImplDao implements IVendorCredentialsDao {

	private static final Logger localLogger = Logger
		.getLogger(VendorCredentialsDaoImpl.class.getName());

	private static final MessageResources messages = MessageResources
		.getMessageResources("DataAccessLocalStrings");
	
	public List queryAllType() throws DataServiceException {
		return queryForList("vendorCredentialsVO.queryAllType", null);
	}
	
	public List queryCatByTypeId(int typeId) throws DataServiceException {
		List list = null;
		
		try {
			list = queryForList("vendorCredentialsVO.queryCatByTypeId", typeId);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		
		return list;
	}

	public List queryCatByType(String type) throws DataServiceException {
		List list = null;
		
		try {
			list = queryForList("vendorCredentialsVO.queryCatByType", type);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		
		return list;
	}
	
	
	public Integer queryForCredExists(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException{
		localLogger.info("[VendorCredentialsDaoImpl] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RESOURCE ID "
				+ vendorCredentialsVO.getVendorId());
		Integer vendorCredID = -1;
		try{
			vendorCredID = (Integer) getSqlMapClient().queryForObject(
					"vendorCredentialsVO.queryForExist", vendorCredentialsVO);
		}catch(Exception exp){
			localLogger.error("[VendorCredentialsDaoImpl.queryForExist - Exception]"
					+ StackTraceHelper.getStackTrace(exp));
			throw new DataServiceException("[VendorCredentialsDaoImpl.queryForExist - Exception]"
							+ exp.getMessage());
		}
		return vendorCredID;
	}
	
	public List queryCredByVendorId(int vendorId) throws DataServiceException {
		List list = null;
		
		try {
			list = queryForList("vendorCredentialsVO.queryCredByVendorId",
					vendorId);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		
		return list;
	}
	
	public VendorCredentialsVO queryCredById(
		VendorCredentialsVO vendorCredentialsVO)
		throws DataServiceException {
	
		VendorCredentialsVO vo = null;
		
		try {
			vo = (VendorCredentialsVO) queryForObject(
					"vendorCredentialsVO.queryCredById", vendorCredentialsVO);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		return vo;
	}
	
	public VendorCredentialsVO insert(VendorCredentialsVO vendorCredentialsVO)
		throws DataServiceException {
		Integer result = null;
		
		try {
			result = (Integer) this.insert("vendorCredentialsVO.insert",
					vendorCredentialsVO);
			vendorCredentialsVO.setVendorCredId(result);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.insert - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.insert");
			throw new DataServiceException(error, ex);
		}
		return vendorCredentialsVO;
	}
	
	public void update(VendorCredentialsVO vendorCredentialsVO)
		throws DataServiceException {
	
		Integer result = null;
		
		try {
			result = (Integer) this.insert("vendorCredentialsVO.update",
					vendorCredentialsVO);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
	}
	
	public VendorCredentialsVO queryByCredIdSec(
		VendorCredentialsVO vendorCredentialsVO)
		throws DataServiceException {
	
		VendorCredentialsVO vo = null;
		
		try {
			vo = (VendorCredentialsVO) queryForObject(
					"vendorCredentialsVO.queryByCredIdSec", vendorCredentialsVO);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		return vo;
	}
	public void updateWfStateId(VendorCredentialsVO vendorCredentialsVO)
	throws DataServiceException {
	
		Integer result = null;
		
		try {
			result = update("vendorCredentials.updateWfStateId",vendorCredentialsVO);
		}catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.update - Exception] "
			+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
	}

	public VendorCredentialsLookupVO getVendorCredLookup(String credDesc) throws DataServiceException {
		VendorCredentialsLookupVO vo = new VendorCredentialsLookupVO();
		vo.setTypeDesc(credDesc);
		
		try {
			vo = (VendorCredentialsLookupVO) queryForObject(
					"vendorCredentialsVO.queryType", vo);
		} catch (Exception ex) {
			localLogger.info("[VendorCredentialsDaoImpl.getVendorCredLookup - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			final String error = messages.getMessage("dataaccess.update");
			throw new DataServiceException(error, ex);
		}
		return vo;
	}

}
