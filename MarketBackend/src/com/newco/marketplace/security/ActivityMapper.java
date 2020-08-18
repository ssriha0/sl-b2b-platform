package com.newco.marketplace.security;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.security.SecurityBusinessBean;
import com.newco.marketplace.interfaces.OrderConstants;

public class ActivityMapper {
	
	private static HashMap<String, ActionActivityVO> actionPermissionMap = null;
	private static SecurityBusinessBean secbb = (SecurityBusinessBean) MPSpringLoaderPlugIn.getCtx()
	.getBean("securityBO");

	private static  synchronized void  getActivityMap() {

		if (actionPermissionMap == null) {
			
			List<ActionActivityVO> actionActivityVO = secbb
					.getActionActivities();
			// List<ActionActivityVO> actionActivityVO =
			// actionActivity.getActionActivities();
			actionPermissionMap = new HashMap<String, ActionActivityVO>();

			if (actionActivityVO != null) {
				for (int i = 0; i < actionActivityVO.size(); i++) {

					ActionActivityVO theActionActivityVO = actionActivityVO
							.get(i);
					actionPermissionMap.put(
							theActionActivityVO.getActionName()+"-"+theActionActivityVO.getRoleId(),
							theActionActivityVO);
				}
			}
		}
	}
	public static int getActionActivity(String actionName_role_id) {
		if(actionPermissionMap == null){
			getActivityMap();
		}
		ActionActivityVO theAction = actionPermissionMap.get(actionName_role_id);
		if (theAction != null) {
			return theAction.getActivityId();
		} else {
			return 0;
		}
	}
	public static ActionActivityVO getActionActivityVO(String actionName_role_id) {
		if(actionPermissionMap == null){
			getActivityMap();
		}
		ActionActivityVO theAction = actionPermissionMap.get(actionName_role_id);
			return theAction;
	}
	public static boolean canDoAction(String actionName_role_id, SecurityContext sc){
		return(
					(sc.getRoleActivityIdList().containsKey(ActivityMapper.getActionActivity(actionName_role_id)+""))
					&&(sc.getRoleActivityIdList().get(ActivityMapper.getActionActivity(actionName_role_id)+"").isChecked())
					);	
	}
	public static boolean canAdminDoAction(String actionName,String userName){
		String roleId = String.valueOf(OrderConstants.NEWCO_ADMIN_ROLEID);
		return secbb.hasPermissionForAction(actionName, userName, roleId);
	}
}
	
	

