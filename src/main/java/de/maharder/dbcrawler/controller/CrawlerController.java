package de.maharder.dbcrawler.controller;

import de.maharder.dbcrawler.templates.ApiTemplater;
import de.maharder.dbcrawler.templates.DbTable;
import de.maharder.dbcrawler.templates.DbTableAttribute;
import de.maharder.dbcrawler.templates.RouteTemplater;
import de.maharder.dbcrawler.variables.GeneratorAnswer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
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

import de.maharder.dbcrawler.variables.AppOptions;

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
	public TitledPane tpLogs;
	@FXML
	public TitledPane tpSettings;
	@FXML
	public CheckBox cbCreateOnlyApiFile;
	@FXML
	public TextField tfApiFileName;
	@FXML
	private CheckBox cbExportApi;
	@FXML
	private CheckBox cbConvertApiFiles;
	private DbController db;
	private final AppOptions options;
	private int total = 0;
	private int now = 0;

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
		cbExportApi.setSelected(options.isExportApi());
		cbConvertApiFiles.setSelected(options.isCreateApiFile());
		cbCreateOnlyApiFile.setSelected(options.isCreateApiFileOnly());

		cbExportApi.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					cbConvertApiFiles.setSelected(false);
					cbConvertApiFiles.setDisable(true);
					createLog("Создание файлов API отключено");
				} else {
					cbConvertApiFiles.setDisable(false);
					createLog("Файлы для DLE будут создаваться рядом с файлами php, например: dle_logs_apiitem.json");
				}
				refreshSettings();
			}
		});

		if (cbExportApi.isSelected()) {
			cbConvertApiFiles.setDisable(false);
		}

		cbConvertApiFiles.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					createLog("Общий файл API не будет создан");
				} else {
					createLog("Общий файл API будет создан по пути вывода файлов с названием:\n\t\t\t\t\tDLE_API_Postman.json");
				}
				refreshSettings();
			}
		});

		tpSettings.setExpanded(true);
		tpSettings.setExpanded(false);
		tpSettings.setExpanded(true);
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
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("О программе");
		alert.setHeaderText("Информация о программе");
		String infoText = String.format("Эта программа должна помочь создавать свои рутинги и описания для DLE API\n\n" +
				"Автор:\t\t%s\n" +
				"Сайт:\t\t%s © 2023\n" +
				"Лицензия:\tMIT\n","Maxim Harder", "https://devcraft.club/");
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
			final Runnable[] task = new Runnable[2];

			clvTables.getCheckModel().getCheckedItems().forEach(new Consumer<String>() {
				@Override
                public void accept(String s) {

					task[0] = () -> {
						try {
							DbTable table = new DbTable(s);
							ResultSet rs = db.query(String.format("DESCRIBE %s", s));
							while (rs.next()) {
								DbTableAttribute dbtAttribute = new DbTableAttribute(
										rs.getString("Field"),
										rs.getString("Type"),
										rs.getString("Null"),
										rs.getString("Default"),
										rs.getString("Extra")
								);
								table.addAttribute(dbtAttribute);
							}

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
								for (String m : answer.getMessage()) {
									createLog(m);
								}

							} catch (Exception e) {
								Notifications.create()
										.title("Ошибка")
										.text(e.getMessage())
										.showError();
							}
							if (cbExportApi.isSelected() && !cbCreateOnlyApiFile.isSelected()) {
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
									for (String m : answer_api.getMessage()) {
										createLog(m);
									}

								} catch (Exception e) {
									Notifications.create()
											.title("Ошибка")
											.text(e.getMessage())
											.showError();
								}
							}

							dbTableList.add(table);

							now++;
							progress();

						} catch (SQLException e) {
							e.printStackTrace();
						}
					};
                }
			});

			task[1] = () -> {
				if (cbCreateOnlyApiFile.isSelected() || cbConvertApiFiles.isSelected()) {
					total++;
					progress();

					ApiController apiController = new ApiController();
					apiController.generateApiObject(dbTableList);
					FileController fileController = new FileController(options.getOutputPath(), options.getApiFileName() + ".json", apiController);
					for (String m : fileController.exportJson().getMessage()) {
						createLog(m);
					}
					now++;
					progress();
				}
			};

			Thread thread1 = new Thread(task[0]);
			Thread thread2 = new Thread(task[1]);
			thread1.start();
			thread2.start();



			toggleBtns(true);
		}
	}

	private void toggleBtns(boolean status) {
        btnDbGetTables.setDisable(!status);
        btnStart.setDisable(!status);
        btnSelectAllTables.setDisable(!status);
	}

	public void createLog(String log) {
		tpLogs.setExpanded(true);
		lvLogsView.getItems().add(String.format("%s:\t%s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), log));
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
		options.setApiFileName(tfApiFileName.getText());
		options.setCreateApiFile(cbConvertApiFiles.isSelected());
		options.setExportApi(cbExportApi.isSelected());
		options.setCreateApiFileOnly(cbCreateOnlyApiFile.isSelected());

		Settings.saveSettings(options);
	}

	public void tfOptionChanged(InputMethodEvent inputMethodEvent){
		refreshSettings();
	}

	public void tfOptionChangedKeyEvent(KeyEvent keyEvent) {
		refreshSettings();
	}
}