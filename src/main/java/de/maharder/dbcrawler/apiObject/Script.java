package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Script {
	@JsonProperty("type")
	@JsonAnyGetter
	private String type;
	@JsonProperty("exec")
	@JsonAnyGetter
	private List<String> exec = new ArrayList<>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getExec() {
		return exec;
	}

	public void setExec(List<String> exec) {
		this.exec = exec;
	}

	public void addExec(String exec) {
		if(!this.exec.contains(exec)) {
			this.exec.add(exec);
		}
	}
}
