package goeuro.appclient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import goeuro.appclient.core.AppClient;
import goeuro.appclient.core.AppClientConfig;
import goeuro.appclient.core.api.PlaceSearcherEngine;
import goeuro.appclient.exception.ValidationException;
import goeuro.appclient.vo.api.Place;
import goeuro.appclient.vo.api.impl.Position;

/**
 * Unit test for PlaceSearcherEngine component.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaceSearcherEngineTest {

	private PlaceSearcherEngine searcher;

	private AppClient appClient;

	@Before
	public void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(AppClientConfig.class);
		context.refresh();

		appClient = (AppClient) context.getBean("mainRunner");
		context.close();

		searcher = appClient.getSearcher();
	}

	@Test(expected = ValidationException.class)
	public void testSearcherWithNull() throws ValidationException {
		searcher.suggestPlacesByCity(null);
	}

	@Test(expected = ValidationException.class)
	public void testSearcherWithEmpty() throws ValidationException {
		searcher.suggestPlacesByCity("");
	}

	@Test(expected = ValidationException.class)
	public void testSearcherWithBlank() throws ValidationException {
		searcher.suggestPlacesByCity("     ");
	}

	@Test
	public void testSearcherWithValidParameterWithNoPlace() throws ValidationException, IOException {

		String json = "[]";
		ArrayList<Place> listPlaceOne = new ArrayList<Place>(0);
		PlaceSearcherEngine searcherMock = spy(searcher);

		String cityName = "Berlin";
		when(searcherMock.connectAndRespond(cityName)).thenReturn(json);

		List<Place> listPlaceTwo = searcherMock.suggestPlacesByCity(cityName);

		assertEquals(listPlaceOne, listPlaceTwo);
	}

	@Test
	public void testSearcherWithValidParameterWithTwoPlaces() throws ValidationException, IOException {

		ArrayList<Place> listPlaceOne = new ArrayList<>(2);
		listPlaceOne
				.add(new goeuro.appclient.vo.api.impl.Place(123L, "Berlin", "location", new Position(123.0D, 123.0D)));
		listPlaceOne
				.add(new goeuro.appclient.vo.api.impl.Place(456L, "Berlin", "station", new Position(456.0D, 456.0D)));

		String json = generateWellFormedJson(listPlaceOne);
		PlaceSearcherEngine searcherMock = spy(searcher);

		String cityName = "Berlin";
		when(searcherMock.connectAndRespond(cityName)).thenReturn(json);

		List<Place> listPlaceTwo = searcherMock.suggestPlacesByCity(cityName);

		assertEquals(listPlaceOne, listPlaceTwo);
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
