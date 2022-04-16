package org.edin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.edin.model.Contract;
import org.edin.parser.XMLParser;

import java.io.IOException;

/**
 * JavaFX App by Edin
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/css/edinStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("JavaFX App by Edin");
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("This window cannot be closed, use the button instead");
            alert.showAndWait();
            event.consume();
        });
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}