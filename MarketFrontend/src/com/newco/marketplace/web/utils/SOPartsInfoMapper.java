package com.newco.marketplace.web.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.web.dto.SOPartsDTO;



public class SOPartsInfoMapper extends ObjectMapperWizard{

	public static List<Part> assignSOPartsShippingInfoListInfotoPartVO(String soId, List<SOPartsDTO> partsListDTO) {
		ArrayList<Part> soPartList = new ArrayList<Part>();
		
		for (SOPartsDTO partDTO : partsListDTO) {
			if(partDTO != null){
			Part part = new Part();
			part.setPartId(partDTO.getPartId());
			part.setSoId(soId);
			
			if(partDTO.getShippingCarrierId() != null){
				Carrier shippingCarrier = new Carrier();
				shippingCarrier.setCarrierId(partDTO.getShippingCarrierId());
				shippingCarrier.setTrackingNumber(partDTO.getShippingTrackingNumber());
				part.setShippingCarrier(shippingCarrier);
				if(partDTO.getShipDate() != null && !"".equalsIgnoreCase(partDTO.getShipDate()))
					part.setShipDate(new Timestamp(getDateFromString(partDTO.getShipDate(), "yyyy-MM-dd").getTime()));
			}
			
			if(partDTO.getCoreReturnCarrierId() != null){
				Carrier coreReturnCarrier = new Carrier();
				coreReturnCarrier.setCarrierId(partDTO.getCoreReturnCarrierId());
				coreReturnCarrier.setTrackingNumber(partDTO.getCoreReturnTrackingNumber());
				part.setReturnCarrier(coreReturnCarrier);
			}
			
			
			soPartList.add(part);
			}
			}
		return soPartList;
	}
	

}
