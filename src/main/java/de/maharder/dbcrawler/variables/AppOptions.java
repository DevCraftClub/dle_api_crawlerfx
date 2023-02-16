package de.maharder.dbcrawler;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement
public class AppOptions {
	private String DbHost;
	private int DbPort = 3306;
	private String DbUser;
	private String DbPassword;
	private String DbName;
	private String OutputPath;
}
