package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.mapper.Mapper;
import com.servicelive.esb.mapper.MapperFactory;
import com.servicelive.esb.mapper.OMSCreateDraftMapper;
import com.servicelive.esb.service.ExceptionHandler;

/**
 * An ESB Action responsible for mapping the input object graph into a list of 
 * CreateDraftRequest objects
 * @author pbhinga
 *
 */
public class CreateDraftRequestMapperAction extends AbstractEsbSpringAction {
	
	private Logger logger = Logger.getLogger(CreateDraftRequestMapperAction.class);
	private Mapper draftMapper = new OMSCreateDraftMapper();

	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Message mapData(Message message) throws Exception {
		logger.info(new StringBuffer("**** Invoking CreateDraftRequestMapperAction ---->")
			.append(message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH)));
		List<ServiceOrder> serviceOrderList = null;
		CreateDraftRequest createDraftReq = null;
		List<CreateDraftRequest> createDraftReqListNew = new ArrayList<CreateDraftRequest>();
		List<CreateDraftRequest> createDraftReqListUpdate = new ArrayList<CreateDraftRequest>();
		
		//Read the Unmarshalled Data (with Leadtime data populated by SST WS) from message body
		ServiceOrders serviceOrders = (ServiceOrders) message.getBody()
			.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		
		Mapper dataMapper = MapperFactory.getInstance(client, MapperFactory.SERVICE_ORDER);
		logger.debug("********dataMapper="+dataMapper);
		
		if(serviceOrders != null)
			serviceOrderList = serviceOrders.getServiceOrders();
		
		logger.debug("********serviceOrderList="+serviceOrderList);
		
		if(serviceOrderList == null) {
			String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
			ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), inputFilefeedName,
				getClass().getName() + " reports: Unrecoverable error occurred. Please process the file again", 
				message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
			throw new Exception("----Unmarshalled ServiceOrder object(s) not found!----");
		}
		
		//Map each ServiceOrder to get associated CreateDraftRequest
		Iterator<ServiceOrder> serviceOrderListIterator = serviceOrderList.iterator();
		while( serviceOrderListIterator.hasNext() )
		{
			ServiceOrder serviceOrder = (ServiceOrder) serviceOrderListIterator.next();

			try
			{
				createDraftReq = (CreateDraftRequest) dataMapper.mapData(serviceOrder);
				if (serviceOrder.getTransactionType().equals(MarketESBConstant.TX_MODE_UPDATE)) {
					createDraftReqListUpdate.add(createDraftReq);
				}
				else {
					createDraftReqListNew.add(createDraftReq);
				}
			}
			catch( Exception e )
			{
				StringBuilder msg = 
					new StringBuilder("Unrecoverable error, removing from list: ")
					.append(serviceOrder.getServiceUnitNumber())
					.append(serviceOrder.getServiceOrderNumber());
				logger.error( msg, e );
				// REMOVE DIRTY SO FROM LIST
				serviceOrderListIterator.remove();
//				String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
//				ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), 
//						inputFilefeedName+".CreateDraftRequestMapper."+i,
//						msg.toString(),
//						orderString.toString(),
//						e);
			}
		}
		
		logger.debug("********Mapping successfully completed for "+serviceOrderList.size()+" objects.");
		
		//Set the mapped objects back onto the message body
		message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW, createDraftReqListNew);
		message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE, createDraftReqListUpdate);				
		return message;
	}
	

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public CreateDraftRequestMapperAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public CreateDraftRequestMapperAction() {}

	public Mapper getDraftMapper() {
		return draftMapper;
	}

	public void setDraftMapper(Mapper draftMapper) {
		this.draftMapper = draftMapper;
	}

}
