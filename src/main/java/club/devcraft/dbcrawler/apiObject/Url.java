package club.devcraft.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Url {
	@JsonProperty("raw")
	@JsonAnyGetter
	private String raw;
	@JsonProperty("protocol")
	@JsonAnyGetter
	private String protocol;
	@JsonProperty("host")
	@JsonAnyGetter
	private List<String> host = new ArrayList<>();
	@JsonProperty("path")
	@JsonAnyGetter
	private List<String> path = new ArrayList<>();
	public void addHost(String host) {
		if (!this.host.contains(host)) this.host.add(host);
	}
	public void addPath(String path) {
		if (!this.path.contains(path)) this.path.add(path);
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public List<String> getHost() {
		return host;
	}

	public void setHost(List<String> host) {
		this.host = host;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}
}
