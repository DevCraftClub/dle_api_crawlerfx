package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SubItem {

	@JsonProperty("name")
	@JsonAnyGetter
	private String name;
	@JsonProperty("protocolProfileBehavior")
	@JsonAnyGetter
	private ProtocolProfileBehavior protocolProfileBehavior;
	@JsonProperty("request")
	@JsonAnyGetter
	private Request request;
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;
	@JsonProperty("response")
	@JsonAnyGetter
	private List<Response> response = new ArrayList<>();
	public void addResponse(Response response) {
        if (!this.response.contains(response)) {
            this.response.add(response);
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProtocolProfileBehavior getProtocolProfileBehavior() {
		return protocolProfileBehavior;
	}

	public void setProtocolProfileBehavior(ProtocolProfileBehavior protocolProfileBehavior) {
		this.protocolProfileBehavior = protocolProfileBehavior;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Response> getResponse() {
		return response;
	}

	public void setResponse(List<Response> response) {
		this.response = response;
	}
}
