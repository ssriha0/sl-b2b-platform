/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.xstream;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.GetBuyerAccountResponse;
import com.newco.marketplace.api.beans.buyerEventCallback.BuyerEventCallbackAckRequest;
import com.newco.marketplace.api.beans.document.DocUploadRequest;
import com.newco.marketplace.api.beans.document.DocUploadResponse;
import com.newco.marketplace.api.beans.fundingsources.CreateFundingSourceRequest;
import com.newco.marketplace.api.beans.fundingsources.CreateFundingSourceResponse;
import com.newco.marketplace.api.beans.fundingsources.GetFundingSourceResponse;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderType;
import com.newco.marketplace.api.beans.search.skillTree.Categories;
import com.newco.marketplace.api.beans.search.skillTree.Category;
import com.newco.marketplace.api.beans.search.skillTree.SkillTreeResponse;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Contact;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Dimensions;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.History;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.LogEntry;
import com.newco.marketplace.api.beans.so.Note;
import com.newco.marketplace.api.beans.so.Notes;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Part;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Phone;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.RoutedProvider;
import com.newco.marketplace.api.beans.so.RoutedProviders;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.addNote.NewNoteType;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteRequest;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocRequest;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.beans.so.cancel.SOCancelRequest;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.beans.so.create.SOCreateRequest;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.beans.so.offer.AcceptCounterOfferRequest;
import com.newco.marketplace.api.beans.so.offer.CounterOfferResponse;
import com.newco.marketplace.api.beans.so.post.Languages;
import com.newco.marketplace.api.beans.so.post.ProviderRouteInfo;
import com.newco.marketplace.api.beans.so.post.SOPostRequest;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.beans.so.post.SpecificProviders;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleInfo;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.beans.so.retrieve.ResponseFilter;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveRequest;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveResponse;
import com.newco.marketplace.api.beans.so.search.SOSearch;
import com.newco.marketplace.api.beans.so.search.SOSearchRequest;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.beans.wallet.AddFundsToWalletRequest;
import com.newco.marketplace.api.beans.wallet.AddFundsToWalletResponse;
import com.newco.marketplace.api.beans.wallet.WalletBalanceResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.TransactionDetail;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResults;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
 
/**
 * This class would act as an Utility Function for XStream conversion of Objects.
 * 
 * @author Infosys
 * @version 1.0
 */
public class XStreamUtility{
	private Logger logger = Logger.getLogger(XStreamUtility.class);

	/**
	 * This method is for the conversion of the request xml 
	 * to createRequest object using Xstream conversion .
	 * 
	 * @param soXml   String
	 * @return SoRequest
	 */
	public SOCreateRequest getCreateRequestObject(String soXml){
		logger.info("Entering XStreamUtility.getCreateRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOCreateRequest.class,Tasks.class,Task.class };
		xstream.processAnnotations(createClasses);
		SOCreateRequest soCreateRequest = (SOCreateRequest) xstream.fromXML(soXml);
		logger.info("Leaving XStreamUtility.getCreateRequestObject()");		
		return soCreateRequest;
	}

	
	/**
	 * This method is for the conversion of the createResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	public String getCreateResponseXML(SOCreateResponse createResponse){
		logger.info("Entering XStreamUtility.getCreateResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				SOCreateResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String createResponseXml = (String) xstream.toXML(createResponse);
		logger.info("Leaving XStreamUtility.getCreateResponseXML()");		
		return createResponseXml;
	}
	
	
	/**
	 * This method is for the conversion of the request xml 
	 * to RetrieveRequest object using Xstream conversion .
	 * 
	 * @param soRequestXml   String
	 * @return retrieveSORequest
	 */
	public SORetrieveRequest getRetrieveRequestObject(String soRequestXml) {
		logger.info("Entering XStreamUtility.getRetrieveRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] retrieveClasses = new Class[] { SORetrieveRequest.class,
				Identification.class,ResponseFilter.class };
		xstream.processAnnotations(retrieveClasses);
		SORetrieveRequest retrieveSORequest = (SORetrieveRequest) xstream
				.fromXML(soRequestXml);
		logger.info("Leaving XStreamUtility.getRetrieveRequestObject()");		
		return retrieveSORequest;
	}

	
	/**
	 * This method is for the conversion of the createResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param retrieveSOResponse   RetrieveSOResponse
	 * @return retrieveResponseXML
	 */
	public String getRetrieveResponseXML(SORetrieveResponse retrieveSOResponse,List<String> responseFilter) {
		logger.info("Entering XStreamUtility.getRetrieveResponseXML()");
		XStream retrieveXstream = new XStream(new DomDriver());
		retrieveXstream.registerConverter(new ResultConvertor());
		retrieveXstream.registerConverter(new ErrorConvertor());		
		Class<?>[] retrieveClasses = new Class[] { SORetrieveResponse.class,
				Results.class, Result.class, ErrorResult.class,
				OrderStatus.class, GeneralSection.class, Location.class,
				Schedule.class ,Pricing.class,Contacts.class,Contact.class,Attachments.class,
				Parts.class, Part.class,CustomReferences.class, CustomReference.class,
				Notes.class,Note.class,History.class,LogEntry.class,RoutedProviders.class,RoutedProvider.class};
				retrieveXstream.processAnnotations(retrieveClasses);
		String retrieveResponseXML = (String) retrieveXstream.toXML(retrieveSOResponse);
		logger.info("Leaving XStreamUtility.getRetrieveResponseXML()");
		return retrieveResponseXML;
	}
	
	
	/**
	 * This method is for the conversion of the request xml 
	 * to SearchRequest object using Xstream conversion .
	 * 
	 * @param searchXml   String
	 * @return SearchRequest
	 */
	public SOSearchRequest getSearchRequestObject(String searchXml) {
		logger.info("Entering XStreamUtility.getSearchRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] searchReqClasses = new Class[] { SOSearchRequest.class,SOSearch.class,
				Identification.class, CustomReference.class };
		xstream.processAnnotations(searchReqClasses);
		SOSearchRequest searchRequest = (SOSearchRequest) xstream.fromXML(searchXml);
		logger.info("Leaving XStreamUtility.getSearchRequestObject()");
		return searchRequest;
	}
	
	
	/**
	 * This method is for the conversion of the response object to 
	 * xml String using Xstream conversion .
	 * 
	 * @param soSearchResponse   SoSearchResponse
	 * @return searchResponseXml String
	 */
	public String getSearchResponseXML(SOSearchResponse soSearchResponse) {
		logger.info("Entering XStreamUtility.getSearchResponseXML()");		
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] searchClasses = new Class[] { SOSearchResponse.class,
				Results.class,Result.class,SearchResults.class,ErrorResult.class,
				OrderStatus.class };
		xstream.processAnnotations(searchClasses);
		String searchResponseXml = (String) xstream.toXML(soSearchResponse);
		logger.info("Leaving XStreamUtility.getSearchResponseXML()");	
		return searchResponseXml;
	}
	
	
	/**
	 * This method is for the conversion of the request xml to CancelRequest
	 * object using Xstream conversion .
	 * 
	 * @param cancelXml
	 *            String
	 * @return CancelRequest
	 */
	public SOCancelRequest getCancelRequestObject(String cancelXml) {
		logger.info("Entering XStreamUtility.getCancelRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] cancelReqClasses = new Class[] {SOCancelRequest.class, Identification.class };
		xstream.processAnnotations(cancelReqClasses);
		SOCancelRequest cancelRequest = (SOCancelRequest) xstream
				.fromXML(cancelXml);
		logger.info("Leaving XStreamUtility.getCancelRequestObject()");		
		return cancelRequest;
	}

	
	/**
	 * This method converts the cancelResponse object into a XML String 
	 * 
	 * @param SOCancelResponse cancelResponse   
	 * @return String cancelResponseXml
	 */
	public String getCancelResponseXML(SOCancelResponse cancelResponse){
		logger.info("Entering XStreamUtility.getCancelResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				SOCancelResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String cancelResponseXml = (String) xstream.toXML(cancelResponse);
		logger.info("Leaving XStreamUtility.getCancelResponseXML()");
		return cancelResponseXml;
		
	}
	

	/**
	 * This method is for the conversion of the request xml 
	 * to RescheduleRequest object using Xstream conversion .
	 * 
	 * @param soXml   String
	 * @return SoRequest
	 */
	public SORescheduleRequest getRescheduleRequestObject(String soXml) {
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] rescheduleClasses = new Class[] { SORescheduleRequest.class,
				Identification.class, SORescheduleInfo.class};
		xstream.processAnnotations(rescheduleClasses);
		SORescheduleRequest rescheduleRequest = (SORescheduleRequest) xstream.fromXML(soXml);
		logger.info("Leaving XStreamUtility.getRescheduleRequestObject()");		
		return rescheduleRequest;
	}
	
	
	/**
	 * This method converts the rescheduleResponse object into a XML String 
	 * 
	 * @param SORescheduleResponse rescheduleResponse   
	 * @return String rescheduleResponseXml
	 */
	public String getRescheduleResponseXML(SORescheduleResponse rescheduleResponse){
		logger.info("Entering XStreamUtility.getRescheduleResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				SORescheduleResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String rescheduleResponseXml = (String) xstream.toXML(rescheduleResponse);
		logger.info("Leaving XStreamUtility.getRescheduleResponseXML()");
		return rescheduleResponseXml;
		
	}

	
	/**
	 * This method is for the conversion of the request xml to PostRequest
	 * object using Xstream conversion .
	 * 
	 * @param postXml
	 *            String
	 * @return PostRequest
	 */
	public SOPostRequest getPostRequestObject(String postXml) {
		logger.info("Entering XStreamUtility.getPostRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] postReqClasses = new Class[] { SOPostRequest.class,ProviderRouteInfo.class,
				Identification.class, Languages.class ,SpecificProviders.class};
		xstream.processAnnotations(postReqClasses);
		SOPostRequest postRequest = (SOPostRequest) xstream.fromXML(postXml);
		logger.info("Leaving XStreamUtility.getPostRequestObject()");
		return postRequest;
	}

	
	/**
	 * This method is for the conversion of the postResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param postResponse  SOPostResponse
	 * @return postResponseXml
	 */
	public String getPostResponseXML(SOPostResponse postResponse) {
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] postResponseClasses = new Class[] {
				SOPostResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(postResponseClasses);
		String postResponseXml = (String) xstream.toXML(postResponse);
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");		
		return postResponseXml;
	}
	
	
	/**
	 * This method is for the conversion of the request xml to AddNoteRequest
	 * object using Xstream conversion .
	 * 
	 * @param addNoteXml String
	 * @return AddNoteRequest
	 */
	public SOAddNoteRequest getAddNoteRequestObject(String addNoteXml) {
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] addNoteReqClasses = new Class[] { SOAddNoteRequest.class,
				Identification.class, NewNoteType.class };
		xstream.processAnnotations(addNoteReqClasses);
		SOAddNoteRequest addNoteRequest = (SOAddNoteRequest) xstream
				.fromXML(addNoteXml);
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");		
		return addNoteRequest;
	}

	
	/**
	 * This method is for the conversion of the AddNoteResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param soAddNoteResponse   AddNoteResponse
	 * @return addNoteResponseXml
	 */
	public String getAddNoteResponseXML(SOAddNoteResponse soAddNoteResponse) {
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] addNoteResponseClasses = new Class[] {
				SOAddNoteResponse.class, Results.class,Result.class,
				ErrorResult.class,};
		xstream.processAnnotations(addNoteResponseClasses);
		String addNoteResponseXml = (String) xstream.toXML(soAddNoteResponse);
		logger.info("Entering XStreamUtility.getRescheduleRequestObject()");		
		return addNoteResponseXml;
	}
	

	/**
	 * This method is for the conversion of the SkillTree Response  object 
	 * to an Xml using Xstream conversion .
	 * 
	 * @param skillTreeResponse   SkillTreeResponse
	 * @return String
	 */
	public String getskillTreeResponseXML(SkillTreeResponse skillTreeResponse) {
		logger.info("Entering XStreamUtility.getskillTreeResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] skillTreeResponseClasses = new Class[] {
				SkillTreeResponse.class, Categories.class,Category.class,
				ErrorResult.class};
		xstream.processAnnotations(skillTreeResponseClasses);
		String skillTreeResponseXml = (String) xstream.toXML(skillTreeResponse);
		logger.info("Exiting XStreamUtility.getskillTreeResponseXML()");		
		return skillTreeResponseXml;
	}

	/**
	 * This method is for the conversion of the ProviderResults Response  object 
	 * to an Xml using Xstream conversion .
	 * 
	 * @param providerResults   ProviderResults
	 * @return String
	 */
	public String getProviderProfiles(ProviderResults providerResults) {
		logger.info("Entering XStreamUtility.getSearchProviderByProfileId()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] proByZipProfileIdResponseClasses = new Class[] {
				ProviderResults.class, ProviderType.class,
				ErrorResult.class};
		xstream.processAnnotations(proByZipProfileIdResponseClasses);
		String proByProfileIdResponseXml = (String) xstream.toXML(providerResults);
		logger.info("Exiting XStreamUtility.getSearchProviderByProfileId()");		
		return proByProfileIdResponseXml;
	}
	
	/**
	 * This method is for the conversion of the walletHistory Response  object 
	 * to an Xml using Xstream conversion .
	 * 
	 * @param walletHistoryResponse   WalletHistoryResponse
	 * @return String
	 */
	public String getWalletHistoryResponseXML(WalletHistoryResponse walletHistoryResponse) {
		logger.info("Entering XStreamUtility.getWalletHistoryResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] walletHistoryResponseClasses = new Class[] {
				WalletHistoryResponse.class, WalletHistoryResults.class,TransactionDetail.class,
				ErrorResult.class};
		xstream.processAnnotations(walletHistoryResponseClasses);
		String walletHistoryResponseXml = (String) xstream.toXML(walletHistoryResponse);
		if(logger.isInfoEnabled()) logger.info("Exiting XStreamUtility.getWalletHistoryResponseXML()");		
		return walletHistoryResponseXml;
	}		
	
	public String getFundingSource(GetFundingSourceResponse fundingSource) {
		logger.info("Entering XStreamUtility.getFundingSource()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		String fundingSourceResponseXml = (String) xstream.toXML(fundingSource);
		logger.info("Exiting XStreamUtility.getFundingSource()");		
		return fundingSourceResponseXml;
	}

	public String createFundingSourceRequest(CreateFundingSourceRequest fundingSource) {
		logger.info("Entering XStreamUtility.createFundingSource");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		String fundingSourceResponseXml = (String) xstream.toXML(fundingSource);
		logger.info("Exiting XStreamUtility.createFundingSource()");		
		return fundingSourceResponseXml;
	}
	
	public String createFundingSourceResponse(CreateFundingSourceResponse fundingSource) {
		logger.info("Entering XStreamUtility.createFundingSource");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		String fundingSourceResponseXml = (String) xstream.toXML(fundingSource);
		logger.info("Exiting XStreamUtility.createFundingSource()");		
		return fundingSourceResponseXml;
	}
	
	public String addFundsToWalletResponse(AddFundsToWalletResponse response) {
		logger.info("Entering XStreamUtility.addFundsToWallet");
		Class<?>[] responseClasses = new Class[] {
				AddFundsToWalletResponse.class, Results.class, Result.class,
				ErrorResult.class};
		
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.processAnnotations(responseClasses);
		
		String fundingSourceResponseXml = (String) xstream.toXML(response);
		logger.info("Exiting XStreamUtility.addFundsToWallet()");		
		return fundingSourceResponseXml;
	}

	/**
	 * This method is for the conversion of the request xml 
	 * to docUploadRequest object using Xstream conversion .
	 * 
	 * @param docXml String
	 * @return docUploadRequest
	 */
	public DocUploadRequest getUploadRequestObject(String docXml){
		logger.info("Entering XStreamUtility.getUploadRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] { 
				DocUploadRequest.class};
		xstream.processAnnotations(createClasses);
		DocUploadRequest docUploadRequest = (DocUploadRequest)
				xstream.fromXML(docXml);
		logger.info("Leaving XStreamUtility.getUploadRequestObject()");		
		return docUploadRequest;
	}
	/**
	 * This method is for the conversion of the docResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param docResponse DocUploadResponse
	 * @return documentResponseXml
	 */
	public String getDocumentResponseXML(DocUploadResponse docResponse){
		logger.info("Entering XStreamUtility.getDocumentResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				DocUploadResponse.class, };
		xstream.processAnnotations(createResponseClasses);
		String documentResponseXml = (String) xstream.toXML(docResponse);
		logger.info("Leaving XStreamUtility.getDocumentResponseXML");		
		return documentResponseXml;
	}
	
	public AddFundsToWalletRequest addFundsToWalletRequest(String request) {
		logger.info("Entering XStreamUtility.addFundsToWalletRequest");
		try {
		Class<?>[] responseClasses = new Class[] {
				AddFundsToWalletRequest.class, Identification.class, ResponseFilter.class};
		
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.processAnnotations(responseClasses);
		
		AddFundsToWalletRequest obj = (AddFundsToWalletRequest) xstream.fromXML(request);
		logger.info("Exiting XStreamUtility.addFundsToWalletRequest()");		
		return obj;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * This method is for the conversion of the walletBalance Response  object 
	 * to an Xml using Xstream conversion .
	 * 
	 * @param walletBalanceResponse   WalletBalanceResponse
	 * @return String
	 */
	public String getWalletBalanceResponseXML(WalletBalanceResponse walletBalanceResponse) {
		logger.info("Entering XStreamUtility.getWalletBalanceResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] walletBalanceResponseClasses = new Class[] {
				WalletBalanceResponse.class,Results.class, Result.class, ErrorResult.class};
		xstream.processAnnotations(walletBalanceResponseClasses);
		String walletBalanceResponseXml = (String) xstream.toXML(walletBalanceResponse);
		if(logger.isInfoEnabled()) logger.info(walletBalanceResponseXml);
		if(logger.isInfoEnabled()) logger.info("Exiting XStreamUtility.getWalletBalanceResponseXML()");		
		return walletBalanceResponseXml;
	}		
	
	
	/**
	 * This method is for the conversion of the request xml 
	 * to addSODocRequest object using Xstream conversion .
	 * 
	 * @param soXml   String
	 * @return SoRequest
	 */
	public AddSODocRequest getAddSODocRequest(String soXml){
		logger.info("Entering XStreamUtility.getCreateRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] {AddSODocRequest.class,Identification.class};//ADD THE REQUEST CLASS ALSO HERE....
		xstream.processAnnotations(createClasses);
		AddSODocRequest addSODocRequest = (AddSODocRequest) xstream.fromXML(soXml);
		logger.info("Leaving XStreamUtility.getCreateRequestObject()");		
		return addSODocRequest;
	}
	/**
	 * This method is for the conversion of the addSODocResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	public String getAddSODocResponseXML(AddSODocResponse addSODocResponse){
		logger.info("Entering XStreamUtility.getCreateResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				AddSODocResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String createResponseXml = (String) xstream.toXML(addSODocResponse);
		logger.info("Leaving XStreamUtility.getCreateResponseXML()");		
		return createResponseXml;
	}

	/**
	 * This method is for the conversion of the request xml 
	 * to addSODocRequest object using Xstream conversion .
	 * 
	 * @param soXml   String
	 * @return SoRequest
	 */
	public AddSODocRequest getDeleteSODocRequest(String soXml){
		logger.info("Entering XStreamUtility.getCreateRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] {AddSODocRequest.class,Identification.class};//ADD THE REQUEST CLASS ALSO HERE....
		xstream.processAnnotations(createClasses);
		AddSODocRequest addSODocRequest = (AddSODocRequest) xstream.fromXML(soXml);
		logger.info("Leaving XStreamUtility.getCreateRequestObject()");		
		return addSODocRequest;
	}
	/**
	 * This method is for the conversion of the addSODocResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param createResponse   SOCreateResponse
	 * @return createResponseXml
	 */
	public String getDeleteSODocResponseXML(AddSODocResponse addSODocResponse){
		logger.info("Entering XStreamUtility.getCreateResponseXML()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createResponseClasses = new Class[] {
				AddSODocResponse.class, Results.class,Result.class,
				OrderStatus.class ,ErrorResult.class,};
		xstream.processAnnotations(createResponseClasses);
		String createResponseXml = (String) xstream.toXML(addSODocResponse);
		logger.info("Leaving XStreamUtility.getCreateResponseXML()");		
		return createResponseXml;
	}
	
	/**
	 * This method is responsible for converting the request xml string into 
	 * POJO object for creating accepting a counter offer. It uses Xstream for 
	 * conversion.
	 * 
	 * @param xmlString
	 * @return AcceptCounterOfferRequest
	 */
	public AcceptCounterOfferRequest getRequestObjectForAcceptOffer (String xmlString) {
		logger.info("Entering XStreamUtility.getRequestObjectForAcceptOffer()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createAcceptOfferClasses = new Class[] { AcceptCounterOfferRequest.class};
		xstream.processAnnotations(createAcceptOfferClasses);
		AcceptCounterOfferRequest counterOfferRequest=null ;
		counterOfferRequest = (AcceptCounterOfferRequest) xstream
				.fromXML(xmlString);
		logger.info("Exiting XStreamUtility.getRequestObjectForAcceptOffer()");		
		return counterOfferRequest;
	}
	

	/**
	 * This method is for the conversion of the CounterOfferResponse object 
	 * to xml using Xstream conversion .
	 * 
	 * @param offerResponse   CounterOfferResponse
	 * @return counterOfferResponseXml
	 */
	public String getResponseXMLForCounterOffer(CounterOfferResponse offerResponse){
		logger.info("Entering XStreamUtility.getResponseXMLForCounterOffer()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] offerResponseClasses = new Class[] {
				CounterOfferResponse.class, Results.class,Result.class,ErrorResult.class,};
		xstream.processAnnotations(offerResponseClasses);
		String counterOfferResponseXml = (String) xstream.toXML(offerResponse);
		logger.info("Leaving XStreamUtility.getResponseXMLForCounterOffer()");		
		return counterOfferResponseXml;
	}
	/**
	 * This method is responsible for converting the request xml string into 
	 * POJO object for getting buyerAccount details. It uses Xstream for 
	 * conversion.
	 * 
	 * @param xmlString
	 * @return AcceptCounterOfferRequest
	 */
	public String getBuyerAccountResponse(GetBuyerAccountResponse accountResponse){
		logger.info("Entering XStreamUtility.getBuyerAccountResponse()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] buyerAccountResponseClasses = new Class[] {
				GetBuyerAccountResponse.class, Results.class,Result.class,ErrorResult.class,};
		xstream.processAnnotations(buyerAccountResponseClasses);
		String buyerAccountResponseXml = (String) xstream.toXML(accountResponse);
		logger.info("Leaving XStreamUtility.getBuyerAccountResponse()");		
		return buyerAccountResponseXml;
	}

	/**
	 * This method is responsible for converting the request xml string into
	 * POJO object for creating Result. It uses Xstream for
	 * conversion.
	 *
	 * @param xmlString
	 * @return AcceptCounterOfferRequest
	 */
	public BuyerEventCallbackAckRequest getResultObjectBuyerEventCallbackAck(String xmlString) {
		logger.info("Entering XStreamUtility.getResultObjectBuyerEventCallbackAck()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new HttpResultConvertor());
		Class<?>[] createEventCallbackAckClasses = new Class[]{BuyerEventCallbackAckRequest.class};
		xstream.processAnnotations(createEventCallbackAckClasses);
		BuyerEventCallbackAckRequest buyerEventCallbackAckRequest = null;
		buyerEventCallbackAckRequest = (BuyerEventCallbackAckRequest) xstream
				.fromXML(xmlString);
		logger.info("Exiting XStreamUtility.getResultObjectBuyerEventCallbackAck()");
		return buyerEventCallbackAckRequest;
	}
}
