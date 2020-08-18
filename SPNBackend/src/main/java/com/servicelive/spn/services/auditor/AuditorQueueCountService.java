package com.servicelive.spn.services.auditor;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.common.detached.SPNAuditorQueueCountVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.PropertyManagerUtil;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.interfaces.IAuditorQueueCountService;

/**
 * 
 * @author svanloon
 *
 */
public class AuditorQueueCountService extends BaseServices implements IAuditorQueueCountService {

	private PropertyManagerUtil propertyManagerUtil;

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.auditor.IAuditorQueueCountService#getCounts(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<SPNAuditorQueueCountVO> getCounts(Integer buyerId) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		Date time = CalendarUtil.getNow();
		CalendarUtil.addMinutes(time, -1*propertyManagerUtil.getSpnAuditorMonitorStickyQueueTimeoutMinutes());
		map.put("time", time);
		map.put("buyerId", buyerId);
		return  getSqlMapClient().queryForList("network.auditorqueuecount.counts", map);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.auditor.IAuditorQueueCountService#getCountsWithDetail(java.lang.Integer, com.servicelive.domain.lookup.LookupSPNWorkflowState)
	 */
	@SuppressWarnings("unchecked")
	public List<SPNAuditorQueueCountVO> getCountsWithDetail(Integer buyerId, LookupSPNWorkflowState state) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("state", state.getId());
		Date time = CalendarUtil.getNow();
		CalendarUtil.addMinutes(time, -1*propertyManagerUtil.getSpnAuditorMonitorStickyQueueTimeoutMinutes());
		map.put("time", time);
		return  getSqlMapClient().queryForList("network.auditorqueuecount.countswithdetail", map);

	}

	/**
	 * @param propertyManagerUtil the propertyManagerUtil to set
	 */
	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}

}
