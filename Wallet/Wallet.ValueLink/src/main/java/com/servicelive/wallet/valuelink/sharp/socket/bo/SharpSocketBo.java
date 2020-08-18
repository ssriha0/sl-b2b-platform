package com.servicelive.wallet.valuelink.sharp.socket.bo;

import java.io.DataInputStream;
import java.io.IOException;

import com.servicelive.wallet.valuelink.socket.SocketContainer;

public interface SharpSocketBo {
	byte[] readSocketWithRetry(DataInputStream dataInputStream, int byteToRead) throws IOException;
	byte[] sendToSharp(byte[] request) throws Exception;
	byte[] sendToSharp(SocketContainer socket,byte[] request) throws Exception;
}
