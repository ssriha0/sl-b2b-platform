package com.newco.marketplace.translator.business;

import java.util.List;

import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public interface ScheduleService {

	boolean scheduleOrder(CreateDraftRequest request, List<SkuPrice> skus, List<NoteRequest> notes,String client) throws Exception;

}
