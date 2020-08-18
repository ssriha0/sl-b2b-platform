/**
 * 
 */
package com.newco.marketplace.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;
import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;


/**
 * @author KSudhanshu
 *
 */
public class AdminLoggingMapper extends ObjectMapper {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object, java.lang.Object)
	 */
	public AdminLoggingVO convertDTOtoVO(Object objectDto, Object objectVO) {
		AdminLoggingDto adminLoggingDto = (AdminLoggingDto) objectDto;
		AdminLoggingVO adminLoggingVO = (AdminLoggingVO) objectVO;
		if (adminLoggingVO != null && adminLoggingDto!=null) {
			if(adminLoggingDto.getUserId() != null)
				adminLoggingVO.setUserId(adminLoggingDto.getUserId());
			adminLoggingVO.setCompanyId(adminLoggingDto.getCompanyId());
			adminLoggingVO.setActivityId(adminLoggingDto.getActivityId());
			adminLoggingVO.setRoleId(adminLoggingDto.getRoleId());
			adminLoggingVO.setLoggId(adminLoggingDto.getLoggId());
		}
		return adminLoggingVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public AdminLoggingDto convertVOtoDTO(Object objectVO, Object objectDto) {
		AdminLoggingDto adminLoggingDto = (AdminLoggingDto) objectDto;
		AdminLoggingVO adminLoggingVO = (AdminLoggingVO) objectVO;
		if (adminLoggingVO != null && adminLoggingDto!= null) {
			if(adminLoggingVO.getUserId() != null)
				adminLoggingDto.setUserId(adminLoggingVO.getUserId());
			adminLoggingDto.setCompanyId(adminLoggingVO.getCompanyId());
			adminLoggingDto.setActivityId(adminLoggingVO.getActivityId());
			adminLoggingDto.setRoleId(adminLoggingVO.getRoleId());
			adminLoggingDto.setLoggId(adminLoggingVO.getLoggId());
		}
		return adminLoggingDto;
	}
	
	
	private Date covertTimeStringToDate(String str){
		if (str!=null && str.trim().length()>0) {
			str="20070101:" + str;
			SimpleDateFormat timeStringformater = new SimpleDateFormat("yyyyMMdd:hh:mm a");
			try {
				return timeStringformater.parse(str);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	private String covertDateToTimeString(Date date){
		if (date!=null) {
			SimpleDateFormat timeStringformater = new SimpleDateFormat("hh:mm a");
			return timeStringformater.format(date);
		}
		return null;
	}

	private boolean isNull(String str) {
		if (str !=null && str.trim().length()>0)
			return false;
		
		return true;
	}
}
