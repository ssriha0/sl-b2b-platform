package com.newco.marketplace.test;

public class TestByteArray {
	public static void main(String[] args) {
		byte[] b = intToByteArray(10);
		System.out.println("Byte Array : "+ b);
	}
	
	public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

}
