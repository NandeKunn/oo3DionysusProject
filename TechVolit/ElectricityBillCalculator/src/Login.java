import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.geometry.Insets;


public class Login extends Application {
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        VBox loginBox = new VBox(10);
        loginBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        loginBox.setPadding(new Insets(20));
        loginBox.setPrefWidth(300);
        loginBox.setPrefHeight(200);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        loginButton.setOnAction(e -> login(primaryStage));

        loginBox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        Scene scene = new Scene(loginBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void login(Stage primaryStage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("user".equalsIgnoreCase(username) && "123".equals(password)) {
            openUserView(primaryStage);
        } else if ("admin".equalsIgnoreCase(username) && "admin".equals(password)) {
            openAdminView(primaryStage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void openUserView(Stage primaryStage) {
        primaryStage.close();
        new ElectricityBillProjection().start(new Stage());
    }

    private void openAdminView(Stage primaryStage) {
        primaryStage.close();
        new ElectricityBillProjectionSettings().start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}