package com.newco.marketplace.web.action.powerbuyer;

import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.web.action.base.SLBaseAction;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 01:13:53 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBBaseAction extends SLBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4507503848923041459L;
	protected static final String ROUTETOSOW= "routeToSOW";
	protected static final String ROUTETOSOD = "routeToSOD";
	protected static final String ROUTETOWFMONITOR = "routeToWFMonitor_workflowTab";
	
	/**
	 * Determines where the user should be sent do based on the Service 
	 * Order status
	 * @param claimVO
	 * @return
	 */
	protected String determineDest(ClaimVO claimVO) { 
		
		String toReturn;
		
		if (null != claimVO)
		{
			toReturn = "routeTo"+claimVO.getQueueDestinationTab();
			if ("SOW".equalsIgnoreCase(claimVO.getQueueDestinationTab()))
			{
				this.pbSoId = claimVO.getSoId();
				this.action = "edit";
			} else {
				this.pbSoId = claimVO.getSoId();
			}
			//getSession().setAttribute("defaultTab", claimVO.getQueueDestinationSubTab());
		} else {
			toReturn = ROUTETOWFMONITOR;
		}
		return toReturn;
	}
}
/*
 * Maintenance History
 * $Log: PBBaseAction.java,v $
 * Revision 1.6  2008/04/26 01:13:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.10.1  2008/04/23 11:41:47  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:19:33  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.4  2008/02/20 00:13:38  glacy
 * fixed power buyer route to SOD from claim next
 *
 * Revision 1.3  2008/02/19 22:35:01  glacy
 * powerbuyer claim fixes for SOW
 *
 * Revision 1.2  2008/02/14 23:44:57  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.1.4.2  2008/02/08 02:34:22  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.1.4.1  2008/02/07 17:25:56  cgarc03
 * determineDest() Set 'soId' and 'action' in session for retrieval by SOWController.
 *
 * Revision 1.1  2008/01/24 21:53:20  mhaye05
 * Initial check in
 *
 */