package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.ITranslationService;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentPart;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;

public class AssurantWSRequestTranslatorAction extends AbstractEsbSpringAction {
	
	private Logger logger = Logger.getLogger(AssurantWSRequestTranslatorAction.class);
	private ITranslationService xlationService = null;
	
	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Message translateData(Message message) throws Exception {
		List<SkuPrice> skus = null;
		
		xlationService = (ITranslationService) SpringUtil.factory.getBean("TranslationService");
		
		//Read the Unmarshalled Data (with Leadtime data populated by SST WS) from message body
		Body body = message.getBody();
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> createDraftReqList = (List<CreateDraftRequest>) body.get(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW);
		if (createDraftReqList != null && createDraftReqList.size() > 0) {
			IncidentEvent event = (IncidentEvent) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
			
			if(createDraftReqList == null) {
				String inputFilefeedName = (String)body.get(MarketESBConstant.FILE_FEED_NAME);
				ExceptionHandler.handle(client, new String((byte[]) body.get()), inputFilefeedName,
						getClass().getName() + " reports: Unrecoverable error occurred while translating the given feed. "
									+ "Please process the feed again", 
						message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
				throw new Exception("----Mapped CreadeDraft object(s) not found!----");
			}
			for(CreateDraftRequest draft : createDraftReqList) 
			{
				//Get the sku price values
				skus = generateSkuPrice(event);
				try
				{
					xlationService.translateDraft(draft, skus, client);
				}
				catch( Exception e )
				{
					String inputFilefeedName =
						(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
					StringBuilder errorMsg = new StringBuilder(
						"Exception returned by Translation service");
					ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()),
							inputFilefeedName,
							errorMsg,
							e);
					throw e;
				}
			}
			logger.info("******** Translation completed for incident: "+event.getIncident().getClientIncidentID());
			
			//Set the mapped objects back onto the message body
			body.add(MarketESBConstant.TRANSLATED_OBJ_GRAPH_NEW, createDraftReqList);
		}
		ClientServiceOrderNoteRequest noteRequest = (ClientServiceOrderNoteRequest) body.get(MarketESBConstant.MAPPED_NOTE_OBJ_GRAPH);
		if (noteRequest != null) {
			noteRequest = xlationService.translateClientNote(noteRequest);
			logger.info("******** Translation successfully completed for 1 ClientNote objects.");
			body.add(MarketESBConstant.TRANSLATED_NOTE_OBJ_GRAPH, noteRequest);
		}
		UpdateIncidentTrackingRequest ackRequest = (UpdateIncidentTrackingRequest) body.get(MarketESBConstant.MAPPED_ACK_OBJ_GRAPH);
		if (ackRequest != null) {
			ackRequest = xlationService.translateIncidentAck(ackRequest);
			logger.info("******** Translation successfully completed for 1 Incident Ack objects.");
			body.add(MarketESBConstant.TRANSLATED_ACK_OBJ_GRAPH, ackRequest);
		}
		return message;
	}
	
	private List<SkuPrice> generateSkuPrice(IncidentEvent event) {
		List<SkuPrice> skuPrices = new ArrayList<SkuPrice>();
		for (IncidentPart part : event.getIncidentParts()) {
			SkuPrice skuPrice = new SkuPrice();
			skuPrice.setLeadTime(3);
			skuPrice.setSku(part.getClassCode());
			skuPrice.setSpecialtyCode("NA");
			skuPrices.add(skuPrice);
		}
		return skuPrices;
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public AssurantWSRequestTranslatorAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public AssurantWSRequestTranslatorAction() {}
}
