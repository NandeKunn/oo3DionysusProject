import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ElectricityBillProjection extends Application {
    private ComboBox<String> applianceBox;
    private TextField hoursUsedField;
    private Label resultLabel;
    private TextArea tipsArea;
    private Map<String, Double> applianceHoursMap;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Bill Predictor");

        BorderPane root = new BorderPane();
        VBox vbox = createVBox();

        root.setCenter(vbox);

        Scene scene = new Scene(root, 600, 400);
        scene.setFill(Color.LIGHTGRAY); // Set the background color to light gray
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        applianceHoursMap = new HashMap<>();
    }

    private VBox createVBox() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-border-color: #dcdcdc; -fx-border-width: 1px;");
        vbox.setPrefWidth(600);
        vbox.setPrefHeight(400);

        Label titleLabel = new Label("Electricity Bill Predictor");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label applianceLabel = new Label("Select or add an appliance:");
        applianceLabel.setStyle("-fx-font-size: 16px;");

        applianceBox = new ComboBox<>(FXCollections.observableArrayList(
                "Refrigerator", "Electric Stove", "Microwave Oven", "Rice Cooker", "Blender",
                "Aircon", "Electric Fan", "Electric Oven", "Pressure Cooker"
        ));
        applianceBox.setEditable(true);

        Label hoursUsedLabel = new Label("Enter the hours used per day:");
        hoursUsedLabel.setStyle("-fx-font-size: 16px;");

        hoursUsedField = new TextField();

        Button addButton = new Button("Add Appliance");
        addButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;"); // Blue with white text
        addButton.setOnAction(e -> addAppliance());

        Button addCustomApplianceButton = new Button("Add Custom Appliance");
        addCustomApplianceButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;"); // Blue with white text
        addCustomApplianceButton.setOnAction(e -> addCustomAppliance());

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;"); // Blue with white text
        resetButton.setOnAction(e -> resetForm());

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");

        tipsArea = new TextArea();
        tipsArea.setEditable(false);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;"); // Blue with white text
        logoutButton.setOnAction(e -> logout());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(applianceLabel, 0, 0);
        gridPane.add(applianceBox, 1, 0);
        gridPane.add(hoursUsedLabel, 0, 1);
        gridPane.add(hoursUsedField, 1, 1);
        gridPane.add(addButton, 0, 2);
        gridPane.add(addCustomApplianceButton, 1, 2);
        gridPane.add(resetButton, 0, 3);
        gridPane.add(resultLabel, 0, 4, 2, 1);
        gridPane.add(tipsArea, 0, 5, 2, 1);
        gridPane.add(logoutButton, 0, 6, 2, 1);

        vbox.getChildren().addAll(titleLabel, gridPane);
        return vbox;
    }

    private void addAppliance() {
        String appliance = applianceBox.getValue();
        double hoursUsed = Double.parseDouble(hoursUsedField.getText());
        applianceHoursMap.put(appliance, hoursUsed);

        updatePredictionAndTips();
        applianceBox.getSelectionModel().clearSelection();
        hoursUsedField.clear();
    }

    private void addCustomAppliance() {
        String customAppliance = applianceBox.getEditor().getText();
        double customHoursUsed = Double.parseDouble(hoursUsedField.getText());

        applianceBox.getItems().add(customAppliance);
        applianceBox.getSelectionModel().select(customAppliance);

        applianceHoursMap.put(customAppliance, customHoursUsed);

        updatePredictionAndTips();
        hoursUsedField.clear();
    }

    private void updatePredictionAndTips() {
        if (!applianceHoursMap.isEmpty()) {
            BillPredictor billPredictor = new BillPredictor();
            double predictedBill = billPredictor.predictNextMonthBill(applianceHoursMap);

            TipsGenerator tipsGenerator = new TipsGenerator();
            String tips = tipsGenerator.suggestTips(predictedBill);

            resultLabel.setText("Predicted next month's bill: " + predictedBill);
            tipsArea.setText(tips);
        } else {
            resultLabel.setText("");
            tipsArea.setText("");
        }
    }

    private void resetForm() {
        applianceHoursMap.clear();
        applianceBox.getSelectionModel().clearSelection();
        hoursUsedField.clear();
        resultLabel.setText("");
        tipsArea.setText("");
    }

    private void logout() {
        Stage stage = (Stage) applianceBox.getScene().getWindow();
        stage.close();
        new Login().start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
