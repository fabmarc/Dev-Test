package goeuro.appclient.util;

public class StringUtils {

	private static final String QUOTE_SYMBOL = "\"";
	private static final String NULL_STRING = "null";

	/*
	 * Done here just to not insert more dependencies
	 */
	public static boolean isBlank(String stringValue) {

		if (stringValue != null && !stringValue.isEmpty()) {
			for (int i = 0; i < stringValue.length(); i++) {
				if (!Character.isSpaceChar(stringValue.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	public static String toStringWithQuote(String stringValue) {
		return (stringValue == null ? NULL_STRING : QUOTE_SYMBOL + stringValue + QUOTE_SYMBOL);
	}

}
