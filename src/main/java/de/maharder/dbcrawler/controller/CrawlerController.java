package de.maharder.dbcrawler;

import de.maharder.dbcrawler.templates.DbTable;
import de.maharder.dbcrawler.templates.DbTableAttribute;
import de.maharder.dbcrawler.templates.RouteTemplater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import de.maharder.dbcrawler.AppOptions;
import de.maharder.dbcrawler.Settings;

public class CrawlerController {

	@FXML
	public TextField tfDbHost;
	@FXML
	public TextField tfDbPort;
	@FXML
	public TextField tfDbUser;
	@FXML
	public TextField tfDbPw;
	@FXML
	public TextField tfFolderSelect;
	@FXML
	public Tooltip ttTargetFolder;
	@FXML
	public Button btnFolderSelect;
	@FXML
	public Button btnDbCheck;
	@FXML
	public Button btnDbGetTables;
	@FXML
	public Button btnInfo;
	@FXML
	public Button btnStart;
	@FXML
	public Button btnSelectAllTables;
	@FXML
	public ListView lvLogsView;
	@FXML
	public ProgressBar pbProgress;
	@FXML
	public CheckListView clvTables;
	@FXML
	public TextField tfDbName;
	private DbController db;
	private AppOptions Options;

	public CrawlerController() {
		this.Options = Settings.loadSettings();
	}

	@FXML
	public void initialize() {
		tfDbHost.setText(Options.getDbHost());
		tfDbPort.setText(String.format("%d", Options.getDbPort()));
		tfDbUser.setText(Options.getDbUser());
		tfDbPw.setText(Options.getDbPassword());
		tfDbName.setText(Options.getDbName());
		tfFolderSelect.setText(Options.getOutputPath());
	}

	@FXML
	public void btnFolderSelectClick(ActionEvent actionEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage stage = (Stage) btnFolderSelect.getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(stage);
		tfFolderSelect.setText(selectedDirectory.getAbsolutePath());
	}

	@FXML
	public void btnConnectionCheck(ActionEvent actionEvent)  {
		if(tfDbHost.getText().isEmpty() || tfDbPort.getText().isEmpty() || tfDbUser.getText().isEmpty() || tfDbPw.getText().isEmpty() || tfDbName.getText().isEmpty()) {
			if (tfDbHost.getText().isEmpty())
				Notifications.create()
					.title("Ошибка")
					.text("Адрес базы данных не задан! К примеру: localhost")
					.showError();
			if (tfDbPort.getText().isEmpty()) {
				Notifications.create()
						.title("Внимание")
						.text("Не указан порт базы данных! Будет использоваться порт по умолчанию: 3306")
						.showInformation();
				tfDbPort.setText("3306");
			}
			if (tfDbUser.getText().isEmpty())
				Notifications.create()
					.title("Ошибка")
					.text("Укажите имя пользователя базы данных! К примеру: mysql")
					.showError();
			if (tfDbPw.getText().isEmpty())
				Notifications.create()
					.title("Ошибка")
					.text("Укажите пароль пользователя базы данных! К примеру: mysql")
					.showError();
			if (tfDbName.getText().isEmpty())
				Notifications.create()
					.title("Ошибка")
					.text("Укажите название базы данных! К примеру: dle160")
					.showError();
		} else {
			db = new DbController(tfDbHost.getText(), Integer.parseInt(tfDbPort.getText()), tfDbUser.getText(), tfDbPw.getText(), tfDbName.getText());
			if(db.checkConnection()) {
				toggleBtns(true);
				Notifications.create()
						.title("Успех")
						.text("Подключение к базе данных успешно установлено!")
						.showConfirm();
				createLog("Установлено успешное соединение с базой данных (" + tfDbName.getText() + ")");
				refreshSettings();
			} else {
				Notifications.create()
						.title("Ошибка")
						.text("Подключение к базе данных не возможно! Проверьте правильность введенных данных.")
						.showWarning();
			}
		}
	}

	@FXML
	public void btnDbGetTablesClick(ActionEvent actionEvent) throws SQLException {
		ResultSet result = db.query("SHOW TABLES");
		clvTables.getItems().clear();
		while(result.next()) {
			clvTables.getItems().add(result.getString(1));
		}

		createLog(String.format("Загружено %d таблиц из базы данных", clvTables.getItems().size()));
	}

	public void btnSelectAllTablesClick(ActionEvent actionEvent) {
		if (btnSelectAllTables.getText().equals("Выбрать все таблицы")) {
			clvTables.getCheckModel().checkAll();
			btnSelectAllTables.setText("Снять выбор");
		} else {
			clvTables.getCheckModel().clearChecks();
			btnSelectAllTables.setText("Выбрать все таблицы");
		}
	}

	@FXML
	public void btnInfoClick(ActionEvent actionEvent) {
	}

	@FXML
	public void btnStartClick(ActionEvent actionEvent) {
		if (tfFolderSelect.getText().isEmpty()) {
			Notifications.create()
                    .title("Ошибка")
                    .text("Не указана директория выбранной папки!")
                    .showError();
		} else {
			createLog(String.format("Выбрано %d таблиц из базы данных для экспорта", clvTables.getCheckModel().getCheckedItems().size()));
			toggleBtns(false);
			clvTables.getCheckModel().getCheckedItems().forEach(new Consumer<String>() {
				@Override
                public void accept(String s) {
                    try {
						DbTable table = new DbTable(s);
                        ResultSet rs = db.query(String.format("DESCRIBE %s", s));
						while (rs.next()) {
							DbTableAttribute dbtAttribute = new DbTableAttribute(
									rs.getString("Field"),
									rs.getString("Type"),
									rs.getString("Null"),
                                    rs.getString("Key"),
                                    rs.getString("Default"),
                                    rs.getString("Extra")
							);
							table.addAttribute(dbtAttribute);
							try {
								RouteTemplater rt = new RouteTemplater(table);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
			});
			toggleBtns(true);
		}
	}

	private void toggleBtns(boolean status) {
        btnDbGetTables.setDisable(!status);
        btnStart.setDisable(!status);
        btnSelectAllTables.setDisable(!status);
	}

	public void createLog(String log) {
		lvLogsView.getItems().add(String.format("%s:\t%s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), log));
	}

	private void refreshSettings() {
		try {
			Options.setDbHost(tfDbHost.getText());
			Options.setDbPort(Integer.parseInt(tfDbPort.getText()));
			Options.setDbUser(tfDbUser.getText());
			Options.setDbPassword(tfDbPw.getText());
			Options.setDbName(tfDbName.getText());
			Options.setOutputPath(tfFolderSelect.getText());
			Settings.saveSettings(Options);
		} catch (URISyntaxException e) {
		    createLog(e.getMessage());
		}
	}

	public void tfOptionChanged(InputMethodEvent inputMethodEvent){
		refreshSettings();
	}
}