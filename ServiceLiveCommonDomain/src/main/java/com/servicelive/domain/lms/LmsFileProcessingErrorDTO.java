package com.servicelive.domain.lms;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file_processing_error")
public class LmsFileProcessingErrorDTO {
	
	// Fields
		@Id
		@GeneratedValue(strategy=IDENTITY)
		@Column(name = "id", unique = true, nullable = false)
		private Integer id;
		
		
		@ManyToOne
		@JoinColumn(name="file_upload_id")
		private LmsFileUploadDTO lmsFileUploadDTO;
		
		@Column(name = "record_text", nullable = false, length=1000)
		private String recordText;
		
		@Column(name = "failure_reason", nullable = false, length=20)
		private String failureReason;
		
		@Column(name = "failure_exception", nullable = true, length=1000)
		private String failureException;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public LmsFileUploadDTO getLmsFileUploadDTO() {
			return lmsFileUploadDTO;
		}

		public void setLmsFileUploadDTO(LmsFileUploadDTO lmsFileUploadDTO) {
			this.lmsFileUploadDTO = lmsFileUploadDTO;
		}

		public String getRecordText() {
			return recordText;
		}

		public void setRecordText(String recordText) {
			this.recordText = recordText;
		}

		public String getFailureReason() {
			return failureReason;
		}

		public void setFailureReason(String failureReason) {
			this.failureReason = failureReason;
		}

		public String getFailureException() {
			return failureException;
		}

		public void setFailureException(String failureException) {
			this.failureException = failureException;
		}	
}
