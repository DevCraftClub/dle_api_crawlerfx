package club.devcraft.dbcrawler.templates;

import club.devcraft.dbcrawler.controller.DbController;
import club.devcraft.dbcrawler.controller.Settings;
import club.devcraft.dbcrawler.variables.AppOptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbTable {
	private String prefix;
	private String name;
	private List<DbTableAttribute> attributes = new ArrayList<DbTableAttribute>();

	public DbTable(String name) {
		String[] split = name.split("_");
		String[] new_name = new String[split.length - 1];
		System.arraycopy(split, 1, new_name, 0, split.length - 1);
        setName(String.join("_", new_name));
		setPrefix(split[0]);
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

		if (attributes.size() == 0) {
		    generateAttributes();
		}

		return attributes;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setAttributes(List<DbTableAttribute> attributes) {
		this.attributes = attributes;
	}

	public void generateAttributes() {
		try {

			AppOptions options = Settings.loadSettings();
			DbController db = new DbController(options.getDbHost(), options.getDbPort(), options.getDbUser(), options.getDbPassword(), options.getDbName());
			ResultSet rs = db.query(String.format("DESCRIBE %s_%s", getPrefix(), getName()));
			if (rs != null) {
				while (rs.next()) {
					DbTableAttribute dbtAttribute = new DbTableAttribute(
							rs.getString("Field"),
							rs.getString("Type"),
							rs.getString("Null"),
							rs.getString("Default"),
							rs.getString("Extra")
					);
					addAttribute(dbtAttribute);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
