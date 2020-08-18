package com.newco.marketplace.persistence.iDao.logging;

import java.util.List;

import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeTypeVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo2;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ILoggingDao {
	
    public void insertLog (SoLoggingVo soLoggingVo) throws DataServiceException;
    
    public List<SoChangeTypeVo> getSOChangeTypes () throws DataServiceException;
    public List<SoChangeDetailVo> getSOChangeDetail(String soID)throws DataServiceException;
    public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soID)throws DataServiceException;

    public List<SoLoggingVo> getSoLogDetails(String soID)throws DataServiceException;
    public List<SoLoggingVo> getSoRescheduleLogDetails(String soID) throws DataServiceException;
	public String  getBuyerUserName(Integer roleId,Integer resourceId) throws DataServiceException;
	public String  getProviderUserName(Integer roleId,Integer resourceId) throws DataServiceException;
	public List<SoLoggingVo2> getSoRescheduleLogDetailsAnyRoles(String soID) throws DataServiceException;

}
