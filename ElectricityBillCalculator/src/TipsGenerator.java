public class TipsGenerator {
    public String suggestTips(double predictedBill) {
        String tips = "Tips to reduce your electricity consumption:\n";
        if (predictedBill > 0 && predictedBill <= 1000) {
            tips += "- Turn off lights and unplug devices when not in use.";
        } else if (predictedBill > 1000 && predictedBill <= 2000) {
            tips += "- Use energy-efficient appliances and LED bulbs.\n";
            tips += "- Adjust your air conditioning temperature to an energy-saving level.";
        } else if (predictedBill > 2000) {
            tips += "- Insulate your home to reduce heating and cooling costs.\n";
            tips += "- Make sure your appliances are energy-efficient.\n";
            tips += "- Consider using renewable energy sources such as solar panels.";
        } else {
            tips += "No specific tips available for the predicted bill.";
        }
        return tips;
    }
}
