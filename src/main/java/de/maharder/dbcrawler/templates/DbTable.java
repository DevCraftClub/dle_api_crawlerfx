package de.maharder.dbcrawler.templates;

import java.util.ArrayList;
import java.util.List;

public class DbTable {
	private String name;
	private List<DbTableAttribute> attributes = new ArrayList<DbTableAttribute>();

	public DbTable(String name) {
		String[] split = name.split("_");
		String[] new_name = new String[split.length - 1];
		System.arraycopy(split, 1, new_name, 0, split.length - 1);
        setName(String.join("_", new_name));

//		System.out.printf("DB Name: %s\n", name);
    }

	public void addAttribute(DbTableAttribute attribute) {
		if(attribute != null && !attributes.contains(attribute)) {
			attributes.add(attribute);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DbTableAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<DbTableAttribute> attributes) {
		this.attributes = attributes;
	}
}
