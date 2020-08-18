package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IBuyerService;
import com.newco.marketplace.translator.business.impl.BuyerService;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;

/**
 * Processes the create draft responses and creates route requests. Sets the buyer credentials up in each request.
 * @author GJACKS8
 *
 */
public class CreateRouteRequestTranslatorAction extends AbstractEsbSpringAction {
	
	private IBuyerService buyerService = null;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	
	
	@SuppressWarnings("unchecked")
	public Message translateRouteData(Message message) throws Exception {
		List<RouteRequest> routeReqList = (List<RouteRequest>)message.getBody().get(MarketESBConstant.MAPPED_ROUTEOBJ_GRAPH);
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		if (routeReqList != null && routeReqList.size() > 0) {
			String userName = (String) resourceBundle.getObject("USER_ID");
			buyerService = (BuyerService) SpringUtil.factory.getBean("BuyerService");
			BuyerCredentials buyerCreds = buyerService.getBuyerCredentials(userName);
			List<RouteRequest> routeRequestList = new ArrayList<RouteRequest>();
			//create a new route request for each order response with no errors or warnings
			if (buyerCreds != null) {
				for (RouteRequest routeRequest : routeReqList) {
					routeRequest.setUserId(buyerCreds.getUsername());
					routeRequest.setPassword(buyerCreds.getPassword());
					routeRequest.setBuyerId(buyerCreds.getBuyerID());
					routeRequest.setPasswordFlag("internal");
					routeRequestList.add(routeRequest);
				}
				message.getBody().add(MarketESBConstant.TRANSLATED_ROUTEOBJ_GRAPH, routeRequestList);
			}
			else {
				String inputFilefeedName = (String) message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
				ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), inputFilefeedName,
					getClass().getName() + " reports: Unrecoverable error occurred while translating the given feed. "
								+ "Buyer credentials not found. Please correct and process the feed again", 
					message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
				throw new Exception("----Buyer Credential object(s) not found!----");
			}
		}
		
		return message;
	}
	
	public CreateRouteRequestTranslatorAction (ConfigTree config) { super.configTree = config; }

}
