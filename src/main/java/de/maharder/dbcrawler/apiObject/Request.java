package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Request {

	@JsonProperty("auth")
    @JsonAnyGetter
    private Auth auth;
	@JsonProperty("method")
	@JsonAnyGetter
	private String method;
	@JsonProperty("header")
	@JsonAnyGetter
	private List<Header> header = new ArrayList<>();
	@JsonProperty("body")
	@JsonAnyGetter
	private Body body;
	@JsonProperty("url")
	@JsonAnyGetter
	private Url url;
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;

	public void addHeader(Header header) {
		if (!this.header.contains(header)) {
			this.header.add(header);
		}
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

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

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
