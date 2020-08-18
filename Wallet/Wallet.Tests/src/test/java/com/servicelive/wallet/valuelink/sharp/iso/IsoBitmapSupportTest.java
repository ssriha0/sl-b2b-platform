package com.servicelive.wallet.valuelink.sharp.iso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.servicelive.common.exception.SLBusinessServiceException;

public class IsoBitmapSupportTest {
	
	private static final String [] PCODES =
		new String[] {"P1",  "P2",  "P3",  "P4", "P5",  "P6",  "P7",  "P8",			// 0x7F -- bit 1 is ALWAYS zero
												 "P13", "P14", "P15", "P16",		// 0x0F
					  "P17", "P18", "P19", "P20",									// 0xF0
					         "P26",        "P28",       "P30",        "P32",		// 0x55
					  "P33",        "P35",       "P37",        "P39",				// 0xAA
					  "P41",														// 0x80
					                                                  "P56"};		// 0x01
	@Test
	public void testGetBitmapFromMessage() throws IOException {

		long expectedBitmap = 0x7F0FF055AA800100L;	
		
		byte [] bitmapByteArray = new byte[] {
			(byte)0x7F, (byte)0x0F, (byte)0xF0, (byte)0x55, (byte)0xAA, (byte)0x80, (byte)0x01, (byte)0x00
		};

		byte [] messageRemainder = new byte[128];		
		Arrays.fill(messageRemainder, (byte)0xDD);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(2);
		os.write("ISOGCQ1200".getBytes());
		os.write(bitmapByteArray);
		os.write(messageRemainder);
		
		byte [] message = os.toByteArray();
		
		long actualBitmap = IsoBitmapSupport.getBitmapFromMessage(message);
		
		Assert.assertEquals(expectedBitmap, actualBitmap);
	}

	@Test
	public void testGetBitmapFromCodes() {

		long expectedBitmap = 0x7F0FF055AA800100L;
		
		long actualBitmap = IsoBitmapSupport.getBitmapFromCodes( Arrays.asList(PCODES) );
		
		System.out.printf("%1$X", actualBitmap);
		
		Assert.assertEquals(expectedBitmap, actualBitmap);
	}

	@Test
	public void testConvertBitmapToByteArray() {

		byte [] expectedByteArray = new byte[] {
			(byte)0x7F, (byte)0x0F, (byte)0xF0, (byte)0x55, (byte)0xAA, (byte)0x80, (byte)0x01, (byte)0x00
		};
				
		long actualBitmap = IsoBitmapSupport.getBitmapFromCodes( Arrays.asList(PCODES) );
		byte [] actualByteArary = IsoBitmapSupport.convertBitmapToByteArray(actualBitmap);	
		
		for( int i=0; i<8; i++ ) {
			System.out.printf("%1$d: %2$X\n",i, actualByteArary[i]);
		}
		for( int i=0; i<8; i++ ) {
			Assert.assertEquals(expectedByteArray[i], actualByteArary[i]);
		}

	}

	@Test
	public void testConvertByteArrayToBitmap() {

		byte [] byteArray = new byte[] {
			(byte)0x7F, (byte)0x0F, (byte)0xF0, (byte)0x55, (byte)0xAA, (byte)0x80, (byte)0x01, (byte)0x00
		};

		long expectedBitmap = 0x7F0FF055AA800100L;
		long actualBitmap = IsoBitmapSupport.convertByteArrayToBitmap(byteArray);

		System.out.printf("%1$X", actualBitmap);
		
		Assert.assertEquals(expectedBitmap, actualBitmap);
	}
	
	@Test
	public void testIsBitmappedElementPresent() throws SLBusinessServiceException {
		long bitmap = 0x7F0FF055AA800100L;
		List<String> pcodeList = Arrays.asList(PCODES);
		for( int i=2; i<64; i++ ) {
			String pcode = ("P"+i);
			if( pcodeList.contains(pcode) ) {
				Assert.assertTrue( IsoBitmapSupport.isBitmappedElementPresent(pcode, bitmap) );
			} else {
				Assert.assertFalse( IsoBitmapSupport.isBitmappedElementPresent(pcode, bitmap) );
			}
		}
	}

	@Test
	public void testIsBitmapped() {
		for( int i=2; i<64; i++ ) {
			Assert.assertTrue( IsoBitmapSupport.isBitmapped("P"+i) );
		}
		Assert.assertFalse( IsoBitmapSupport.isBitmapped("P1"));
		Assert.assertFalse( IsoBitmapSupport.isBitmapped("MTI"));
		Assert.assertFalse( IsoBitmapSupport.isBitmapped("BITMAP"));
		Assert.assertFalse( IsoBitmapSupport.isBitmapped("START_DELIMETER_1"));
		Assert.assertFalse( IsoBitmapSupport.isBitmapped("START_DELIMETER_2"));
	}

}
