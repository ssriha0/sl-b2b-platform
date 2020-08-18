package com.newco.marketplace.web.dto;

import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * DTO for Parts Management Tab
 *
 */
public class PartsManagementTabDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = 2650172279841075524L;
	private String soId;
	private Integer invoiveId;
	private File filesToUpload[];
	private File fileSelect;
	//private List<File> files;
    //private List<String> filesContentType;
    //private List<String> filesFileName;
    private List<Integer> documentIds;
    private String responseMessage;

	// DTO for edit part
	private InvoicePartsVO editPartVO;
	
	// DTO for add invoice 
	private InvoiceDetailsVO invoiceDetailsVO;
	private List<InvoicePartsVO> addInvoiceDTO;
	private List<SOWError> pmErrors = new ArrayList<SOWError>();
	//part number count
	private Integer partNoCount;
	//Invoice number count
	private Integer invoiceNumberCount;
	private String partNumPosition;
	
	public String getPartNumPosition() {
		return partNumPosition;
	}

	public void setPartNumPosition(String partNumPosition) {
		this.partNumPosition = partNumPosition;
	}

	public Integer getPartNoCount() {
		return partNoCount;
	}

	public void setPartNoCount(Integer partNoCount) {
		this.partNoCount = partNoCount;
	}

	public Integer getInvoiceNumberCount() {
		return invoiceNumberCount;
	}

	public void setInvoiceNumberCount(Integer invoiceNumberCount) {
		this.invoiceNumberCount = invoiceNumberCount;
	}

	public InvoiceDetailsVO getInvoiceDetailsVO() {
		return invoiceDetailsVO;
	}

	public void setInvoiceDetailsVO(InvoiceDetailsVO invoiceDetailsVO) {
		this.invoiceDetailsVO = invoiceDetailsVO;
	}
	
	public List<SOWError> getPmErrors() {
		return pmErrors;
	}

	public InvoicePartsVO getEditPartVO() {
		return editPartVO;
	}

	public void setEditPartVO(InvoicePartsVO editPartVO) {
		this.editPartVO = editPartVO;
	}

	public List<InvoicePartsVO> getAddInvoiceDTO() {
		return addInvoiceDTO;
	}

	public void setAddInvoiceDTO(List<InvoicePartsVO> addInvoiceDTO) {
		this.addInvoiceDTO = addInvoiceDTO;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Integer getInvoiveId() {
		return invoiveId;
	}

	public void setInvoiveId(Integer invoiveId) {
		this.invoiveId = invoiveId;
	}
	
	public void setPmErrors(List<SOWError> pmErrors) {
		this.pmErrors = pmErrors;
	}

	public File[] getFilesToUpload() {
		return filesToUpload;
	}

	public void setFilesToUpload(File[] filesToUpload) {
		this.filesToUpload = filesToUpload;
	}

	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}

	public File getFileSelect() {
		return fileSelect;
	}

	public void setFileSelect(File fileSelect) {
		this.fileSelect = fileSelect;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	
}
