package com.servicelive.wallet.valuelink.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.IValueLinkRequestHandlerBO;

public class SocketContainerPool implements InitializingBean{
	private static final Logger logger = Logger
			.getLogger(SocketContainerPool.class);
			
	private SocketContainerFactory socketContainerFactory;
	private BlockingQueue<SocketContainer> liveConnectionPool; 
	private BlockingQueue<SocketContainer> usedConnectionPool; 
	private BlockingQueue<SocketContainer> suspectConnectionPool;
	private Queue<SocketContainer> deadSocketContainerQueue;
	
	private IValueLinkRequestHandlerBO requestHandler;
	private SocketConfig socketConfig;
	private boolean useIPSocket;
		
	private static volatile boolean heartbeatFlag = false;	
	private Lock suspectQueueLock = new ReentrantLock();
	
	public SocketContainerPool() {
		
	}
	
	public void afterPropertiesSet(){
		if(!isUseIPSocket()){
			return;
		}
		
		liveConnectionPool = new LinkedBlockingQueue<SocketContainer>(socketConfig.getMaxConnections());
		usedConnectionPool = new LinkedBlockingQueue<SocketContainer>(socketConfig.getMaxConnections());
		suspectConnectionPool = new LinkedBlockingQueue<SocketContainer>(socketConfig.getMaxConnections());
		deadSocketContainerQueue = new LinkedBlockingQueue<SocketContainer>(socketConfig.getMaxConnections());
		
		initializeAll();
		
		Thread liveConnectionPoolMonitor = new Thread(new SocketContainerLiveObjectMonitor());
		liveConnectionPoolMonitor.start();
		
		Thread suspectConnectionPoolMonitor = new Thread(new SocketContainerSuspectObjectMonitor());
		suspectConnectionPoolMonitor.start();
		
		Thread deadConnectionPoolMonitor = new Thread(new SocketContainerInitializer());
		deadConnectionPoolMonitor.start();
	}
	
	private void initializeAll(){
		logger.info("Initializing all SHARP socket connections");
		HostPortConfig currentHostPortConfig = socketConfig
				.getCurrentHostPortConfig();
		
		int count = 0;
		while (count < socketConfig.getMaxConnections()) {			
			try {				
				Integer port = currentHostPortConfig.getPort();
				initialize(currentHostPortConfig.getHostname(), port);
				
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Initialization failed for host="
						+ currentHostPortConfig.getHostname() + ", port="
						+ currentHostPortConfig.getPort());
				deadSocketContainerQueue.offer(new SocketContainer(currentHostPortConfig.getHostname(), 
						currentHostPortConfig.getPort()));
				logger.error("Dead Queue="+deadSocketContainerQueue.size());
			}
			currentHostPortConfig = currentHostPortConfig.next();
			count ++;
		}
	}
	
	private void initialize(String hostname, int port) throws SocketException, IOException{		
		logger.info("Initializing SHARP socket connections for host"+ hostname + ", port="
				+ port);

		InetAddress inetAddress = InetAddress
				.getByName(hostname);
		SocketContainer socketContainer = initializeConnection(inetAddress, port);
		// Send heart beat message over new socket
		if(sendHeartbeat(socketContainer)){
			addObject(socketContainer);
		}else{
			suspectConnectionPool.add(socketContainer);
		}
	}
		
	private boolean reInitialize(String hostname, int port) throws SocketException, IOException{		
		logger.info("Initializing SHARP socket connections for host"+ hostname + ", port="
				+ port);

		InetAddress inetAddress = InetAddress
				.getByName(hostname);
		SocketContainer socketContainer = initializeConnection(inetAddress, port);
		// Send heart beat message over new socket
		if(sendHeartbeat(socketContainer)){
			addObject(socketContainer);
			return true;
		}else{
			return false;
		}
	}
	
	private SocketContainer initializeConnection(InetAddress inetAddress, int port ) throws SocketException, IOException{
		SSLSocket sslSocket = socketContainerFactory.buildSSLSocket(inetAddress, port);
		SocketContainer socketContainer = new SocketContainer(inetAddress.getHostName(), port);		
		socketContainer.setSocket(sslSocket);
		socketContainer.initialize();
		
		return socketContainer;
	}
	
	private boolean sendHeartbeat(SocketContainer socket){
		try {			
			requestHandler.sendHeartbeatMessage(socket);
			return true;
		} catch (SLBusinessServiceException e) {
			// Add to suspect connection.
			e.printStackTrace();
			logger.error("Initialization: Heartbeat message failed, moving connection to suspect pool-"+e.getMessage());	
			
			return false;
		}
	}
	

	public void addObject(SocketContainer object) {
		try {
			liveConnectionPool.put(object);
		} catch (InterruptedException e) {
			// Ideally this should not happen
			e.printStackTrace();
			logger.error("Failed to add object to pool-"+e.getMessage());
		}
	}

	public SocketContainer borrowObject() throws InterruptedException {
		SocketContainer socketContainer = liveConnectionPool.take();
		usedConnectionPool.put(socketContainer);
		
		logger.info("Borrowed Socket host="+ socketContainer.getSocket().getInetAddress().getHostName()+", port="+socketContainer.getSocket().getPort());
		
		return socketContainer;
	}

	public void returnObject(SocketContainer object) throws InterruptedException {
		liveConnectionPool.put(object);
		usedConnectionPool.remove(object);
	}
	
	public void invalidateObject(SocketContainer object) throws InterruptedException{
		object.resetTimeoutAttributes();
		suspectConnectionPool.put(object);
		usedConnectionPool.remove(object);
	}
	
	
	class SocketContainerLiveObjectMonitor implements Runnable {
		public void run() {
			while (true) {
				try {				
					Thread.sleep(socketConfig.getHearbeatInterval());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				suspectQueueLock.lock();
				
				Iterator<SocketContainer> queueIterator = liveConnectionPool.iterator();
				
				while (queueIterator.hasNext()) {
					SocketContainer socket = queueIterator.next();
					
					// Send heartbeat message over socket
					if(!sendHeartbeat(socket)){
						queueIterator.remove();
						suspectConnectionPool.add(socket);
					}				
				}
				
				heartbeatFlag = true;
				
				suspectQueueLock.unlock();
			}
		}
	}
	
	class SocketContainerSuspectObjectMonitor implements Runnable{

		public void run() {
			while(true){
				if(heartbeatFlag){
					suspectQueueLock.lock();
					
					try {		
						// Wait 2 minutes before checking for suspect connections.
						Thread.sleep(socketConfig.getReconnectTimeout());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
					Iterator<SocketContainer> queueIterator = suspectConnectionPool.iterator();
					while (queueIterator.hasNext()) {
						SocketContainer socket = queueIterator.next();					
						
						if(!sendHeartbeat(socket)){
							// If unable to send heartbeat message again, reinitialize the connection.
							deadSocketContainerQueue.offer(socket);						
							queueIterator.remove();
						}						
					}
				
					heartbeatFlag = false;
					
					suspectQueueLock.unlock();
				}
				
				// Wait for 5s to avoid this infinite loop hog the CPU.
				try{
					Thread.sleep(5000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}		
	}
	
		
	class SocketContainerInitializer implements Runnable{

		public void run() {
			while(true){
				logger.info("DEAD QUEUE SIZE="+deadSocketContainerQueue.size());
				Iterator<SocketContainer> queueIterator = deadSocketContainerQueue.iterator();
				logger.info("HAS NEXT="+queueIterator.hasNext());
				while (queueIterator.hasNext()) {
					SocketContainer socket = queueIterator.next();
					logger.info("NEXT="+socket.getHostName());		
					
					//InetAddress inetAddress = socket.getSocket().getInetAddress();
					int port = socket.getPort();

					logger.info("Re-initializing SHARP socket connections for host"+ socket.getHostName() + ", port="
							+ port);
					
					try{
					//	initialize(socket.getHostName(), port);
						if(reInitialize(socket.getHostName(), port)){
							socket.close();
							queueIterator.remove();
						}
					} catch (IOException e) {
						// Do nothing if re-initialization fails. As SocketContainer is still in dead queue, reconnect attempt will happen with next iteration.
						e.printStackTrace();
						logger.error("Re-initialization failed for host="
								+ socket.getHostName() + ", port="
								+ port);						
					}				
				}
				
				try {
					Thread.sleep(socketConfig.getReconnectTimeout());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		}
		
	}

	public SocketContainerFactory getSocketContainerFactory() {
		return socketContainerFactory;
	}

	public void setSocketContainerFactory(
			SocketContainerFactory socketContainerFactory) {
		this.socketContainerFactory = socketContainerFactory;
	}

	public Queue<SocketContainer> getLiveConnectionPool() {
		return liveConnectionPool;
	}

	public void setLiveConnectionPool(BlockingQueue<SocketContainer> liveConnectionPool) {
		this.liveConnectionPool = liveConnectionPool;
	}

	public Queue<SocketContainer> getSuspectConnectionPool() {
		return suspectConnectionPool;
	}

	public void setSuspectConnectionPool(
			BlockingQueue<SocketContainer> suspectConnectionPool) {
		this.suspectConnectionPool = suspectConnectionPool;
	}

	public IValueLinkRequestHandlerBO getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(IValueLinkRequestHandlerBO requestHandler) {
		this.requestHandler = requestHandler;
	}

	public SocketConfig getSocketConfig() {
		return socketConfig;
	}

	public void setSocketConfig(SocketConfig socketConfig) {
		this.socketConfig = socketConfig;
	}

	public boolean isUseIPSocket() {
		return useIPSocket;
	}

	public void setUseIPSocket(boolean useIPSocket) {
		this.useIPSocket = useIPSocket;
	}
}

