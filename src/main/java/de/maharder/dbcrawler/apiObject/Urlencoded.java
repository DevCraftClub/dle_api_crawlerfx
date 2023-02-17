package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Urlencoded {

	@JsonProperty("key")
	@JsonAnyGetter
	private String key;
	@JsonProperty("value")
	@JsonAnyGetter
	private String value;
	@JsonProperty("type")
	@JsonAnyGetter
	private String type;
	@JsonProperty("disabled")
	@JsonAnyGetter
	private boolean disabled;
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
