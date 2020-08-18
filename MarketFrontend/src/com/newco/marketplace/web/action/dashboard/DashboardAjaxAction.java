package com.newco.marketplace.web.action.dashboard;

import java.util.Random;
import javax.servlet.http.HttpServletResponse;

import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.opensymphony.xwork2.Preparable;



public class DashboardAjaxAction extends SLBaseAction implements Preparable
{
	private static final long serialVersionUID = 10003;//arbitrary number to get rid of warning
	
	private static SODashboardVO dbVO = null;
	private ISODashBoardDelegate dashboardDelegate;
	
	public DashboardAjaxAction(ISODashBoardDelegate delegate)
	{
		this.dashboardDelegate = delegate;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception
	{
		this.createCommonServiceOrderCriteria();
	}

	public String execute() throws Exception
	{
		
		return SUCCESS;
	}
	
	public String periodicRefresh() {
		try{
			// Data needed for most database calls
			String companyId = null;
			String resourceId = null;
			Integer roleIdInt=null;
			
			 LoginCredentialVO context = get_commonCriteria().getSecurityContext().getRoles();
			if(context != null)
			{
				// Extract info about our user
				companyId = get_commonCriteria().getSecurityContext().getCompanyId() + "";
				resourceId = get_commonCriteria().getSecurityContext().getVendBuyerResId() + "";
				roleIdInt = context.getRoleId();
			}
			else
			{
				companyId = "1";
				resourceId = null;
				roleIdInt = 1;			
			}

			SODashboardVO vo;			
			// Get the data from the backend/database here.
			vo = dashboardDelegate.getDashBoardWidgetDetailsCache(companyId, resourceId, roleIdInt);
			// Zoya - when your stuff is 'ready', comment out the next line
			//vo = this.getDashboardVO_Mock();
			
			
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			
			StringBuffer sb = new StringBuffer();
			sb.append("<message_output>");
			
			String dateString = DateUtils.getHeaderDate();
			double totalAmt = vo.getTotalDollars();
			String totalAmtInDecimal = UIUtils.formatDollarAmount(totalAmt);
			
			// Extract certain pieces of data to be updated on the front-end
			sb.append("<accepted>" + vo.getAccepted() + "</accepted>");
			sb.append("<available_balance>" + java.text.NumberFormat.getCurrencyInstance().format(vo.getAvailableBalance()) + "</available_balance>");			
			sb.append("<current_balance>" + java.text.NumberFormat.getCurrencyInstance().format(vo.getCurrentBalance()) + "</current_balance>");
			sb.append("<drafted>" + vo.getDrafted() + "</drafted>");
			sb.append("<num_issues>" + vo.getNumIssues() + "</num_issues>");
			sb.append("<num_technicians_unapproved>" + vo.getNumTechniciansUnapproved() + "</num_technicians_unapproved>");
			sb.append("<posted>" + vo.getPosted() + "</posted>");
			sb.append("<problem>" + vo.getProblem() + "</problem>");
			sb.append("<date_dashboard>" + dateString + "</date_dashboard>");
			sb.append("<received>" + vo.getReceived() + "</received>");
			sb.append("<todays>" + vo.getTodays() + "</todays>");
			sb.append("<total_dollars>" + totalAmtInDecimal + "</total_dollars>");
			sb.append("<total_orders>" + vo.getTotalOrders() + "</total_orders>");
			
			sb.append("</message_output>");
			
			response.getWriter().write(sb.toString());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SODashboardVO getDashboardVO()
	{
		// Zoya - your stuff goes here
		return null;
	}
	
	private SODashboardVO getDashboardVO_Mock()
	{
		// Init our mock dashboard VO.
		if(dbVO == null)
		{
			dbVO = new SODashboardVO();
			dbVO.setAccepted(111);
			dbVO.setAvailableBalance(111.0);
			dbVO.setCurrentBalance(222.22);
			dbVO.setDrafted(1111);
			dbVO.setNumIssues(1234);
			dbVO.setNumTechniciansUnapproved(555);
			dbVO.setPosted(666);
			dbVO.setProblem(888);
			dbVO.setProfileChanges("dummy string from back-end");
			dbVO.setReceived(999);
			dbVO.setTodays(3333);
			dbVO.setTotalDollars(234.56);
			dbVO.setTotalOrders(99999);
		}
		
		
		Random rand = new Random();
		int percent=1;
		

		if( rand.nextInt(100) > percent)
			dbVO.setAccepted(dbVO.getAccepted() + 1);
		
		if( rand.nextInt(100) > percent)
			dbVO.setCurrentBalance(dbVO.getCurrentBalance() + 1.23);
		
		if( rand.nextInt(100) > percent)
		{
			Double val = rand.nextDouble()%20.0;
			if(rand.nextBoolean())
				val *= -1;
			
			dbVO.setAvailableBalance(dbVO.getAvailableBalance() - val);			
		}
		if( rand.nextInt(100) > percent)		
			dbVO.setDrafted(dbVO.getDrafted() + 1);
		
		if( rand.nextInt(100) > percent)		
			dbVO.setNumIssues(dbVO.getNumIssues() + 2);
		if( rand.nextInt(100) > percent)		
			dbVO.setNumTechniciansUnapproved(dbVO.getNumTechniciansUnapproved() + 1);
		if( rand.nextInt(100) > percent)		
			dbVO.setPosted(dbVO.getPosted() + 1);
		if( rand.nextInt(100) > percent)		
			dbVO.setProblem(dbVO.getProblem() + 1);
		if( rand.nextInt(100) > percent)		
			dbVO.setProfileChanges("dummy string from back-end");
		if( rand.nextInt(100) > percent)		
			dbVO.setReceived(dbVO.getReceived() + 1);
		if( rand.nextInt(100) > percent)		
			dbVO.setTodays(dbVO.getTodays() + 1);
		if( rand.nextInt(100) > percent)		
			dbVO.setTotalDollars(dbVO.getTotalDollars() + 13.22);
		if( rand.nextInt(100) > percent)		
			dbVO.setTotalOrders(dbVO.getTotalOrders() + 3);
		
		return dbVO;
	}
	
}
