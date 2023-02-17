package de.maharder.dbcrawler.templates;

import de.maharder.dbcrawler.apiObject.*;
import de.maharder.dbcrawler.controller.FileController;
import de.maharder.dbcrawler.controller.Settings;
import de.maharder.dbcrawler.variables.AppOptions;
import de.maharder.dbcrawler.variables.GeneratorAnswer;

import java.io.*;
import java.util.*;

public class ApiTemplater {
	private DbTable table;

	public ApiTemplater(DbTable table) {
		setTable(table);
	}

	public GeneratorAnswer generateTemplate() throws IOException {
		GeneratorAnswer answer = new GeneratorAnswer();
		AppOptions settings = Settings.loadSettings();
		String path = settings.getOutputPath() + "/" + table.getName();
		File output_path = new File(path);

		if (!output_path.exists()) {
			boolean dir_created = output_path.mkdirs();
			if (!dir_created) {
				answer.setSuccess(false);
				answer.addMessage("Невозможно создать папку или получить доступ к пути: " + path);
			}
		}

		if (answer.isSuccess()) {
			FileController fileController = new FileController(settings.getOutputPath(), table.getName() + "_apiitem.json", generateItem());
			answer = fileController.exportJson();
		}


		return answer;
	}

	public Item generateItem() {
		List<Header> attr_header = getHeaders();

		Item item = new Item();
		item.setName(table.getName());

		SubItem getItem = generateSubItem(String.format("/api/v1/%s[/]", table.getName()), "GET", String.format("API GET запрос к таблице ```dle_%s``` для вывода и фильтрации данных", table.getName()), generateEmptyBody(), generateUrl(), attr_header);
		SubItem postItem = generateSubItem(String.format("/api/v1/%s[/]", table.getName()), "POST", String.format("API POST запрос к таблице ```dle_%s``` для создания нового объекта Указываем ID объекта в ссылке запроса", table.getName()), generateBody(), generateUrl(), new ArrayList<>());
		SubItem putItem = generateSubItem(String.format("/api/v1/%s/1[/]", table.getName()), "PUT", String.format("API PUT запрос к таблице ```dle_%s``` для её изменения. Указываем ID объекта в ссылке запроса", table.getName()), generateBody(), generateUrl(1), new ArrayList<>());
		SubItem deleteItem = generateSubItem(String.format("/api/v1/%s/1[/]", table.getName()), "DELETE", String.format("API DELETE запрос к таблице ```dle_%s``` для её удаления. Указываем ID объекта в ссылке запроса", table.getName()), new Body(), generateUrl(1), new ArrayList<>());

		item.addItem(getItem);
		item.addItem(postItem);
		item.addItem(putItem);
		item.addItem(deleteItem);

		item.setDescription(String.format("Данные для доступа к таблице ```dle_%s```", table.getName()));

		item.addEvent(generateEvent("prerequest", "text/javascript"));
		item.addEvent(generateEvent("test", "text/javascript"));

		return item;
	}

	private Event generateEvent(String name, String type) {
		Event event = new Event();
        return event.generateEvent(name, type);
	}

	private Response generateResponse(String name, String method, List<Header> header, Body body) {
		Response response = new Response();
		response.setName(name);

		Header apiHeader = new Header();
		apiHeader.setKey("x-api-key");
		apiHeader.setValue("b1a57-77e2a-aa048-82a9f-f542d-dfd95-522b3");
		apiHeader.setType("string");
		apiHeader.setDescription("API ключ");

		header.add(apiHeader);

		OriginalRequest originalRequest = new OriginalRequest();
		originalRequest.setMethod(method);
		originalRequest.setHeader(header);
		originalRequest.setBody(body);

		response.setOriginalRequest(originalRequest);
		response.set_postman_previewlanguage("php");
		response.setHeader(null);
		response.setCookie(new ArrayList<>());
		response.setBody(null);

		return response;
	}

	private SubItem generateSubItem(String name, String method, String description, Body body, Url url, List<Header> header) {
		SubItem subItem = new SubItem();
		subItem.setName(name);

		ProtocolProfileBehavior getProtocolProfileBehavior = new ProtocolProfileBehavior();
		getProtocolProfileBehavior.setDisableBodyPruning(true);
		subItem.setProtocolProfileBehavior(getProtocolProfileBehavior);

		Request request = new Request();
		request.setAuth(new Auth().generateAuth());
		request.setMethod(method.toUpperCase());
		request.setHeader(header);
		request.setDescription(description);
		request.setBody(body);
		request.setUrl(url);

		subItem.setRequest(request);
		subItem.addResponse(generateResponse(name, method, header, body));

		return subItem;
	}

	private List<Header> getHeaders() {
		List<Header> attr_header = new ArrayList<Header>();
		for (DbTableAttribute attr : table.getAttributes()) {

			Header attrHeader = new Header();
			attrHeader.setKey(attr.getName());
			if (attr.getType().equals("integer")) {
				attrHeader.setValue("1");
			} else {
				attrHeader.setValue("Текстовой формат");
			}
			attrHeader.setType(attr.getType());
			attrHeader.setDescription(String.format("Поле атрибута: %s", attr.getName()));

			attr_header.add(attrHeader);

		}

		return attr_header;
	}

	private Body generateEmptyBody() {
		Body body = new Body();
		body.setMode("urlencoded");

		return body;
	}

	private Body generateBody() {
		Body body = new Body();
		body.setMode("urlencoded");
		List<Urlencoded> urlencoded_list = new ArrayList<Urlencoded>();

		for (Header h : getHeaders()) {

			boolean required = false;

			Optional<DbTableAttribute> attr = table.getAttributes().stream().filter(a -> a.getName().equals(h.getKey())).findFirst();
			if (attr.isPresent()) {
				required = attr.get().isRequired();
				if (attr.get().isIdKey()) {
					continue;
				}
			}

			Urlencoded urlencoded = new Urlencoded();
			urlencoded.setKey(h.getKey());
			urlencoded.setValue(h.getValue());
			urlencoded.setType(h.getType());

			urlencoded.setDescription(String.format("%s%s", h.getDescription(), (required ? " (Обязательно к заполнению)" : "")));
			urlencoded_list.add(urlencoded);
		}

		body.setUrlencoded(urlencoded_list);

		return body;
	}

	private Url generateUrl() {
		Url url = new Url();
		url.setRaw(String.format("https://dle.devcraft.club/api/v1/%s/", table.getName()));
		url.setProtocol("https");

		for (String h : new String[]{"dle", "devcraft", "club"}) url.addHost(h);
		for (String p : new String[]{"api", "v1", table.getName(), ""}) url.addPath(p);

		return url;
	}

	private Url generateUrl(int id) {
		Url url = generateUrl();
		url.setRaw(String.format("https://dle.devcraft.club/api/v1/%s/%d/", table.getName(), id));
		url.addPath(String.format("%d", id));

		return url;
	}

	public DbTable getTable() {
		return table;
	}

	public void setTable(DbTable table) {
		this.table = table;
	}
}
