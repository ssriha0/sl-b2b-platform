/**
 * 
 */
package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.ASSIGN_SKILL_INFO_OBJ;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.POPUP;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.PROVIDERINFO_OBJ;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.PROVIDER_PHOTO;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.RESULT_LOAD_SKILL_SUCCESS;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.ROOT_SKILL_NODE_ID;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.SUCCESS_EXTERNAL;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.SharedSecret;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.dto.provider.ProviderInfoPagesDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.web.security.NonSecurePage;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * @author hoza
 *
 */
//Curretnlty this class is not model driven but in future when in need create this Action Model drive using the ProviderInfoPagesDto

public class ProviderInfoPagesAction extends SLBaseAction implements
		Preparable, SessionAware,ServletRequestAware {
	
	private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProviderInfoPagesAction.class.getName());
	
	
	private IProviderProfilePagesDelegate providerProfilePagesDelegate;
	
	 
	
	
	public String execute() throws Exception{
		boolean isExternal = false;
		ProviderInfoPagesDto dataDto = new ProviderInfoPagesDto();
		String google_map_key = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.GOOGLE_MAP_API_KEY);
		List <String> errorList = new ArrayList <String>();
		if (google_map_key == null)
		{
			return "fail";
		}
		
		//get the resouce id from the security context dont need Vendor Id.. we will grab from the resource
		
	/*	vendorId = (String) ActionContext.getContext().getSession().get(
		"vendorId");*/
		String popup =  (String) ServletActionContext.getRequest().getParameter(POPUP);
			if("true".equalsIgnoreCase(popup)){
				isExternal = false;
			}
			else{
				isExternal = true;
			}
		String resourceId =  (String) ServletActionContext.getRequest().getParameter(RESOURCE_ID);
		
		if(resourceId == null ) {
			//do something to return
			resourceId = (String) ActionContext.getContext().getSession().get(RESOURCE_ID);
			if(resourceId == null) {
				return "fail";
			}
			logger.debug(" could not retrive the resouceid ");
		}
		
		logger.debug(" In PorviderInfo pages " + resourceId);
		
		dataDto  = providerProfilePagesDelegate.getPublicProfile(Integer.parseInt(resourceId));
		
		
		if(dataDto != null) {
			dataDto.setIsExternal(isExternal);
			
			
			if(get_commonCriteria() != null) {
				String roleType = get_commonCriteria().getRoleType();
				if(roleType != null && roleType.equalsIgnoreCase(OrderConstants.BUYER)) {
					try {
						Integer buyerId = get_commonCriteria().getCompanyId();
						String userName = get_commonCriteria().getTheUserName();
						SharedSecret sharedSecret = new SharedSecret();
						sharedSecret.setBuyerId(buyerId);
						sharedSecret.setCreatedDate(new Date());
						sharedSecret.setIpAddress(request.getRemoteAddr());
						sharedSecret.setUserName(userName);
						String sharedSecretString = CryptoUtil.encryptObject(sharedSecret);
						dataDto.setSharedSecretString(sharedSecretString);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			
			
			CompanyProfileVO companyProfileVO = dataDto.getCompanyPublicInfo();
			if(null != companyProfileVO ){
				errorList = companyProfileVO.getErrorList();
				if(null==errorList||errorList.isEmpty()){
					dataDto.setHasErrors(false);
				}else{
					dataDto.setHasErrors(true);
				}					
			}		
		}
	
		Integer loggedInUserId = null;
		//createCommonServiceOrderCriteria();
		SecurityContext securityContext = null;
		if(getSession().getAttribute(SECURITY_KEY) != null ) {
		 securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		 loggedInUserId = securityContext.getCompanyId();
		}
		if(loggedInUserId != null){
			Boolean isSecuredInfoViewable = providerProfilePagesDelegate.isProviderNetworkTabViewable(
					loggedInUserId.intValue(), Integer.parseInt(resourceId));
			dataDto.setIsSecuredInfoViewable(isSecuredInfoViewable);
		}
		
		dataDto.setGoogleMapKey(google_map_key);
		request.setAttribute(PROVIDERINFO_OBJ, dataDto);
		if(isExternal)	return SUCCESS_EXTERNAL;
		return SUCCESS;
	}
	
	public String loadSkills() throws Exception {
		String resourceid = request.getParameter(RESOURCE_ID);
		String rootSkillNode = request.getParameter(ROOT_SKILL_NODE_ID);
		if(resourceid != null && rootSkillNode != null ) {
			//rootSkillNode = rootSkillNode.substring(1);//stip off the x
			int resourceId_int = Integer.parseInt(resourceid);
			int rootSkillNode_int = Integer.parseInt(rootSkillNode);
			//do business here to convert to 
			ResourceSkillAssignDto  skillAssignInfo = providerProfilePagesDelegate.getSkills(resourceId_int, rootSkillNode_int);
			request.setAttribute(ASSIGN_SKILL_INFO_OBJ, skillAssignInfo);
			
		}
		return RESULT_LOAD_SKILL_SUCCESS;
	}

	public ProviderInfoPagesAction(IProviderProfilePagesDelegate xproviderProfilePagesDelegate) {
		this.providerProfilePagesDelegate = xproviderProfilePagesDelegate;
		
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub

	}
	
	
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
		
	}
	
	private PublicProfileVO getMockproviderInfoVO() {
		PublicProfileVO vo = new PublicProfileVO();
		vo.setFirstName("Himanshu");
		vo.setLastName("Oza");
		return vo;
	}



	public IProviderProfilePagesDelegate getProviderProfilePagesDelegate() {
		return providerProfilePagesDelegate;
	}



	public void setProviderProfilePagesDelegate(
			IProviderProfilePagesDelegate providerProfilePagesDelegate) {
		this.providerProfilePagesDelegate = providerProfilePagesDelegate;
	}
	
	public String displayPhoto() throws Exception {
		
		String resourceid =  (String) ServletActionContext.getRequest().getParameter(RESOURCE_ID);
		ProviderDocumentVO documentVO;
		Integer max_width = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_width"));
		Integer max_height = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_height"));
		
		if (resourceid != null)
		{
			documentVO = providerProfilePagesDelegate.getPrimaryPicture(Integer.parseInt(resourceid));
			if (documentVO != null)
			{
				DocumentVO selectedImage = documentVO.getDocDetails();
				if (selectedImage != null && selectedImage.getBlobBytes() != null)
				{
					Image sourceImage = new ImageIcon(selectedImage.getBlobBytes()).getImage();
					Integer actualWidth = sourceImage.getWidth(null);
					Integer actualHeight = sourceImage.getHeight(null);

					if(actualWidth > 0 && actualHeight > 0)
					{
						if (actualWidth < max_width && actualWidth > 0)
						{
							max_width = actualWidth;
						}
						if (actualHeight < max_height && actualHeight > 0)
						{
							max_height = actualHeight;
						}

						selectedImage.setBlobBytes(DocumentUtils.resizeoImage(selectedImage.getBlobBytes(), max_width, max_height));
						SecurityChecker sc = new SecurityChecker();
						if (StringUtils.equals(selectedImage.getFormat(), "image/bmp")
								|| StringUtils.equals(selectedImage.getFormat(), "image/gif")
								|| StringUtils.equals(selectedImage.getFormat(), "image/jpeg")
								|| StringUtils.equals(selectedImage.getFormat(), "image/jpg"))
						{
							
							String format = sc.securityCheck(selectedImage.getFormat());
							ServletActionContext.getResponse().setContentType(format);
						}
						else
						{
							ServletActionContext.getResponse().setContentType("text/html");
						}

						String title = sc.securityCheck(selectedImage.getTitle());
						String header = "attachment;filename=\"" + title + "\"";
						ServletActionContext.getResponse().setHeader("Content-Disposition", header);
						InputStream in = new ByteArrayInputStream(selectedImage.getBlobBytes());
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
					else
					{
						logger.info("displayPhoto() width or height is less than zero for resourceId=" + resourceid);
					}									
				}
				else
				{
					logger.info("displayPhoto() has failed for resourceId=" + resourceid);
				}				
			}
			else
			{
				logger.info("displayPhoto() has failed for resourceId=" + resourceid);
			}
		}

		return NONE;
	}

	public String loadPhoto() throws Exception {

		try{
			ProviderDocumentVO documentVO = new ProviderDocumentVO();
			String resourceid =  (String) ServletActionContext.getRequest().getParameter(RESOURCE_ID);
			Integer max_width = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_width"));
			Integer max_height = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_height"));
			String link = (String) ServletActionContext.getRequest().getParameter("link");
			String isPopup = (String) ServletActionContext.getRequest().getParameter("isPopup");
			String link_back = new String();
			
			if ("1".equals(link))
			{
				link_back = genLinkBack1(resourceid, isPopup); 
			}
			
			 if( resourceid != null) {
				 documentVO  = providerProfilePagesDelegate.getPrimaryPicture(Integer.parseInt(resourceid));
			 }
			request.setAttribute(PROVIDER_PHOTO, documentVO);		
			request.setAttribute("max_width", max_width);		
			request.setAttribute("max_height", max_height);
			request.setAttribute("link_back", link_back);
			request.setAttribute(RESOURCE_ID, resourceid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "loadPhoto";
	}	
	
	private String genLinkBack1(String resourceId, String isPopup)
	{
		String tmpLinkBack = new String(request.getContextPath());
		tmpLinkBack = tmpLinkBack.concat("/providerProfileInfoAction_execute.action?resourceId=");
		tmpLinkBack = tmpLinkBack.concat(resourceId);
		if("true".equals(isPopup))
		{
			tmpLinkBack= tmpLinkBack.concat("&popup=true");
		}
		
		return tmpLinkBack;
	}

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();		
	}
}
