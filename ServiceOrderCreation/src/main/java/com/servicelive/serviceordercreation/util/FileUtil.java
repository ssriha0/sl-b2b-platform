package com.servicelive.serviceordercreation.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author svanloo
 *
 */
public class FileUtil {
	private static int READ_LEN = 1024;

	/**
	 * 
	 * Read a file and return it as a String.
	 * @param absolutePath 
	 * @return String
	 * @throws IOException 
	 * 
	 */
	public static StringBuilder readFile(URL url) throws IOException {

		InputStream is = url.openStream();
		StringBuilder sb = new StringBuilder();

		try {
			byte[] buffer = new byte[READ_LEN];
			int bytesRead;

			while ((bytesRead = is.read(buffer)) > 0) {
				char[] chars = new char[bytesRead];
				for(int i = 0; i < bytesRead; i++) {
					chars[i] = (char) buffer[i];
				}
				sb.append(chars);
			}
		} catch (IOException ioException) {
			System.out.println("IOException " + ioException.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// do nothing
			}
		}

		return sb;
	}
}
