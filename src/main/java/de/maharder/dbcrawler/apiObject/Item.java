package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Item {

	@JsonProperty("name")
	@JsonAnyGetter
	private String name;
	@JsonProperty("item")
	@JsonAnyGetter
	private List<SubItem> item = new ArrayList<>();
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;
	@JsonProperty("event")
	@JsonAnyGetter
	private List<Event> event = new ArrayList<>();
	public void addEvent(Event event) {
		if (!this.event.contains(event)) {
			this.event.add(event);
		}
	}
	public void addItem(SubItem item) {
		if (!this.item.contains(item)) {
			this.item.add(item);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubItem> getItem() {
		return item;
	}

	public void setItem(List<SubItem> item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}
}
