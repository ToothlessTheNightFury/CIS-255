import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.stage.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;

import java.io.IOException;
import java.util.*;
import java.nio.file.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ClaimsPaidBarChartEC extends Application {

    /* PROJECT: You will set these values based on the data file. */
    private int yAxisMin, yAxisMax; // the min and max of claims paid
    private String dataLabel; // the year of the data

    private static final int TICK_INTERVAL = 1000; // used to customize the visual display

    /* PROJECT: Test with all three valid file names and one invalid file name. */
    // private String fileName;
    // private String fileName = "Disability_Insurance_2016.csv";
    // private String fileName = "Disability_Insurance_2017.csv";
    // private String fileName = "Disability_Insurance_2018.csv";
    // private String fileName = "Disability_Insurance_BadFileName.csv";

    // use this file ONLY if completing the extra credit
    private String fileName = "Disability_Insurance_2016-2018_ExtraCredit.csv";

    private String errorMessage = "";
    private int numOfYears;

    @Override
    public void start(Stage stage) {

        fileName = FileNameBox.getFileName(fileName);
        System.out.printf("FILE NAME\n%s\n\n", fileName);

        /* creates the chart, x-axis, and y-axis */
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        BarChart barChart = new BarChart(xAxis, yAxis);
        barChart.setTitle("Disability Insurance Claims Paid");
        xAxis.setLabel("Month");
        yAxis.setLabel("Claims Paid");
        yAxis.setTickUnit(TICK_INTERVAL);

        /* PROJECT: This is the series for the chart. You will add data to this series.  */
        ArrayList<Series> claimsPaidByMonthList = new ArrayList<>();
        boolean success = fillData(claimsPaidByMonthList);
        if (!success) {
            showAlert(errorMessage);
            return;
        }

        /* PROJECT: Once the fillData method is done, you have set the yAxisMin, yAxisMax,
         * and dataLabel variables. This code now updates the display using those values. */
        int yAxisDisplayMin = getVisuallyAppealingAxisValue(yAxisMin);
        int yAxisDisplayMax = getVisuallyAppealingAxisValue(yAxisMax);
        yAxis.setLowerBound(yAxisDisplayMin-TICK_INTERVAL);
        yAxis.setUpperBound(yAxisDisplayMax+TICK_INTERVAL);

        // adds the data series to the chart, creates a scene, sets the stage, and shows!
        barChart.getData().addAll(claimsPaidByMonthList);
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert (String errorMessage) {
        System.out.printf("ERROR\n%s", errorMessage);
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.setHeaderText("");
        alert.show();
    }

    /* PROJECT: Your code goes in this method. I've added comments below
     * to remind you of what this method needs to do. */
    private boolean fillData(ArrayList<Series> seriesList) {
        /* PROJECT: YOUR CODE GOES HERE! */

        // read in the data file
        // use a Scanner to parse the remaining lines of the file, which each contain a month name and val
        ArrayList<String> dataFile = getDataFile (fileName);

        // use exception handling to account for a bad file. if a file is invalid, display an alert
        if (!isValidFile(dataFile, fileName)) {
            return false;
        }

        // Create InsuranceData list to store all insurance claim data
        ArrayList<InsuranceData> insuranceData = new ArrayList<>();

        // Store all insurance data from dataFile in insuranceData
        storeInsuranceData(dataFile, insuranceData);

        // Create a series for every year
        createAllDataSeries(seriesList, numOfYears);

        // set the dataLabel (the year) from the first line of the file
        setDataLabel(dataFile, seriesList);

        // create a Data object to add to the series for each month/value pair: series.getData().add(new Data(monthLabel, monthValue));
        addDataToGraph(seriesList, insuranceData);

        // calculate the min and max values seen and update yAxisMin and yAxisMax based on these values
        setYAxisMinMax(insuranceData);

        return true;
    }

    private ArrayList<String> getDataFile(String fileName) {
        ArrayList<String> outputArray = new ArrayList<String>();

        try {
            Path inputPath = Paths.get(fileName);
            Scanner fileScan = new Scanner(inputPath);

            while(fileScan.hasNextLine()) {
                outputArray.add(fileScan.nextLine());
            }

        } catch(IOException ex) {
            return new ArrayList<String>(); // If file not found, return blank ArrayList
        }

        return outputArray;
    }

    private boolean isValidFile(ArrayList<String> dataFile, String fileName) {

        // Check for empty ArrayList. If blank, file not found
        if (dataFile.isEmpty()) {
            errorMessage = String.format("File blank or not found: %s\nCannot run program.", fileName);
            return false;
        }

        // Get years and number of years
        String[] years = dataFile.get(0).split(",");
        numOfYears = years.length - 1;

        // Check for at least "Month, YEAR". If not, not enough years and not valid file
        if (years.length < 2) {
            String data = dataFile.get(0);
            errorMessage = String.format("Invalid file: %s\n'%s' on line 1 not valid data. Must have \"Month\" followed by number of years, separated by commas.", fileName, data);
            return false;
        }

        // Check for array size equal to or less than 1. If not, no data points found and not valid file
        if (dataFile.size() <= 1) {
            errorMessage = String.format("Invalid file: %s\nNo data points found.", fileName);
            return false;
        }

        // Check all data for valid input. If not valid input, not valid file
        for (int i = 1; i < dataFile.size(); i++) {
            int lineNum = i + 1;
            String data = dataFile.get(i);
            String[] array = data.split(",");

            // If data has one or less data points, not valid data
            if (array.length <= 1) {
                errorMessage = String.format("Invalid data '%s' on line %s:\nNo data points found.", data, lineNum);
                return false;
            }

            // If data line has a different number of years than specified on first line, not valid data
            int numYearsInLine = array.length - 1;
            if (numYearsInLine != numOfYears) {
                errorMessage = String.format("Invalid data '%s' on line %s:\nLine 1 specified %d years, but line %d gives %d year(s).",
                        data, lineNum, numOfYears, lineNum, numYearsInLine);
                return false;
            }

            // If values given after month aren't numeric, not valid data
            if (!isValidDataLine(array)) {
                errorMessage = String.format("Invalid data '%s' on line %d:\nNon-numeric value found when expecting numeric value.", data, lineNum);
                return false;
            }
        }

        return true;
    }

    private boolean isValidDataLine (String[] array) {
        for (int i = 1; i < array.length; i++) {
            String str = array[i];
            if (!isNumeric(str)) {
                return false;
            }
        }
        return true;
    }

    private void storeInsuranceData(ArrayList<String> dataFile, ArrayList<InsuranceData> insuranceData) {
        for (int i = 1; i < dataFile.size(); i++) {
            String dataStr = dataFile.get(i);
            insuranceData.add(new InsuranceData(dataStr));
        }
    }

    private void createAllDataSeries(ArrayList<Series> seriesList, int numOfYears) {
        for (int i = 0; i < numOfYears; i++) {
            seriesList.add(new Series());
        }
    }

    private void setDataLabel(ArrayList<String> dataFile, ArrayList<Series> seriesList) {
        // Data label stored in 0th index of dataFile list
        String str = dataFile.get(0);
        String[] array = str.split(",");

        for (int i = 0; i < seriesList.size(); i++) {
            seriesList.get(i).setName(array[i + 1]);
        }
    }

    private void addDataToGraph(ArrayList<Series> seriesList, ArrayList<InsuranceData> insuranceData) {
        System.out.println("DATA INPUT");
        for (InsuranceData data : insuranceData) {
            addDataToSeries(seriesList, data);
            System.out.println(data);
        }
    }

    private void addDataToSeries(ArrayList<Series> seriesList, InsuranceData data) {
        for (int i = 0; i < seriesList.size(); i++) {
            Series series = seriesList.get(i);
            series.getData().add(new XYChart.Data(data.getMonth(), data.getYearValue(i)));
        }
    }

    private void setYAxisMinMax(ArrayList<InsuranceData> insuranceData) {
        InsuranceData minData = Collections.min(insuranceData); // Get InsuranceData object with smallest value
        InsuranceData maxData = Collections.max(insuranceData); // Get InsuranceData object with largest value

        yAxisMin = minData.getMin(); // Get min value from smallest InsuranceData object
        yAxisMax = maxData.getMax(); // Get max value from largest InsuranceData object
    }

    private int getVisuallyAppealingAxisValue(int value) {
        return (value + (TICK_INTERVAL-1)) / TICK_INTERVAL  * TICK_INTERVAL;
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}