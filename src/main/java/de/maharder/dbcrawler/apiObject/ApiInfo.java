package de.maharder.dbcrawler.apiObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiInfo {
	@JsonProperty
	@JsonAnyGetter
	private String name;
	@JsonProperty
	@JsonAnyGetter
	private String description = "Неофициальное API для CMS Datalife Engine! Актуально для версий DLE 13.x-14.x.\n\nВсе **GET** запросы поддерживают следующий синтакс:\n\n!!**Исключения: orderby, sort и limit**!!\n\n```json\n{\n    \"Parameter\": \"Xvalue\"\n}\n```\n\nВместо **X** другой знак:\n\n| Знак | Описание |\n|-----------|--------------|\n| *без* | Всёравно, что оператор **=**, пример (SQL): `Parameter = 'Value'` |\n| **!** | Всёравно, что оператор **<>**, пример (SQL): `Parameter <> 'Value'` |\n| **%** | Всёравно, что оператор **LIKE**, пример (SQL): `Parameter LIKE '%Value%'` |\n| **<** | Всёравно, что оператор **<**, пример (SQL): `Parameter < 'Value'` |\n| **<=** | Всёравно, что оператор **<=**, пример (SQL): `Parameter <= 'Value'` |\n| **>** | Всёравно, что оператор **>**, пример (SQL): `Parameter > 'Value'` |\n| **>=** | Всёравно, что оператор **>=**, пример (SQL): `Parameter >= 'Value'` |\n\n**Integer** в случае передачи со знаком передавать как **String**!\n\n**Пример**:\n```json\n{\n    \"Parameter\": \"!value\"\n}\n```\n\n**!!!ВСЕ GET ЗАПРОСЫ УКАЗЫВАЮТСЯ В HEADER!!!**\n\n**!!!ВСЕ POST/PUT ЗАПРОСЫ ПЕРЕДАЮТСЯ С ПАРАМЕТРОМ `content-type: application/x-www-form-urlencoded`!!!**\n\n**API-ключ передаётся при всех запросах ТОЛЬКО через HEADER!**\n\n\n**Запросы, помимо основных ключей, поддерживают три следующих ключа**\n\n| Ключ| Описание |\n|-----------|--------------|\n| **limit** | Ограничение в выводе данных при запросе, к примеру: 5 |\n| **sort** | Сортировка обйектов по убыванию или по увеличению, к примеру: ASC. По умолчанию: DESC|\n| **orderby** | Сортировка вывода списка по определённому ключу, к примеру: name. По умолчанию: ID запрашиваемой базы |\n\nЭти ключи указываются в заголовке (Header) при запросах GET\n\n\n\n**Формат данных в запросах**\n\n\nЧтобы правильно понять API, нужно знать следущую символику:\n\n\n| Знак | Описание |\n|-----------|--------------|\n| [*] | Значение заключённое в [] необязательно. Его можно не использовать при запросе. |\n| {*} | Обязательное значение. При отсутствии будет выдавать ошибки. |";
	@JsonProperty
	@JsonAnyGetter
	private String schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
