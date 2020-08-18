package com.servicelive.orderfulfillment.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import com.servicelive.common.util.BoundedBufferedReader;

public class ResourceLoader {
	private static final Logger logger = Logger.getLogger(ResourceLoader.class);
    public static String loadContentFromResourcePath(String resourcePath) throws IOException {
        InputStream inputStream =  Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        StringBuilder stringBuilder = new StringBuilder("");
        if (inputStream != null) {
            String line;
            BoundedBufferedReader reader=null;
            try {
                reader = new BoundedBufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line=reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } 
            catch(Exception e)
            {
            	e.printStackTrace();
            }
            finally {
            	try
            	{
            	if(inputStream!=null)
                inputStream.close();
            	if(reader!=null)
            	reader.close();
            	}
            	catch(Exception e)
            	{
            		//logging error as this can never occur
            		logger.error("Caught inside: <ResourceLoader::loadContentFromResourcePath()>:Error: Got an exception that should not occur", e);
            	}
            }
        }
        return stringBuilder.toString();
    }

}
