package com.newco.marketplace.business.businessImpl.FormatFile;

import java.io.File;
import java.util.HashMap;

import com.newco.marketplace.util.BaseFileUtil;

public class BAIFileImpl extends BaseFileUtil {

	   //parse the file and change to new file format
	   @Override
	public void formatFile(File inputFile, File outputFile, HashMap propertyMap){
		   //Implement to change the format
		   FileFactory fileFactory = new FileFactory();
		   BAIFileImpl baiFileImpl = (BAIFileImpl)fileFactory.createFileImpl("Bai");
		   
	   }
	
}