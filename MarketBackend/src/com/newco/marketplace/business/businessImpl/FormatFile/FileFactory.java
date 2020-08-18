package com.newco.marketplace.business.businessImpl.FormatFile;

import com.newco.marketplace.util.BaseFileUtil;

public class FileFactory {
	
	public static final String NACHA_FILE = "Nacha";
	
	public static final String BAI_FILE = "Bai";
	
	public static final String GL_FILE = "GL";
	
	public static final String TRANS_FILE = "Trans";
	public static final String OFAC_FILE="OFAC";
	
	BaseFileUtil genFile = null;
	
	public BaseFileUtil createFileImpl (String type){
		    if (type.equals(FileFactory.BAI_FILE))
		    	genFile = new BAIFileImpl();
		    if (type.equals(FileFactory.OFAC_FILE))
		    	genFile = new OFACFileImpl();
		    return genFile;
	}
		
}
