package com.servicelive.wallet.valuelink.socket;

class HostPortConfig {
	String hostname;
	Integer port;
	HostPortConfig nextConfig;

	public void setNext(HostPortConfig nextConfig) {
		this.nextConfig = nextConfig;
	}

	public HostPortConfig next() {
		return this.nextConfig;
	}

	public HostPortConfig(String hostname, Integer port) {
		super();
		this.hostname = hostname;
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public Integer getPort() {
		return port;
	}
}
