package com.newco.marketplace.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

//import com.sun.image.codec.jpeg.JPEGCodec;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 00:51:53 $
 */

/*
 * Maintenance History
 * $Log: DocumentUtils.java,v $
 * Revision 1.8  2008/04/26 00:51:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.28.1  2008/04/23 11:42:01  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:17:45  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2007/11/30 23:22:07  langara
 * fixed documents message
 *
 * Revision 1.5  2007/11/14 17:49:58  mhaye05
 * added convertBytesToMegaBytes()
 *
 * Revision 1.4  2007/11/13 22:51:53  mhaye05
 * changed return type for convertBytesToKilobytes
 *
 * Revision 1.3  2007/11/12 15:58:23  mhaye05
 * updated convertBytesToKiloBytes to round to the nearest whole number
 *
 */
public class DocumentUtils {
	private static final Logger logger = Logger.getLogger(DocumentUtils.class);
	
	public static byte[] getBytesFromInputStream(InputStream is, int length)
			throws IOException {

		/*
		 * You cannot create an array using a long type. It needs to be an int
		 * type. Before converting to an int type, check to ensure that file is
		 * not larger than Integer.MAX_VALUE;
		 */
		if (length > Integer.MAX_VALUE) {
			throw new IOException("file too large to process ");

		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {

			offset += numRead;

		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read inputstream ");
		}

		is.close();
		return bytes;

	}
	
	public static byte[] resizeoImage(byte[] inImagedata, 
			int targetWidth, int targetHeight) {

		int scaledWidth;
		int scaledHeight;

		Image sourceImage = new ImageIcon(inImagedata).getImage();
		float targetscale = 0;
		float scale1 = (int) ((float)targetWidth/(float)(sourceImage.getWidth(null))*100);
		float scale2 = (int) ((float)targetHeight/(float)(sourceImage.getHeight(null))*100) ;
		if (scale1 < scale2)
			targetscale = scale1;
		else
			targetscale = scale2;
		targetscale = targetscale / 100;
		
		scaledWidth = (int) (sourceImage.getWidth(null) * targetscale);
		scaledHeight = (int) (sourceImage.getHeight(null) * targetscale);
		if (logger.isDebugEnabled()) {
			logger.debug("image width = " + sourceImage.getWidth(null));
			logger.debug("image height = " + sourceImage.getHeight(null));

			logger.debug("target width = " + targetWidth);
			logger.debug("target height = " + targetHeight);

			logger.debug("scale1 = " + scale1);
			logger.debug("scale2 = " + scale2);
			logger.debug("targetscale = " + targetscale);


			logger.debug("new width = " + scaledWidth);
			logger.debug("new height = " + scaledWidth);

		}
		
		BufferedImage resizedImage = scaleImage(sourceImage, scaledWidth,
				scaledHeight);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//i am added for jdk1.8
		try{
		ImageIO.write(resizedImage,"JPEG", out);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image " );
			logger.error(e);
		     }
		//I kept comment  for jdk1.8
		/*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		try {
			encoder.encode(resizedImage);			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image " );
			logger.error(e);
		}*/
		
		
		byte[] b = out.toByteArray();

		return b;
	}

	/**
	 * convertBytesToKiloBytes takes bytes and converts it to Kilobytes.  
	 * The returned value is rounded to the nearest whole number 
	 * @param bytes
	 * @return
	 */
	public static double convertBytesToKiloBytes (int bytes, boolean round) {
		double dFormat;
		double dd2dec; 

		DecimalFormat df = new DecimalFormat("#########0.00");
		
		if (round){
			dFormat = Math.round(bytes * 0.0009765625);
			dd2dec = new Double(df.format(dFormat)).doubleValue();
			
		}else{
			dFormat = bytes * 0.0009765625;
			dd2dec = new Double(df.format(dFormat)).doubleValue();
		}
		
		return dd2dec;
	}

	/**
	 * convertBytesToMegaBytes takes bytes and converts it to Megabytes.  
	 * The returned value is rounded to the nearest whole number 
	 * @param bytes
	 * @return
	 */
	public static double convertBytesToMegaBytes (int bytes, boolean round) {
		double dFormat;
		double dd2dec; 

		DecimalFormat df = new DecimalFormat("#########0.00");
		
		if (round){
			dFormat = Math.round(bytes * .000000953674316 );
			dd2dec = new Double(df.format(dFormat)).doubleValue();
		}else{
			dFormat = bytes * .000000953674316;
			dd2dec = new Double(df.format(dFormat)).doubleValue();
		}
		
		return dd2dec;

	}	
	
	private static BufferedImage scaleImage(Image sourceImage, int width,
			int height) {
		ImageFilter filter = new ReplicateScaleFilter(width, height);
		ImageProducer producer = new FilteredImageSource(sourceImage
				.getSource(), filter);
		Image resizedImage = Toolkit.getDefaultToolkit().createImage(producer);

		return toBufferedImage(resizedImage);
	}

	private static BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bufferedImage;
	}
}
