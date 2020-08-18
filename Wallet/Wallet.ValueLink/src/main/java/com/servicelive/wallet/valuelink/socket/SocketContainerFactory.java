package com.servicelive.wallet.valuelink.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

public class SocketContainerFactory {
	private static final Logger logger = Logger
			.getLogger(SocketContainerFactory.class);

	private SocketConfig socketConfig;

	/*public void initialize() {
		HostPortConfig currentHostPortConfig = socketConfig
				.getCurrentHostPortConfig();
		while (currentHostPortConfig != null) {
			try {
				InetAddress inetAddress = InetAddress
						.getByName(currentHostPortConfig.getHostname());
				Integer port = currentHostPortConfig.getPort();

				SSLSocket sslSocket = buildSSLSocket(inetAddress, port);
				SocketContainer socketContainer = new SocketContainer();
				socketContainer.setSocket(sslSocket);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Initialization failed for host="
						+ currentHostPortConfig.getHostname() + ", port="
						+ currentHostPortConfig.getPort());
			}
			currentHostPortConfig = currentHostPortConfig.next();
		}
	}
*/
	public SSLSocket buildSSLSocket(InetAddress inetAddress, Integer port)
			throws IOException, SocketException {
		logger.info("Creating socket with IP: " + inetAddress.toString()
				+ " and port: " + port);
		SSLSocket sslSocket = (SSLSocket) SSLSocketFactory.getDefault()
				.createSocket();
		sslSocket.connect(new InetSocketAddress(inetAddress, port),
				socketConfig.getConnectTimeout());
		sslSocket.setSoTimeout(socketConfig.getReceiveTimeout());
		// sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
		sslSocket.startHandshake();
		logger.info("Created socket.");
		return sslSocket;
	}

	public SocketConfig getSocketConfig() {
		return socketConfig;
	}

	public void setSocketConfig(SocketConfig socketConfig) {
		this.socketConfig = socketConfig;
	}

}
