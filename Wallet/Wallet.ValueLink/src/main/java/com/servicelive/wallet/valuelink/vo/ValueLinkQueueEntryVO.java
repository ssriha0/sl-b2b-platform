package com.servicelive.wallet.valuelink.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkQueueEntryVO.
 */
public class ValueLinkQueueEntryVO implements Serializable {

	/**
	 * Enum QueueStatus.
	 */
	public enum QueueStatus {
		ERROR(2), SENT(1), WAITING(0);

		/**
		 * parseInt.
		 * 
		 * @param val 
		 * 
		 * @return QueueStatus
		 */
		static QueueStatus parseInt(int val) {

			switch (val) {
			case 0:
				return WAITING;
			case 1:
				return SENT;
			case 2:
				return ERROR;
			default:
				throw new RuntimeException("Invalid QueueStatus");
			}
		}

		/** ival. */
		private int ival;

		/**
		 * QueueStatus.
		 * 
		 * @param val 
		 */
		QueueStatus(int val) {
			ival = val;
		}

		/**
		 * intValue.
		 * 
		 * @return int
		 */
		public int intValue() {
			return ival;
		}
	}

	/** serialVersionUID. */
	private static final long serialVersionUID = -3609843660080003964L;;

	/** fulfillmentEntryId. */
	private long fulfillmentEntryId;

	/** fulfillmentGroupId. */
	private long fulfillmentGroupId;

	/** message. */
	private byte[] message;

	private long messageTypeId;

	/** primaryAccountNumber. */
	private Long primaryAccountNumber;
	
	/** queueStatusId. */
	private int queueStatusId;

	/** sendCount. */
	private int sendCount;

	/**
	 * getFulfillmentEntryId.
	 * 
	 * @return long
	 */
	public long getFulfillmentEntryId() {

		return fulfillmentEntryId;
	}

	/**
	 * getFulfillmentGroupId.
	 * 
	 * @return long
	 */
	public long getFulfillmentGroupId() {

		return fulfillmentGroupId;
	}

	/**
	 * getMessage.
	 * 
	 * @return byte[]
	 */
	public byte[] getMessage() {

		return message;
	}

	/**
	 * getPrimaryAccountNumber.
	 * 
	 * @return Long
	 */
	public Long getPrimaryAccountNumber() {

		return primaryAccountNumber;
	}

	/**
	 * getQueueStatus.
	 * 
	 * @return QueueStatus
	 */
	public QueueStatus getQueueStatus() {

		return QueueStatus.parseInt(this.queueStatusId);
	}

	/**
	 * getQueueStatusId.
	 * 
	 * @return int
	 */
	public int getQueueStatusId() {

		return queueStatusId;
	}

	/**
	 * getSendCount.
	 * 
	 * @return int
	 */
	public int getSendCount() {

		return sendCount;
	}

	/**
	 * setFulfillmentEntryId.
	 * 
	 * @param fulfillmentEntryId 
	 * 
	 * @return void
	 */
	public void setFulfillmentEntryId(long fulfillmentEntryId) {

		this.fulfillmentEntryId = fulfillmentEntryId;
	}

	/**
	 * setFulfillmentGroupId.
	 * 
	 * @param fulfillmentGroupId 
	 * 
	 * @return void
	 */
	public void setFulfillmentGroupId(long fulfillmentGroupId) {

		this.fulfillmentGroupId = fulfillmentGroupId;
	}

	/**
	 * setMessage.
	 * 
	 * @param message 
	 * 
	 * @return void
	 */
	public void setMessage(byte[] message) {

		this.message = message;
	}

	/**
	 * setPrimaryAccountNumber.
	 * 
	 * @param primaryAccountNumber 
	 * 
	 * @return void
	 */
	public void setPrimaryAccountNumber(Long primaryAccountNumber) {

		this.primaryAccountNumber = primaryAccountNumber;
	}

	/**
	 * setQueueStatus.
	 * 
	 * @param queueStatus 
	 * 
	 * @return void
	 */
	public void setQueueStatus(QueueStatus queueStatus) {

		this.queueStatusId = queueStatus.intValue();
	}

	/**
	 * setQueueStatusId.
	 * 
	 * @param queueStatusId 
	 * 
	 * @return void
	 */
	public void setQueueStatusId(int queueStatusId) {

		this.queueStatusId = queueStatusId;
	}

	/**
	 * setSendCount.
	 * 
	 * @param sendCount 
	 * 
	 * @return void
	 */
	public void setSendCount(int sendCount) {

		this.sendCount = sendCount;
	}
	
	/**
	 * incrementSendCount
	 * 
	 * @return void
	 */
	public void incrementSendCount() {
		this.sendCount++;
	}
	
	public long getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
}
