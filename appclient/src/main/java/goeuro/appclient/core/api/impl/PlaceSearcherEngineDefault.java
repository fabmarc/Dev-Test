package goeuro.appclient.core.api.impl;

import static goeuro.appclient.util.StringUtils.isBlank;
import static goeuro.appclient.util.StringUtils.toStringWithQuotationMarks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import goeuro.appclient.core.AppClientProperties;
import goeuro.appclient.core.api.PlaceSearcherEngine;
import goeuro.appclient.core.api.PlaceSearcherParser;
import goeuro.appclient.exception.SystemException;
import goeuro.appclient.exception.ValidationException;
import goeuro.appclient.vo.api.Place;

/**
 * This engine encapsulates the technology and/or tool used to connect to the data source
 * 
 * @author marconi
 */
@Component
public class PlaceSearcherEngineDefault implements PlaceSearcherEngine {

	private final static Logger LOGGER = Logger.getLogger(PlaceSearcherEngineDefault.class);

	@Autowired
	private PlaceSearcherParser parser;

	@Override
	public List<Place> suggestPlacesByCity(String city) throws ValidationException {

		LOGGER.info("Entered");
		try {

			String json = connectAndRespond(city);
			List<Place> places = parser.toList(json);

			LOGGER.info("Success");
			return places;

		} finally {
			LOGGER.info("Exiting...");
		}

	}

	private void close(Scanner scanner) {
		try {
			if (scanner != null) {
				scanner.close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public PlaceSearcherParser getParser() {
		return parser;
	}

	public void setParser(PlaceSearcherParser parser) {
		this.parser = parser;
	}

	@Override
	public String connectAndRespond(String city) throws ValidationException {

		LOGGER.info("Entered");

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Parameter city = " + toStringWithQuotationMarks(city));
		}

		if (isBlank(city)) {
			throw new ValidationException("Parameter city is required");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Property " + toStringWithQuotationMarks(AppClientProperties.GOEURO_SUGGEST_API_ENDPOINT) + " = "
					+ toStringWithQuotationMarks(AppClientProperties.getSuggestEndpoint()));
		}

		String endpoint = AppClientProperties.getSuggestEndpoint() + city;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Endpoint to call = " + toStringWithQuotationMarks(endpoint));
		}

		Scanner scanner = null;
		try {

			LOGGER.info("Connection to Endpoint...");

			URL url = new URL(endpoint);
			URLConnection conn = url.openConnection();
			conn.connect();
			
			InputStream input = conn.getInputStream();
			String encoding = AppClientProperties.getResponseEncoding();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Property " + toStringWithQuotationMarks(AppClientProperties.GOEURO_SUGGEST_RESPONSE_ENCODING)
						+ " = " + toStringWithQuotationMarks(encoding));
			}

			(scanner = new Scanner(input, encoding)).useDelimiter("\\A");
			String json = scanner.hasNext() ? scanner.next() : "";

			LOGGER.debug("Response (JSON) = " + json);

			LOGGER.info("Success");
			return json;

		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SystemException("Error while trying to connect to Endpoint: " + toStringWithQuotationMarks(endpoint), e);

		} finally {
			close(scanner);
			LOGGER.info("Exiting...");
		}

	}

}
