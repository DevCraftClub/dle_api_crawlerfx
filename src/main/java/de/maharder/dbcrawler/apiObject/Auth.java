package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Auth {
	@JsonProperty("type")
	@JsonAnyGetter
	private String type;
	@JsonProperty("apikey")
	@JsonAnyGetter
	private List<KeyValue> apikey = new ArrayList<>();

	public void addApikey(KeyValue keyValue) {
		if(!this.apikey.contains(keyValue)) {
			this.apikey.add(keyValue);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<KeyValue> getApikey() {
		return apikey;
	}

	public void setApikey(List<KeyValue> apikey) {
		this.apikey = apikey;
	}
}
