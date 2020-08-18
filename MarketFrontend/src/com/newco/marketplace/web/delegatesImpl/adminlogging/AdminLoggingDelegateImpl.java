package com.newco.marketplace.web.delegatesImpl.adminlogging;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.adminlogging.IAdminLoggingBean;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.delegates.adminlogging.IAdminLoggingDelegate;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;
import com.newco.marketplace.web.utils.AdminLoggingMapper;

public class AdminLoggingDelegateImpl implements IAdminLoggingDelegate{
	
	private static final Logger logger = Logger.getLogger(AdminLoggingDelegateImpl.class.getName());
	private IAdminLoggingBean adminLoggingBean;
	private AdminLoggingMapper adminLoggingMapper;
	public AdminLoggingDelegateImpl(IAdminLoggingBean adminLoggingBean,
			AdminLoggingMapper adminLoggingMapper) {
		this.adminLoggingBean = adminLoggingBean;
		this.adminLoggingMapper = adminLoggingMapper;
	}
	
	
	/**
	 * 
	 * @param adminLoggingDto
	 * @return
	 */ 
	public AdminLoggingDto insertAdminLogging(AdminLoggingDto  adminLoggingDto){
		AdminLoggingVO objAdminLoggingVO = new AdminLoggingVO();
		AdminLoggingDto objAdminLoggingDto = new AdminLoggingDto();
		AdminLoggingVO adminLoggingVO = adminLoggingMapper.convertDTOtoVO(adminLoggingDto, objAdminLoggingVO);
		try {
			objAdminLoggingVO = adminLoggingBean.insertAdminLogging(adminLoggingVO);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		adminLoggingDto = adminLoggingMapper.convertVOtoDTO(objAdminLoggingVO, objAdminLoggingDto);
		return  adminLoggingDto;
	}
	/***
	 * 
	 * @param adminLoggingDto
	 * @return
	 */
	public AdminLoggingDto updateAdminLogging(AdminLoggingDto adminLoggingDto){
		AdminLoggingVO objAdminLoggingVO = new AdminLoggingVO();
		AdminLoggingDto objAdminLoggingDto = new AdminLoggingDto();
		AdminLoggingVO adminLoggingVO = adminLoggingMapper.convertDTOtoVO(adminLoggingDto, objAdminLoggingVO);
		try {
			objAdminLoggingVO = adminLoggingBean.updateAdminLogging(adminLoggingVO);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		adminLoggingDto = adminLoggingMapper.convertVOtoDTO(objAdminLoggingVO, objAdminLoggingDto);
		return  adminLoggingDto;
	}
	
	
	public AdminLoggingDto getActivityId(AdminLoggingDto adminLoggingDto){
		LookupVO lookupVO = new LookupVO();
		if(adminLoggingDto.getActivityName() != null)
			lookupVO.setDescr(adminLoggingDto.getActivityName());
		AdminLoggingDto ObjAdminLoggingDto = new AdminLoggingDto();
		try {
			lookupVO = adminLoggingBean.getActivityId(lookupVO);
			ObjAdminLoggingDto.setActivityId(Integer.parseInt(lookupVO.getId()));
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return ObjAdminLoggingDto;
	}
}
