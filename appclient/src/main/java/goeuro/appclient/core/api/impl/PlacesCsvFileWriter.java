package goeuro.appclient.core.api.impl;

import static goeuro.appclient.util.StringUtils.toStringWithQuotationMarks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import goeuro.appclient.exception.SystemException;
import goeuro.appclient.util.StringUtils;
import goeuro.appclient.vo.api.Place;

/**
 * This file writer is for CSV format
 * 
 * @author marconi
 */
@Component
public class PlacesCsvFileWriter implements goeuro.appclient.core.api.PlacesFileWriter {

	private final static Logger LOGGER = Logger.getLogger(PlacesCsvFileWriter.class);

	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final String FILE_HEADER = "id" + COMMA_DELIMITER + "name" + COMMA_DELIMITER + "type"
			+ COMMA_DELIMITER + "latitude" + COMMA_DELIMITER + "longitude";

	@Override
	public void write(String fileName, List<Place> places) {

		LOGGER.info("Entered");
		FileWriter fileWriter = null;

		try {
			if (StringUtils.isBlank(fileName)) {
				throw new SystemException("fileName is blank");
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Parameter fileName = " + toStringWithQuotationMarks(fileName));
			}

			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER);

			LOGGER.info("Creating result file...");

			if (places != null) {
				for (Place place : places) {

					fileWriter.append(NEW_LINE_SEPARATOR);
					fileWriter.append(toString(place.getId()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(place.getName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(place.getType());
					fileWriter.append(COMMA_DELIMITER);

					if (place.getPosition() != null) {
						fileWriter.append(toString(place.getPosition().getLatitude()));
					}
					fileWriter.append(COMMA_DELIMITER);

					if (place.getPosition() != null) {
						fileWriter.append(toString(place.getPosition().getLongitude()));
					}
				}
			}
			LOGGER.info("Success");

		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SystemException("Error while trying to write the file: " + toStringWithQuotationMarks(fileName), e);

		} finally {
			close(fileWriter);
			LOGGER.info("Exiting...");
		}
	}

	private String toString(Number value) {
		return (value == null ? "" : value.toString());
	}

	private void close(Writer writer) {
		if (writer != null) {
			try {
				writer.flush();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			try {
				writer.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
