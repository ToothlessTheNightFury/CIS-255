import java.util.ArrayList;
import java.util.Collections;

public class InsuranceData implements Comparable<InsuranceData> {

    private String month;
    private ArrayList<Integer> yearValue = new ArrayList<>();

    public InsuranceData (String data) {
        String[] array = data.split(",");

        // Set the month
        month = array[0];

        // Add each year to the array list
        for (int i = 1; i < array.length; i++) {
            String str = array[i];
            yearValue.add(Integer.parseInt(str));
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYearValue (int index) {
        return yearValue.get(index);
    }

    public Integer getNumOfYears() {
        return yearValue.size();
    }


    public Integer getMin() {
        return Collections.min(yearValue);
    }

    public Integer getMax() {
        return Collections.max(yearValue);
    }

    @Override
    public int compareTo(InsuranceData o) {
        Integer min = Collections.min(yearValue);
        Integer minO = Collections.min(o.yearValue);

        return min.compareTo(minO);
    }

    @Override
    public String toString() {
        String yearValueStr = yearValue.toString();
        yearValueStr = yearValueStr.replaceAll("\\[", "{");
        yearValueStr = yearValueStr.replaceAll("]", "}");

        return String.format("[%s, %s]", getMonth(), yearValueStr);
    }
}
