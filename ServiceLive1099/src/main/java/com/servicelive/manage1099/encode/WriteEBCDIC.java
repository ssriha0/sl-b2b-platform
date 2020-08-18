package com.servicelive.manage1099.encode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.file.FileHandler;

/**
 * 
 * @author mjoshi1
 * 
 */
public class WriteEBCDIC {

	/**
	 * 
	 * @param writeString
	 * @throws IOException
	 */
	public static void writeToOutFile(String writeString) throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
			fos = new FileOutputStream(FileConstants.FILE_NAME_WITH_PATH);
			osw = new OutputStreamWriter(fos, "Cp1047"); // EBCDIC

			osw.flush();
			fos.write(writeString.getBytes());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (osw != null && fos != null) {
				osw.close();
				fos.close();
			}
		}

	}

	/**
	 * 
	 * @param resultArray
	 * @throws IOException
	 */

	public static int writeToOutFile(ArrayList<String> resultArray)
			throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {
			FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
			fos = new FileOutputStream(FileConstants.FILE_NAME_WITH_PATH);
			osw = new OutputStreamWriter(fos, "Cp1047"); // EBCDIC

			int i = 0;
			for (String writeString : resultArray) {
				osw.flush();
				fos.write(writeString.getBytes());

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (osw != null && fos != null) {
				osw.close();
				fos.close();
			}
		}

		return 1;

	}

	/**
	 * 
	 * @param resultArray
	 * @throws IOException
	 */

	@Deprecated
	public static int writeToOutputFile(Map<Integer, List> resultMap)
			throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {

			FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);

			fos = new FileOutputStream(FileConstants.FILE_NAME_WITH_PATH, true);
			osw = new OutputStreamWriter(fos, "Cp1047"); // EBCDIC

			for (int index = 0; index < resultMap.size(); index++) {

				List<String> alist = resultMap.get(index);

				int i = 0;

				for (Object field : alist) {
					if (i < 14 || i == 20) {
						// System.out.println(i+" .  osw ="+field);
						osw.write(field.toString());

					} else {
						osw.flush();
						// System.out.println(i+" .  fos ="+field);
						fos.write(field.toString().getBytes());
					}
					i++;
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (osw != null && fos != null) {
				osw.close();
				fos.close();
			}
		}

		return 1;

	}

	/**
	 * 
	 * @param resultArray
	 * @throws IOException
	 */

	public static int writeBytesToOutputFile(Map<Integer, List> resultMap)
			throws Exception {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {

			FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);

			fos = new FileOutputStream(FileConstants.FILE_NAME_WITH_PATH, true);
			osw = new OutputStreamWriter(fos, "Cp1047"); // EBCDIC for mainframe
			//osw = new OutputStreamWriter(fos, "UTF8"); // For reading the flat file formed
			for (int index = 0; index < resultMap.size(); index++) {

				List<byte[]> alist = resultMap.get(index);

				int i = 0;

				for (byte[] field : alist) {
					// All the fields from 1 to 14 are written as it is.
					// Fields from 15 to 20 are written in packed decimal
					// Field 21st is again written as it is.
					if (i < 14 || i == 20) {
						
						osw.write(new String(field));

					} else {
						osw.flush();
						
						fos.write(field);
						
					}
					i++;
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (osw != null && fos != null) {
				osw.close();
				fos.close();
			}
		}

		return 1;

	}

	/**
	 * 
	 * @param resultArray
	 * @throws IOException
	 */

	public static int writeToOutputFileTest(byte[] str) throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		try {

			FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH_TEST);

			fos = new FileOutputStream(FileConstants.FILE_NAME_WITH_PATH_TEST,
					true);
			osw = new OutputStreamWriter(fos, "Cp1047"); // EBCDIC

			osw.flush();
			// System.out.println(i+" .  fos ="+field);
			// osw.write(str.getBytes());
			fos.write(str);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (osw != null && fos != null) {
				osw.close();
				fos.close();
			}
		}

		return 1;

	}

}
