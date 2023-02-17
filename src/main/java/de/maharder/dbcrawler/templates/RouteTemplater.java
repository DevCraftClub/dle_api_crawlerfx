package de.maharder.dbcrawler.templates;

import de.maharder.dbcrawler.controller.FileController;
import de.maharder.dbcrawler.variables.AppOptions;
import de.maharder.dbcrawler.CrawlerApplication;
import de.maharder.dbcrawler.controller.Settings;
import de.maharder.dbcrawler.variables.GeneratorAnswer;
import de.maharder.dbcrawler.variables.PossibleData;

import java.io.*;
import java.net.URL;

public class RouteTemplater {
	private final URL template = CrawlerApplication.class.getResource("assets/routing_template.txt");
	private DbTable table;

	public RouteTemplater(DbTable table) throws IOException {
		setTable(table);
	}

	public GeneratorAnswer generateTemplate() throws IOException {
		AppOptions settings = Settings.loadSettings();
		String path = settings.getOutputPath() + "/" + table.getName();
		File output_path = new File(path);
		GeneratorAnswer answer = new GeneratorAnswer();

		if (!output_path.exists()) {
			boolean dir_created = output_path.mkdirs();
			if (!dir_created) {
				answer.setSuccess(false);
				answer.addMessage("Невозможно создать папку или получить доступ к пути: " + path);
			}
		}

		if (answer.isSuccess()) {
			answer.addMessage("Установленый путь создан: " + path);
			StringBuilder template_txt = new StringBuilder();
			File template_file = new File(getTemplate().getPath());
			try (FileReader fileReader = new FileReader(template_file); BufferedReader reader = new BufferedReader(fileReader)) {
				String line;
				while ((line = reader.readLine()) != null) {
					template_txt.append(line).append("\n");
				}

				String template_text = template_txt.toString().replaceAll("%DB_NAME%", table.getName());

				StringBuilder possible_data = new StringBuilder();

				for (DbTableAttribute attr : table.getAttributes()) {
					possible_data.append(new PossibleData(attr.getName(), attr.getType(), attr.isRequired(), attr.isPost(), attr.getLength()));
				}

				template_text = template_text.replaceAll("%POSSIBLE_DATA%", possible_data.toString());

				FileController fileController = new FileController(settings.getOutputPath(), table.getName() + ".php", template_text);
				fileController.exportFile().getMessage().forEach(answer::addMessage);

			} catch (IOException e) {
				answer.setSuccess(false);
				answer.addMessage("Невозможно получить доступ к файлу: " + e.getMessage());
			}
		}

		return answer;

	}

	public URL getTemplate() {
		return template;
	}

	public DbTable getTable() {
		return table;
	}

	public void setTable(DbTable table) {
		this.table = table;
	}
}
