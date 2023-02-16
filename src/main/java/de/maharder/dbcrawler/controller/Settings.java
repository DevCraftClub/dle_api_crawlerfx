package de.maharder.dbcrawler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Settings {
	private static final URL SETTINGS_FILE = CrawlerApplication.class.getResource("settings.xml");

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

	public static void saveSettings(AppOptions settings) throws URISyntaxException {

		File file = new File(SETTINGS_FILE.toURI());

		try (FileOutputStream stream = new FileOutputStream(file)) {
			JAXBContext context = JAXBContext.newInstance(AppOptions.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(settings, stream);
		} catch (JAXBException | IOException ex) {
			ex.printStackTrace();
		}
	}

}



