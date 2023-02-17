package de.maharder.dbcrawler.controller;

import de.maharder.dbcrawler.CrawlerApplication;
import de.maharder.dbcrawler.variables.AppOptions;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Settings {
//	private static final URL SETTINGS_FILE = Settings.class.getResource("settings.xml");
	private static final URL SETTINGS_FILE = CrawlerApplication.class.getResource("assets/settings.xml");

	public static AppOptions loadSettings() {

		File file = new File(SETTINGS_FILE.getPath());
		if (!file.exists()) {
			return new AppOptions();
		}

		try (FileInputStream stream = new FileInputStream(file)) {
			JAXBContext context = JAXBContext.newInstance(AppOptions.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (AppOptions) unmarshaller.unmarshal(stream);
		} catch (JAXBException | IOException ex) {
			ex.printStackTrace();
			return new AppOptions();
		}
	}

	public static void saveSettings(AppOptions settings) {

		try {
			File file = new File(SETTINGS_FILE.getPath());

			try (FileOutputStream stream = new FileOutputStream(file)) {
				JAXBContext context = JAXBContext.newInstance(AppOptions.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.marshal(settings, stream);
			} catch (JAXBException | IOException ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}



