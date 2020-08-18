package com.newco.marketplace.web.delegatesImpl.buyer;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.business.iBusiness.IBuyerFileUpload.IBuyerFileUploadBO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;
import com.newco.marketplace.web.delegates.buyer.IBuyerFileUploadDelegate;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditDTO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditErrorDTO;
import com.newco.marketplace.web.utils.UploadAuditMapper;

public class BuyerFileUploadDelegateImpl implements IBuyerFileUploadDelegate {

	private IBuyerFileUploadBO iBuyerFileUploadBO;
	private UploadAuditMapper uploadAuditMapper;
	private UploadAuditVO uploadAuditVO;

	public UploadFileVO getUserLogo(Integer buyerId) throws DelegateException {
		try {
			UploadFileVO uploadFileVO = iBuyerFileUploadBO.getUserLogo(buyerId);
			return uploadFileVO;
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.getValidZipCode() due to :", ex);
		}
	}
	
	public List<RunningHistoryVO> getRunningHistory(Integer buyerResourceId)throws DelegateException {
		try {
			List runningHistoryList = iBuyerFileUploadBO.getRunningHistory(buyerResourceId);
			return runningHistoryList;
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.getValidZipCode() due to ", ex);
		}
	}
	
	public BuyerFileUploadDelegateImpl(IBuyerFileUploadBO iBuyerFileUploadBO,
			UploadAuditMapper uploadAuditMapper, UploadAuditVO uploadAuditVO) {
		this.iBuyerFileUploadBO = iBuyerFileUploadBO;
		this.uploadAuditMapper = uploadAuditMapper;
		this.uploadAuditVO = uploadAuditVO;
	}

	public boolean getValidZipCode(String zip) throws DelegateException {
		try {
			return iBuyerFileUploadBO.getValidZipCode(zip);
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.getValidZipCode() due to ", ex);
		}
	}

	public List getTheSkillTree() throws DelegateException {
		List skillTree = null;
		try {
			skillTree = iBuyerFileUploadBO.getTheSkillTree();
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.getTheSkillTree() due to ", ex);
		}
		return skillTree;
	}

	public String insertFiletoDb(UploadAuditDTO uploadAuditDTO) throws DelegateException {
		try {
			return iBuyerFileUploadBO.insertFiletoDb(convertDTOtoVO(uploadAuditDTO, uploadAuditVO));
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.insertFiletoDb() due to ", ex);
		}
	}

	/**
	 * @param uploadAuditVO
	 * @param uploadAuditDTO
	 * @return
	 */
	public UploadAuditDTO convertVOtoDTO(UploadAuditVO uploadAuditVO, UploadAuditDTO uploadAuditDTO) {
		return (UploadAuditDTO) uploadAuditMapper.convertVOtoDTO(uploadAuditVO, uploadAuditDTO);
	}

	/**
	 * @param uploadAuditDTO
	 * @param uploadAuditVO
	 * @return
	 */
	public UploadAuditVO convertDTOtoVO(UploadAuditDTO uploadAuditDTO, UploadAuditVO uploadAuditVO) {
		return (UploadAuditVO) uploadAuditMapper.convertDTOtoVO(uploadAuditDTO, uploadAuditVO);
	}
	
	/**
	 * This method is used to fetch the list of error records in an uploaded file.
	 * @param fileId
	 * @return UploadAuditErrorDTO list
	 */
	public List<UploadAuditErrorDTO> getErrorRecordList(Integer fileId) throws DelegateException {
		try {		
			List<UploadAuditErrorVO> errorRecords = new ArrayList<UploadAuditErrorVO>(); 
			errorRecords = iBuyerFileUploadBO.getErrorRecordList(fileId);
			return uploadAuditMapper.convertUploadAuditErrorVOToErrorDTO(errorRecords);   
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @BuyerFileUploadDelegateImpl.insertFiletoDb() due to ", ex);
		}
	}

	public IBuyerFileUploadBO getIBuyerFileUploadBO() {
		return iBuyerFileUploadBO;
	}

	public void setIBuyerFileUploadBO(IBuyerFileUploadBO buyerFileUploadBO) {
		iBuyerFileUploadBO = buyerFileUploadBO;
	}

	public UploadAuditMapper getUploadAuditMapper() {
		return uploadAuditMapper;
	}

	public void setUploadAuditMapper(UploadAuditMapper uploadAuditMapper) {
		this.uploadAuditMapper = uploadAuditMapper;
	}

	public UploadAuditVO getUploadAuditVO() {
		return uploadAuditVO;
	}

	public void setUploadAuditVO(UploadAuditVO uploadAuditVO) {
		this.uploadAuditVO = uploadAuditVO;
	}
}
