package com.servicelive.ibatis.sqlchecker;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class UrlFinder {

	public List<URL> find(String[] startDirectories, String includePattern, String excludePattern) throws IOException {
		List<URL> results = new ArrayList<URL>();
		for(String startDirectory:startDirectories) {
			List<URL> tempUrls = find(startDirectory, includePattern, excludePattern);
			if(tempUrls != null && !tempUrls.isEmpty()) {
				results.addAll(tempUrls);
			}
		}
		return results;
	}

	/**
	 * 
	 * @return List
	 * @throws MalformedURLException 
	 */
	private List<URL> find(String startDirectory, String includePattern, String excludePattern) throws IOException {
		if(includePattern == null) {
			throw new RuntimeException("includePattern should not be null.");
		}

		if(startDirectory == null) {
			throw new RuntimeException("startDirectory should not be null.");
		}
		return loadFiles(new File(startDirectory), includePattern, excludePattern);
	}

	private List<URL> loadFiles(File startingDirectory, String includePattern, String excludePattern) throws MalformedURLException {
		List<URL> fileCollection = new ArrayList<URL>();
		File[] list = startingDirectory.listFiles();
		if(list == null) {
			throw new RuntimeException("directory not found " + startingDirectory); 
		}
		for(File f: list) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(matchesPattern(fileName, includePattern, excludePattern) ) {
					fileCollection.add(f.toURL());
				}
			} else {
				String directoryName = f.getName();
				if(directoryName.equals(".") || directoryName.equals("..") ) {
					continue;
				}
				fileCollection.addAll(loadFiles(f, includePattern, excludePattern));
			}
		}		
		return fileCollection;
		
	}

	private boolean matchesPattern(String fileName, String includePattern, String excludePattern) {
		Pattern incPat = Pattern.compile(includePattern);
		Matcher matcher = incPat.matcher(fileName);
		boolean result = matcher.find();
		if(!result) {
			return false;
		}
		
		if(excludePattern == null || excludePattern.trim().equals("")) {
			return true;
		}

		boolean temp = fileName.matches(excludePattern);
		if(temp) {
			return false;
		}
		return true;
	}
}
