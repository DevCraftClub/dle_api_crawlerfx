package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {

	@JsonProperty("key")
	@JsonAnyGetter
	private String key;
	@JsonProperty("value")
	@JsonAnyGetter
	private String value;
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;
	@JsonProperty("type")
	@JsonAnyGetter
	private String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
