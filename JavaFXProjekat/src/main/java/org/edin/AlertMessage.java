package org.edin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertMessage {

    public static void display(String title, String message){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300.0);
        stage.setMinHeight(200.0);

        Label label = new Label();
        label.setText(message);
        Button button = new Button("OK, Close the window");
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label,button);
        vBox.setPadding(new Insets(20,20,20,20));
        vBox.setSpacing(20.0);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
