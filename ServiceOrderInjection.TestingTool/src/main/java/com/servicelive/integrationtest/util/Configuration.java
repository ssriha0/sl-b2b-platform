package com.servicelive.integrationtest.util;

import static com.servicelive.integrationtest.util.ConfigKeys.ASSURANT_ESB_INBOX_LOCATION;
import static com.servicelive.integrationtest.util.ConfigKeys.CONFIG_FILE;
import static com.servicelive.integrationtest.util.ConfigKeys.HSR_ESB_INBOX_LOCATION;
import static com.servicelive.integrationtest.util.ConfigKeys.OMS_ESB_INBOX_LOCATION;
import static com.servicelive.integrationtest.util.ConfigKeys.OUTPUT_ROOT_FOLDER;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Java object to hold all environmental configuration properties.
 * 
 * @author mustafa
 */
public class Configuration {
	private static final String DIRECTORY_NAME_SUFFIX = "/";
	private static Properties sysProps = new Properties();

	static {
		init();
	}

	private static void init() {
		String configurationFile = System.getProperty(CONFIG_FILE.getKeyName());
		File fileProperties = new File(configurationFile);
		if (!fileProperties.exists() || !fileProperties.canRead()) {
			// Properties haven't been configured right!!!!
			// We want to kill the application and throw an exception.
			throw new RuntimeException(
					"Configurations have not been initialized correctly.["
							+ fileProperties.getAbsolutePath() + "]");
		}
		try {
			sysProps.load(new FileInputStream(fileProperties));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Configuration() {

	}

	public final String getAssurantEsbInbox() {
		return getValueWithSuffix(sysProps.getProperty(
				ASSURANT_ESB_INBOX_LOCATION.getKeyName()),
				DIRECTORY_NAME_SUFFIX);
	}

	public final String getHsrEsbInbox() {
		return getValueWithSuffix(sysProps.getProperty(
				HSR_ESB_INBOX_LOCATION.getKeyName()),
				DIRECTORY_NAME_SUFFIX);
	}

	public final String getOmsEsbInbox() {
		return getValueWithSuffix(sysProps.getProperty(
				OMS_ESB_INBOX_LOCATION.getKeyName()),
				DIRECTORY_NAME_SUFFIX);
	}

	public final String getOutputRootFolder() {
		return getValueWithSuffix(sysProps.getProperty(
				OUTPUT_ROOT_FOLDER.getKeyName()),
				DIRECTORY_NAME_SUFFIX);
	}

	private String getValueWithSuffix(String value, String suffix) {
		if (value == null || suffix == null || value.endsWith(suffix)) return value;
		return String.format("%s%s", value, suffix);
	}
}
