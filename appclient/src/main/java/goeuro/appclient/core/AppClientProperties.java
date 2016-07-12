package goeuro.appclient.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AppClientProperties {
	
	public static final String GOEURO_SUGGEST_API_ENDPOINT = "goeuro.suggest.api.endpoint";

	public static final String GOEURO_SUGGEST_RESPONSE_ENCODING = "goeuro.suggest.response.encoding";
	
	public static final String GOEURO_APPCLIENT_RESULT_FILE_NAME = "goeuro.appclient.result.file.name";
	
	private static final String BUNDLE_NAME = "goeuro.appclient.core.appclient";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private AppClientProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getSuggestEndpoint() {
		return getString(GOEURO_SUGGEST_API_ENDPOINT);
	}

	public static String getResponseEncoding() {
		return getString(GOEURO_SUGGEST_RESPONSE_ENCODING);
	}

	public static String getResultFileName() {
		return getString(GOEURO_APPCLIENT_RESULT_FILE_NAME);
	}

}
