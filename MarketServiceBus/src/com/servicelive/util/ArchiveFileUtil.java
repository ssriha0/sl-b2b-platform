package com.servicelive.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ArchiveFileUtil {
	// Archiving methods
	public static String archiveFile(String inputFileFullPath, String archiveFolderName, String fileSuffix) throws FileArchiveException {
		if (fileSuffix == null) {
			fileSuffix = "";
		}
		
		File current = new File(inputFileFullPath);
		if (current == null || !current.exists()) {
			throw new FileArchiveException("Unable to move the file to the destination folder because the source file does not exist.");
		}
		
		Calendar currentTime = Calendar.getInstance();
				
		DecimalFormat twoDigitFormatter = new DecimalFormat("00");
		DecimalFormat threeDigitFormatter = new DecimalFormat("000");
		// Check if the folder for the year exists
		String folderName = String.format("%s/%s", archiveFolderName, currentTime.get(Calendar.YEAR));
		createIfDoesNotExist(folderName);
		
		// Check if the folder for the month exists
		folderName = String.format("%s/%s", folderName, twoDigitFormatter.format(currentTime.get(Calendar.MONTH) + 1));
		createIfDoesNotExist(folderName);
		
		// Check if the folder for the day exists
		folderName = String.format("%s/%s", folderName, twoDigitFormatter.format(currentTime.get(Calendar.DAY_OF_MONTH)));
		createIfDoesNotExist(folderName);
		
		// Create a timestamp to prepend to the filename
		String timeStamp = twoDigitFormatter.format(currentTime.get(Calendar.HOUR_OF_DAY)) +
			twoDigitFormatter.format(currentTime.get(Calendar.MINUTE)) +
			twoDigitFormatter.format(currentTime.get(Calendar.SECOND)) +
			threeDigitFormatter.format(currentTime.get(Calendar.MILLISECOND));
		
		String newFileName = String.format("%s/%s-%s%s", folderName, timeStamp, getFileNameFromPath(inputFileFullPath), fileSuffix);
		
		File renamed = new File(newFileName);
		return moveFile(current, renamed);
	}
		
	private static void createIfDoesNotExist(String folderName) throws FileArchiveException {
		File folder = new File(folderName);
		if (folder != null && !folder.exists()) {
			if (!folder.mkdirs()) {
				throw new FileArchiveException(String.format("Attempt to create directory '%s' failed", folderName));
			}
		}		
	}
	
	private static String moveFile(File currentFileLocation, File newFileLocation) throws FileArchiveException {
		if (newFileLocation != null) {
			if (newFileLocation.exists()) {
				newFileLocation.delete();
			}
			
			if (currentFileLocation.renameTo(newFileLocation)) {
				return newFileLocation.getPath();
			} else {
				throw new FileArchiveException(String.format("Attempt to rename file '%s' to '%s' failed", currentFileLocation.getPath(), newFileLocation.getPath()));
			}
		}
		return "";
	}
	
	private static String getFileNameFromPath(String originalFilePath) {
		String filePath = originalFilePath;
		if (filePath != null) {
			String separator = "/";
			if (filePath.indexOf("\\") != -1) {
				separator = "\\";
			}
			
			int index = filePath.lastIndexOf(separator);
			if (index == filePath.length() + 1) {
				filePath = filePath.substring(0, filePath.length() - 1);
				index = filePath.lastIndexOf(separator);
			}
			if (index > 0) {
				return filePath.substring(index + 1);
			}
		}		
		return originalFilePath;
	}
}
