package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;

import com.newco.marketplace.web.constants.SOConstants;




public class ServiceOrderStatusDAO implements IServiceOrderStatusDAO, SOConstants{

	private static ArrayList<ServiceOrderStatus>list = null;
	private static ArrayList<ServiceOrderStatusAndSubStatus> statusesList = null;
	
	public ServiceOrderStatusDAO()
	{
		initList();
	}
	
	private static void initList()
	{
		if(list != null)
			return;
		list = new ArrayList<ServiceOrderStatus>();
		list.add( new ServiceOrderStatus(DRAFT_INDEX,DRAFT));
		list.add( new ServiceOrderStatus(ROUTED_INDEX, ROUTED));
		list.add( new ServiceOrderStatus(EXPIRED_INDEX, EXPIRED));
		list.add( new ServiceOrderStatus(VOIDED_INDEX, VOIDED));
		list.add( new ServiceOrderStatus(ACCEPTED_INDEX, ACCEPTED));
		list.add( new ServiceOrderStatus(ACTIVE_INDEX,ACTIVE));
		list.add( new ServiceOrderStatus(PROBLEM_INDEX, PROBLEM));
		list.add( new ServiceOrderStatus(COMPLETED_INDEX, COMPLETED));
		list.add( new ServiceOrderStatus(CLOSED_INDEX, CLOSED));
		list.add( new ServiceOrderStatus(CANCELLED_INDEX, CANCELLED));			
		list.add( new ServiceOrderStatus(SENT_INDEX, SENT));					
	}

	public  static ArrayList<ServiceOrderStatus> getAllStatus()
	{
		if(list == null)
		{
			initList();
		}
		return list;
	}
	
	public  ArrayList<ServiceOrderStatus> findAll()
	{
		if(list == null)
		{
			initList();
		}
		return list;
	}

	public ArrayList<ServiceOrderStatusAndSubStatus> findAllStatuses()
	{
		ServiceOrderStatusAndSubStatus statuses = null;
		if(statusesList == null)
		{
			statusesList = new ArrayList<ServiceOrderStatusAndSubStatus>();
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(DRAFT);
			statuses.setSubStatus(getSubStatus(DRAFT_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(ROUTED);
			statuses.setSubStatus(getSubStatus(ROUTED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(EXPIRED);
			statuses.setSubStatus(getSubStatus(EXPIRED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(VOIDED);
			statuses.setSubStatus(getSubStatus(VOIDED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(ACCEPTED);
			statuses.setSubStatus(getSubStatus(ACCEPTED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(ACTIVE);
			statuses.setSubStatus(getSubStatus(ACTIVE_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(PROBLEM);
			statuses.setSubStatus(getSubStatus(PROBLEM_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(COMPLETED);
			statuses.setSubStatus(getSubStatus(COMPLETED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(CLOSED);
			statuses.setSubStatus(getSubStatus(COMPLETED_INDEX));
			statusesList.add(statuses);
			
			statuses = new ServiceOrderStatusAndSubStatus();
			statuses.setStatus(CANCELLED);
			statuses.setSubStatus(getSubStatus(CANCELLED_INDEX));
			statusesList.add(statuses);
		}
		return statusesList;
	}
	public String getStatusStringById(Integer id)
	{
		if(id == null)
		{
			//TODO Log this error
			return null;
		}
		if(list == null)
		{
			return null;
		}
		// More Mock DB
		for(ServiceOrderStatus status : list)
		{
			if((status.getId() != null) && (status.getId().intValue() == id.intValue()))
			{
				return status.getName();
			}
		}
		return null;
	}
	
	public static ArrayList<ServiceOrderSubStatus> getSubStatus(Integer status)
	{
		if(status == null)
			return null;
		
		ArrayList<ServiceOrderSubStatus> subStatus = null;
		
		switch(status.intValue())
		{
			case 1: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(PART_BACK_ORDERED_INDEX,PART_BACK_ORDERED));
					subStatus.add(new ServiceOrderSubStatus(PART_ON_ORDER_INDEX,PART_ON_ORDER));
					break;
					
			case 2: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(RELEASED_BY_PROVIDER_INDEX,RELEASED_BY_PROVIDER));
					subStatus.add(new ServiceOrderSubStatus(LOCKED_FOR_BUYER_INDEX,LOCKED_FOR_BUYER));
					break;	
			case 3: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(RELEASED_BY_PROVIDER_INDEX,RELEASED_BY_PROVIDER));
					break;	
			
			case 4: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_BUYER_SIDE_INDEX,CANCELLED_BY_BUYER_SIDE));
					subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_INDEX,CANCELLED_BY_END_CUSTOMER));
					break;	
					
			case 5: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_CUSTOMER_INDEX,RESCHEDULED_CUSTOMER));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_PROVIDER_INDEX,RESCHEDULED_PROVIDER));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_CUSTOMER_NO_SHOW_INDEX,RESCHEDULED_CUSTOMER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_PROVIDER_NO_SHOW_INDEX,RESCHEDULED_PROVIDER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_HOLD_INDEX,PART_RECEIVED_HOLD));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_PROVIDER_INDEX,PART_RECEIVED_PROVIDER));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_CUSTOMER_INDEX,PART_RECEIVED_CUSTOMER));
					subStatus.add(new ServiceOrderSubStatus(PART_BACK_ORDERED_INDEX,PART_BACK_ORDERED));
					subStatus.add(new ServiceOrderSubStatus(PART_ON_ORDER_INDEX,PART_ON_ORDER));
					subStatus.add(new ServiceOrderSubStatus(NEED_ADDITIONAL_PARTS_INDEX,NEED_ADDITIONAL_PARTS));
					subStatus.add(new ServiceOrderSubStatus(PART_SHIPPED_INDEX,PART_SHIPPED));
					break;	
			
			case 6: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(PROVIDER_ON_SITE_INDEX,PROVIDER_ON_SITE));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_CUSTOMER_INDEX,RESCHEDULED_CUSTOMER));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_PROVIDER_INDEX,RESCHEDULED_PROVIDER));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_CUSTOMER_NO_SHOW_INDEX,RESCHEDULED_CUSTOMER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(RESCHEDULED_PROVIDER_NO_SHOW_INDEX,RESCHEDULED_PROVIDER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_HOLD_INDEX,PART_RECEIVED_HOLD));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_PROVIDER_INDEX,PART_RECEIVED_PROVIDER));
					subStatus.add(new ServiceOrderSubStatus(PART_RECEIVED_CUSTOMER_INDEX,PART_RECEIVED_CUSTOMER));
					subStatus.add(new ServiceOrderSubStatus(PART_BACK_ORDERED_INDEX,PART_BACK_ORDERED));
					subStatus.add(new ServiceOrderSubStatus(PART_ON_ORDER_INDEX,PART_ON_ORDER));
					subStatus.add(new ServiceOrderSubStatus(NEED_ADDITIONAL_PARTS_INDEX,NEED_ADDITIONAL_PARTS));
					subStatus.add(new ServiceOrderSubStatus(PART_SHIPPED_INDEX,PART_SHIPPED));
					subStatus.add(new ServiceOrderSubStatus(JOB_DONE_INDEX,JOB_DONE));
					break;	
					
			case 7: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(ABANDONED_WORK_INDEX,ABANDONED_WORK));
					subStatus.add(new ServiceOrderSubStatus(ADDITIONAL_PART_REQUIRED_INDEX,ADDITIONAL_PART_REQUIRED));
					subStatus.add(new ServiceOrderSubStatus(ADDITIONAL_WORK_REQUIRED_INDEX,ADDITIONAL_WORK_REQUIRED));
					subStatus.add(new ServiceOrderSubStatus(OUT_OF_SCOPE_INDEX,OUT_OF_SCOPE));
					subStatus.add(new ServiceOrderSubStatus(PROPERTY_DAMAGE_INDEX,PROPERTY_DAMAGE));
					subStatus.add(new ServiceOrderSubStatus(PROVIDER_NO_SHOW_INDEX,PROVIDER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(END_CUSTOMER_NO_SHOW_INDEX,END_CUSTOMER_NO_SHOW));
					subStatus.add(new ServiceOrderSubStatus(PROVIDER_NOT_QUALIFIED_INDEX,PROVIDER_NOT_QUALIFIED));
					subStatus.add(new ServiceOrderSubStatus(SITE_NOT_READY_INDEX,SITE_NOT_READY));
					subStatus.add(new ServiceOrderSubStatus(UNPROFESSIONAL_ACTION_INDEX,UNPROFESSIONAL_ACTION));
					subStatus.add(new ServiceOrderSubStatus(WORK_NOT_COMPLETE_INDEX,WORK_NOT_COMPLETE));
					break;	
			
			case 8: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(AWAITING_PAYMENT_INDEX,AWAITING_PAYMENT));
					subStatus.add(new ServiceOrderSubStatus(DOCUMENT_REQUIRED_INDEX,DOCUMENT_REQUIRED));					
					break;	
			
			case 9: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					break;	
					
			case 10: subStatus = new ArrayList<ServiceOrderSubStatus>();
					subStatus.add(new ServiceOrderSubStatus(PLEASE_SELECT_INDEX,PLEASE_SELECT));
					subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_BUYER_SIDE_INDEX,CANCELLED_BY_BUYER_SIDE));
					subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_INDEX,CANCELLED_BY_END_CUSTOMER));
					subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_NO_SHOW_INDEX,CANCELLED_BY_END_CUSTOMER_NO_SHOW));
					break;			
		}
		return subStatus;
	}

}
