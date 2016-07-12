package goeuro.appclient.vo.api.impl;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(value = { "key", "fullName", "iata_airport_code", "countryId", "country", "locationId",
		"inEurope", "countryCode", "coreCountry", "distance", "names", "alternativeNames" })
public class Place implements goeuro.appclient.vo.api.Place {

	public Place() {
	}

	public Place(Long id, String name, String type, Position position) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.position = position;
	}

	@JsonProperty(value = "_id")
	private Long id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "type")
	private String type;

	@JsonProperty(value = "geo_position")
	private Position position;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", type=" + type + ", position=" + position + "]";
	}

}
