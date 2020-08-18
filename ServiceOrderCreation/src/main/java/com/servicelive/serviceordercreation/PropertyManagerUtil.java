package com.servicelive.serviceordercreation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author svanloon
 *
 */
public class PropertyManagerUtil {
	private String fileName;

	private Properties properties = new Properties();

	/**
	 * 
	 */
	public PropertyManagerUtil(String fileName) {
		super();
		this.fileName = fileName;
		File file = new File(fileName);
		if(file.exists() == false) {
			throw new RuntimeException("Couldn't find file " + fileName);
		}
		InputStream is;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't find file " + fileName, e);
		}
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load properties", e);
		}
		try {
			is.close();
		} catch (IOException e) {
			// ignore exception
		}
	}
	public Set<String> keySet() {
		Set<Object> keySetObject = properties.keySet();
		Set<String> keySetString = new HashSet<String>();
		for(Object key :keySetObject) {
			keySetString.add(key.toString());
		}
		return keySetString;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
		store("updated/added " + key);
	}

	private void store(String comment) {
		File file = new File(fileName);
		OutputStream os;
		try {
			os = new BufferedOutputStream(new FileOutputStream(file));
			properties.store(os, comment);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
