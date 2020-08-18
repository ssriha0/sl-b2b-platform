package com.newco.spring.jms;

import javax.jms.Message;

public interface ISpringJMSFacility {

	public void sendMesage(final String txtMessage);
	
	public void sendMesage(final byte[] bytes);
	
	public void sendNMMMesage(final byte[] bytes);

	public void sendBalanceMesage(final byte[] bytes);
	
	public void sendLocalMesage(final byte[] bytes,final Long pan);
	
	public void sendExceptionMesage(final byte[] bytes);
	
	public Message Receive();
	
	public Message ReceiveNMM();
	
	public Message ReceiveBalance();
	
	//Finance refactoring changes
	public void sendWorkerMesage(final byte[] bytes,final String queueName);
	
	public void sendBalanceMesageSync(final byte[] bytes,final Long pan);
	
	public Message ReceiveSync(final String pan);
}
