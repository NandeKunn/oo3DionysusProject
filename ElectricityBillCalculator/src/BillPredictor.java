import java.util.HashMap;
import java.util.Map;

public class BillPredictor {
    private Map<String, Double> appliancePowerMap;

    public BillPredictor() {
        initializeAppliancePowerMap();
    }

    public double predictNextMonthBill(Map<String, Double> applianceWattageMap) {
        double totalPowerUsage = calculateTotalPowerUsage(applianceWattageMap);

        // Calculate the predicted bill based on power usage and rates
        // Replace this logic with your actual calculation based on rates and power usage
        double predictedBill = totalPowerUsage * 0.1;

        return predictedBill;
    }

    private double calculateTotalPowerUsage(Map<String, Double> applianceWattageMap) {
        double totalPowerUsage = 0.0;

        for (String appliance : applianceWattageMap.keySet()) {
            double wattage = applianceWattageMap.get(appliance);
            totalPowerUsage += wattage;
        }

        return totalPowerUsage;
    }

    private void initializeAppliancePowerMap() {
        appliancePowerMap = new HashMap<>();
        // Initialize your appliancePowerMap here
        appliancePowerMap.put("Refrigerator", 150.0);
        appliancePowerMap.put("Electric Stove", 1000.0);
        appliancePowerMap.put("Microwave Oven", 800.0);
        appliancePowerMap.put("Rice Cooker", 400.0);
        appliancePowerMap.put("Blender", 300.0);
        appliancePowerMap.put("Aircon", 1000.0);
        appliancePowerMap.put("Electric Fan", 75.0);
        appliancePowerMap.put("Electric Oven", 1000.0);
        appliancePowerMap.put("Pressure Cooker", 700.0);
    }
}
