package club.devcraft.dbcrawler.controller;

import club.devcraft.dbcrawler.templates.ApiTemplater;
import club.devcraft.dbcrawler.templates.DbTable;
import club.devcraft.dbcrawler.templates.RouteTemplater;
import club.devcraft.dbcrawler.variables.AppOptions;
import club.devcraft.dbcrawler.variables.GeneratorAnswer;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.List;

public class WorkController extends Thread {
	private AppOptions options;
	private DbController db;
	private String table;
	private List<String> antworten = new ArrayList<>();
	private boolean finish = false;

	public WorkController(String table) {
		this.table = table;
		options = Settings.loadSettings();
		db = new DbController(options.getDbHost(), options.getDbPort(), options.getDbUser(), options.getDbPassword(), options.getDbName());
	}

	@Override
	public void run() {

		DbTable table = new DbTable(this.table);

		try {
			RouteTemplater rt = new RouteTemplater(table);
			GeneratorAnswer answer = rt.generateTemplate();
			if (!answer.isSuccess()) {
				for (String m : answer.getMessage()) {
					Notifications.create()
							.title("Ошибка")
							.text(m)
							.showError();
				}
			}
			antworten.addAll(answer.getMessage());

		} catch (Exception e) {
			GeneratorAnswer answer = new GeneratorAnswer();
			answer.setSuccess(false);
			answer.addMessage(e.getMessage());
			antworten.addAll(answer.getMessage());
		}
		if (options.isExportApi() && !options.isCreateApiFileOnly()) {
			try {
				ApiTemplater api = new ApiTemplater(table);
				GeneratorAnswer answer_api = api.generateTemplate();
				if (!answer_api.isSuccess()) {
					for (String m : answer_api.getMessage()) {
						Notifications.create()
								.title("Ошибка")
								.text(m)
								.showError();
					}
				}
				antworten.addAll(answer_api.getMessage());

			} catch (Exception e) {
				GeneratorAnswer answer = new GeneratorAnswer();
				answer.setSuccess(false);
				answer.addMessage(e.getMessage());
				antworten.addAll(answer.getMessage());
			}
		}

		this.finish = true;


	}

	public List<String> getAntworten() {
		return antworten;
	}

	public boolean isFinish() {
		return finish;
	}
}
