package com.newco.marketplace.persistence.iDao.so;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerSOTemplateDAO {
	
	public void save(BuyerSOTemplateVO vo) throws DataServiceException;
	
	public Integer update(BuyerSOTemplateVO vo) throws DataServiceException;
	
	public void delete(BuyerSOTemplateVO vo) throws DataServiceException;
	
	public BuyerSOTemplateVO load(Integer templateID) throws DataServiceException;
	
	public BuyerSOTemplateVO load(Integer buyerID, String templateName) throws DataServiceException;
	
	/**
	 * This method loads so templateds for specified buyer
	 * @param buyerID
	 * @return templateList
	 * @throws DataServiceException
	 */
	public List<BuyerSOTemplateVO> loadBuyerSoTemplates(Integer buyerID) throws DataServiceException;
	
	/**
	 * This method updates doc list in buyer so template
	 * @param buyerSOTemplateVO
	 * @return rowsUpdatedCnt
	 * @throws DataServiceException
	 */
	public Integer updateDocListInBuyerSOTemplate(BuyerSOTemplateVO vo) throws DataServiceException;
	
	/**
	 * This method loads teamplate data for the given template id & buyer id
	 * @param templateId
	 * @param buyerId
	 * @return BuyerSOTemplateVO
	 * @throws DataServiceException
	 */
	public BuyerSOTemplateVO loadBuyerSOTemplate(Integer templateId, Integer buyerId) throws DataServiceException;
}
