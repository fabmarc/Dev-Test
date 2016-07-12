package goeuro.appclient.core.api;

import java.util.List;

import goeuro.appclient.vo.api.Place;

/**
 * This interface specifies a parser to convert a string (XML, JSON etc) to objects. 
 *  
 * @author marconi
 */
public interface PlaceSearcherParser {

	List<Place> toList(String string);

	Place[] toArray(String string);
}
