package com.newco.marketplace.dto.vo.webservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class WSPayloadVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9019726463520990783L;

	private Date createTimestamp;
	
	private byte[] payload;
	
	private String methodName;
	
	private String portName;
	
	private int limiter;
	
	private long queueID;

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}
	
	public void setPayload(Object object) throws IOException {
		byte[] bytes ;
        ByteArrayOutputStream out = new ByteArrayOutputStream() ;
        ObjectOutputStream objOut = new ObjectOutputStream(out) ;
        objOut.writeObject(object) ;
        objOut.flush() ;
        bytes = out.toByteArray() ;
        objOut.close() ;
        this.payload = bytes;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getLimiter() {
		return limiter;
	}

	public void setLimiter(int limiter) {
		this.limiter = limiter;
	}

	public long getQueueID() {
		return queueID;
	}

	public void setQueueID(long queueID) {
		this.queueID = queueID;
	}
	
}
