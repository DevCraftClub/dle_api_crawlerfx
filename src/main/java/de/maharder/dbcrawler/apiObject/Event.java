package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Event {
	@JsonProperty("listen")
	@JsonAnyGetter
	private String listen;
	@JsonProperty("script")
	@JsonAnyGetter
	private Script script;

	public String getListen() {
		return listen;
	}

	public void setListen(String listen) {
		this.listen = listen;
	}

	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public Event generateEvent(String name, String type) {

		return generateEvent(name, type, new ArrayList<>());
	}

	public Event generateEvent(String name, String type, List<String> exec) {
		setListen(name);

		Script script = new Script();
		script.setType(type);
		exec.forEach(script::addExec);
		script.setExec(exec);

		setScript(script);
		return this;
	}
}
