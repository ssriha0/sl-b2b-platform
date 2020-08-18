package com.newco.marketplace.business.businessImpl.serviceorder;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.dto.BuyerSOTemplateContactDTO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplatePartDTO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.newco.marketplace.persistence.iDao.so.IBuyerSOTemplateDAO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import com.newco.marketplace.exception.core.BusinessServiceException;

public class BuyerSOTemplateBO implements IBuyerSOTemplateBO {
	
	private static final Logger logger = Logger.getLogger(BuyerSOTemplateBO.class);

	private IBuyerSOTemplateDAO templateDAO;
	private ContactDao contactDao;
	
	/**
	 * Convert the buyer so template into XML using extreme
	 */
	public String getBuyerSOTemplateDTOAsXML(BuyerSOTemplateDTO dto) {
		XStream xstream = new XStream();
		xstream.alias("buyerTemplate", BuyerSOTemplateDTO.class);
		xstream.alias("contact", BuyerSOTemplateContactDTO.class);
		xstream.alias("part", BuyerSOTemplatePartDTO.class);
		String xml = xstream.toXML(dto);
		return xml;
	}

	/**
	 * Convert a string of XML representing a BuyerSOTemplateDTO from XML to the object using xstream
	 */
	public BuyerSOTemplateDTO getBuyerSOTemplateXMLAsDTO(String xml) {
		
		XStream xstream = new XStream(new DomDriver());
		BuyerSOTemplateDTO dto = null;
		try {
			xstream.alias("buyerTemplate", BuyerSOTemplateDTO.class);
			xstream.alias("contact", BuyerSOTemplateContactDTO.class);
			xstream.alias("part", BuyerSOTemplatePartDTO.class);
			dto = (BuyerSOTemplateDTO)xstream.fromXML(xml);
			Integer altContactId = dto.getAltBuyerContactId();
			Contact contact = null;
			if(altContactId != null && altContactId > 0) {
				contact = getContactDao().query(altContactId);		
				BuyerSOTemplateContactDTO contactDTO = mapContactVoAlternateContactVO(contact);
				dto.setAltBuyerContact(contactDTO);
			}
		} catch (DataServiceException e) {
			logger.error("Exception loading alternate buyer contact");
		}
		return dto;
	}

	private BuyerSOTemplateContactDTO mapContactVoAlternateContactVO(Contact contact){
		BuyerSOTemplateContactDTO buyerSOTemplateContactDTO = null;
		if(contact != null){
			buyerSOTemplateContactDTO = new BuyerSOTemplateContactDTO();
			
			String str = contact.getCity()+","+contact.getStateCd()+","+contact.getZip();
			String individualName = contact.getFirstName() + " " + contact.getLastName();
			buyerSOTemplateContactDTO.setCityStateZip(str);		
			buyerSOTemplateContactDTO.setIndividualName(individualName);
			buyerSOTemplateContactDTO.setCompanyName(contact.getBusinessName());
			buyerSOTemplateContactDTO.setEmail(contact.getEmail());
			buyerSOTemplateContactDTO.setFax(contact.getFaxNo());			
			buyerSOTemplateContactDTO.setPager(contact.getPagerText());			
			buyerSOTemplateContactDTO.setPhoneHome(contact.getHomeNo());
			buyerSOTemplateContactDTO.setPhoneMobile(contact.getCellNo());
			buyerSOTemplateContactDTO.setPhonePrimary(contact.getPhoneNo());			
			buyerSOTemplateContactDTO.setStreetAddress(contact.getStreet_1());
			buyerSOTemplateContactDTO.setStreetAddress2(contact.getStreet_2());	
			buyerSOTemplateContactDTO.setResourceId(contact.getResourceId());
		}
		return buyerSOTemplateContactDTO;
	}

	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer buyerID, String templateName) {
		BuyerSOTemplateDTO dto = null;
		try {
			BuyerSOTemplateVO vo = getTemplateDAO().load(buyerID, templateName);
			if (vo != null) {
				dto = getBuyerSOTemplateXMLAsDTO(vo.getTemplateData());
			}
		} catch (DataServiceException e) {
			logger.error("Error loading template", e);
		}
		return dto;
	}

	public Boolean saveBuyerSOTemplate(Integer buyerID, String name, BuyerSOTemplateDTO dto) {
		Boolean success = new Boolean(true);
		String xml = getBuyerSOTemplateDTOAsXML(dto);
		BuyerSOTemplateVO vo = new BuyerSOTemplateVO();
		vo.setBuyerID(buyerID);
		vo.setMainServiceCategory(Integer.parseInt(dto.getMainServiceCategory()));
		vo.setTemplateData(xml);
		vo.setTemplateName(name);
		
		try {
			getTemplateDAO().save(vo);
		} catch (DataServiceException e) {
			success = new Boolean(false);
			logger.error("Error saving template", e);
		} catch (Exception ex) {
			success = new Boolean(false);
			logger.error("Error saving template", ex);
		}
		
		return success;
	}

	public Boolean updateBuyerSOTemplate(Integer buyerID, String name, BuyerSOTemplateDTO dto) {
		Boolean success = new Boolean(true);
		try{
			String xml = getBuyerSOTemplateDTOAsXML(dto);
			BuyerSOTemplateVO vo = new BuyerSOTemplateVO();
			vo.setBuyerID(buyerID);
			vo.setMainServiceCategory(Integer.parseInt(dto.getMainServiceCategory()));
			vo.setTemplateData(xml);
			
			if(StringUtils.isNotBlank(name)){
				vo.setTemplateName(name.trim());
			}
			
			getTemplateDAO().update(vo);
		}catch(DataServiceException e){
			success = new Boolean(false);
			logger.error("Error updating template", e);
		}
		return success;
	}
	
	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer templateId) {
		BuyerSOTemplateDTO dto = null;
		try {
			BuyerSOTemplateVO vo = getTemplateDAO().load(templateId);
			if (vo != null) {
				dto = getBuyerSOTemplateXMLAsDTO(vo.getTemplateData());
			}
		} catch (DataServiceException e) {
			logger.error("Error loading template", e);
		}
		return dto;
	}
	
	/**
	 * This method loads teamplate data for the given template id & buyer id
	 * @param templateId
	 * @param buyerId
	 * @return BuyerSOTemplateVO
	 * @throws BusinessServiceException
	 */
	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer templateId,Integer buyerId) throws BusinessServiceException{
		BuyerSOTemplateDTO buyerSOTemplateDTO = null;
		try {
			BuyerSOTemplateVO buyerSOTemplateVO = getTemplateDAO().loadBuyerSOTemplate(templateId,buyerId);
			if (buyerSOTemplateVO != null) {
				buyerSOTemplateDTO = getBuyerSOTemplateXMLAsDTO(buyerSOTemplateVO.getTemplateData());
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException("Error in BuyerSOTemplateBO.loadBuyerSOTemplate() method",dse);
		}
		return buyerSOTemplateDTO;
	}
	
	
	/**
	 * This method updates docList in so template, when document is deleted from Document Manager
	 * @param buyerId
	 * @param docTitle
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void updateDocListInBuyerSoTemplate(Integer buyerId,String docTitle) throws BusinessServiceException {
		List<BuyerSOTemplateVO> buyerSOTemplates = null;
		BuyerSOTemplateDTO buyerSOTemplateDTO = null;
		try {
			buyerSOTemplates= getTemplateDAO().loadBuyerSoTemplates(buyerId);
			if(null != buyerSOTemplates && !buyerSOTemplates.isEmpty()){
				for(BuyerSOTemplateVO buyerSOTemplateVO : buyerSOTemplates){
					buyerSOTemplateDTO = getBuyerSOTemplateXMLAsDTO(buyerSOTemplateVO.getTemplateData());
					List<String> documentTitles = buyerSOTemplateDTO.getDocumentTitles();
					if(null != documentTitles && !documentTitles.isEmpty()){
						if(documentTitles.contains(docTitle)){
							documentTitles.remove(docTitle);
							updateBuyerSOTemplate(buyerId,buyerSOTemplateDTO,buyerSOTemplateVO);						
						}
					}
				}
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException("Error in BuyerSOTemplateBO.updateDocListInBuyerSoTemplate() method",dse);
		}
	}
	
	/**
	 * This method updates docList in buyer so templatte
	 * @param buyerId
	 * @param buyerSOTemplateDTO
	 * @param buyerSOTemplateVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void updateBuyerSOTemplate(Integer buyerId,BuyerSOTemplateDTO buyerSOTemplateDTO,BuyerSOTemplateVO buyerSOTemplateVO) throws BusinessServiceException{
		String templateXml = getBuyerSOTemplateDTOAsXML(buyerSOTemplateDTO);
		BuyerSOTemplateVO soTemplateVo = new BuyerSOTemplateVO();
		soTemplateVo.setBuyerID(buyerId);
		soTemplateVo.setTemplateName(buyerSOTemplateVO.getTemplateName());
		soTemplateVo.setTemplateData(templateXml);
		try {
			int updateCnt = getTemplateDAO().updateDocListInBuyerSOTemplate(soTemplateVo);
		} catch (DataServiceException dse) {
			throw new BusinessServiceException("Error while updating template docList in BuyerSOTemplateBO.updateBuyerSOTemplate() method",dse);
		}
	}
	
	public Boolean deleteBuyerSOTemplate(Integer buyerID, BuyerSOTemplateDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IBuyerSOTemplateDAO getTemplateDAO() {
		return templateDAO;
	}

	public void setTemplateDAO(IBuyerSOTemplateDAO templateDAO) {
		this.templateDAO = templateDAO;
	}

	/**
	 * @return the contactDao
	 */
	public ContactDao getContactDao() {
		return contactDao;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

}
