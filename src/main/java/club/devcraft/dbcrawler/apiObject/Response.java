package club.devcraft.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Response {
	@JsonProperty
	private String name;
	@JsonProperty
	private OriginalRequest originalRequest;
	@JsonProperty
	private String _postman_previewlanguage;
	@JsonProperty
	private Object header;
	@JsonProperty
	private List<Object> cookie = new ArrayList<Object>();
	@JsonProperty
	private Object body;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OriginalRequest getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(OriginalRequest originalRequest) {
		this.originalRequest = originalRequest;
	}

	public String get_postman_previewlanguage() {
		return _postman_previewlanguage;
	}

	public void set_postman_previewlanguage(String _postman_previewlanguage) {
		this._postman_previewlanguage = _postman_previewlanguage;
	}

	public Object getHeader() {
		return header;
	}

	public void setHeader(Object header) {
		this.header = header;
	}

	public List<Object> getCookie() {
		return cookie;
	}

	public void setCookie(List<Object> cookie) {
		this.cookie = cookie;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
}
