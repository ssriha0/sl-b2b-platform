package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;

import com.newco.marketplace.web.constants.SOConstants;




public class ServiceOrderSubStatusDAO implements IServiceOrderSubStatusDAO,SOConstants{

	private static ArrayList<ServiceOrderSubStatus>list = null;
	private static ArrayList<ServiceOrderSubStatus>subStatus = null;
	public ArrayList<ServiceOrderSubStatus> findAll()
	{
		if(subStatus == null)//list == null)
		{
			/*	
			list = new ArrayList<ServiceOrderSubStatus>();
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.ABANDONED_WORK, "Abandoned Work"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.ADDITIONAL_PART_REQUIRED, "Additional Part Required"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.ADDITIONAL_WORK_REQUIRED, "Additional Work Required"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.CANCELLED_BY_BUYER_SIDE, "Cancelled by Buyer Side"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.CANCELLED_BY_END_CUSTOMER, "Cancelled by End User"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.CANCELLED_BY_PROVIDER_SIDE, "Cancelled by Provider Side"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.END_CUSTOMER_NO_SHOW, "Customer No Show"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.FUNDS_TRANSFERRED, "Funds Transferred"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.INCOMPLETE_STATEMENT_OF_WORK, "Incomplete Statement of Work"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.JOB_DONE_TO_BE_COMPLETED_FOR_PAYMENT, "Job Done to be Completed for Payment"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.NO_COMMUNICATION_OR_NOTES, "No Communication or Notes"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.OUT_OF_SCOPE, "Out of Scope"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_BACK_ORDERED, "Part Back Ordered"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_ON_ORDER, "Part on Order"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_RECEIVED_HOLD_FOR_PICKUP, "Part Received Hold For Pickup"));		
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_RECEIVED_BY_END_CUSTOMER, "Part Received by End Customer"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_RECEIVED_BY_TEAM_MEMBER, "Part Received by Team Member"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PART_SHIPPED, "Part Shipped"));
			list.add( new ServiceOrderSubStatus( ServiceOrderSubStatus.PROPERTY_DAMAGE, "Property Damage"));
			*/
			subStatus = new ArrayList<ServiceOrderSubStatus>();
			subStatus.add(new ServiceOrderSubStatus(PART_BACK_ORDERED_INDEX,PART_BACK_ORDERED));
			subStatus.add(new ServiceOrderSubStatus(PART_ON_ORDER_INDEX,PART_ON_ORDER));
			subStatus.add(new ServiceOrderSubStatus(RELEASED_BY_PROVIDER_INDEX,RELEASED_BY_PROVIDER));
			subStatus.add(new ServiceOrderSubStatus(LOCKED_FOR_BUYER_INDEX,LOCKED_FOR_BUYER));
			subStatus.add(new ServiceOrderSubStatus(RELEASED_BY_PROVIDER_INDEX,RELEASED_BY_PROVIDER));
			subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_BUYER_SIDE_INDEX,CANCELLED_BY_BUYER_SIDE));
			subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_INDEX,CANCELLED_BY_END_CUSTOMER));
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
			subStatus.add(new ServiceOrderSubStatus(AWAITING_PAYMENT_INDEX,AWAITING_PAYMENT));
			subStatus.add(new ServiceOrderSubStatus(DOCUMENT_REQUIRED_INDEX,DOCUMENT_REQUIRED));	
			subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_BUYER_SIDE_INDEX,CANCELLED_BY_BUYER_SIDE));
			subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_INDEX,CANCELLED_BY_END_CUSTOMER));
			subStatus.add(new ServiceOrderSubStatus(CANCELLED_BY_END_CUSTOMER_NO_SHOW_INDEX,CANCELLED_BY_END_CUSTOMER_NO_SHOW));
			
		}
		return subStatus;//list;
	}
	
	public String findById(Integer id)
	{
		for(ServiceOrderSubStatus obj : subStatus)//list)
		{
			if(obj.getId().intValue() == id.intValue())
				return obj.getName();
		}
		return null;
	}

}
