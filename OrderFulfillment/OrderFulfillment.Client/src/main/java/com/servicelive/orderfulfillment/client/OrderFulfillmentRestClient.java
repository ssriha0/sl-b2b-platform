package com.servicelive.orderfulfillment.client;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.client.utils.Configuration;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.IOrderFulfillmentBO;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.PendingServiceOrdersResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

import com.servicelive.orderfulfillment.serviceinterface.vo.CreateLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;

public class OrderFulfillmentRestClient implements IOrderFulfillmentBO {
   
	private Logger logger = Logger.getLogger(OrderFulfillmentRestClient.class);
	private String baseUrl;
	
	public OrderFulfillmentRestClient(){
		baseUrl = Configuration.getServiceBaseUrl();
		if(StringUtils.isBlank(baseUrl)){
			throw new IllegalStateException("Required System property missing.");
		}
	}
	
	/**
	 * Overridden constructor to allow to create an instance without referring to the system property. Use 
	 * the corresponding factory method to instantiate the object.
	 * @param baseUrl
	 */
	private OrderFulfillmentRestClient(String baseUrl){
	    this.baseUrl=baseUrl;
	}
	
	/**
	 * Factory method to allow creation of Client using an over ridden value for 
	 * BaseURL.
	 * @param baseUrl
	 * @return
	 */
	public static OrderFulfillmentRestClient newInstanceWitURL(String baseUrl){
		if(StringUtils.isBlank(baseUrl)){
			throw new IllegalArgumentException("Can not construct client with Empty URL.");
		}
		return new OrderFulfillmentRestClient(baseUrl);
	}
	
	public OrderFulfillmentResponse createServiceOrder(CreateOrderRequest request) {
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		try {
			String contentXml = serializeRequest(request, CreateOrderRequest.class);
			response = sendRequest(contentXml, "/orders");
		} catch (HttpException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		}
		return response;
	}
	
	public LeadResponse createLead(CreateLeadRequest request) {
		LeadResponse response = new LeadResponse();
		try {
			String contentXml = serializeRequest(request, CreateLeadRequest.class);
			logger.info("Lead Request: "+contentXml);
			response = sendLeadRequest(contentXml, "/lead");
		} catch (HttpException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		}
		return response;
	}

	public OrderFulfillmentResponse isServiceOrderNew(String soId){
		try {
			GetMethod get = new GetMethod(baseUrl + "/orders/" + soId);
			logger.info("Sending request = " + soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(get);
			String response = get.getResponseBodyAsString();
			if (get.getStatusCode() == 200){
				return deserializeOrderFulfillmentResponse(response);
			}else {
				OrderFulfillmentResponse ofResponse = new OrderFulfillmentResponse(); 
				ofResponse.addError(response);
				return ofResponse;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public ServiceOrder getServiceOrder(String soId){
		try {
			GetMethod get = new GetMethod(baseUrl + "/orders/" + soId);
			logger.info("Sending request = " + soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(get);
			if (get.getStatusCode() == 200){
				String response = get.getResponseBodyAsString();
				InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
			    JAXBContext ctx = JAXBContext.newInstance(ServiceOrder.class);
			    Unmarshaller unmarshaller = ctx.createUnmarshaller();
			    return (ServiceOrder) unmarshaller.unmarshal(is); 
			}else{
				logger.error("Bad request to " + baseUrl + "/orders/" + soId);
				return null;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

	public PendingServiceOrdersResponse getPendingServiceOrders() {
		try {
			GetMethod get = new GetMethod(baseUrl + "/pending-orders");
			logger.info("Sending request ---> GET /pending-orders");
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(get);
			if (get.getStatusCode() == 200){
				String response = get.getResponseBodyAsString();
				logger.error( response );
				PendingServiceOrdersResponse resp = deserializePendingServiceOrdersResponse(response);
				return resp;
			} else {
				logger.error("Bad request to " + baseUrl + "/pending-orders");
				return null;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isSignalAvailable(String soId, SignalType signalType) {
		try {
			GetMethod get = new GetMethod(baseUrl + "/orders/" + soId + "/signals/" + signalType);
			logger.info("Sending request = " + soId);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(get);
			if (get.getStatusCode() == 200){
				String response = get.getResponseBodyAsString();
				OrderFulfillmentResponse resp = deserializeOrderFulfillmentResponse(response);
				return (Boolean) resp.isSignalAvailable();
			}else{
				logger.error("Bad request to " + baseUrl + "/orders/" + soId);
				return false;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public OrderFulfillmentResponse processGroupSignal(String groupId, SignalType signalType, OrderFulfillmentRequest request) {
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		try {
			String contentXml = serializeRequest(request, OrderFulfillmentRequest.class);
			response = sendRequest(contentXml, "/group/" + groupId + "/signals/" + signalType.name());
		} catch (HttpException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		}
		return response;
	}
	
	public OrderFulfillmentResponse processOrderSignal(String soId, SignalType signalType, OrderFulfillmentRequest request) {
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		try {
			String contentXml = serializeRequest(request, OrderFulfillmentRequest.class);
			response = sendRequest(contentXml, "/orders/" + soId + "/signals/" + signalType.name());
		} catch (HttpException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		}
		return response;
	}
	
	public LeadResponse processLeadSignal(String leadId, SignalType signalType, OrderFulfillmentLeadRequest request) {
		LeadResponse response = new LeadResponse();
		try {
			String contentXml = serializeRequest(request, OrderFulfillmentLeadRequest.class);
			response = sendLeadRequest(contentXml, "/lead/" + leadId + "/signals/" + signalType.name());
		} catch (HttpException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			response.addError(e.getMessage());
		}
		return response;
	}
	
	private OrderFulfillmentResponse sendRequest(String contentXml, String url) throws HttpException, IOException{

		PostMethod post = new PostMethod(baseUrl + url);
		post.addRequestHeader("Accept", "application/xml");
        logger.info(String.format("Sending request to %1$s and payload is %2$s", post.getURI(), contentXml));
		RequestEntity requestEntity = new StringRequestEntity(contentXml, "application/xml", null);
		post.setRequestEntity(requestEntity);
		post.setContentChunked(true);
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(post);
		logger.info(post.getStatusCode());
		
		if (post.getStatusCode() == 200){
			String fromxml = post.getResponseBodyAsString();
			logger.info("Received response = " + fromxml);
			return deserializeOrderFulfillmentResponse(fromxml);
		}else{
			OrderFulfillmentResponse response = new OrderFulfillmentResponse();
			response.addError("Server responded with error" + post.getResponseBodyAsString());
			return response;
		}
	}
	
	private LeadResponse sendLeadRequest(String contentXml, String url) throws HttpException, IOException{

		PostMethod post = new PostMethod(baseUrl + url);
		post.addRequestHeader("Accept", "application/xml");
        logger.info(String.format("Sending request to %1$s and payload is %2$s", post.getURI(), contentXml));
		RequestEntity requestEntity = new StringRequestEntity(contentXml, "application/xml", null);
		post.setRequestEntity(requestEntity);
		post.setContentChunked(true);
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(post);
		logger.info(post.getStatusCode());
		
		if (post.getStatusCode() == 200){
			String fromxml = post.getResponseBodyAsString();
			logger.info("Received response = " + fromxml);
			return deserializeLeadResponse(fromxml);
		}else{
			LeadResponse response = new LeadResponse();
			response.addError("Server responded with error" + post.getResponseBodyAsString());
			return response;
		}
	}
	
	private String serializeRequest(Object request, Class<?> classz){
		try {
			OutputStream outputStream = new ByteArrayOutputStream();
		    JAXBContext ctx = JAXBContext.newInstance(classz);
		    Marshaller marshaller = ctx.createMarshaller();
		    marshaller.marshal(request, outputStream);
		    return outputStream.toString();
		}
		catch (JAXBException e) {
		    e.printStackTrace();
		}catch(Exception e){
			 e.printStackTrace();
		}
		return null;
	}
	
	private PendingServiceOrdersResponse deserializePendingServiceOrdersResponse( String objectXml ) {
		return this.<PendingServiceOrdersResponse>deserializeRequest(objectXml, PendingServiceOrdersResponse.class);
	}
	
	private OrderFulfillmentResponse deserializeOrderFulfillmentResponse( String objectXml ) {
		return this.<OrderFulfillmentResponse>deserializeRequest(objectXml,OrderFulfillmentResponse.class);
	}
	
	private LeadResponse deserializeLeadResponse( String objectXml ) {
		return this.<LeadResponse>deserializeRequest(objectXml,LeadResponse.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T deserializeRequest(String objectXml, Class c){
		try {
			InputStream is = new ByteArrayInputStream(objectXml.getBytes("UTF-8"));
		    JAXBContext ctx = JAXBContext.newInstance(c);
		    Unmarshaller unmarshaller = ctx.createUnmarshaller();
		    return (T) unmarshaller.unmarshal(is); 
		}
		catch (JAXBException e) {
		    e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


}
