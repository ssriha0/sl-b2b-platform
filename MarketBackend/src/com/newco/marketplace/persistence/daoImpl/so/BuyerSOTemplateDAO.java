package com.newco.marketplace.persistence.daoImpl.so;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.IBuyerSOTemplateDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerSOTemplateDAO extends ABaseImplDao implements IBuyerSOTemplateDAO {

	private static final Logger logger = Logger.getLogger(BuyerSOTemplateDAO.class.getName());
	
	public void delete(BuyerSOTemplateVO vo) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public BuyerSOTemplateVO load(Integer templateID) throws DataServiceException {
		return(BuyerSOTemplateVO) queryForObject("buyerSOTemplate.query.templateId", templateID);		
	}

	public BuyerSOTemplateVO load(Integer buyerID, String templateName) throws DataServiceException {
		BuyerSOTemplateVO vo = new BuyerSOTemplateVO();
		vo.setBuyerID(buyerID);
		vo.setTemplateName(templateName);
		vo = (BuyerSOTemplateVO) queryForObject("buyerSOTemplate.query", vo);
		return vo;
	}

	public void save(BuyerSOTemplateVO vo) throws DataServiceException {
		insert("buyerSOTemplate.insert", vo);		
	}

	/**
	 * This method loads so templateds for specified buyer
	 * @param buyerID
	 * @return templateList
	 * @throws DataServiceException
	 */
	public List<BuyerSOTemplateVO> loadBuyerSoTemplates(Integer buyerID) throws DataServiceException{
		List<BuyerSOTemplateVO> templateList = null;
		logger.debug("Entering BuyerSOTemplateDAO --> loadBuyerSoTemplates()");
		try{
			templateList = (List<BuyerSOTemplateVO>)queryForList("buyerSOTemplate.query.buyerId", buyerID);			
		}catch(Exception ex){
			logger.error("Error while loading buyer so templates in BuyerSOTemplateDAO.loadBuyerSoTemplates()");
			throw new DataServiceException("Error while loading buyer so templates in BuyerSOTemplateDAO.loadBuyerSoTemplates()",ex);
		}
		logger.debug("Leaving BuyerSOTemplateDAO --> loadBuyerSoTemplates()");		
		return templateList;
	}

	public Integer update(BuyerSOTemplateVO vo) throws DataServiceException {
		logger.debug("Entering BuyerSOTemplateDAO --> update()");
		int rowsUpdated = update("buyerSOTemplate.update", vo);
		logger.debug("Leaving BuyerSOTemplateDAO --> update()");
		
		return rowsUpdated;
	}
	
	/**
	 * This method updates doc list in buyer so template
	 * @param buyerSOTemplateVO
	 * @return rowsUpdatedCnt
	 * @throws DataServiceException
	 */
	public Integer updateDocListInBuyerSOTemplate(BuyerSOTemplateVO buyerSOTemplateVO) throws DataServiceException{
		int rowsUpdatedCnt = 0;
		try{
			rowsUpdatedCnt = update("buyerSOTemplateDocList.update", buyerSOTemplateVO);		
		}catch(Exception ex){
			logger.error("Error while updating so template doclist in BuyerSOTemplateDAO.updateDocListInBuyerSOTemplate()");
			throw new DataServiceException("Error while updating so template doclist in BuyerSOTemplateDAO.updateDocListInBuyerSOTemplate()",ex);
		}
		return rowsUpdatedCnt;
	}
	
	/**
	 * This method loads teamplate data for the given template id & buyer id
	 * @param templateId
	 * @param buyerId
	 * @return BuyerSOTemplateVO
	 * @throws DataServiceException
	 */
	public BuyerSOTemplateVO loadBuyerSOTemplate(Integer templateId, Integer buyerId) throws DataServiceException {
		BuyerSOTemplateVO buyerSOTemplateVO = new BuyerSOTemplateVO();
		try{
			buyerSOTemplateVO.setBuyerID(buyerId);
			buyerSOTemplateVO.setTemplateID(templateId);
			buyerSOTemplateVO = (BuyerSOTemplateVO) queryForObject("buyerSOTemplate.queryByBuyerIdAndTemplateId", buyerSOTemplateVO);
		}catch(Exception ex){
			logger.error("Error while loading buyer so templates in BuyerSOTemplateDAO.loadBuyerSOTemplate()");
			throw new DataServiceException("Error while loading buyer so templates in BuyerSOTemplateDAO.loadBuyerSOTemplate()",ex);
		}
		return buyerSOTemplateVO;
	}

	
}
