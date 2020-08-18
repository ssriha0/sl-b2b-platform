package com.servicelive.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipFileUtil {
	private static Logger logger = Logger.getLogger(ZipFileUtil.class);
	private static SimpleDateFormat formatDate = new SimpleDateFormat("MMddyyHHmmss");
	private static String FILE_EXTENSION =".zip";
	/**
	 * @param srcFiles
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	public static File AddToZipFile(String[] srcFiles, String zipFile,String fileName)throws IOException {
		//Creating todays date with timestamp to differentiate Zip file generated
		Date today = Calendar.getInstance().getTime();
		String uniqueTime = formatDate.format(today);
		File zipFileGenerated =null;
		//TO DO: Move to constant 
	    String zipExt = FILE_EXTENSION;
	    zipFile = zipFile.concat(fileName).concat(uniqueTime).concat(zipExt);
	    try{
		// create byte buffer
		byte[] buffer = new byte[25600];
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		logger.info("Starting zipping files");
		for (int i = 0; i < srcFiles.length; i++) {
			File srcFile = new File(srcFiles[i]);
			FileInputStream fis = new FileInputStream(srcFile);
			// begin writing a new ZIP entry, positions the stream to the start
			// of the entry data
			zos.putNextEntry(new ZipEntry(srcFile.getName()));
			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			// close the InputStream
			fis.close();
		}
		zipFileGenerated = new File(zipFile);
        // close the ZipOutputStream
		zos.close();
	    }catch (NullPointerException npe) {
	    	logger.error("File path does not exists:"+ npe.getMessage());
	    	throw new IOException();
		}catch (IOException ioe) {
			logger.error("Zip File cannot be created in the location:"+ ioe.getMessage());
	    	throw new IOException();
		}catch (Exception e) {
			logger.error("Some thing Gone wrong in zip file process:"+ e.getMessage());
	    	throw new IOException();
		}
		return zipFileGenerated;
		

	}
}
