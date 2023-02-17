package de.maharder.dbcrawler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CrawlerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CrawlerApplication.class.getResource("assets/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Обработчик баз данных для DLE API");
        stage.setScene(scene);
        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(CrawlerApplication.class.getResourceAsStream("assets/icon.png"))));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}