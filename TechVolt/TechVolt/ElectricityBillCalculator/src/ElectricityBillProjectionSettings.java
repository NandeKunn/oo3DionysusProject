import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.geometry.Insets;

public class ElectricityBillProjectionSettings extends Application {

    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) {
        currentStage = primaryStage;
        primaryStage.setTitle("Electricity Bill Projection Settings");

        VBox vbox = new VBox(10);
        vbox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setPadding(new Insets(20));
        vbox.setPrefWidth(400);
        vbox.setPrefHeight(200);

        Label titleLabel = new Label("Electricity Bill Projection Settings");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label settingsLabel = new Label("Hi Sir have a wonderful day!");

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

        vbox.getChildren().addAll(titleLabel, settingsLabel, logoutButton);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void logout() {
        currentStage.close();
        new Login().start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
