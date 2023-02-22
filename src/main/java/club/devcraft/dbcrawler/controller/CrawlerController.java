package club.devcraft.dbcrawler.controller;

import club.devcraft.dbcrawler.templates.DbTable;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.function.Consumer;

import club.devcraft.dbcrawler.variables.AppOptions;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import static java.lang.Thread.sleep;

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
	public ListView<String> lvLogsView;
	@FXML
	public ProgressBar pbProgress;
	@FXML
	public CheckListView<String> clvTables;
	@FXML
	public TextField tfDbName;
	@FXML
	public TextField tfApiFileName;
	@FXML
	public Tab tabLogs;
	@FXML
	public Tab tabSettings;
	@FXML
	public Button btnApiFilePath;
	@FXML
	public TextField tfApiFilePath;
	@FXML
	public CheckBox cbCreateSubFolder;
	@FXML
	public CheckBox cbAutoSave;
	@FXML
	public Button btnSaveSettings;
	@FXML
	public Panel panelMain;
	@FXML
	public VBox vboxMain;
	@FXML
	private CheckBox cbExportApi;
	@FXML
	private CheckBox cbExportApiFile;
	@FXML
	public CheckBox cbCreateOnlyApiFile;
	private DbController db;
	private final AppOptions options;
	private int total = 0;
	private int now = 0;
//	private WorkController worker;

	public CrawlerController() {
		this.options = Settings.loadSettings();
	}

	@FXML
	public void initialize() {
		tfDbHost.setText(options.getDbHost());
		tfDbPort.setText(String.format("%d", options.getDbPort()));
		tfDbUser.setText(options.getDbUser());
		tfDbPw.setText(options.getDbPassword());
		tfDbName.setText(options.getDbName());
		tfFolderSelect.setText(options.getOutputPath());
		tfApiFileName.setText(options.getApiFileName());
		tfApiFilePath.setText(options.getApiFilePath());
		cbExportApi.setSelected(options.isExportApi());
		cbExportApiFile.setSelected(options.isCreateApiFile());
		cbCreateOnlyApiFile.setSelected(options.isCreateApiFileOnly());
		cbAutoSave.setSelected(options.isAutoSave());
		cbCreateSubFolder.setSelected(options.isCreateSubfolders());

		panelMain.getStyleClass().add("panel-primary");

		btnSaveSettings.getStyleClass().setAll("btn", "btn-primary");
		btnDbGetTables.getStyleClass().setAll("btn", "btn-default");
		btnDbCheck.getStyleClass().setAll("btn", "btn-success");
		btnInfo.getStyleClass().setAll("btn", "btn-info");
		btnStart.getStyleClass().setAll("btn", "btn-success");
		btnSelectAllTables.getStyleClass().setAll("btn", "btn-primary");

		cbExportApi.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				cbExportApiFile.setSelected(false);
				cbExportApiFile.setDisable(true);
				cbCreateOnlyApiFile.setDisable(true);
				cbCreateOnlyApiFile.setSelected(false);
				tfApiFileName.setDisable(true);
				tfApiFilePath.setDisable(true);
				createLog("Создание файлов API отключено, все настройки связанные с этим будут отключены");
			} else {
				cbExportApiFile.setDisable(false);
				cbCreateOnlyApiFile.setDisable(false);
				tfApiFileName.setDisable(false);
				tfApiFilePath.setDisable(false);
				createLog("Файлы для DLE будут создаваться рядом с файлами php, например: dle_logs_apiitem.json");
			}
			refreshSettings();

		});

		if (cbExportApi.isSelected()) {
			cbExportApiFile.setDisable(false);
		}

		cbExportApiFile.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				createLog("Общий файл API не будет создан");
			} else {
				createLog("Общий файл API будет создан по пути вывода файлов с названием:\n\t\t\t\t\t" + tfApiFileName.getText() + "_Postman.json");
			}
			refreshSettings();

		});

		cbCreateOnlyApiFile.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				createLog("Отдельные файлы для API создаваться не будут, лишь один единый");
			}
			refreshSettings();

		});

		cbAutoSave.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				createLog("Настройки будут автоматически сохраняться");
			} else {
				createLog("Автосохранение настроек отключено!");
			}
			refreshSettings();
		});

		cbCreateSubFolder.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				createLog("Для каждой таблицы будет создана отдельная папка");
			} else {
				createLog("Все файлы будут сохранены по указанному выше пути:\n\t\t\t\t\t" + tfFolderSelect.getText() + "/");
			}
		});
	}

	@FXML
	public void btnFolderSelectClick(ActionEvent actionEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage stage = (Stage) btnFolderSelect.getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) tfFolderSelect.setText(selectedDirectory.getAbsolutePath());
	}

	@FXML
	public void btnConnectionCheck(ActionEvent actionEvent) {
		if (tfDbHost.getText().isEmpty() || tfDbPort.getText().isEmpty() || tfDbUser.getText().isEmpty() || tfDbPw.getText().isEmpty() || tfDbName.getText().isEmpty()) {
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
			if (db.checkConnection()) {
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
		while (result.next()) {
			clvTables.getItems().add(result.getString(1));
		}

		createLog(String.format("Загружено %d таблиц из базы данных", clvTables.getItems().size()));
	}

	public void btnSelectAllTablesClick(ActionEvent actionEvent) {
		if (btnSelectAllTables.getText().equals("Выбрать все")) {
			clvTables.getCheckModel().checkAll();
			btnSelectAllTables.setText("Снять выбор");
		} else {
			clvTables.getCheckModel().clearChecks();
			btnSelectAllTables.setText("Выбрать все");
		}
	}

	@FXML
	public void btnInfoClick(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("О программе");
		alert.setHeaderText("Информация о программе");
		String infoText = String.format("Эта программа должна помочь создавать свои рутинги и описания для DLE API\n\n" +
				"Автор:\t\t%s\n" +
				"Сайт:\t\t%s © 2023\n" +
				"Лицензия:\tMIT\n", "Maxim Harder", "https://devcraft.club/");
		alert.setContentText(infoText);

		alert.showAndWait();
	}

	@FXML
	public void btnStartClick(ActionEvent actionEvent) {
		pbProgress.setProgress(0);
		if (tfFolderSelect.getText().isEmpty()) {
			Notifications.create()
					.title("Ошибка")
					.text("Не указана директория выбранной папки!")
					.showError();
		} else {
			total = clvTables.getCheckModel().getCheckedItems().size();

			createLog(String.format("Выбрано %d таблиц из базы данных для экспорта", total));
			toggleBtns(false);

			List<DbTable> dbTableList = new ArrayList<>();

			clvTables.getCheckModel().getCheckedItems().forEach(new Consumer<String>() {
				@Override
				public void accept(String s) {
					WorkController worker = new WorkController(s);
					worker.start();

					try {
						worker.join(500L);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}

					if (worker.isFinish()) {

						for (String m : worker.getAntworten()) {
							createLog(m);
						}
						try {
							worker.join();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}


						dbTableList.add(new DbTable(s));
						now++;
						progress();
					}
				}

			});

			Task task2 = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					if (cbCreateOnlyApiFile.isSelected() || cbExportApiFile.isSelected()) {
						Platform.runLater(() -> {
							total++;
							progress();

							ApiController apiController = new ApiController();
							apiController.generateApiObject(dbTableList);

							String apiPath = tfFolderSelect.getText();
							if (!tfApiFilePath.getText().isEmpty()) {
								apiPath = tfApiFilePath.getText();
							}
							FileController fileController = new FileController(apiPath, options.getApiFileName() + "_POSTMAN", apiController);
							for (String m : fileController.exportJson().getMessage()) {
								createLog(m);
							}
							now++;
							progress();
						});
					}

					return null;
				}
			};

			new Thread(task2).start();

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
		lvLogsView.scrollTo(lvLogsView.getItems().size()-1);
	}

	private void progress() {
		pbProgress.setProgress(Double.parseDouble(String.format("%d.0", now)) * 100 / total);
	}

	private void refreshSettings() {
		options.setDbHost(tfDbHost.getText());
		options.setDbPort(Integer.parseInt(tfDbPort.getText()));
		options.setDbUser(tfDbUser.getText());
		options.setDbPassword(tfDbPw.getText());
		options.setDbName(tfDbName.getText());
		options.setOutputPath(tfFolderSelect.getText());
		options.setAutoSave(cbAutoSave.isSelected());
		options.setCreateSubfolders(cbCreateSubFolder.isSelected());
		options.setExportApi(cbExportApi.isSelected());
		options.setCreateApiFile(cbExportApiFile.isSelected());
		options.setCreateApiFileOnly(cbCreateOnlyApiFile.isSelected());
		options.setApiFileName(tfApiFileName.getText());
		options.setApiFilePath(tfApiFilePath.getText());

		Settings.saveSettings(options);
	}

	public void tfOptionChanged(InputMethodEvent inputMethodEvent) {
		if (cbAutoSave.isSelected()) refreshSettings();
	}

	public void tfOptionChangedKeyEvent(KeyEvent keyEvent) {
		if (cbAutoSave.isSelected()) refreshSettings();

	}

	public void btnApiFilePathClick(ActionEvent actionEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage stage = (Stage) btnApiFilePath.getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) tfApiFilePath.setText(selectedDirectory.getAbsolutePath());
	}

	public void btnSaveSettingsClick(ActionEvent actionEvent) {
		refreshSettings();
		createLog("Настройки сохранены");
	}
}