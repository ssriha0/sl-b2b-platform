package com.servicelive.orderfulfillment.command;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class UpdateLegacyStatusCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String leavingStateName = SOCommandArgHelper.extractStringArg(processVariables, 1);
		String enteringStateName = SOCommandArgHelper.extractStringArg(processVariables, 2);
		Object enteringSubStatusIdAsObject = processVariables.get(OrderfulfillmentConstants.PVKEY_PROBLEM_SUBSTATUS_ID);
		Integer enteringSubStatusId = extractEnteringSubStatusId(enteringSubStatusIdAsObject);
		
		LegacySOStatus leavingState = LegacySOStatus.valueOf(leavingStateName.toUpperCase());
		LegacySOStatus enteringState = LegacySOStatus.valueOf(enteringStateName.toUpperCase());

		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		doUpdateForStateLeaving(leavingState, so);
		logger.info("Substatus Id before Updating status:"+ so.getWfSubStatusId());
		doUpdateForStateEntering(enteringState, so, now, enteringSubStatusId);
		logger.info("Substatus Id  after updating status:"+ so.getWfSubStatusId());
		so.setLastStatusChange(now);
		so.setModifiedBy(userName);
		logger.info("Befor updating the info");
		serviceOrderDao.update(so);
		logger.info("Substatus Id after DB update:"+ so.getWfSubStatusId());
	}

	private Integer extractEnteringSubStatusId(Object enteringSubStatusIdAsObject) {
		Integer enteringSubStatusId = null;
		if (enteringSubStatusIdAsObject instanceof String) {
			String enteringSubStatusIdAsString = (String) enteringSubStatusIdAsObject;
			try {
				enteringSubStatusId = Integer.parseInt(enteringSubStatusIdAsString);
				if (enteringSubStatusId != null && enteringSubStatusId.intValue() == 0) {
					enteringSubStatusId = null;
				}
			}
			catch (NumberFormatException e) {
				// ignore
			}
			catch (IllegalArgumentException e) {
				// ignore
			}
		}
		return enteringSubStatusId;
	}
	
	private void doUpdateForStateEntering(LegacySOStatus enteringState, ServiceOrder so, Date now, Integer enteringSubStatusId) {
		so.setWfStateId(enteringState.getId());
        so.setWfSubStatusId(null);
        
		switch (enteringState) {
		case DRAFT:
			break;
		case DELETED:
			so.setDeletedDate(now);
			break;
		case POSTED:
			if (so.getInitialRoutedDate() == null) {
				so.setInitialRoutedDate(now);
			}
			so.setRoutedDate(now);
			break;
		case CANCELLED:
			so.setCancelledDate(now);
			break;
		case VOIDED:
			so.setVoidedDate(now);
			break;
		case EXPIRED:
			so.setExpiredDate(now);
			break;
//		case CONDITIONAL_OFFER:
//			break;
		case ACCEPTED:
			so.setAcceptedDate(now);
			break;
		case ACTIVE:
            so.setActivatedDate(now);
			break;
		case COMPLETED:
			so.setCompletedDate(now);
            so.setWfSubStatusId(LegacySOSubStatus.AWAITING_PAYMENT.getId());
			break;
		case PROBLEM:
			so.setProblemDate(now);
			so.setWfSubStatusId(enteringSubStatusId);
			break;
		case CLOSED:
			so.setClosedDate(now);
			break;			
		case PENDINGCANCEL:
			so.setPendingCancelDate(now);
			so.setWfSubStatusId(enteringSubStatusId);
			break;			
			
//		case INACTIVE_GROUP:
//			break;
//		default:
//			break;
		}
	}

	public void doUpdateForStateLeaving(LegacySOStatus leavingState, ServiceOrder so) {
		if (leavingState != LegacySOStatus.START) {
			so.setLastStatusId(leavingState.getId());
		}
	}
}
