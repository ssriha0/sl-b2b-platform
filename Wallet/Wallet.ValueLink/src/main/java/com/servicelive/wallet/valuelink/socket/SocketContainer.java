package com.servicelive.wallet.valuelink.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketContainer {
	
	private long id = System.identityHashCode(this);
	private Socket socket;

	private DataOutputStream outputStream;
	private DataInputStream inputStream;
    private volatile int timeoutCount = 0;
    private volatile long lastTimeoutTime = 0;
    private Status status = Status.IN_POOL;
    private String deviceNo = null;
    private boolean nmmValidated;
    
    private String hostName; 
    private int port;
    
    public SocketContainer(){
    	
    }
    
	public SocketContainer(String hostName, int port) {
		this.setHostName(hostName);
		this.setPort(port);
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOut(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void initialize() {
		initializeInputStream();
		initializeOutputStream();
	}
	
	public boolean isNmmValidated() {
		return nmmValidated;
	}

	public void setNmmValidated(boolean nmmValidated) {
		this.nmmValidated = nmmValidated;
	}

	private void initializeInputStream() {
		if (inputStream == null) {
			try {
				inputStream = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				
			}
		}
	}

	private void initializeOutputStream() {
		if (outputStream == null) {
			try {
				outputStream = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				
			}
		}
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void close() {
		closeSocket();
		closeInputStream();
		closeOutputStream();
	}

	private void closeInputStream() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				
			} finally {
				inputStream = null;
			}
		}
	}

	private void closeOutputStream() {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				
			} finally {
				outputStream = null;
			}
		}
	}

	private void closeSocket() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				
			}
		}
	}

	public int getTimeoutCount() {
		return timeoutCount;
	}
	
	public int incrementAndGetTimeoutCount() {
		return ++timeoutCount;
	}
	
	public void resetTimeoutAttributes() {
		this.timeoutCount = 0;
		this.lastTimeoutTime = 0;
	}

	public void setLastTimeoutTime() {
		this.lastTimeoutTime = System.currentTimeMillis();
	}
	
	public long getLastTimeoutTime() {
		return this.lastTimeoutTime;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public enum Status {
		IN_POOL, TEMPORARILY_OUT_OF_POOL, READY_FOR_EVICTION;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocketContainer [id=");
		builder.append(id);
		builder.append(", deviceNo=");
		builder.append(deviceNo);
		builder.append(", socket=");
		builder.append(socket);
		builder.append(", status=");
		builder.append(status);
		builder.append(", timeoutCount=");
		builder.append(timeoutCount);
		builder.append("]");
		return builder.toString();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}