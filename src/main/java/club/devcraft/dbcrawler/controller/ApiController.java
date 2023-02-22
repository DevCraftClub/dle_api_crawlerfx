package club.devcraft.dbcrawler.controller;

import club.devcraft.dbcrawler.apiObject.*;
import club.devcraft.dbcrawler.templates.DbTable;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.devcraft.dbcrawler.templates.ApiTemplater;
import club.devcraft.dbcrawler.variables.AppOptions;
import java.util.ArrayList;
import java.util.List;

public class ApiController {
	@JsonProperty("info")
	@JsonAnyGetter
	private ApiInfo info;
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

	public ApiController() {
	}

	public ApiInfo getApiInfo() {
		return info;
	}

	public void setApiInfo(ApiInfo apiInfo) {
		this.info = apiInfo;
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

	public void generateApiObject(List<DbTable> tables) {

		AppOptions settings = Settings.loadSettings();
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setName(settings.getApiFileName());
		setApiInfo(apiInfo);

		for (DbTable table: tables) {
			ApiTemplater api = new ApiTemplater(table);
			addItem(api.generateItem());
		}

		setAuth(new Auth().generateAuth());

		Event preEvent = new Event();
		List<String> exec = new ArrayList<>();
		exec.add("pm.variables.get(\"contentType\");");
		addEvent(preEvent.generateEvent("prerequest", "text/javascript", exec));

		Event testEvent = new Event();
		addEvent(testEvent.generateEvent("test", "text/javascript"));

		ApiVariable contentType = new ApiVariable();
		contentType.setKey("contentType");
		contentType.setValue("Content-Type");
		addVariable(contentType);
	}
}
