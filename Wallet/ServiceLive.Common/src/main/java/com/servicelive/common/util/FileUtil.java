/**
 * 
 */
package com.servicelive.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.servicelive.common.crypto.Algorithm;
import com.servicelive.common.crypto.Encryptor;
import com.servicelive.common.crypto.EncryptorFactory;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author S. Ujwala
 */
public class FileUtil {

	/** logger. */
	private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

	// -----------------------------------------------------------------------
	/**
	 * Unconditionally close an <code>InputStream</code>.
	 * 
	 * @param input the InputStream to close, may be null or already closed
	 * 
	 * @throws IOException 
	 */
	public static void closeInputFile(InputStream input) throws IOException {

		IOUtils.closeQuietly(input);
	}

	// -----------------------------------------------------------------------
	/**
	 * Unconditionally close an <code>OutputStream</code>.
	 * 
	 * @param output the OutputStream to close, may be null or already closed
	 * 
	 * @throws IOException 
	 */
	public static void closeOutputFile(OutputStream output) throws IOException {

		IOUtils.closeQuietly(output);
	}

	// -----------------------------------------------------------------------
	/**
	 * Copies a file to a new location preserving the file date.
	 * <p>
	 * This method copies the contents of the specified source file to the specified destination file. The directory holding the destination file is created if it does not exist. If the destination file exists, then this method will overwrite it.
	 * 
	 * @param srcFile an existing file to copy, must not be <code>null</code>
	 * @param destFile the new file, must not be <code>null</code>
	 * 
	 * @throws NullPointerException if source or destination is <code>null</code>
	 * @throws IOException if an IO error occurs during copying
	 * 
	 * @see #copyFileToDirectory(File, File)
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {

		FileUtils.copyFile(srcFile, destFile);
	}

	// -----------------------------------------------------------------------
	/**
	 * Copies a file to a directory preserving the file date.
	 * <p>
	 * This method copies the contents of the specified source file to a file of the same name in the specified destination directory. The destination directory is created if it does not exist. If the destination file exists, then this method will overwrite it.
	 * 
	 * @param srcFile an existing file to copy, must not be <code>null</code>
	 * @param destDir the directory to place the copy in, must not be <code>null</code>
	 * @param preserveFileDate 
	 * 
	 * @throws NullPointerException if source or destination is null
	 * @throws IOException if an IO error occurs during copying
	 * 
	 * @see #copyFile(File, File, boolean)
	 */
	public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws IOException {

		FileUtils.copyFileToDirectory(srcFile, destDir);
	}

	/**
	 * deleteFile.
	 * 
	 * @param file 
	 * 
	 * @return void
	 */
	public static void deleteFile(File file) {

		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			logger.error("BaseFileUtil-->deleteFile()-->EXCEPTION-->", e);
		}
	}

	/**
	 * getDirectoryFiles.
	 * 
	 * @param path 
	 * 
	 * @return String[]
	 */
	public static String[] getDirectoryFiles(String path) {

		String[] fileNames = null;
		List<String> alFileNames = new ArrayList<String>();
		try {
			File f = new File(path);
			File files[] = f.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()) {
						alFileNames.add(files[i].getName());
					}
				}
				fileNames = new String[alFileNames.size()];
				fileNames = alFileNames.toArray(fileNames);
			}
		} catch (Exception e) {
			logger.error("BaseFileUtil-->deleteFile()-->EXCEPTION-->", e);
		}
		return fileNames;
	}

	/**
	 * getFileNameWithoutExtension.
	 * 
	 * @param fileNameWithExtension 
	 * 
	 * @return String
	 */
	public static String getFileNameWithoutExtension(String fileNameWithExtension) {

		return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));

	}

	// -----------------------------------------------------------------------
	/**
	 * Return an Iterator for the lines in a <code>File</code> using the default encoding for the VM.
	 * 
	 * @param file the file to open for input, must not be <code>null</code>
	 * 
	 * @return an Iterator of the lines in the file, never <code>null</code>
	 * 
	 * @throws IOException in case of an I/O error (file closed)
	 * 
	 * @see #lineIterator(File, String)
	 */
	public static LineIterator lineIterator(File file) throws IOException {

		return FileUtils.lineIterator(file);
	}

	/**
	 * moveFile.
	 * 
	 * @param srcFile 
	 * @param destFile 
	 * 
	 * @return void
	 * 
	 * @throws IOException 
	 */
	public static void moveFile(File srcFile, File destFile) throws IOException {

		FileUtils.moveFile(srcFile, destFile);
	}

	// -----------------------------------------------------------------------
	/**
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * An exception is thrown if the file does not exist.
	 * An exception is thrown if the file object exists but is a directory.
	 * An exception is thrown if the file exists but cannot be read.
	 * 
	 * @param file the file to open for input, must not be <code>null</code>
	 * 
	 * @return a new {@link FileInputStream} for the specified file
	 * 
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException if the file cannot be read
	 */

	public static FileInputStream openInputFile(File file) throws IOException {

		return FileUtils.openInputStream(file);
	}

	// -----------------------------------------------------------------------
	/**
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * The parent directory will be created if it does not exist.
	 * The file will be created if it does not exist.
	 * An exception is thrown if the file object exists but is a directory.
	 * An exception is thrown if the file exists but cannot be written to.
	 * An exception is thrown if the parent directory cannot be created.
	 * 
	 * @param file the file to open for output, must not be <code>null</code>
	 * 
	 * @return a new {@link FileOutputStream} for the specified file
	 * 
	 * @throws IOException if a parent directory needs creating but that fails
	 */

	public static FileOutputStream openOutputFile(File file) throws IOException {

		return FileUtils.openOutputStream(file);
	}

	/**
	 * readLine.
	 * 
	 * @param file 
	 * 
	 * @return String
	 * 
	 * @throws IOException 
	 */
	public static String readLine(File file) throws IOException {

		return FileUtils.readFileToString(file);
	}

	// -----------------------------------------------------------------------
	/**
	 * Reads the contents of a file line by line to a List of Strings using the default encoding for the VM.
	 * The file is always closed.
	 * 
	 * @param file the file to read, must not be <code>null</code>
	 * 
	 * @return the list of Strings representing each line in the file, never <code>null</code>
	 * 
	 * @throws IOException in case of an I/O error
	 */
	public static List readLines(File file) throws IOException {

		return FileUtils.readLines(file);
	}

	/**
	 * splitRecordData.
	 * 
	 * @param tempRecord 
	 * @param token 
	 * 
	 * @return List<Integer>
	 * 
	 * @throws NumberFormatException 
	 */
	public static List<Integer> splitRecordData(String tempRecord, String token) throws NumberFormatException {

		List<Integer> ledgerEntryIds = new ArrayList<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(tempRecord, token);
		while (tokenizer.hasMoreTokens()) {

			ledgerEntryIds.add(Integer.valueOf(tokenizer.nextToken()));
		}
		return ledgerEntryIds;
	}

	// -----------------------------------------------------------------------
	/**
	 * Writes the <code>toString()</code> value of each item in a collection to
	 * the specified <code>File</code> line by line.
	 * The default VM encoding and the specified line ending will be used.
	 * 
	 * @param file the file to write to
	 * @param lines the lines to write, <code>null</code> entries produce blank lines
	 * @param lineEnding the line separator to use, <code>null</code> is system default
	 * 
	 * @throws IOException in case of an I/O error
	 * 
	 * @since Commons IO 1.3
	 */
	public static void writeLinesToFile(File file, Collection lines, String lineEnding) throws IOException {

		FileUtils.writeLines(file, lines, lineEnding);
	}

	// -----------------------------------------------------------------------
	/**
	 * Writes a String to a file creating the file if it does not exist using the default encoding for the VM.
	 * 
	 * @param file the file to write
	 * @param data the content to write to the file
	 * 
	 * @throws IOException in case of an I/O error
	 */
	public static void writeStringToFile(File file, String data) throws IOException {

		FileUtils.writeStringToFile(file, data);
	}

	// -----------------------------------------------------------------------
	/**
	 * Writes a String to a file creating the file if it does not exist using the default encoding for the VM.
	 * 
	 * 
	 * @param file the file to write
	 * @param data the content to write to the file
	 * @param enrypt - boolean value to trigger pgp encryption.
	 * 
	 * @throws IOException in case of an I/O error
	 */
	public static void writeStringToFile(File file, String data, boolean encrypt) throws IOException {

		if(encrypt)
		{
			
			Encryptor encryptor = EncryptorFactory.getEncryptor(Algorithm.PGP, null);
			String filePath = file.getAbsolutePath();
			logger.debug("Creating an Encrypted File to path: " + filePath);
			boolean success = encryptor.writeStringToEncryptedFile(filePath, data);
			
		}
		else
		{
			FileUtils.writeStringToFile(file, data);
		}
	}
}
