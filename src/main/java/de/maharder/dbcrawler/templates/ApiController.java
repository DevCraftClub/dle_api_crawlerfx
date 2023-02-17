package de.maharder.dbcrawler.templates;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.maharder.dbcrawler.apiObject.*;

import java.util.ArrayList;
import java.util.List;

public class ApiController {
	@JsonProperty("info")
	@JsonAnyGetter
	private ApiInfo apiInfo;
	@JsonProperty
	@JsonAnyGetter
    private List<Item> item = new ArrayList<>();
	@JsonProperty
	@JsonAnyGetter
	private Auth auth;
	@JsonProperty
	@JsonAnyGetter
    private List<Event> event = new ArrayList<>();
	@JsonProperty
    @JsonAnyGetter
    private List<ApiVariable> variable = new ArrayList<>();

	private DbTable table;

	public ApiController(DbTable table) {
		setTable(table);
	}

	public ApiInfo getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(ApiInfo apiInfo) {
		this.apiInfo = apiInfo;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

	public List<ApiVariable> getVariable() {
		return variable;
	}

	public void setVariable(List<ApiVariable> variable) {
		this.variable = variable;
	}

	public void addVariable(ApiVariable variable) {
		if (!this.variable.contains(variable)) {
			this.variable.add(variable);
		}
	}

	public void addEvent(Event event) {
        if (!this.event.contains(event)) {
            this.event.add(event);
        }
    }

	public void addItem(Item item) {
        if (!this.item.contains(item)) {
            this.item.add(item);
        }
    }

	public DbTable getTable() {
		return table;
	}

	public void setTable(DbTable table) {
		this.table = table;
	}
}
