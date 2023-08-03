import java.io.Serializable;

public class ApplianceData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double hoursUsed;
    private double wattage;

    public ApplianceData(double hoursUsed, double wattage) {
        this.hoursUsed = hoursUsed;
        this.wattage = wattage;
    }

    public double getHoursUsed() {
        return hoursUsed;
    }

    public double getWattage() {
        return wattage;
    }
}
