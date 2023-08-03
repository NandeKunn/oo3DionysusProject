import java.util.HashMap;
import java.util.Map;

public class BillPredictor {
    private Map<String, ApplianceData> appliancePowerMap;

    public Map<String, ApplianceData> getAppliancePowerMap() {
        return appliancePowerMap;
    }
    public BillPredictor() {
        appliancePowerMap = new HashMap<>();
        initializeAppliancePowerMap();
    }

    public double predictNextMonthBill(Map<String, ApplianceData> applianceHoursMap) {
        double totalPowerUsage = calculateTotalPowerUsage(applianceHoursMap);

        double predictedBill = totalPowerUsage * 0.1;
        return predictedBill;
    }

    public void addCustomAppliance(String appliance, double hoursUsed, double wattage) {
        ApplianceData applianceData = new ApplianceData(hoursUsed, wattage);
        appliancePowerMap.put(appliance, applianceData);
    }

    private double calculateTotalPowerUsage(Map<String, ApplianceData> applianceHoursMap) {
        double totalPowerUsage = 0.0;

        for (String appliance : applianceHoursMap.keySet()) {
            ApplianceData applianceData = applianceHoursMap.get(appliance);
            double hoursUsed = applianceData.getHoursUsed();
            double wattage = applianceData.getWattage();


            if (wattage == 0.0 && appliancePowerMap.containsKey(appliance)) {
                wattage = appliancePowerMap.get(appliance).getWattage();
            }

            double wattageUsed = wattage * hoursUsed;
            totalPowerUsage += wattageUsed;
        }

        return totalPowerUsage;
    }

    private void initializeAppliancePowerMap() {
        appliancePowerMap.put("Refrigerator", new ApplianceData(0, 150.0));
        appliancePowerMap.put("Electric Stove", new ApplianceData(0, 1000.0));
        appliancePowerMap.put("Microwave Oven", new ApplianceData(0, 800.0));
        appliancePowerMap.put("Rice Cooker", new ApplianceData(0, 400.0));
        appliancePowerMap.put("Blender", new ApplianceData(0, 300.0));
        appliancePowerMap.put("Aircon", new ApplianceData(0, 1000.0));
        appliancePowerMap.put("Electric Fan", new ApplianceData(0, 75.0));
        appliancePowerMap.put("Electric Oven", new ApplianceData(0, 1000.0));
        appliancePowerMap.put("Pressure Cooker", new ApplianceData(0, 700.0));
        appliancePowerMap.put("Lights", new ApplianceData(0, 60.0));
        appliancePowerMap.put("Television", new ApplianceData(0, 200.0));
        appliancePowerMap.put("Laptop", new ApplianceData(0, 60.0));
        appliancePowerMap.put("Desktop Computer", new ApplianceData(0, 250.0));
        appliancePowerMap.put("Washing Machine", new ApplianceData(0, 500.0));
        appliancePowerMap.put("Dishwasher", new ApplianceData(0, 1200.0));
        appliancePowerMap.put("Water Heater", new ApplianceData(0, 1500.0));
        appliancePowerMap.put("Toaster", new ApplianceData(0, 800.0));
        appliancePowerMap.put("Coffee Maker", new ApplianceData(0, 600.0));
        appliancePowerMap.put("Iron", new ApplianceData(0, 1000.0));
        appliancePowerMap.put("Hair Dryer", new ApplianceData(0, 1200.0));
    }

}
