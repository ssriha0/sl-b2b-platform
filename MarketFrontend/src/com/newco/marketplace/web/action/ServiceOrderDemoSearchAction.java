package com.newco.marketplace.web.action;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.opensymphony.xwork2.Preparable;



public class ServiceOrderDemoSearchAction extends SLBaseAction implements Preparable
{
	static final long serialVersionUID = 10002;//arbitrary number to get rid of warning
	private ArrayList<LabelValueBean> stateList = new ArrayList<LabelValueBean>();
	private ArrayList<LabelValueBean> radiusList = new ArrayList<LabelValueBean>();

	/*
	private ISOMonitorDelegate serviceOrderManager;
	public ServiceOrderMonitorAction(ISOMonitorDelegate manager)
	{
		this.serviceOrderManager = manager;
	}
	*/


	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception
	{

	}

	public String execute() throws Exception
	{
		//if(stateList == null)
		{
			stateList.add(new LabelValueBean("Alabama", 1 +""));
			stateList.add(new LabelValueBean("Alaska", 2 +""));
			stateList.add(new LabelValueBean("Arkansas", 3 +""));
		}
		//if(radiusList == null)
		{
			radiusList.add(new LabelValueBean("10", 10 + ""));
			radiusList.add(new LabelValueBean("25", 25 + ""));
			radiusList.add(new LabelValueBean("50", 50 + ""));
		}
		setAttribute("stateList", stateList);
		setAttribute("radiusList", radiusList);
		
		return "success";
	}

	public ArrayList<LabelValueBean> getStateList() {
		return stateList;
	}

	public void setStateList(ArrayList<LabelValueBean> stateList) {
		this.stateList = stateList;
	}

	public ArrayList<LabelValueBean> getRadiusList() {
		return radiusList;
	}

	public void setRadiusList(ArrayList<LabelValueBean> radiusList) {
		this.radiusList = radiusList;
	}
	

	/*
	public ISOMonitorDelegate getServiceOrderManager() {
		return serviceOrderManager;
	}

	public void setServiceOrderManager(
			ISOMonitorDelegate serviceOrderManager) {
		this.serviceOrderManager = serviceOrderManager;
	}
	*/
	
	
}
