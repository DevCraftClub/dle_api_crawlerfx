module de.maharder.dbcrawler {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires java.sql;
	requires jakarta.xml.bind;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;
	requires com.google.gson;

	opens de.maharder.dbcrawler to javafx.fxml;
	opens de.maharder.dbcrawler.variables to jakarta.xml.bind;
//	opens de.maharder.dbcrawler.controller to jakarta.xml.bind;
	opens de.maharder.dbcrawler.controller to javafx.fxml;
//	opens de.maharder.dbcrawler.apiObject to com.fasterxml.jackson.databind;
	opens de.maharder.dbcrawler.apiObject to com.google.gson;

	exports de.maharder.dbcrawler;
	exports de.maharder.dbcrawler.variables;
	exports de.maharder.dbcrawler.controller;
	exports de.maharder.dbcrawler.templates;
	exports de.maharder.dbcrawler.apiObject;
}