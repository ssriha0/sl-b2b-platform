package com.newco.marketplace.web.utils;

import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.dto.ajax.AjaxSPNCampaignXTO;
import com.newco.marketplace.web.dto.ajax.AjaxSPNXTO;
import com.newco.marketplace.web.dto.ajax.AjaxSelectItemDTO;
import com.newco.marketplace.web.dto.ajax.AjaxSubSelectItemDTO;

public class AjaxHelperUtil {
	
	public static List<AjaxSelectItemDTO> convertLookVoToAJAXSelect(List <LookupVO> lookList) {
		return null;
	}
	
	public static List<AjaxSelectItemDTO> convertLookVoToAJAXSelectWithSubSelect(List <LookupVO> lookList) {
		return null;
	}
	
	public static List<AjaxSubSelectItemDTO> convertLookVoToAJAXSubSelectList(List <LookupVO> lookList) {
		return null;
	}
	
	
	public static AjaxResultsDTO convertLookVoToAJAXSubSelectList(List <LookupVO> lookList, boolean hasDeepCopy) {
		AjaxSelectItemDTO selectContainer = new AjaxSelectItemDTO("-1","container","container");
		int count = 0;
		for(LookupVO lookup : lookList)	{
			selectContainer.addSubSelectItem(new AjaxSubSelectItemDTO(""+count,
																		lookup.getDescr() != null ? lookup.getDescr().trim() : "N/A",
																		lookup.getId() != null ? lookup.getId().toString() : "-1" ));
			count++;
		}
		AjaxResultsDTO resultsDTO = new AjaxResultsDTO();
		resultsDTO.addAjaxAbleItems(selectContainer);
		return resultsDTO;
	}
	
	
	public static AjaxResultsDTO serializeSPNCampaign(List <SPNCampaignVO> list, boolean hasDeepCopy) {
		AjaxSPNXTO spnNetwork = new AjaxSPNXTO();
		for(SPNCampaignVO campaign : list) {
			spnNetwork.addAjaxAbleItems(new AjaxSPNCampaignXTO(campaign));
		}
		AjaxResultsDTO resultsDTO = new AjaxResultsDTO();
		resultsDTO.addAjaxAbleItems(spnNetwork);
		return resultsDTO;
	}
	

}
