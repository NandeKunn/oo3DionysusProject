public class TipsGenerator {
    public String suggestTips(double predictedBill) {
        String tips = "Tips to reduce your electricity consumption:\n";
        if (predictedBill > 0 && predictedBill <= 2000) {
            tips += "- Turn off and unplug devices when not in use.\n";
            tips += "- Use natural lighting whenever possible.\n";
            tips += "- Use power strips to easily turn off multiple devices.\n";
            tips += "- Replace incandescent bulbs with energy-efficient LED or CFL bulbs.\n";
            tips += "- Invest in energy-efficient appliances and regularly maintain them.\n";
            tips += "- Seal windows and doors to prevent air leakage.\n";
        } else if (predictedBill > 2000 && predictedBill <= 4000) {
            tips += "- Opt for energy-efficient appliances and LED lighting.\n";
            tips += "- Consider installing a programmable or smart thermostat.\n";
            tips += "- Perform a home energy audit to identify areas for improvement.\n";
            tips += "- Wash clothes in cold water and run full loads only.\n";
            tips += "- Insulate your home to improve energy conservation.\n";
        } else if (predictedBill > 4000) {
            tips += "- Consider upgrading to a high-efficiency HVAC system.\n";
            tips += "- Install solar panels to supplement your energy needs.\n";
            tips += "- Turn off and unplug electronics when not in use.\n";
            tips += "- Use a power strip to easily turn off multiple devices.\n";
            tips += "- Insulate your home and seal ducts to improve HVAC efficiency.\n";
        } else {
            tips += "Your electricity usage is efficient. Keep up the good work!\n";
        }
        return tips;
    }
}
