package goeuro.appclient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import goeuro.appclient.core.AppClient;
import goeuro.appclient.core.AppClientConfig;
import goeuro.appclient.core.api.PlacesFileWriter;
import goeuro.appclient.exception.SystemException;
import goeuro.appclient.exception.ValidationException;
import goeuro.appclient.vo.api.Place;
import goeuro.appclient.vo.api.impl.Position;

/**
 * Unit test for PlacesFileWriter component.
 */
public class PlacesFileWriterTest {

	private PlacesFileWriter writer;

	@Before
	public void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(AppClientConfig.class);
		context.refresh();

		AppClient appClient = (AppClient) context.getBean("mainRunner");
		context.close();

		writer = appClient.getWriter();
	}

	@Test(expected = SystemException.class)
	public void testWriterPassingFileNameAsNull() {
		writer.write(null, null);
	}

	@Test(expected = SystemException.class)
	public void testWriterPassingFileNameAsEmpty() {
		writer.write("", null);
	}

	@Test(expected = SystemException.class)
	public void testWriterPassingFileNameAsBlank() {
		writer.write("     ", null);
	}

	@Test(expected = SystemException.class)
	public void testWriterPassingInvalidFileName() {
		writer.write("Result/.csv", null);
	}

	@Test
	public void testWriterPassingValidFileNameAndPlacesAsNull() throws FileNotFoundException, IOException {

		String fileName = "Result.csv";
		ArrayList<Place> places = null;

		writer.write(fileName, places);

		String fileContent = rescueFileContent(fileName);
		String expectedContent = generateExpectedContent(places);

		Assert.assertEquals(expectedContent, fileContent);
	}

	@Test
	public void testWriterPassingValidFileNameAndPlacesAsEmpty() throws FileNotFoundException, IOException {

		String fileName = "Result.csv";
		ArrayList<Place> places = new ArrayList<>(0);

		writer.write(fileName, places);

		String fileContent = rescueFileContent(fileName);
		String expectedContent = generateExpectedContent(places);

		Assert.assertEquals(expectedContent, fileContent);
	}

	@Test
	public void testWriterPassingValidFileNameAndTwoPlaces() throws ValidationException, IOException {

		ArrayList<Place> places = new ArrayList<>(2);
		places.add(new goeuro.appclient.vo.api.impl.Place(123L, "Berlin", "location", new Position(123.0D, 123.0D)));
		places.add(new goeuro.appclient.vo.api.impl.Place(456L, "Berlin", "station", new Position(456.0D, 456.0D)));

		String fileName = "Result.csv";
		writer.write(fileName, places);
		
		String fileContent = rescueFileContent(fileName);
		String expectedContent = generateExpectedContent(places);

		Assert.assertEquals(expectedContent, fileContent);
	}

	private String generateExpectedContent(ArrayList<Place> places) {

		StringBuilder sb = new StringBuilder();
		sb.append("id,name,type,latitude,longitude");
		if (places != null) {
			for (Place place : places) {
				sb.append("\n").append(place.getId()).append(",");
				sb.append(place.getName()).append(",").append(place.getType()).append(",");
				sb.append(place.getPosition().getLatitude()).append(",");
				sb.append(place.getPosition().getLongitude());
			}
		}
		return sb.toString();
	}

	private String rescueFileContent(String fileName) throws FileNotFoundException, IOException {

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (sb.length() != 0) sb.append("\n");
				sb.append(line);
			}
		}
		return sb.toString();
	}

}
