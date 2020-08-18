package com.newco.marketplace.business.iBusiness.ptd;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.ptd.PTDFileVO;
import com.newco.marketplace.dto.vo.ptd.PTDFullfillmentEntryVO;
import com.newco.marketplace.dto.vo.ptd.PtdEntryVO;

public interface IPTDFileProcessor {

	public void processPTDFile() throws Exception;

	public void insertPTDRecords(ArrayList<PtdEntryVO> PtdEntryVOList) throws Exception;

	public ArrayList<PTDFullfillmentEntryVO> getFullfillmentEntryVOList(PTDFileVO ptdFileVO) throws Exception;
}
