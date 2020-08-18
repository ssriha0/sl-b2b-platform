package com.newco.marketplace.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;

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
		}else{
			return true;
		}
		
	}
	

}
