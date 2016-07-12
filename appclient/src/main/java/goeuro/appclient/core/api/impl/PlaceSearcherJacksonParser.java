package goeuro.appclient.core.api.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import goeuro.appclient.core.api.PlaceSearcherParser;
import goeuro.appclient.exception.SystemException;
import goeuro.appclient.vo.api.Place;

/**
 * This parser uses Jackson technology to convert string (JSON) to object (Place)
 * 
 * @author marconi
 */
@Component
public class PlaceSearcherJacksonParser implements PlaceSearcherParser {

	private final static Logger LOGGER = Logger.getLogger(PlaceSearcherJacksonParser.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<Place> toList(String json) {

		LOGGER.info("Entered");

		try {
			Place[] placeArray = toArray(json);

			if (placeArray != null) {

				LOGGER.info("Converting to list...");
				List<Place> placeList = Arrays.asList(placeArray);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("List size = " + placeList.size());
				}

				LOGGER.info("Success");
				return placeList;
			}
			return null;

		} finally {
			LOGGER.info("Exiting...");
		}
	}

	@Override
	public Place[] toArray(String json) {

		LOGGER.info("Entered");

		// JSON from String to Object
		try {
			if (json == null) {
				throw new SystemException("json == null");
			}

			LOGGER.info("Converting to array...");
			goeuro.appclient.vo.api.impl.Place[] places = mapper.readValue(json,
					goeuro.appclient.vo.api.impl.Place[].class);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Array length = " + places.length);
			}

			LOGGER.info("Success");
			return places;

		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SystemException("Error while trying to convert JSON (String) to Object ("
					+ goeuro.appclient.vo.api.impl.Place[].class.getName() + ")", e);

		} finally {
			LOGGER.info("Exiting...");
		}
	}

}
