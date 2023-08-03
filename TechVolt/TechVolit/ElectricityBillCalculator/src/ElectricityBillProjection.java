import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.Month;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class ElectricityBillProjection extends Application {
    private ComboBox<String> monthComboBox;
    private ComboBox<String> applianceBox;
    private TextField hoursUsedField;
    private TextField wattageField;
    private Label resultLabel;
    private TextArea tipsArea;
    private Map<String, ApplianceData> applianceHoursMap;
    private ListView<String> addedAppliancesList;
    private List<String> addedAppliances;
    private Label titleLabel;
    private GridPane gridPane;
    private Button saveButton;
    private Button openButton;
    private boolean isPresetApplianceSelected = false;
    private MonthPicker monthPicker;



    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Bill Predictor");

        openButton = new Button("Open Data");
        openButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        openButton.setOnAction(e -> {
            YearMonth selectedYearMonth = monthPicker.getValue();
            if (selectedYearMonth != null) {
                loadData(selectedYearMonth);
            }
        });


        BorderPane root = new BorderPane();
        VBox vbox = createVBox();

        YearMonth startMonth = YearMonth.of(2023, Month.JANUARY);
        YearMonth endMonth = YearMonth.of(2023, Month.DECEMBER);
        monthPicker = new MonthPicker();
        monthPicker.setPromptText("Select a month");

        VBox topVBox = new VBox(10);
        topVBox.setPadding(new Insets(10));
        topVBox.getChildren().addAll(titleLabel, monthPicker, gridPane);

        root.setTop(topVBox);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 600, 400);
        scene.setFill(Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        monthPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadData(newValue);
            }
        });

        Button addCustomApplianceButton = new Button("Add Custom Appliance");
        addCustomApplianceButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        addCustomApplianceButton.setOnAction(e -> addCustomAppliance());

        applianceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BillPredictor billPredictor = new BillPredictor();
                Map<String, ApplianceData> presetAppliancePowerMap = billPredictor.getAppliancePowerMap();
                boolean isPresetAppliance = presetAppliancePowerMap.containsKey(newValue);
                isPresetApplianceSelected = isPresetAppliance;

                addCustomApplianceButton.setDisable(isPresetAppliance);
            }
        });

        addCustomApplianceButton.setOnAction(e -> {
            String customAppliance = applianceBox.getEditor().getText();
            String hoursUsedText = hoursUsedField.getText();

            try {
                double customHoursUsed = Double.parseDouble(hoursUsedText);
                double wattage = Double.parseDouble(wattageField.getText());

                if (isPresetApplianceSelected) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Operation");
                    alert.setHeaderText("Preset Appliance Selected");
                    alert.setContentText("Please click 'Add Appliance' if you want to add a preset appliance. " +
                            "Or add a specific wattage if you want to add a custom appliance.");
                    alert.showAndWait();
                    return;
                }

                ApplianceData applianceData = new ApplianceData(customHoursUsed, wattage);
                applianceBox.getItems().add(customAppliance);
                applianceBox.getSelectionModel().select(customAppliance);
                applianceHoursMap.put(customAppliance, applianceData);
                updateApplianceList();

                updatePredictionAndTips();
                hoursUsedField.clear();
                wattageField.clear();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for hours used or wattage.");
            }
        });
        applianceHoursMap = new HashMap<>();
        addedAppliances = new ArrayList<>();
    }

    private VBox createVBox() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-border-color: #dcdcdc; -fx-border-width: 1px;");
        vbox.setPrefWidth(600);
        vbox.setPrefHeight(400);

        Label wattageLabel = new Label("Enter the wattage (in watts):");
        wattageLabel.setStyle("-fx-font-size: 16px;");

        wattageField = new TextField();
        titleLabel = new Label("Electricity Bill Predictor");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label applianceLabel = new Label("Select or add an appliance:");
        applianceLabel.setStyle("-fx-font-size: 16px;");

        applianceBox = new ComboBox<>(FXCollections.observableArrayList(
                "Refrigerator", "Electric Stove", "Microwave Oven", "Rice Cooker", "Blender",
                "Aircon", "Electric Fan", "Electric Oven", "Pressure Cooker", "Lights",
                "Television", "Laptop", "Desktop Computer", "Washing Machine", "Dishwasher",
                "Water Heater", "Toaster", "Coffee Maker", "Iron", "Hair Dryer"
        ));
        applianceBox.setEditable(true);

        Label hoursUsedLabel = new Label("Enter the hours used per day:");
        hoursUsedLabel.setStyle("-fx-font-size: 16px;");

        hoursUsedField = new TextField();

        Button addButton = new Button("Add Appliance");
        addButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        addButton.setOnAction(e -> addAppliance());

        Button addCustomApplianceButton = new Button("Add Custom Appliance");
        addCustomApplianceButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        addCustomApplianceButton.setOnAction(e -> addCustomAppliance());

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        resetButton.setOnAction(e -> resetForm());

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");

        tipsArea = new TextArea();
        tipsArea.setEditable(false);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

        saveButton = new Button("Save Data");
        saveButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            YearMonth selectedYearMonth = monthPicker.getValue();
            if (selectedYearMonth != null) {
                String selectedMonth = selectedYearMonth.getMonth().toString();
                saveData(selectedMonth);
            }
        });


        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(applianceLabel, 0, 0);
        gridPane.add(applianceBox, 1, 0);
        gridPane.add(hoursUsedLabel, 0, 1);
        gridPane.add(hoursUsedField, 1, 1);
        gridPane.add(wattageLabel, 0, 2);
        gridPane.add(wattageField, 1, 2);
        gridPane.add(addButton, 0, 3);
        gridPane.add(addCustomApplianceButton, 1, 3);
        gridPane.add(resetButton, 0, 4);
        gridPane.add(resultLabel, 0, 5, 2, 1);
        gridPane.add(tipsArea, 0, 6, 2, 1);
        gridPane.add(logoutButton, 0, 7, 2, 1);
        gridPane.add(new Label(""), 0, 8);
        gridPane.add(saveButton, 0, 9);
        gridPane.add(openButton, 1, 9);
        addedAppliancesList = new ListView<>();
        vbox.getChildren().addAll(titleLabel, gridPane, addedAppliancesList);

        return vbox;
    }


    private void addAppliance() {
        String appliance = applianceBox.getValue();
        double hoursUsed = parseDoubleValue(hoursUsedField.getText());

        if (Double.isNaN(hoursUsed)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid input for hours used.");
            alert.showAndWait();
            return;
        }

        BillPredictor billPredictor = new BillPredictor();
        Map<String, ApplianceData> presetAppliancePowerMap = billPredictor.getAppliancePowerMap();

        if (presetAppliancePowerMap.containsKey(appliance)) {
            double customWattage = parseDoubleValue(wattageField.getText());
            if (!Double.isNaN(customWattage)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Custom Wattage Provided for Preset Appliance");
                alert.setContentText("Please do not enter custom wattage for a preset appliance. If you want to add a custom appliance, click 'Add Custom Appliance'.");
                alert.showAndWait();
                wattageField.clear();
                return;
            }

            double presetWattage = presetAppliancePowerMap.get(appliance).getWattage();
            ApplianceData applianceData = new ApplianceData(hoursUsed, presetWattage);
            applianceHoursMap.put(appliance, applianceData);
            updateApplianceList();
            updatePredictionAndTips();
            applianceBox.getSelectionModel().clearSelection();
            hoursUsedField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Appliance");
            alert.setHeaderText("Appliance not found in preset list.");
            alert.setContentText("Please use the 'Add Custom Appliance' button to add a custom appliance.");
            alert.showAndWait();
        }
    }




    private void addCustomAppliance() {
        String customAppliance = applianceBox.getEditor().getText();
        double hoursUsed = parseDoubleValue(hoursUsedField.getText());
        double wattage = parseDoubleValue(wattageField.getText());

        if (customAppliance.isEmpty() || customAppliance.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid custom appliance name");
            alert.setContentText("Please enter a valid custom appliance name.");
            alert.showAndWait();
            return;
        }

        if (applianceBox.getSelectionModel().getSelectedIndex() != -1) {
            BillPredictor billPredictor = new BillPredictor();
            Map<String, ApplianceData> presetAppliancePowerMap = billPredictor.getAppliancePowerMap();
            String selectedAppliance = applianceBox.getValue();

            if (presetAppliancePowerMap.containsKey(selectedAppliance) && Double.isNaN(wattage)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid input for wattage.");
                alert.setContentText("Please enter the wattage (in watts) for the preset appliance or select 'Add Appliance' to add a preset appliance.");
                alert.showAndWait();
                return;
            }
        }

        ApplianceData applianceData = new ApplianceData(hoursUsed, wattage);
        applianceBox.getItems().add(customAppliance);
        applianceBox.getSelectionModel().select(customAppliance);
        applianceHoursMap.put(customAppliance, applianceData);
        updateApplianceList();

        updatePredictionAndTips();
        hoursUsedField.clear();
        wattageField.clear();
    }


    private double parseDoubleValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private void updatePredictionAndTips() {
        if (!applianceHoursMap.isEmpty()) {
            BillPredictor billPredictor = new BillPredictor();
            double predictedBill = billPredictor.predictNextMonthBill(applianceHoursMap);

            String formattedBill = String.format("₱%.2f", predictedBill);

            TipsGenerator tipsGenerator = new TipsGenerator();
            String tips = tipsGenerator.suggestTips(predictedBill);

            resultLabel.setText("Predicted next month's bill: " + formattedBill);
            tipsArea.setText(tips);
        } else {
            resultLabel.setText("");
            tipsArea.setText("");
        }
    }


    private void updateApplianceList() {
        addedAppliances = new ArrayList<>(applianceHoursMap.keySet());

        List<String> addedAppliances = new ArrayList<>(applianceHoursMap.keySet());

        addedAppliancesList.setItems(FXCollections.observableList(addedAppliances));
    }

    private void resetForm() {
        applianceHoursMap.clear();
        applianceBox.getSelectionModel().clearSelection();
        hoursUsedField.clear();
        wattageField.clear();
        resultLabel.setText("");
        tipsArea.setText("");
        addedAppliances.clear();
        updateApplianceList();
    }


    private void updateMonth() {
        String selectedMonth = monthComboBox.getValue();
        if (selectedMonth != null) {
            saveData(selectedMonth);
            loadData(selectedMonth);
        }
    }
    private void loadData(YearMonth yearMonth) {
        String month = yearMonth.getMonth().toString();
        loadData(month);
    }

    private void loadData(String month) {
        try {
            FileInputStream fis = new FileInputStream("data_" + month + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            applianceHoursMap.clear();
            applianceHoursMap = (HashMap<String, ApplianceData>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Data loaded successfully for the month: " + month);
            updateApplianceList();
            updatePredictionAndTips();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }


    private void saveData(String month) {
        try {
            FileOutputStream fos = new FileOutputStream("data_" + month + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(applianceHoursMap);
            oos.close();
            fos.close();
            System.out.println("Data saved successfully for the month: " + month);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    private void saveData(YearMonth yearMonth) {
        String month = yearMonth.getMonth().toString();
        try {
            FileOutputStream fos = new FileOutputStream("data_" + month + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(applianceHoursMap);
            oos.close();
            fos.close();
            System.out.println("Data saved successfully for the month: " + month);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private void saveData() {
        saveData("default");
    }

    private void loadData() {
        loadData("default");
        applianceHoursMap.clear();
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
