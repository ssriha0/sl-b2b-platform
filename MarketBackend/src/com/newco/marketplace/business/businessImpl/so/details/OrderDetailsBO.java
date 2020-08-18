package com.newco.marketplace.business.businessImpl.so.details;

import com.newco.marketplace.business.businessImpl.so.order.BaseOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class OrderDetailsBO extends BaseOrderBO {

	public ProcessResponse process(Integer buyerId, String soId) {
		ProcessResponse processResp = new ProcessResponse();
		/*if (logger.isDebugEnabled())
			logger.debug("Order details has started for service order: " + soId
					+ " buyer id: " + buyerId);

		try {
			// basic request validation
			ValidatorResponse validatorResp = new OrderDetailsValidator()
					.validate(buyerId, soId);

			if (validatorResp.isError()) {
				if (logger.isInfoEnabled())
					logger
							.info("Order details has failed validation for service order: "
									+ soId
									+ " buyer id: "
									+ buyerId
									+ " reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}

			// verify buyer has access to service order
			ServiceOrder so = new ServiceOrder();
			so.setSoId(soId);
			if (!isBuyerAuthorized(buyerId, so))
				throw new Exception("Buyer id: " + buyerId
						+ " is NOT authorized to access service order: " + soId);

			// query service order header details...
			ServiceOrderDetail soDtl = getOrderDetails(soId);
			
			// retrieve service order note(s) details next...
			soDtl.setNotes(getNoteDetails(soId));

			// retrieve service order task(s) details next...
			soDtl.setTasks(getTaskDetails(soId));

			// retrieve buyer details next...
			soDtl.setBuyer(getBuyerDetails(soId));
			
			// retrieve provider details next if available...
			int state = soDtl.getWfStateId().intValue(); 
			
			// retrieve routed provider details if available...
			if( state >= ROUTED_STATUS)
				soDtl.setRoutedProviders(getRoutedProviderDetails(soId));

			// retrieve accepted provider details if available...
			if( state >= ACCEPTED_STATUS)
				soDtl.setAcceptedProvider(getAcceptedProviderDetails(soDtl, so));

			// setup a valid process response
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(soDtl);

			if (logger.isDebugEnabled())
				logger
						.debug("Order details have been successfully retrieved for service order: "
								+ soId);

		} catch (Throwable t) {
			logger.error("Order details error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}

		return processResp;
	}

	private boolean isBuyerAuthorized(Integer buyerId, ServiceOrder so)
			throws Exception {

		boolean b = false;

		try {
			// lookup service order to verify its existence and that the buyer
			// has accessed to it
			ServiceOrder tmp = getServiceOrderDao().query(so);

			// check existence of so
			if (tmp == null)
				throw new Exception("Service order: " + so.getSoId()
						+ " does not exist in DB...");

			//copy values over...
			so.clone(tmp);
			
			// verify buyer has access to so
			if (buyerId.equals(so.getBuyer().getBuyerId()));
				b = true;

		} catch (Exception e) {
			logger.error("Cannot retrieve service order: " + so.getSoId()
					+ " to validate access for buyer id: " + buyerId
					+ " reason: " + e.getMessage());
			throw e;
		}

		return b;
	}

	private ServiceOrderDetail getOrderDetails(String soId) throws Exception {

		try {
			ServiceOrder so = new ServiceOrder();
			so.setSoId(soId);
			ServiceOrderDetail soDtl = getServiceOrderDao().queryDetail(so);

			if (soDtl == null)
				throw new Exception(
						"Unable to find retrieve order details for service order: "
								+ soId);

			return soDtl;

		}

		catch (Exception e) {
			logger.error("Cannot retrieve order details for service order: "
					+ soId + " reason: " + e.getMessage());
			throw e;
		}
	}

	private ArrayList<ServiceOrderNoteDetail> getNoteDetails(String soId)
			throws Exception {

		try {
			//ArrayList<ServiceOrderNoteDetail> notes = getServiceOrderDao()
				//	.queryNotesDetail(soId);

			//return notes;
		}

		catch (Exception e) {
			logger.error("Cannot retrieve note details for service order: "
					+ soId + " reason: " + e.getMessage());
			throw e;
		}
		return null;
	}

	private ArrayList<ServiceOrderTaskDetail> getTaskDetails(String soId)
			throws Exception {

		try {
			ArrayList<ServiceOrderTaskDetail> tasks = getServiceOrderDao()
					.queryTasksDetail(soId);
			if (CollectionUtils.size(tasks) == 0)
				throw new Exception(
						"Unable to retrieve task details for service order: "
								+ soId);

			// retrieve part details for each task
			Iterator<ServiceOrderTaskDetail> iter = tasks.iterator();
			while (iter.hasNext()) {
				ServiceOrderTaskDetail task = iter.next();
				Part part = new Part();
				part.setTaskId(task.getTaskId());
				part.setSoId(soId);
				ArrayList<PartDetail> parts = getServiceOrderDao()
						.queryPartsDetail(part);
				task.setPartList(parts);
			}

			return tasks;
		}

		catch (Exception e) {
			logger.error("Cannot retrieve task details for service order: "
					+ soId + " reason: " + e.getMessage());
			throw e;
		}
	}
	
	private BuyerDetail getBuyerDetails(String soId) throws Exception {

		try {
			BuyerDetail buyerDtl = getServiceOrderDao().queryBuyerDetail(soId);

			if (buyerDtl == null)
				throw new Exception(
						"Unable to retrieve buyer details for service order: "
								+ soId);

			return buyerDtl;
		}
		catch (Exception e) {
			logger.error("Cannot retrieve buyer details for service order: "
					+ soId + " reason: " + e.getMessage());
			throw e;
		}
	}

	private ArrayList<ProviderDetail> getRoutedProviderDetails(String soId) throws Exception {

		try {
			ArrayList<ProviderDetail> list = getServiceOrderDao().queryRoutedProviderDetail(soId);

			if (CollectionUtils.size(list) == 0)
				throw new Exception(
						"Unable to retrieve routed provider details for service order: "
								+ soId);

			return list;
		}
		catch (Exception e) {
			logger.error("Cannot retrieve provider details for service order: "
					+ soId + " reason: " + e.getMessage());
			throw e;
		}
	}
	
	private ProviderDetail getAcceptedProviderDetails(ServiceOrderDetail soDtl, ServiceOrder so) throws Exception {

		//look for the accepted provider id in the routed provider list
		Iterator<ProviderDetail> iter = soDtl.getRoutedProviders().iterator();
		ProviderDetail provider = null;
		Integer acceptedId = so.getAcceptedResource().getResourceId();
		
		while(iter.hasNext()) {
			provider = iter.next();
			if(provider.getProviderId().equals(acceptedId)) {
				soDtl.setAcceptedProvider(provider);
				break;
			}
			provider = null;
		}	
		
		if (provider == null)
			throw new Exception(
					"Unable to retrieve accepted provider details for service order: "
							+ so.getSoId());

		return provider;*/
		return processResp;
	}
}
