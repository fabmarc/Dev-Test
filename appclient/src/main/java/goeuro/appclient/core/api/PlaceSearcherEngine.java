package goeuro.appclient.core.api;

import java.util.List;

import goeuro.appclient.exception.ValidationException;
import goeuro.appclient.vo.api.Place;

/**
 * This interface specifies an engine which search for suggestions.
 *  
 * @author marconi
 */
public interface PlaceSearcherEngine {

	
	List<Place> suggestPlacesByCity(String city) throws ValidationException;

	String connectAndRespond(String city) throws ValidationException;

	PlaceSearcherParser getParser();

}
