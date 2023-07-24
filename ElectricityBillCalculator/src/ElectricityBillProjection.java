import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ElectricityBillProjection extends Application {
    private ComboBox<String> applianceBox;
    private TextField wattageField;
    private Label resultLabel;
    private TextArea tipsArea;
    private Map<String, Double> applianceWattageMap;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Bill Predictor");

        Label titleLabel = new Label("Electricity Bill Predictor");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label applianceLabel = new Label("Appliance:");
        applianceLabel.setStyle("-fx-font-size: 16px;");
        applianceBox = new ComboBox<>(FXCollections.observableArrayList(
                "Refrigerator", "Electric Stove", "Microwave Oven", "Rice Cooker", "Blender", "Aircon", "Electric Fan", "Electric Oven", "Pressure Cooker"
        ));

        Label wattageLabel = new Label("Wattage:");
        wattageLabel.setStyle("-fx-font-size: 16px;");
        wattageField = new TextField();

        Button addButton = new Button("Add Appliance");
        addButton.setOnAction(e -> addAppliance());

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");

        tipsArea = new TextArea();
        tipsArea.setEditable(false);

        VBox vbox = new VBox(10, titleLabel, applianceLabel, applianceBox, wattageLabel, wattageField, addButton, resultLabel, tipsArea);
        vbox.setPrefWidth(600);
        vbox.setPrefHeight(400);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        applianceWattageMap = new HashMap<>();
    }

    private void addAppliance() {
        String appliance = applianceBox.getValue();
        double wattage = Double.parseDouble(wattageField.getText());
        applianceWattageMap.put(appliance, wattage);

        BillPredictor billPredictor = new BillPredictor();
        double predictedBill = billPredictor.predictNextMonthBill(applianceWattageMap);

        TipsGenerator tipsGenerator = new TipsGenerator();
        String tips = tipsGenerator.suggestTips(predictedBill);

        resultLabel.setText("Predicted next month's bill: " + predictedBill);
        tipsArea.setText(tips);

        applianceBox.getSelectionModel().clearSelection();
        wattageField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
