package com.servicelive.wallet.valuelink.sharp.iso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.SLBusinessServiceException;

public class IsoBitmapSupport {

	private static Map<String,Integer> ALL_PCODES = new HashMap<String,Integer>();
	
	static {
		for( int i=2; i<64; i++ ) {
			ALL_PCODES.put("P" + i, i);
		}		
	}
	
	public static long getBitmapFromMessage(byte [] byteMessage) {
		long bitmap = 0L;
		if( byteMessage != null ) {
			if( byteMessage.length < 19 ) {
				throw new ArrayIndexOutOfBoundsException("byteMessage bust be at least 19 bytes long, but it was only " + byteMessage.length);
			}
			byte [] byteArray = new byte[8];
			System.arraycopy( byteMessage, 11, byteArray, 0, 8);
			bitmap = convertByteArrayToBitmap(byteArray);
		}
		return bitmap;
	}

	public static long getBitmapFromCodes(List<String> usedPCodes) {
		long bitmap = 0L;
		if( usedPCodes != null ) {
			for ( String code : usedPCodes ) {
				if( ALL_PCODES.containsKey(code) ) {
					int bit = ALL_PCODES.get(code);
					// PCode 1 is indicated by the most significant bit position
					// PCode 64 is indicated by the least significant bit position
					bitmap |= (1L << (64-bit));
				}
			}
		}
		return bitmap;
	}
			
	public static byte[] convertBitmapToByteArray(long bitmap) {
		long mask = 0xFF;		
		byte[] byteArray = new byte[8];
		for( int i=0; i < byteArray.length; i++ ) {
			byteArray[i] = (byte)((bitmap >> (8*(7-i))) & mask); 
		}
		return byteArray;
	}
	
	public static long convertByteArrayToBitmap(byte [] byteMessage) {
		long bitmap = 0L;
		long shifted = 0L;
		long mask = 0x000000FFL;
		if( byteMessage != null ) {
			if( byteMessage.length != 8 ) {
				throw new ArrayIndexOutOfBoundsException("byteMessage must be 8 bytes long, but it was " + byteMessage.length);
			}
			for( int i=0; i<8; i++ ) {
				shifted = (((long)byteMessage[i]) & mask) << (8*(7-i));
				bitmap |= shifted;
			}
		}
		return bitmap;
	}
	public static boolean isElementPresent( String code, byte [] message ) throws SLBusinessServiceException {
		return isBitmappedElementPresent(code,getBitmapFromMessage(message));
	}
	public static boolean isBitmappedElementPresent( String code, long bitmap ) throws SLBusinessServiceException {
		if( !isBitmapped(code) ) {
			throw new SLBusinessServiceException(code + " is not bitmapped.");
		}
		int bit = ALL_PCODES.get(code);
		long mask = (1L << (64-bit));
		
		return ((mask & bitmap) != 0L);
	}
		
	public static boolean isBitmapped( String code ) {
		return ALL_PCODES.containsKey(code);
	}
}
