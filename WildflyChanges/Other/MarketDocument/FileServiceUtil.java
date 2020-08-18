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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
//i kept comment for jdk1.8
/*import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
*/
/**
 * 
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:40:35 $
 * 
 */
public class FileServiceUtil {

	private static final Logger logger = Logger.getLogger(FileServiceUtil.class
			.getName());

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FileServiceUtil.deleteFileResource("C://delete/test/New Folder/");
		} catch (Throwable e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
	
	public static int deleteFileResource(String path)
	throws DataServiceException {
		return deleteFileResource(path,true);
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws DataServiceException
	 */
	private static int deleteFileResource(String path,boolean self)
			throws DataServiceException {
		int count[]=new int[]{0};
		try {
			if(!deleteFileOrDirectory(path,count,self))
				throw new DataServiceException("Delete Document Failure");
			return count[0];
		} catch (RuntimeException e) {
			String message = "Delete Operation Failure:" + path;
			logger.error(message);
			e.printStackTrace();
			throw new DataServiceException("Delete Document Failure",e);
		}		
	}

	/**
	 * Call the deleteFileResource(). This is a recursive.deleteFileResource()
	 * is a wrapper for handling the exceptions
	 * 
	 * @param filePath
	 * @return true if path is deleted
	 */
	private static boolean deleteFileOrDirectory(String filePath,int[] count,boolean self) {
		File path = new File(filePath);
	//	if (path == null || !path.exists())
	//		return false;
		if (path.isDirectory()){
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getPath());
				if (files[i].isDirectory()) {
					deleteFileOrDirectory(files[i].getPath(),count,true);
				} else {
					files[i].delete();
					count[0]++;
				}
			}
		}
		if(self){
			path.delete();
			count[0]++;			
		}
		if(path.exists()){
			count[0]=-1;
			return false;			
		}
		return true;
		
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
		//I am added for jdk1.8
		try {
			ImageIO.write(resizedImage, "JPEG", out);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image ");
			logger.error(e);
		}
		
		//I kept comment for jdk1.8
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
