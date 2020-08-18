package com.newco.marketplace.web.action.memberOffer;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.jsoup.Jsoup;
import javax.servlet.ServletOutputStream;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferDetailsVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOffersCriteriaVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.servicelive.memberOffer.services.MemberOfferService;
import com.newco.marketplace.dto.vo.DocumentVO;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import javax.activation.MimetypesFileTypeMap;

public class MemberOfferAction extends SLBaseAction {
	Logger logger = Logger.getLogger(MemberOfferAction.class);
	private static final long serialVersionUID = 1L;
	private MemberOfferService memberOfferService;
	private static final String SORT_CRITERIA = "sortCriteria";
	private static final String PER_PAGE_OFFERS = "offerCount";
	private static final String CURRENT_PAGE_NO = "currentPageNo";
	private static final String OFFER_IMAGE_URL = "imageURL";
	private static final String OFFER_VIEW ="offerview";
	private static final int DEFAULT_PAGE_NO =1;
	private static final int DEFAULT_SORT_CRITERIA =1;
	private static final int DEFAULT_PER_PAGE_OFFERS=6;
	private static final String DEFAULT_OFFER_VIEW="tree";
	private static final String LOAD_PREVIOUS_PAGE_LINK="loadPreviousPage";
	private static final String DEFAULT_OFFER_URL_125 = "resources/images/no_image_125_125.png";
	private static final String DEFAULT_OFFER_URL_250="resources/images/no_image_250_250.png";
	private static final int OFFER_DESCRIPTION_TREE_VIEW_LENGTH = 75;
	private static final int OFFER_DESCRIPTION_VERTICAL_VIEW_LENGTH =250;
	private static final int DEAL_OF_DAY_DESCRIPTION_LENGTH = 275;
	private static final int DEFAULT_SIZE_250=250;
	private static final int DEFAULT_SIZE_125=125;
	private static final int DEAL_OF_DAY_TITLE_LENGTH = 16;
	private static final int TREE_VIEW_TITLE_LENGTH = 16;
	private static final int VERTICAL_VIEW_TITLE_LENGTH = 22;
	private static final int OFFER_DETAILS_COMPANY_NAME_LENGTH=35;
	private static final int POPUP_COMPANY_NAME_LENGTH=43;
	private static final String EXCLUDED_HTML_TAGS="<br>|<br/>|<div>|</div>|<head>|</head>|<li>|</li>|<hr>|</hr>|<p>|</p>|<ul>|</ul>";

	public MemberOfferAction() {
		// do nothing
	}
/**
 * Method to set different criteria for member offer fetch.This method sets values for sorting and pagination.
 * 
 */
	public String fetchAllOffers() {
		MemberOffersCriteriaVO memberOfferVO = new MemberOffersCriteriaVO();
		List<MemberOfferVO> offerList = new ArrayList<MemberOfferVO>();
		MemberOfferVO dealOfTheDay = new MemberOfferVO();
		int sortCriteria;
		int perPageOffers;
		int currentPageNo;
		String offerView="";
		if (StringUtils.isNotBlank(getRequest().getParameter(LOAD_PREVIOUS_PAGE_LINK)) && ((getRequest().getParameter(LOAD_PREVIOUS_PAGE_LINK)).equalsIgnoreCase("true"))){
			memberOfferVO = (MemberOffersCriteriaVO)getSession().getAttribute("offerCriterias");
		}else{
			if (StringUtils.isNotBlank(getRequest().getParameter(SORT_CRITERIA))) {
				sortCriteria = Integer.parseInt(getRequest().getParameter(
						SORT_CRITERIA));
				memberOfferVO.setSortCriteria(sortCriteria);
			} else {
				memberOfferVO.setSortCriteria(DEFAULT_SORT_CRITERIA);
			}
			if (StringUtils.isNotBlank(getRequest().getParameter(PER_PAGE_OFFERS))) {
				perPageOffers = Integer.parseInt(getRequest().getParameter(
						PER_PAGE_OFFERS));
				memberOfferVO.setPerPageOfferCount(perPageOffers);
			} else {
				memberOfferVO.setPerPageOfferCount(DEFAULT_PER_PAGE_OFFERS);
			}
			if (StringUtils.isNotBlank(getRequest().getParameter(CURRENT_PAGE_NO))) {
				currentPageNo = Integer.parseInt(getRequest().getParameter(
						CURRENT_PAGE_NO));
				memberOfferVO.setCurrentPageNo(currentPageNo);
			} else {
				memberOfferVO.setCurrentPageNo(DEFAULT_PAGE_NO);
			}
			if (StringUtils.isNotBlank(getRequest().getParameter(OFFER_VIEW))) {
				offerView = getRequest().getParameter(OFFER_VIEW);
				memberOfferVO.setOfferView(offerView);
			} else {
				memberOfferVO.setOfferView(DEFAULT_OFFER_VIEW);
			}
		}
		dealOfTheDay = getDealOfTheDay();
	    dealOfTheDay.setDescription(removeHTML(dealOfTheDay.getDescription()));
		offerList = getFilteredOffers(memberOfferVO);
		int totalOfferCount = getTotalOfferCount();
		memberOfferVO.setTotalNumberOfPages(getTotalPageCount(memberOfferVO.getPerPageOfferCount(),totalOfferCount));
		getSession().setAttribute("totalOfferCount", totalOfferCount);
		getRequest().setAttribute("dealOfDay", dealOfTheDay);
		getRequest().setAttribute("offerList", offerList);
		getSession().setAttribute("offerCriterias", memberOfferVO);
		return SUCCESS;

	}
	
	/**
	 * This method decides the total number of pages based on the per page offer count.
	 * @param perPageOfferCount
	 * @param totalOfferCount
	 * @return totalPages
	 */
	
	private int getTotalPageCount(int perPageOfferCount,int totalOfferCount){
		int totalPages = 0;
		if(totalOfferCount < perPageOfferCount)
			totalPages = 1;
		else if((totalOfferCount%perPageOfferCount)>0)
			totalPages = (totalOfferCount / perPageOfferCount)+1;
		else
			totalPages = (totalOfferCount / perPageOfferCount);
		
		return totalPages;
	}
	
	/**
	 * This method gives the total count of member offers.
	 * @return totalPageCount
	 */
	private Integer getTotalOfferCount(){
		Integer totalPageCount = 0;
		try{
			totalPageCount =  memberOfferService.getTotalOfferCount();
			
		}catch (BusinessServiceException bse){
			logger.error("Exception in MemberOfferAction --> getTotalPageNo() due to"
					+ bse.getMessage());
		}
		return totalPageCount;
	}
	
	/**
	 * This method identifies the offer of the day.It selects the offer whose deal_of_day_ind is 1.
	 * If none is marked as 1 then returns the offer with highest click count.
	 * @return
	 */
	private MemberOfferVO getDealOfTheDay(){
		MemberOfferVO dealOfTheDay = new MemberOfferVO();
		try{
			dealOfTheDay =  memberOfferService.getDealOfTheDay();
			processOfferDescriptionAndTitle(dealOfTheDay,DEAL_OF_DAY_DESCRIPTION_LENGTH,DEAL_OF_DAY_TITLE_LENGTH);
		}catch (BusinessServiceException bse) {
			logger.error("Exception in MemberOfferAction --> getDealOfTheDay()"
					+ bse.getMessage());
		}
		return dealOfTheDay;
	}

/**
 * This method returns the offer list after applying the criteria given in  criteriaVO
 * @param criteriaVO
 * @return memberOfferList
 */
	private List<MemberOfferVO> getFilteredOffers(
			MemberOffersCriteriaVO criteriaVO) {
		List<MemberOfferVO> memberOfferList = new ArrayList<MemberOfferVO>();
		int descLength = 0;
		int titleLength = 0;
		try {
			memberOfferList = memberOfferService.getFilteredOffers(criteriaVO);
			if(criteriaVO.getOfferView().equals(DEFAULT_OFFER_VIEW)){
				descLength = OFFER_DESCRIPTION_TREE_VIEW_LENGTH;
				titleLength = TREE_VIEW_TITLE_LENGTH;
			}else{
				descLength = OFFER_DESCRIPTION_VERTICAL_VIEW_LENGTH;
				titleLength = VERTICAL_VIEW_TITLE_LENGTH;
			}
				
			if (null != memberOfferList) {
				for (MemberOfferVO memberOffer : memberOfferList) {
					processOfferDescriptionAndTitle(memberOffer,descLength,titleLength);
			    	memberOffer.setDescription(removeHTML(memberOffer.getDescription()));
				}
			}
		} catch (BusinessServiceException bse) {
			logger.error("Exception in MemberOfferAction --> getFilteredOffers() due to"
					+ bse.getMessage());
		}
		return memberOfferList;
	}
	
	/**
	 * This method trims the offer description and title to make it fit to the page.
	 * @param offerVO
	 * @param descLength
	 * @param titleLength
	 */
	
	private void processOfferDescriptionAndTitle(MemberOfferVO offerVO,int descLength,int titleLength){
		if(null != offerVO){
			String desc = offerVO.getDescription();
			String title = offerVO.getCompanyName();
			if(null != desc){
				if(desc.length() >=  descLength)
					offerVO.setDescription(desc.substring(0, descLength)+"...");
			}
			if(null != title){
				if(title.length() > titleLength)
					offerVO.setCompanyName(title.substring(0, titleLength)+"...");	
			}
		}
	}
	/**
	 * This method trims the companyname to make it fit to the page.
	 * @param offerDetaisVO
	 * @param offerDetailsCompanyName
	 * @param popUpCompanyName
	 */
	public void SetCompanyNameForOfferDetailsAndPopUp(
			MemberOfferDetailsVO offerDetaisVO,
			int offerDetailsCompanyNameLength, int popUpCompanyNameLength) {
		String companyName = offerDetaisVO.getCompanyName();
		String trimedCompanyName = null;
		if (null != offerDetaisVO) {
			if (companyName.length() > popUpCompanyNameLength) {
				trimedCompanyName = companyName.substring(0,
						offerDetailsCompanyNameLength) + "...";
				offerDetaisVO.setOfferDetailsCompanyName(trimedCompanyName);
				trimedCompanyName = companyName.substring(0,
						popUpCompanyNameLength) + "...";
				offerDetaisVO.setPopUpCompanyName(trimedCompanyName);
			} else if (companyName.length() > offerDetailsCompanyNameLength) {
				trimedCompanyName = companyName.substring(0,
						popUpCompanyNameLength) + "...";
				offerDetaisVO.setOfferDetailsCompanyName(trimedCompanyName);
				offerDetaisVO.setPopUpCompanyName(companyName);
				
			} else {
				offerDetaisVO.setOfferDetailsCompanyName(companyName);
				offerDetaisVO.setPopUpCompanyName(companyName);
			}
		}
	}

	/**
	 * This method loads the member offer images from the respected urls.
	 * @return
	 * @throws IOException
	 */
	
	public String retrieveOfferImage() throws IOException{		
		String offerURL =  (String) ServletActionContext.getRequest().getParameter(OFFER_IMAGE_URL);
		Integer max_width = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_width"));
		Integer max_height = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_height"));
		File file = new File(offerURL);
		try {
			if (StringUtils.isBlank(offerURL)) {
				if (null != max_width && null != max_height) {
					if (max_width.intValue() == DEFAULT_SIZE_125
							&& max_height.intValue() == DEFAULT_SIZE_125) {
						offerURL = DEFAULT_OFFER_URL_125;
					} else if (max_width.intValue() == DEFAULT_SIZE_250
							&& max_height.intValue() == DEFAULT_SIZE_250) {
						offerURL = DEFAULT_OFFER_URL_250;
					}
					ClassPathResource classRes = new ClassPathResource(offerURL);
					file = classRes.getFile();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizeImage(file,max_width,max_height);
		return NONE;
	
	}
	
	/**
	 * This method resizes an image to the given width and height. 
	 * @param file
	 * @param maxWidth
	 * @param maxHeight
	 * @throws IOException
	 */
	
	private void resizeImage(File file,Integer maxWidth,Integer maxHeight) throws IOException{
		try{
		//"/aapl/sl/member_offer_widget_image.png";
		//C:\\temp\\testCreated1.txt
		Integer max_width =maxWidth;
		Integer max_height=maxHeight;
		DocumentVO offerImageObj = new DocumentVO();   
                           
            //Converts the file to byte[] and save in DocumentVO
            offerImageObj.setBlobBytes(FileUtils.readFileToByteArray(file));
            offerImageObj.setDocument(file);
            offerImageObj.setFormat(getFileMimeType(file));
            Image sourceImage = new ImageIcon(offerImageObj.getBlobBytes()).getImage();
            Integer actualWidth = sourceImage.getWidth(null);
			Integer actualHeight = sourceImage.getHeight(null);
			if(actualWidth > 0 && actualHeight > 0)
			{
				if(!(max_width.equals(actualWidth)&& max_height.equals(actualHeight)))
					offerImageObj.setBlobBytes(DocumentUtils.scaleImage(offerImageObj.getBlobBytes(), max_width, max_height));
				SecurityChecker sc = new SecurityChecker();
				if(null!=offerImageObj.getFormat()){
					String format = sc.securityCheck(offerImageObj.getFormat());
					ServletActionContext.getResponse().setContentType(format);
				}
				InputStream in = new ByteArrayInputStream(offerImageObj.getBlobBytes());
				ServletOutputStream outs = ServletActionContext.getResponse().getOutputStream();
				int bit = 256;
				while ((bit) >= 0)
				{
					bit = in.read();
					outs.write(bit);
				}
				outs.flush();
				outs.close();
				in.close();
			}
		} catch (FileNotFoundException filenotFound) {
			String fileNotFoundURL = null;
			if (null != maxWidth && null != maxHeight) {
			if (maxWidth.intValue() == DEFAULT_SIZE_125 && maxHeight.intValue() == DEFAULT_SIZE_125) {
				fileNotFoundURL = DEFAULT_OFFER_URL_125;
			} else if (maxWidth.intValue() == DEFAULT_SIZE_250
					&& maxHeight.intValue() == DEFAULT_SIZE_250) {
				fileNotFoundURL = DEFAULT_OFFER_URL_250;
			}

			ClassPathResource classRes = new ClassPathResource(fileNotFoundURL);
			file = classRes.getFile();
			Integer size = maxWidth;
			resizeImageForFileNotFound(file, size);
			}
		} catch (Exception e) {
			logger.error("Exception in MemberOfferAction --> resizeImage()" + e);
		}
	}
	
	/**
	 * This method takes the file as input and resizes the to the given size
	 * @param file
	 * @param size
	 */
	private void resizeImageForFileNotFound(File file, Integer size) {
		DocumentVO offerImageObj = new DocumentVO();
		try {
			offerImageObj.setBlobBytes(FileUtils.readFileToByteArray(file));
			offerImageObj.setDocument(file);
			offerImageObj.setFormat(getFileMimeType(file));
			SecurityChecker sc = new SecurityChecker();
			if(size>0){
				if (null != offerImageObj.getFormat()) {
				String format = sc.securityCheck(offerImageObj.getFormat());
				ServletActionContext.getResponse().setContentType(format);
			}
			InputStream in = new ByteArrayInputStream(
					offerImageObj.getBlobBytes());
			ServletOutputStream outs = ServletActionContext.getResponse()
					.getOutputStream();
			int bit = 256;
			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();} 
			}catch (IOException e) {
			logger.error("Exception in MemberOfferAction --> resizeImageForFileNotFound()" + e);

		}

	}

	/**
	 * This method retrieves the mine type of the file
	 * @param file
	 * @return
	 */
	private String getFileMimeType(File file){
		String mimeType = "";
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		mimeType = mimeTypesMap.getContentType(file);
		return mimeType;
	}
	
	
	/**
	 * fetches the  details of  the selected member offer
	 * @return
	 */
	public String displayOfferDetails() {
		String offerId=getRequest().getParameter("memberOfferId");
		if(StringUtils.isNotEmpty(offerId)){
		int memberOfferId = Integer.parseInt(offerId);
        SecurityContext securityContext=(SecurityContext) getSession().getAttribute(SECURITY_KEY);
	    int providerId = securityContext.getVendBuyerResId();
		MemberOfferDetailsVO detailsVO = new MemberOfferDetailsVO();
	    if (StringUtils.isNotEmpty(getRequest().getParameter("memberOfferId"))
				    && StringUtils.isNotEmpty(Integer.toString(providerId))){
			try {
				detailsVO = memberOfferService
						.getMemberOfferDetails(memberOfferId);
			} catch (BusinessServiceException bse) {
				logger.error("Exception in MemberOfferAction --> getMemberOfferDetails()"
						+ bse.getMessage());
			}
			SetCompanyNameForOfferDetailsAndPopUp(detailsVO,OFFER_DETAILS_COMPANY_NAME_LENGTH, POPUP_COMPANY_NAME_LENGTH);
		    detailsVO.setMainTextOne(removeHTML(detailsVO.getMainTextOne()));
			setAttribute("specificDetails", detailsVO);
			setAttribute("offerId", offerId);

			return SUCCESS;
		} else{
			return ERROR;
		}
	    }else {
			return ERROR;
		}

	}

	/**
	 * updates the view count of the member offer
	 */
	public void updateViewCount() {
		SecurityContext securityContext=(SecurityContext) getSession().getAttribute(SECURITY_KEY);
	    int providerId = securityContext.getVendBuyerResId();
	    String OfferId=getRequest().getParameter("memberOfferId");
	    if (StringUtils.isNotEmpty(getRequest().getParameter("memberOfferId"))
			    && StringUtils.isNotEmpty(Integer.toString(providerId))){
	    int memberOfferId = Integer.parseInt(OfferId);
	    try {
			memberOfferService.updateViewCount(memberOfferId, providerId);
		} catch (BusinessServiceException bse) {
			logger.error("Exception in MemberOfferAction --> updateViewCount()"
					+ bse.getMessage());

		}}
	}
	
	
	/**
	 * This method sets the criteria for sorting and pagination.
	 * @return
	 */
	public String applyFilters(){
		MemberOffersCriteriaVO memberOfferVO = new MemberOffersCriteriaVO();
		List<MemberOfferVO> memberOfferList = new ArrayList<MemberOfferVO>();
		int sortCriteria;
		int perPageOffers;
		int currentPageNo;
		String offerView;
		if (StringUtils.isNotBlank(getRequest().getParameter(SORT_CRITERIA))) {
			sortCriteria = Integer.parseInt(getRequest().getParameter(
					SORT_CRITERIA));
			memberOfferVO.setSortCriteria(sortCriteria);
		} 
		if (StringUtils.isNotBlank(getRequest().getParameter(PER_PAGE_OFFERS))) {
			perPageOffers = Integer.parseInt(getRequest().getParameter(
					PER_PAGE_OFFERS));
			memberOfferVO.setPerPageOfferCount(perPageOffers);
		}
		if (StringUtils.isNotBlank(getRequest().getParameter(CURRENT_PAGE_NO))) {
			currentPageNo = Integer.parseInt(getRequest().getParameter(
					CURRENT_PAGE_NO));
			memberOfferVO.setCurrentPageNo(currentPageNo);
		}
		if (StringUtils.isNotBlank(getRequest().getParameter(OFFER_VIEW))) {
			offerView = getRequest().getParameter(OFFER_VIEW);
			memberOfferVO.setOfferView(offerView);
		}
		memberOfferList = getFilteredOffers(memberOfferVO);
		int totalOfferCount = (Integer)getSession().getAttribute("totalOfferCount");
		memberOfferVO.setTotalNumberOfPages(getTotalPageCount(memberOfferVO.getPerPageOfferCount(),totalOfferCount));
		getRequest().setAttribute("offerList", memberOfferList);
		getSession().setAttribute("offerCriterias", memberOfferVO);
		return SUCCESS;
	}
	/**
	This method removes the html tags from the string
	* @param String
	* @return String
	**/
	public String removeHTML(String htmlString) {
		Pattern pattern = null;
		Matcher matcher = null;
		String regex = EXCLUDED_HTML_TAGS;
		pattern = pattern.compile(regex);
		htmlString = pattern.matcher(htmlString).replaceAll("");
		return htmlString;
	}
	
	
	public MemberOfferService getMemberOfferService() {
		return memberOfferService;
	}

	public void setMemberOfferService(MemberOfferService memberOfferService) {
		this.memberOfferService = memberOfferService;
	}
	
}
