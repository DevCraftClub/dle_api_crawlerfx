package club.devcraft.dbcrawler.templates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbTableAttribute {
	private String name;
	private String type;
	private boolean required = false;
	private boolean idKey = false;
	private boolean post = false;
	private int length = 0;

	public DbTableAttribute(String name, String type, String null_field, String default_field, String extra) {
//		System.out.printf("%s:\t%s\n", "Name", name);
//		System.out.printf("%s:\t%s\n", "Type", type);
//		System.out.printf("%s:\t%s\n", "Null", null_field);
//		System.out.printf("%s:\t%s\n", "Default", default_field);
//		System.out.printf("%s:\t%s\n\n", "Extra", extra);

		setName(name);

		Pattern pattern = Pattern.compile("(\\w+)\\((\\d+)\\)");
		Matcher matcher = pattern.matcher(type);

		if (matcher.find()) {
			setType(convertType(matcher.group(1)));
			setLength(Integer.parseInt(matcher.group(2)));
		} else {
			pattern = Pattern.compile("(\\w+)");
			matcher = pattern.matcher(type);
			if (matcher.find()) {
				setType(convertType(matcher.group(1)));
			}
		}

		if (null_field != null && null_field.equals("NO")) setRequired(true);
		if (default_field != null && default_field.equals("null")) setRequired(false);
		if (extra != null && !extra.equals("auto_increment")) {
			setPost(true);
		}
		if (extra != null && extra.equals("auto_increment")) {
			setIdKey(true);
		}
	}

	private String convertType(String type) {
		return switch (type) {
			case "int", "tinyint", "smallint", "mediumint" -> "integer";
			case "varchar", "text" -> "string";
			case "date" -> "DATE";
			case "time" -> "time";
			case "timestamp" -> "timestamp";
			case "boolean" -> "boolean";
			case "float", "double", "decimal" -> "double";
			default -> type;
		};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isIdKey() {
		return idKey;
	}

	public void setIdKey(boolean idKey) {
		this.idKey = idKey;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
