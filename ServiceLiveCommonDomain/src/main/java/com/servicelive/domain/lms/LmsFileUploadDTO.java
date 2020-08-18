package com.servicelive.domain.lms;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "file_upload_details")
public class LmsFileUploadDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	// Fields
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "resource_id", nullable = true, length = 10)
	private int resourceId;

	@OneToMany(mappedBy="lmsFileUploadDTO",targetEntity=LmsFileProcessingErrorDTO.class,
			fetch=FetchType.EAGER)
	private List<LmsFileProcessingErrorDTO> lmsFileProcessingErrorDTO ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "processed_on")
	private Date processedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "file_content", nullable = false)
	private byte[] fileContent;

	@Column(name = "name_of_file", nullable = true,length = 200)
	private String uploadFileFileName;

	@Transient
	private File uploadFile;

	@Transient
	private String fileName;

	@Column(name = "file_status", nullable = true,length = 200)
	private String fileStatus;

	@Column(name = "total_records_cnt")
	private int rowCount;

	@Column(name = "error_records_cnt")
	private int errorRecordsCnt;

	// SL-21142
	/*@Column(name = "issuer_id")
	private Integer buyerId;*/

	@Transient
	private File fileByteToText;

	public File getFileByteToText() {
		return fileByteToText;
	}

	public void setFileByteToText(File fileByteToText) {
		this.fileByteToText = fileByteToText;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}



	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public byte[] getFileContent() {
		try {
			return this.fileContent;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void setfileContent(byte[] uploadedfilename) {
		try {
			String str = "";
			// byte[] bytes = new byte[];

			// InputStream fin = new java.io.FileInputStream(uploadedfilename);
			this.fileContent = uploadedfilename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// uploadedfile.
	}

	public void setBlobFromFile(File uploadedfile) {
		try {
			InputStream is = new FileInputStream(uploadedfile);
			long length = uploadedfile.length();
			if (length > Integer.MAX_VALUE) {
				// File is too large
			}

			// int tscfdsx=is.read(this.fileContent);
			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ uploadedfile.getName());
			}

			// Close the input stream and return bytes
			is.close();

			this.fileContent = bytes;
		} catch (Exception e) {

		}
	}

	public List<LmsFileProcessingErrorDTO> getLmsFileProcessingErrorDTO() {
		return lmsFileProcessingErrorDTO;
	}

	public void setLmsFileProcessingErrorDTO(
			List<LmsFileProcessingErrorDTO> lmsFileProcessingErrorDTO) {
		this.lmsFileProcessingErrorDTO = lmsFileProcessingErrorDTO;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public Date getProcessedOn() {
		return processedOn;
	}

	public void setProcessedOn(Date processedOn) {
		this.processedOn = processedOn;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getErrorRecordsCnt() {
		return errorRecordsCnt;
	}

	public void setErrorRecordsCnt(int errorRecordsCnt) {
		this.errorRecordsCnt = errorRecordsCnt;
	}
	// SL-21142
	/*public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}*/	
}
