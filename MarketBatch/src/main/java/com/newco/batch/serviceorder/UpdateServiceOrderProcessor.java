package com.newco.batch.serviceorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.sears.os.vo.SerializableBaseVO;

public class UpdateServiceOrderProcessor extends ABatchProcess {

	private static final Logger logger = Logger
			.getLogger(UpdateServiceOrderProcessor.class);
	private IServiceOrderSearchBO searchBO;
	private IServiceOrderBO soBO;
	private IOrderGroupBO orderGroupBO;
	private IPowerBuyerBO powerBuyerBO;
    //private OFHelper ofHelper;
    private IEmailTemplateBO emailTemplateBean = null;
    public static final String ACTIVATION_ERROR_EMAIL_TO_ADDRESS = 
    	PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.ACTIVATION_ERROR_EMAIL_TO_ADDRESS);
    public static final String ACTIVATION_ERROR_EMAIL_FROM_ADDRESS = 
    	PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.ACTIVATION_ERROR_EMAIL_FROM_ADDRESS);

	@Override
	public int execute() {
		SecurityContext securityContext = SecurityUtil
				.getSystemSecurityContext();
		if (securityContext.getCompanyId() != null){
			Integer buyerId = securityContext.getCompanyId();
			ServiceOrderUtil.enrichSecurityContext(securityContext, buyerId);
		}
		
		try {
			// process records that are ready for activation
			activateOrders(securityContext);

			// process conditional offer that are ready for expiration
			expireConditionalOffer(securityContext);

			// process records that are ready for expiration
			expireOrders(securityContext);

			// removing this step since other queues in new WFM cover the cases
			// Change the substatus of the OMS orders that are not scheduled confirmed
			//updateToNeedsAttention(securityContext);
			
			// update WFM queues
			updateWFMQueues(securityContext);

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		return (0);
	}

	/**
	 * Expires the orders that are in posted status and current time is passed appointment start date
	 * 
	 * @param securityContext
	 */
	private void expireOrders(SecurityContext securityContext) {
		for (ServiceOrderSearchResultsVO soRecord : getOrdersForExpiration()) {
			try {
				// mark order as expired
				logger.info("UpdateServiceOrderProcessor-->Expire SO-->"
						+ soRecord.getSoId());
				soBO.expirePostedSO(soRecord.getSoId(), securityContext);
			} catch (Throwable t) {
				logger.error("Error expiring SO -->" + soRecord.getSoId(), t);
			}
		}
	}

	/**
	 * @param securityContext
	 */
	private void expireConditionalOffer(SecurityContext securityContext) {
		for (ServiceOrderSearchResultsVO soRecord : getConditionalOrdersForExpiration()) {
			try {
				// mark conditional offer as expired
				logger
						.debug("UpdateServiceOrderProcessor-->Expire Conditional Offer-->"
								+ soRecord.getSoId());
				soBO.expireConditionalOffer(soRecord.getSoId(), soRecord
						.getAcceptedResourceId(),soRecord.getGroupId(), securityContext);
			} catch (Throwable t) {
				logger.error("Error expiring conditional offer for SO -->" + soRecord.getSoId(), t);
			}
		}
	}

	/**
	 * Activates the orders that are in accepted status and current time is passed appointment start date
	 * 
	 * @param securityContext
	 */
	private void activateOrders(SecurityContext securityContext) {
		for (ServiceOrderSearchResultsVO soRecord : getOrdersForActivation()) {
			try {
				// mark order as active
				logger.info("UpdateServiceOrderProcessor-->Activate SO-->"
						+ soRecord.getSoId());
				soBO
						.activateAcceptedSO(soRecord.getSoId(),
								securityContext);
			} catch (Throwable t) {
				logger.error("Error activating SO -->" + soRecord.getSoId(), t);
			}
		}
	}

	@Override
	public void setArgs(String[] args) {
		// do nothing
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		searchBO = (IServiceOrderSearchBO) ctx.getBean("soSearchBO");
		soBO = (IServiceOrderBO) ctx.getBean("soBOAOP");
	}

	/**
	 * This method returns a list of service orders that may need to be marked
	 * as active. The criteria for marking an order active is - Status has to be
	 * ACCEPTED Service Date 1 has to less than or equal to current date Service
	 * Start time is past current time *
	 * 
	 * Note - We are not comparing the start time with current time in the sql
	 * since start time is stored as a string in the database
	 * 
	 * @return List of service orders that are in accepted state and the service
	 *         Date1 is equal to or less than current date and start time is
	 *         past current time
	 */

	public List<ServiceOrderSearchResultsVO> getOrdersForActivation() {
		serviceOrderTabsVO request = new serviceOrderTabsVO();


		// set time comparison fields
		Calendar cal = Calendar.getInstance();
		logger.info(cal.getTime());

		// since service date is stored as a sql timestamp, clear the time
		// fields from now
		/*
		 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0);
		 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0);
		 */
		Timestamp currentDate = new Timestamp(cal.getTimeInMillis());
		currentDate = TimeUtils.convertToGMT(currentDate,
				OrderConstants.CST_ZONE);

		request.setSoStatus(String.valueOf(OrderConstants.ACCEPTED_STATUS));
		request.setCurrentDate(currentDate);

		List<ServiceOrderSearchResultsVO> searchList = null;
		List<ServiceOrderSearchResultsVO> processList = new ArrayList<ServiceOrderSearchResultsVO>();
        try {
            searchList = searchBO.findServiceOrderByStatusForBatch(request);
		} catch (Exception e) {
			handleCannotActivateServiceOrder(request, e);
		}
        for(ServiceOrderSearchResultsVO soHdr:searchList){
            try {
				String timeStr = soHdr.getServiceTimeStart();
				if(StringUtils.isNotBlank(timeStr)){
					String hours = timeStr.substring(0, 2);
					String min = timeStr.substring(3, 5);
					String sec = "00";
					String timeType = timeStr.substring(6, 8);
					//Converting the 12 hour format to 24 hour format(SL-6640)
					if (timeType.equalsIgnoreCase("PM") && Integer.parseInt(hours) < 12){
						int hh = Integer.parseInt(hours) + 12;
						hours = new Integer(hh).toString();
					}
					//To make 12:00 AM as 00:00 AM to avoid the confusion between 12:00 AM & 12:00 PM
					if(timeType.equalsIgnoreCase("AM") && "12".equalsIgnoreCase(hours)){
						hours = "00";
					}
					timeStr = hours + ":" + min + ":" + sec;
				}
				else {
					handleCannotActivateServiceOrder(soHdr, 
							new RuntimeException("getServiceTimeStart is empty"));
					continue;
				}
				if (TimeUtils.isPastCurrentTimeInServiceLocTimezone(soHdr
						.getAppointStartDate(), timeStr, Locale.US, soHdr
						.getServiceLocationTimezone())) {
					processList.add(soHdr);
				}
            }
            catch(Exception e)
            {
				handleCannotActivateServiceOrder(soHdr, e);
            	continue;
            }
		}
		logger.info("The Resultset for Activate orders is - " + processList.size());

		return processList;

	}

	/**
	 * This method returns a list of service orders that need to be marked as
	 * expired. The criteria for marking an order expired is - Status has to be
	 * POSTED and service start date and time is past current time.
	 * If this find any order in the group, it updates the status of the group
	 * to be expired.
	 * 
	 * Note - We are not comparing the start time with current time in the sql
	 * since start time is stored as a string in the database.
	 * 
	 * @return List of service orders that are in routed state and service start
	 * date and time is past current time
	 */
	public List<ServiceOrderSearchResultsVO> getOrdersForExpiration() {
		serviceOrderTabsVO request = new serviceOrderTabsVO();
		ServiceOrderSearchResultsVO soHdr = null;

		// set time comparison fields
		Calendar cal = Calendar.getInstance();
		logger.info(cal.getTime());
		Timestamp currentDate = TimeUtils.convertToGMT(new Timestamp(cal.getTimeInMillis()), OrderConstants.CST_ZONE);

		request.setSoStatus(String.valueOf(OrderConstants.ROUTED_STATUS));
		request.setCurrentDate(currentDate);

		List<ServiceOrderSearchResultsVO> searchList = null;
		List<ServiceOrderSearchResultsVO> processList = new ArrayList<ServiceOrderSearchResultsVO>();
		try {
			searchList = searchBO.findServiceOrderByStatusForBatch(request);

			for (int i = 0; i < searchList.size(); i++) {
				soHdr = searchList.get(i);
//                if(ofHelper.isNewSo(soHdr.getSoId())){
//                    continue; //Order created through New OrderFulfillment System will not need batch.
//                }
				//If the process list does not already contain the SO, check for expiration, otherwise
				//check the next SO.
				if (!processList.contains(soHdr)) {
					String timeStartStr = soHdr.getServiceTimeStart();
					String timeEndStr = soHdr.getServiceTimeEnd();
					String serviceDateTypeId;
					if(soHdr.getServiceDateTypeId() != null) {
						serviceDateTypeId = soHdr.getServiceDateTypeId().toString();
					} else {
						serviceDateTypeId = "";
					}

					if (OrderConstants.FIXED_DATE.equals(serviceDateTypeId)) {
						if (TimeUtils.isPastCurrentTime(soHdr.getAppointStartDate(), timeStartStr, Locale.US, soHdr.getServiceLocationTimezone())) {
							//If this SO belongs to a group, add all the orders in this group for expiration
							addOrdersInGroupForExpiration(soHdr, processList);
						}
					} else if (OrderConstants.RANGE_DATE.equals(serviceDateTypeId)) {
						if (TimeUtils.isPastCurrentTime(soHdr.getAppointEndDate(), timeEndStr, Locale.US, soHdr.getServiceLocationTimezone())) {
							//If this SO belongs to a group, add all the orders in this group for expiration
							addOrdersInGroupForExpiration(soHdr, processList);
						}
					}else if(OrderConstants.PREFERENCES.equals(serviceDateTypeId)){
						logger.info("entering into else if to expire preferences order");
						ServiceDatetimeSlot serviceDatetimeSlot = searchBO.getDateTimeSlotForOrder(soHdr);
						if (TimeUtils.isPastCurrentTime(serviceDatetimeSlot.getServiceStartDate(), serviceDatetimeSlot.getServiceStartTime(), Locale.US, serviceDatetimeSlot.getTimeZone())) {
							//If this SO belongs to a group, add all the orders in this group for expiration
							addOrdersInGroupForExpiration(soHdr, processList);
						}
					}
				}
			}
			logger.info("The Resultset for Expiration orders is - "	+ processList.size());
		} catch (DataServiceException e) {
			logger.error(e.getLocalizedMessage());
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			logger.error("Error finding orders in the group.");
			logger.error(e.getLocalizedMessage());
		}
		catch (Exception e) {
			if(soHdr != null) {
				logger.error("UpdateServiceOrderProcessor-->Error in getOrdersForExpiration for -->" + soHdr.getSoId(), e);
			} else {
				logger.error("UpdateServiceOrderProcessor-->Error in getOrdersForExpiration", e);
			}
		}
		return processList;

	}

	/**
	 * Add all the orders in this group for expiration
	 * 
	 * @param soHdr
	 * @param processList
	 * @throws BusinessServiceException
	 * @throws DataServiceException
	 */
	private void addOrdersInGroupForExpiration(
			ServiceOrderSearchResultsVO soHdr,
			List<ServiceOrderSearchResultsVO> processList)
			throws com.newco.marketplace.exception.BusinessServiceException,
			DataServiceException {
		if (soHdr.getParentGroupId() != null) {
			processList.addAll(orderGroupBO.getServiceOrdersForGroup(soHdr.getParentGroupId()));
			//Update order group status to expired
			orderGroupBO.updateSoGroupStatus(soHdr.getParentGroupId(), null, OrderConstants.EXPIRED_STATUS, 0, false);
		}
		else{
			processList.add(soHdr);
		}
	}

	public List<ServiceOrderSearchResultsVO> getConditionalOrdersForExpiration() {
		logger
				.info("Entered UpdateServiceOrderProcessor--> getConditionalOrdersForExpiration()");
		List<ServiceOrderSearchResultsVO> condOfferExpireList = null;

		Calendar cal = Calendar.getInstance();
		logger.info("Current Time " + cal.getTime());

		Timestamp currentDateTime = new Timestamp(cal.getTimeInMillis());
		currentDateTime = TimeUtils.convertToGMT(currentDateTime,
				OrderConstants.CST_ZONE);

		try {
			condOfferExpireList = searchBO
					.findConditionalOffers(currentDateTime);
		} catch (DataServiceException e) {
			logger.error(e.getLocalizedMessage());
		}
		return condOfferExpireList;
	}

	/**
	 * Re-populate all WFM Queues.
	 * 
	 * @param securityContext
	 */
	private void updateWFMQueues(SecurityContext securityContext) {
		logger.info("UpdateServiceOrderProcessor-->updateWFMQueues");
		try {
			powerBuyerBO.updateWFMQueues();
		} catch (Throwable t) {
			logger.error("Error updating WFM Queues -->", t);
		}
	}
	
	private void handleCannotActivateServiceOrder(SerializableBaseVO vo,
			Throwable t)
	{
		logger.error("Cannot activate ServiceOrder");
		getEmailTemplateBean().sendErrorEmail(vo, "Error activating service order",
				ACTIVATION_ERROR_EMAIL_TO_ADDRESS,ACTIVATION_ERROR_EMAIL_FROM_ADDRESS);
		return;
	}

	/**
	 * @return the emailTemplateBean
	 */
	public IEmailTemplateBO getEmailTemplateBean() {
		return emailTemplateBean;
	}


	/**
	 * @param emailTemplateBean the emailTemplateBean to set
	 */
	public void setEmailTemplateBean(IEmailTemplateBO emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}
	public IServiceOrderSearchBO getSearchBO() {
		return searchBO;
	}

	public void setSearchBO(IServiceOrderSearchBO searchBO) {
		this.searchBO = searchBO;
	}

	public IServiceOrderBO getSoBO() {
		return soBO;
	}

	public void setSoBO(IServiceOrderBO soBO) {
		this.soBO = soBO;
	}

	/**
	 * @return the orderGroupBO
	 */
	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	/**
	 * @param orderGroupBO the orderGroupBO to set
	 */
	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public void setPowerBuyerBO(IPowerBuyerBO powerBuyerBO) {
		this.powerBuyerBO = powerBuyerBO;
	}

//    public void setOfHelper(OFHelper ofHelper) {
//        this.ofHelper = ofHelper;
//    }
}
