package goeuro.appclient.core.api;

import java.util.List;

import goeuro.appclient.vo.api.Place;

/**
 * This interface specifies a file writer to save the places in any needed format.
 *  
 * @author marconi
 */
public interface PlacesFileWriter {

	void write(String fileName, List<Place> places);
}
