package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.complete.BuyerRef;
import com.newco.marketplace.api.beans.so.complete.BuyerRefs;
import com.newco.marketplace.api.beans.so.complete.PartList;
import com.newco.marketplace.api.beans.so.complete.Parts;
import com.newco.marketplace.api.beans.so.complete.SOCompleteSOResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class SOCompleteMapper {

	
	public List<Part> mapPartsRequest(Parts parts){
		List<Part> returnPartList = null;
		List<PartList> partList = null;
		if(null != parts){
			partList = parts.getPart(); 
			returnPartList = new ArrayList<Part>();
			if(null!=partList && partList.size()!=0){
				for(PartList partListData : partList){
					Part partBI = new Part();
					partBI.setManufacturer(partListData.getManufacturer());
					Carrier carrier = new Carrier();
					carrier.setCarrierName(partListData.getReturnCarrier());
					carrier.setTrackingNumber(partListData.getReturnTrackingNumber());
					partBI.setPartId(Integer.parseInt(partListData.getPartsId()));
					partBI.setReturnCarrier(carrier);
					returnPartList.add(partBI);
				}
			}
		
		}
		return returnPartList;
	}
	
	public List<BuyerReferenceVO> mapBuyerRef(BuyerRefs buyerRefs){
		List<BuyerReferenceVO> buyerRefVO = new ArrayList<BuyerReferenceVO>();
		if(null != buyerRefs){
			List<BuyerRef> buyerRef = buyerRefs.getBuyerRef();
			if(null!=buyerRef && buyerRef.size()!=0){
				for(BuyerRef buyerRefData : buyerRef){
					BuyerReferenceVO buyerReferenceVO = new BuyerReferenceVO();
					buyerReferenceVO.setReferenceType(buyerRefData.getReferenceType());
					buyerReferenceVO.setReferenceDescription(buyerRefData.getReferenceValue());
					buyerRefVO.add(buyerReferenceVO);
				}
			}
		}
		
		return buyerRefVO;
	}
	
	public SOCompleteSOResponse mapCompleteSOResponse(ProcessResponse pResp){
		//logger.info("Entering SOCompleteMapper.mapCompleteSOResponse()");
		SOCompleteSOResponse completeSOResponse = new SOCompleteSOResponse();
		Results results = new Results();
		if (pResp.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results=Results.getSuccess(ResultsCode.COMPLETE_SO_SUCCESS.getMessage());
		} else {
			//logger.info("CompleteSO operation failed. Setting result and message as Failure");
			results= Results.getError(pResp.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}		
		completeSOResponse.setResults(results);
		//logger.info("Leaving SOAddNoteMapper.mapAddNoteResponse()");
		return completeSOResponse;
	}
}
