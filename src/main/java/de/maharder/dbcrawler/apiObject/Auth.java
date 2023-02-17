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

	public Auth generateAuth() {
		Auth auth = new Auth();
		auth.setType("apikey");
		auth.addApikey(generateKeyValue("key", "x-api-key"));
		auth.addApikey(generateKeyValue("value", "b1a57-77e2a-aa048-82a9f-f542d-dfd95-522b3"));

		return auth;
	}

	private KeyValue generateKeyValue(String key, String value) {
		return generateKeyValue(key, value, "string");
	}

	private KeyValue generateKeyValue(String key, String value, String type) {
		KeyValue keyValue = new KeyValue();
		keyValue.setKey(key);
		keyValue.setValue(value);
		keyValue.setType(type);

		return keyValue;
	}
}
