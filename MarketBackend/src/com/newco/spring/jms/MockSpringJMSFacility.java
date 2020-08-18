package com.newco.spring.jms;

import javax.jms.Message;

public class MockSpringJMSFacility implements ISpringJMSFacility {

	public Message Receive() {
		// TODO Auto-generated method stub
		return null;
	}

	public Message ReceiveNMM() {
		// TODO Auto-generated method stub
		return null;
	}
	public Message ReceiveBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendExceptionMesage(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	public void sendLocalMesage(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	public void sendMesage(String txtMessage) {
		// TODO Auto-generated method stub
		
	}

	public void sendMesage(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	public void sendNMMMesage(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}
	
	public void sendBalanceMesage(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	public void sendLocalMesage(byte[] bytes, Long pan) {
		// TODO Auto-generated method stub
		
	}

	public void sendWorkerMesage(byte[] bytes, String queueName) {
		// TODO Auto-generated method stub
		
	}

	public void sendBalanceMesageSync(byte[] bytes, Long pan) {
		// TODO Auto-generated method stub
		
	}

	public Message ReceiveSync(String pan) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
