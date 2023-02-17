package de.maharder.dbcrawler.templates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbTable {
	private String name;
	private FieldType type;
	private boolean required = false;
	private boolean post = false;
	private int length = 0;

}
