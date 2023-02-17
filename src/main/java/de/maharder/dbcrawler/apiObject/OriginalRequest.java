package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OriginalRequest{
	@JsonProperty
	private String method;
	@JsonProperty
	private List<Header> header = new ArrayList<Header>();
	@JsonProperty
	private Body body;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Header> getHeader() {
		return header;
	}

	public void setHeader(List<Header> header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}