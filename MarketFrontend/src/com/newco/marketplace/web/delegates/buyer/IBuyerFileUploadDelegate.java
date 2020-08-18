package com.newco.marketplace.web.delegates.buyer;

import java.util.List;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditDTO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditErrorDTO;

/**
 * @author paugus2
 *
 */
public interface IBuyerFileUploadDelegate {
	public boolean getValidZipCode(String zip) throws DelegateException; 
	public List getTheSkillTree() throws DelegateException;
	public String insertFiletoDb(UploadAuditDTO uploadAuditDTO) throws DelegateException;
	
	
	
	public UploadFileVO getUserLogo(Integer buyerId)throws DelegateException;
	public List<RunningHistoryVO> getRunningHistory(Integer buyer_Id)throws DelegateException;
	/**
	 * @param uploadAuditVO
	 * @param uploadAuditDTO
	 * @return
	 */
	public UploadAuditDTO convertVOtoDTO(UploadAuditVO uploadAuditVO, UploadAuditDTO uploadAuditDTO);		
			
	/**
	 * @param uploadAuditDTO
	 * @param uploadAuditVO
	 * @return
	 */
	public UploadAuditVO convertDTOtoVO(UploadAuditDTO uploadAuditDTO, UploadAuditVO uploadAuditVO);
	
	public List<UploadAuditErrorDTO> getErrorRecordList(Integer fileId)throws DelegateException;    
}