package com.newco.marketplace.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class Bitmapper {

	private static Logger logger = Logger.getLogger(Bitmapper.class);

	private static final byte[] NMM_BIT_MAP = { (byte) 0x00, (byte) 0x30,
			(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00,
			(byte) 0x00 };

	public static String getHexValueFromBits(String data,
			ByteArrayOutputStream byteArrayOutputStream) throws IOException {
		logger.debug("BitMap str before : " + data);
		// String i = Integer.toHexString(Integer.parseInt(data,2));
		int startInd = 0;
		int endInd = 4;
		int totalLength = data.length();
		String hexaValue = "";
		while (!(endInd > totalLength)) {
			String temp = data.substring(startInd, endInd);
			hexaValue = hexaValue
					+ Integer.toHexString(Integer.parseInt(temp, 2));
			startInd = endInd;
			endInd = endInd + 4;
		}
		logger.debug("BitMap str after : " + hexaValue);

		int secstartInd = 0;
		int secendInd = 2;
		int sectotalLength = hexaValue.length();
		String sechexaValue;
		int s = 0;
		while (!(secendInd > sectotalLength)) {
			String temp = hexaValue.substring(secstartInd, secendInd);
			/*
			 * String temp1=temp.substring(0, 1); String temp2=temp.substring(1,
			 * 2);
			 * sechexaValue=checkFour(Integer.toBinaryString(Integer.parseInt(temp1)))+checkFour(Integer.toBinaryString(Integer.parseInt(temp2)));
			 * int s = Integer.parseInt(sechexaValue, 2);
			 */
			s = Integer.parseInt(temp, 16);

			byteArrayOutputStream.write(s);
			secstartInd = secendInd;
			secendInd = secendInd + 2;
		}
		// byteArrayOutputStream.write(NMM_BIT_MAP);
		// byteArrayOutputStream.write(s);
		logger.debug("BitMap str after : " + byteArrayOutputStream.toString());
		return "";
	}

	public static String getHexValueFromASCII(String data) {
		// System.out.println("Incoming text: "+data);
		// String binary = Integer.toBinaryString(new Integer(data).intValue());
		// String i = Integer.toHexString(Integer.parseInt(data,2));
		return "1";
	}

	public static TreeMap<Integer, Integer> constructBitmapTemplateHash() {
		TreeMap<Integer, Integer> myTreeMap = new TreeMap<Integer, Integer>();
		for (int i = 1; i < 65; i++) {
			myTreeMap.put(new Integer(i), new Integer(0));
		}
		return myTreeMap;
	}

	public static byte[] getBinaryBitmap(String bitmap,
			ByteArrayOutputStream byteArrayOutputStream) throws IOException {
		BitSet bs = new BitSet();
		String b;
		int j = 0;
		for (int i = 0; i < bitmap.length(); i++) {
			b = bitmap.substring(i, i + 1);
			if (b.equals("0")) {
				bs.set(i, false);
			} else {
				bs.set(i, true);
			}
			j = i;
		}
		byte myBytes[] = toByteArray(bs);
		byteArrayOutputStream.write(myBytes);
		return myBytes;
	}

	private static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[8];
		System.out.println("bits.length()/8=" + (bits.length() / 8));
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				int a = 1 << (8 - (i % 8) - 1);
				// int a = 1<<(i%8);
				bytes[7 - (bytes.length - i / 8 - 1)] |= (byte) a;
				// System.out.println("byte["+(bytes.length-i/8-1)+"]="+(bytes[bytes.length-i/8-1]));
			}
		}
		return bytes;
	}

}
