package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.mapper.AssurantAckToWSAckRequestMapper;
import com.servicelive.esb.mapper.AssurantCreateDraftMapper;
import com.servicelive.esb.mapper.AssurantNoteMapper;
import com.servicelive.esb.mapper.Mapper;
import com.servicelive.esb.service.ExceptionHandler;

/**
 * An ESB Action responsible for mapping the input object graph into a list of 
 * CreateDraftRequest objects
 * @author pbhinga
 *
 */
public class AssurantCreateDraftRequestMapperAction extends AbstractEsbSpringAction {
	
	private Logger logger = Logger.getLogger(CreateDraftRequestMapperAction.class);
	
	private Mapper draftMapper = new AssurantCreateDraftMapper();
	private Mapper noteMapper = new AssurantNoteMapper();
	private Mapper ackMapper = new AssurantAckToWSAckRequestMapper();
	
	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Message mapData(Message message) throws Exception {
		//Read the Unmarshalled Data
		boolean foundData = false;
		Body body = message.getBody();
		IncidentEvent incidentEvent = (IncidentEvent) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		IncidentNote incidentNote = (IncidentNote) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH_NOTE);
		IncidentAck incidentAck = (IncidentAck) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH_ACK);
		if (incidentEvent != null) {
			mapIncidentEventToWsRequest(body, incidentEvent);
			foundData = true;
		}
		if (incidentNote != null) {
			mapIncidentNoteToWsRequest(body, incidentNote);
			foundData = true;
		}
		if (incidentAck != null) {
			mapIncidentAckToWsRequest(body, incidentAck);
			foundData = true;
		}
		if(!foundData) {
			String inputFilefeedName = (String) body.get(MarketESBConstant.FILE_FEED_NAME);
			
			ExceptionHandler.handle(MarketESBConstant.Client.ASSURANT, new String((byte[]) body.get()), inputFilefeedName,
					getClass().getName() + " reports: Unrecoverable error occurred. Please process the file again", 
					message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
			throw new Exception("----Unmarshalled ServiceOrder object(s) not found!----");
		}
		
		return message;
	}

	/**
	 * @param body
	 * @param incidentAck
	 * @throws Exception
	 */
	private void mapIncidentAckToWsRequest(Body body, IncidentAck incidentAck)
			throws Exception {
		UpdateIncidentTrackingRequest ackRequest = (UpdateIncidentTrackingRequest) ackMapper.mapData(incidentAck);
		logger.info("********Mapping successfully completed for incident acknowledgement:" + incidentAck.getIncident().getClientIncidentID() + " objects.");
		body.add(MarketESBConstant.MAPPED_ACK_OBJ_GRAPH, ackRequest);
	}

	/**
	 * @param body
	 * @param incidentNote
	 * @throws Exception
	 */
	private void mapIncidentNoteToWsRequest(Body body,
			IncidentNote incidentNote) throws Exception {
		ClientServiceOrderNoteRequest noteRequest = (ClientServiceOrderNoteRequest) noteMapper.mapData(incidentNote);
		logger.info("********Mapping successfully completed for incident note:" + incidentNote.getIncident().getClientIncidentID() + " objects.");
		body.add(MarketESBConstant.MAPPED_NOTE_OBJ_GRAPH, noteRequest);
	}

	/**
	 * @param body
	 * @param incidentEvent
	 * @throws Exception
	 */
	private void mapIncidentEventToWsRequest(Body body, IncidentEvent incidentEvent)
			throws Exception {
		CreateDraftRequest createDraftReq = null;
		List<CreateDraftRequest> createDraftReqList = new ArrayList<CreateDraftRequest>();
		createDraftReq = (CreateDraftRequest) draftMapper.mapData(incidentEvent);
		createDraftReqList.add(createDraftReq);
		logger.info("********Mapping completed for incident: "+incidentEvent.getIncident().getClientIncidentID());
		//Set the mapped objects back onto the message body
		body.add(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW, createDraftReqList);
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public AssurantCreateDraftRequestMapperAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public AssurantCreateDraftRequestMapperAction() {}

	public Mapper getDraftMapper() {
		return draftMapper;
	}

	public void setDraftMapper(Mapper draftMapper) {
		this.draftMapper = draftMapper;
	}

}
