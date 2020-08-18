package com.servicelive.wallet.valuelink.sharp.socket.bo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.servicelive.wallet.valuelink.socket.SocketContainer;
import com.servicelive.wallet.valuelink.socket.SocketContainerPool;


public class SharpSocketBoImpl implements SharpSocketBo {
	private static final Logger logger = Logger.getLogger(SharpSocketBoImpl.class);
	//@Value("${sharp.socket.retry.count:3}")
	private Integer retryCount;
	
	private SocketContainerPool socketContainerPool;
	
	
	
	public byte[] readSocketWithRetry(DataInputStream dataInputStream, int byteToRead) throws IOException {
		byte[] data = new byte[byteToRead];
		dataInputStream.readFully(data);
		logger.info("received message: "+new String(data));
		logger.info("received message length: "+data.length);
		
		return data;
	}
	
	
	public byte[] sendToSharp(SocketContainer socketContainer, byte[] request) throws IOException, InterruptedException {
		DataOutputStream dataOutputStream = socketContainer.getOutputStream();
		byte[] lengthArr = new byte[2];
		System.out.println("request length: "+request.length);
		lengthArr[0] = (byte) ((request.length >> 8) & 0x000000FF);
		lengthArr[1] = (byte) (request.length & 0x00FF);
		dataOutputStream.write(ArrayUtils.addAll(lengthArr, request));
		dataOutputStream.flush();
		Thread.sleep(100);
		logger.info("receiving message1...");
		byte[] dataLength = readSocketWithRetry(socketContainer.getInputStream(), 2);
		logger.info("dataLength: "+dataLength);
		logger.info("dataLength[0]: "+dataLength[0]);
		logger.info("int dataLength[0]: "+(int) dataLength[0]);
		logger.info("dataLength[1]: "+dataLength[1]);
		logger.info("int dataLength[1]: "+(int) dataLength[1]);
		
		int low = dataLength[0] & 0xff;
		logger.info("low: "+low);
		logger.info("low << 8"+(low << 8));
		int high = dataLength[1] & 0xff;
		logger.info("high: "+high);
		int length = (low << 8 | high);
		logger.info("length: "+length);
		logger.info("receiving message2...: "+length);
		socketContainer.resetTimeoutAttributes();
		return readSocketWithRetry(socketContainer.getInputStream(), length);
	}
	
	public byte[] sendToSharp(byte[] request) throws Exception {
		SocketContainer socketContainer = null;
		boolean isObjectInvalidated = false;		
		
		try {
			//socketContainer = borrowObject();
			socketContainer = (SocketContainer) socketContainerPool.borrowObject();
			
			return sendToSharp(socketContainer, request);
		
		} catch (IOException exception) {
			exception.printStackTrace();
			logger.error(exception);
			
			int timeoutCount = socketContainer.incrementAndGetTimeoutCount();
			if (timeoutCount >= retryCount) {
				socketContainer.setLastTimeoutTime();				
				isObjectInvalidated = true;
				socketContainerPool.invalidateObject(socketContainer);
			}		
			
			throw new Exception(exception.getMessage(), exception);
		} finally {
			if (!isObjectInvalidated) {
				socketContainerPool.returnObject(socketContainer);
			}
		}
		
	}


	
	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public SocketContainerPool getSocketContainerPool() {
		return socketContainerPool;
	}

	public void setSocketContainerPool(SocketContainerPool socketContainerPool) {
		this.socketContainerPool = socketContainerPool;
	}
}
