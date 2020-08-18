package com.servicelive.tokenization.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

import com.servicelive.tokenization.log.Log;

public class FileUtil {
	public String writeKeyFile(String key) {
		BufferedWriter writer = null;
		String filePath = null;
		try {
			// create a temporary file
			String fileName = new StringBuilder("Key_")
					.append(new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(Calendar.getInstance().getTime()))
					.append(".txt").toString();
			File keyFile = new File(fileName);

			Log.writeLog(Level.INFO,
					"File written..." + keyFile.getCanonicalPath());
			filePath = keyFile.getCanonicalPath();

			writer = new BufferedWriter(new FileWriter(keyFile));
			writer.write(key);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"FileUtil - Exception message: " + e.getMessage());
			Log.writeLog(Level.SEVERE,
					"FileUtil - Severe Exception, program will terminate!!! "
							+ e);
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				Log.writeLog(Level.SEVERE,
						"FileUtil - Could not close writer");
			}
		}
		return filePath;
	}
}