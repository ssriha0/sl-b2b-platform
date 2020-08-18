package com.newco.marketplace.util;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.BadElementException;
import com.lowagie.text.pdf.PdfPCell;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class DocumentUtils {
	private static final Logger logger = Logger.getLogger(DocumentUtils.class);
	
	public static byte[] getBytesFromInputStream(InputStream is, int length)
			throws IOException {

		/*
		 * You cannot create an array using a long type. It needs to be an int
		 * type. Before converting to an int type, check to ensure that file is
		 * not loarger than Integer.MAX_VALUE;
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

		logger.debug("image width = " + sourceImage.getWidth(null));
		logger.debug("image height = " + sourceImage.getHeight(null));
		
		logger.debug("target width = " + targetWidth);
		logger.debug("target height = " + targetHeight);
		
		logger.debug("scale1 = " + scale1);
		logger.debug("scale2 = " + scale2);
		logger.debug("targetscale = " + targetscale);
		
		
		logger.debug("new width = " + scaledWidth);
		logger.debug("new height = " + scaledWidth);
		
		
		
		BufferedImage resizedImage = scaleImage(sourceImage, scaledWidth,
				scaledHeight);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		try {
			encoder.encode(resizedImage);			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image " );
			logger.error(e);
		}
		
		
		byte[] b = out.toByteArray();

		return b;
	}
	
	public static byte[] scaleImage(byte[] inImagedata, 
			int targetWidth, int targetHeight) {
		Image sourceImage = new ImageIcon(inImagedata).getImage();

		BufferedImage resizedImage = scaleImage(sourceImage, targetWidth,
				targetHeight);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		try {
			encoder.encode(resizedImage);			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image " );
			logger.error(e);
		}
		
		
		byte[] b = out.toByteArray();

		return b;
	}

	public static boolean IsResizable(byte[] inImagedata, int targetWidth,
			int targetHeight) {
		Image sourceImage = new ImageIcon(inImagedata).getImage();
		if (null != sourceImage) {
			if (sourceImage.getWidth(null) > targetWidth
					|| sourceImage.getHeight(null) > targetHeight) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
    public static com.lowagie.text.Image getCheckBox() throws IOException, BadElementException{
    	ClassPathResource classRes = new ClassPathResource("resources/images/checkbox.gif");
    	File file = classRes.getFile();
		byte[] checkBoxImageByte = FileUtils.readFileToByteArray(file);
		com.lowagie.text.Image checkBoxImage = com.lowagie.text.Image.getInstance(checkBoxImageByte);
		//PdfPCell checkBoxCell=new PdfPCell(checkBoxImage);
		return checkBoxImage;
    	
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
	
	public static void main(String... args) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("C:/temp/Huge_Scanned_Image.jpg");
		int availableBytesLength = fis.available();
		byte[] imageData = new byte[availableBytesLength];
		fis.read(imageData);
		fis.close();
		byte[] newImageData = resizeoImage(imageData, 800, 600);
		FileOutputStream fos = new FileOutputStream("C:/temp/Compressed_Image.jpg");
		fos.write(newImageData);
		fos.close();
	}
}
