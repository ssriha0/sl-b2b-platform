package com.newco.marketplace.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;




/**
 * Util Class For Generating CSV File
 *
 */
public class CSVUtil {

	private CSVUtil(){
		
	}

	public static final File convertToCSV(List<String> header, List<String> footer, List<ArrayList<String>> body) throws IOException {
		File csvFile = new File("Reports.csv");
	    FileWriter writer;
		
			writer = new FileWriter(csvFile);
			 PrintWriter pw = new PrintWriter(writer);
	    for(String head : header){
	    	pw.write(head ==null?"": head.toString());pw.append(',');
	    }
	    pw.append('\n');
	    
	    for(ArrayList<String> content : body){
	    	for(String column :content){
	    		
	            pw.append("\"");
	            if(column.contains("\"")){
	                column= column.replace( "\"", "\"\"");	            
	                }
	            pw.append(column);
	            pw.append("\"");
	            pw.append(",");
	           

	    		
	    	}
	    	 pw.append("\n");
	    }

	    for(String foot : footer){
	    	pw.write(foot ==null?"": foot.toString());pw.append(',');
	    	
	    }
	    pw.append('\n');
        //Flush the output to the file
        pw.flush();
        //Close the Print Writer
        pw.close();
        //Close the File Writer
        writer.close();
        


		return csvFile;

	}
	
}

