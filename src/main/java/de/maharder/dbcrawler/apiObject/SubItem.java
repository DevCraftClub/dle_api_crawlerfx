package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Item {

	@JsonProperty("name")
	@JsonAnyGetter
	private String name;
	@JsonProperty("item")
	@JsonAnyGetter
	private List<Item> item = new ArrayList<>();
	@JsonProperty("description")
	@JsonAnyGetter
	private String description;
	@JsonProperty("event")
	@JsonAnyGetter
	private List<Event> event = new ArrayList<>();
	@JsonProperty("protocolProfileBehavior")
	@JsonAnyGetter
	private ProtocolProfileBehavior protocolProfileBehavior;
	@JsonProperty("response")
	@JsonAnyGetter
	private List<String> response = new ArrayList<>();
	@JsonProperty("request")
	@JsonAnyGetter
	private Request request;
	public void addEvent(Event event) {
		if (!this.event.contains(event)) {
			this.event.add(event);
		}
	}
	public void addResponse(String response) {
        if (!this.response.contains(response)) {
            this.response.add(response);
        }
    }
	public void addItem(Item item) {
		if (!this.item.contains(item)) {
			this.item.add(item);
		}
	}

}
