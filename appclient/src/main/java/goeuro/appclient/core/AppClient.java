package goeuro.appclient.core;

import static goeuro.appclient.util.StringUtils.toStringWithQuote;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import goeuro.appclient.core.api.PlaceSearcherEngine;
import goeuro.appclient.core.api.PlacesFileWriter;
import goeuro.appclient.exception.ValidationException;
import goeuro.appclient.vo.api.Place;

public class AppClient {

	private final static Logger LOGGER = Logger.getLogger(AppClient.class);

	@Autowired
	private PlaceSearcherEngine searcher;

	@Autowired
	private PlacesFileWriter writer;

	public void run(String[] args) {

		LOGGER.info("Entered");

		try {

			if (args.length == 0) {

				LOGGER.info("No argument typed");
				showSintaxeMessage();
				return;
			}

			String cityName = args[0];
			List<Place> places = null;

			try {

				LOGGER.info("Searching places...");
				places = searcher.suggestPlacesByCity(cityName);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Places found = " + places);
				}

			} catch (ValidationException e) {

				LOGGER.error(e.getMessage(), e);
				showSintaxeMessage();
				return;
			}

			String fileName = AppClientProperties.getResultFileName();

			if (LOGGER.isDebugEnabled()) {

				LOGGER.debug("Property " + toStringWithQuote(AppClientProperties.GOEURO_APPCLIENT_RESULT_FILE_NAME)
						+ " = " + toStringWithQuote(fileName));
			}

			writer.write(fileName, places);

			String message = fileName + " is created";
			LOGGER.info(message);

			System.out.println(message);

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);
			System.out.println("Occurred an error during execution. See the log file.");

		} finally {
			LOGGER.info("Exiting...");
		}
	}

	private void showSintaxeMessage() {

		System.out.println("Sintaxe: java -jar GoEuroTest.jar CITY_NAME");
		System.out.println("Parameter CITY_NAME is required.");
		System.out.println("Example: java -jar GoEuroTest.jar Berlin");
	}

	public PlaceSearcherEngine getSearcher() {
		return searcher;
	}

	public void setSearcher(PlaceSearcherEngine searcher) {
		this.searcher = searcher;
	}

	public PlacesFileWriter getWriter() {
		return writer;
	}

	public void setWriter(PlacesFileWriter writer) {
		this.writer = writer;
	}

}
