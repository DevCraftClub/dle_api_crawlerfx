package club.devcraft.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Body {
	@JsonProperty("mode")
	@JsonAnyGetter
	private String mode;
	@JsonProperty("urlencoded")
	@JsonAnyGetter
	private List<Urlencoded> urlencoded = new ArrayList<>();

	public void addUrlencoded(Urlencoded urlencoded) {
		if(!this.urlencoded.contains(urlencoded)) this.urlencoded.add(urlencoded);
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Urlencoded> getUrlencoded() {
		return urlencoded;
	}

	public void setUrlencoded(List<Urlencoded> urlencoded) {
		this.urlencoded = urlencoded;
	}
}
