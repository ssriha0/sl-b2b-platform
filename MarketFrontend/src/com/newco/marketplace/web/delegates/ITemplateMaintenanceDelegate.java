package com.newco.marketplace.web.delegates;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.web.dto.TemplateMaintenanceDTO;

public interface ITemplateMaintenanceDelegate {

	public List<DocumentVO> getBuyerDocuments(Integer buyerId);
	
	public boolean saveTemplate(Integer buyerId, TemplateMaintenanceDTO templateMaintenanceDTO);
	
	public boolean updateTemplate(Integer buyerId, TemplateMaintenanceDTO templateMaintenanceDTO);
	
	public BuyerSOTemplateDTO getTemplateDetails(Integer templateId);
	
	public Map<Integer, String> getBuyerLogoDocuments(Integer buyerId, Integer categoryId, Integer roleId, Integer userId);

}
