module dbcrawler {
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

	opens club.devcraft.dbcrawler.apiObject to com.google.gson;
	opens club.devcraft.dbcrawler to com.google.gson, javafx.controls, javafx.fxml, jakarta.xml.bind;
	opens club.devcraft.dbcrawler.controller to com.google.gson, javafx.fxml;
	opens club.devcraft.dbcrawler.variables to com.google.gson, jakarta.xml.bind;

	exports club.devcraft.dbcrawler;
}