import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonthPicker extends ComboBox<YearMonth> {

    private static final String dateFormatPattern = "MMMM yyyy";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(dateFormatPattern, Locale.ENGLISH);
    private static final YearMonth START_MONTH = YearMonth.of(2023, Month.JANUARY);
    private static final YearMonth END_MONTH = YearMonth.of(2023, Month.DECEMBER);

    public MonthPicker() {
        setConverter(new StringConverter<YearMonth>() {
            @Override
            public String toString(YearMonth yearMonth) {
                if (yearMonth != null) {
                    return dateFormat.format(yearMonth);
                } else {
                    return "";
                }
            }

            @Override
            public YearMonth fromString(String string) {
                if (string != null && !string.trim().isEmpty()) {
                    return YearMonth.parse(string, dateFormat);
                } else {
                    return null;
                }
            }
        });

        setPromptText(dateFormatPattern.toLowerCase());

        // Populate the months from the start month to the end month
        List<YearMonth> months = new ArrayList<>();
        YearMonth currentMonth = START_MONTH;
        while (!currentMonth.isAfter(END_MONTH)) {
            months.add(currentMonth);
            currentMonth = currentMonth.plusMonths(1);
        }
        setItems(FXCollections.observableArrayList(months));
    }
}
