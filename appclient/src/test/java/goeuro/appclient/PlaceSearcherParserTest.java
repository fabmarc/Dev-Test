package goeuro.appclient;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import goeuro.appclient.core.AppClient;
import goeuro.appclient.core.AppClientConfig;
import goeuro.appclient.core.api.PlaceSearcherParser;
import goeuro.appclient.exception.SystemException;
import goeuro.appclient.vo.api.Place;
import goeuro.appclient.vo.api.impl.Position;

/**
 * Unit test for PlaceSearcherParser component.
 */
public class PlaceSearcherParserTest {

	private PlaceSearcherParser parser;

	@Before
	public void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(AppClientConfig.class);
		context.refresh();

		AppClient appClient = (AppClient) context.getBean("mainRunner");
		context.close();

		parser = appClient.getSearcher().getParser();
	}

	@Test
	public void testConvertToArrayPassingJsonWithObjects() {

		Place[] arrayPlaceOne = new goeuro.appclient.vo.api.impl.Place[] {
				new goeuro.appclient.vo.api.impl.Place(123L, "Berlin", "location", new Position(123.0D, 123.0D)),
				new goeuro.appclient.vo.api.impl.Place(456L, "Berlin", "station", new Position(456.0D, 456.0D)) };

		String json = generateWellFormedJson(arrayPlaceOne);

		Place[] arrayPlaceTwo = parser.toArray(json);

		assertArrayEquals(arrayPlaceOne, arrayPlaceTwo);
	}

	@Test
	public void testConvertToArrayPassingJsonWithNoObject() {

		Place[] arrayPlaceOne = new goeuro.appclient.vo.api.impl.Place[] {};

		String json = generateWellFormedJson(arrayPlaceOne);

		Place[] arrayPlaceTwo = parser.toArray(json);

		assertArrayEquals(arrayPlaceOne, arrayPlaceTwo);

		json = generateWellFormedJson((Place[]) null);

		arrayPlaceTwo = parser.toArray(json);

		assertArrayEquals(arrayPlaceOne, arrayPlaceTwo);
	}

	@Test(expected = SystemException.class)
	public void testConvertToArrayPassingNull() {
		parser.toArray(null);
	}

	@Test(expected = SystemException.class)
	public void testConvertToArrayPassingEmpty() {
		parser.toArray("");
	}

	@Test(expected = SystemException.class)
	public void testConvertToArrayPassingBlank() {
		parser.toArray("   ");
	}

	@Test(expected = SystemException.class)
	public void testConvertToArrayPassingMalformedJson() {
		parser.toArray("[{\"xyx\":\"xyz\"},{\"name\":\"Berlin\"}]");
	}

	private String generateWellFormedJson(Place[] places) {

		StringBuilder json = new StringBuilder();
		boolean isFirstGone = false;
		json.append("[");

		if (places != null) {
			for (Place place : places) {
				if (isFirstGone) json.append(",");
				json.append("{\"_id\":\"").append(place.getId());
				json.append("\", \"name\":\"").append(place.getName());
				json.append("\", \"type\":\"").append(place.getType());
				json.append("\", \"geo_position\":{\"latitude\":\"").append(place.getPosition().getLatitude());
				json.append("\", \"longitude\":\"").append(place.getPosition().getLongitude());
				json.append("\"}}");
				isFirstGone = true;
			}
		}
		return json.append("]").toString();
	}

	@Test
	public void testConvertToListPassingJsonWithObjects() {

		ArrayList<Place> listPlaceOne = new ArrayList<>(2);
		listPlaceOne.add(new goeuro.appclient.vo.api.impl.Place(123L, "Berlin", "location", new Position(123.0D, 123.0D)));
		listPlaceOne.add(new goeuro.appclient.vo.api.impl.Place(456L, "Berlin", "station", new Position(456.0D, 456.0D)));

		String json = generateWellFormedJson(listPlaceOne);

		List<Place> listPlaceTwo = parser.toList(json);

		assertEquals(listPlaceOne, listPlaceTwo);
	}

	@Test
	public void testConvertToListPassingJsonWithNoObject() {

		ArrayList<Place> listPlaceOne = new ArrayList<>(2);

		String json = generateWellFormedJson(listPlaceOne);

		List<Place> listPlaceTwo = parser.toList(json);

		assertEquals(listPlaceOne, listPlaceTwo);

		json = generateWellFormedJson((List<Place>) null);

		listPlaceTwo = parser.toList(json);

		assertEquals(listPlaceOne, listPlaceTwo);
	}

	@Test(expected = SystemException.class)
	public void testConvertToListPassingNull() {
		parser.toList(null);
	}

	@Test(expected = SystemException.class)
	public void testConvertToListPassingEmpty() {
		parser.toList("");
	}

	@Test(expected = SystemException.class)
	public void testConvertToListPassingBlank() {
		parser.toList("   ");
	}

	@Test(expected = SystemException.class)
	public void testConvertToListPassingMalformedJson() {
		parser.toList("[{\"xyx\":\"xyz\"},{\"name\":\"Berlin\"}]");
	}

	private String generateWellFormedJson(List<Place> places) {

		StringBuilder json = new StringBuilder();
		boolean isFirstGone = false;
		json.append("[");

		if (places != null) {
			for (Place place : places) {
				if (isFirstGone) json.append(",");
				json.append("{\"_id\":\"").append(place.getId());
				json.append("\", \"name\":\"").append(place.getName());
				json.append("\", \"type\":\"").append(place.getType());
				json.append("\", \"geo_position\":{\"latitude\":\"").append(place.getPosition().getLatitude());
				json.append("\", \"longitude\":\"").append(place.getPosition().getLongitude());
				json.append("\"}}");
				isFirstGone = true;
			}
		}
		return json.append("]").toString();
	}

}
