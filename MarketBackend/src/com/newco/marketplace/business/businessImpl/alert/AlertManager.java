package com.newco.marketplace.business.businessImpl.alert;

import java.sql.Timestamp;

import com.newco.marketplace.business.businessImpl.MPBaseBusinessBean;
import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.dto.vo.alert.OutboundAlertVO;
import com.newco.marketplace.dto.vo.alert.TimedAlertVO;

public class AlertManager extends MPBaseBusinessBean {

	public static TimedAlertVO createTimeBasedAlert(Timestamp aTs){
		TimedAlertVO aTimedAlertVO = new TimedAlertVO();
		aTimedAlertVO.setAlertTime(aTs);
		return aTimedAlertVO;
	}
	public static OutboundAlertVO createOutboundAlert(){
		OutboundAlertVO aOutboundAlertVO = new OutboundAlertVO();
		return aOutboundAlertVO;
	}
	public static AlertTaskVO createTaskAlert(){
		AlertTaskVO aTaskAlertVO = new AlertTaskVO();
		return aTaskAlertVO;
	}
	
}
