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
public class ClaimsPaidBarChart extends Application {

	/* PROJECT: You will set these values based on the data file. */
	private int yAxisMin, yAxisMax; // the min and max of claims paid
	private String dataLabel; // the year of the data
	
	private static final int TICK_INTERVAL = 1000; // used to customize the visual display

	/* PROJECT: Test with all three valid file names and one invalid file name. */
	// private String fileName;
	private String fileName = "Disability_Insurance_2016.csv";
	// private String fileName = "Disability_Insurance_2017.csv";
	// private String fileName = "Disability_Insurance_2018.csv";
	// private String fileName = "Disability_Insurance_BadFileName.csv";
	
	// Use this file ONLY if completing the extra credit
	// private String fileName = "Disability_Insurance_2016-2018_ExtraCredit.csv";

	private final String validDataPattern = "[a-zA-Z]\\w+,[\\d]+";

	private String errorMessage = "";

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
		Series claimsPaidByMonth = new Series();
		boolean success = fillData(claimsPaidByMonth);
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
		claimsPaidByMonth.setName(dataLabel);

		// adds the data series to the chart, creates a scene, sets the stage, and shows!
		barChart.getData().add(claimsPaidByMonth);
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
	private boolean fillData(Series series) {	
		/* PROJECT: YOUR CODE GOES HERE! */

		// read in the data file
		// use a Scanner to parse the remaining lines of the file, which each contain a month name and val
		ArrayList<String> dataFile = getDataFile (fileName);

		// use exception handling to account for a bad file. if a file is invalid, display an alert
		if (!isValidFile(dataFile, fileName)) {
			return false;
		}

		// Create monthLabel and monthValue lists to store month labels and their respective values
		ArrayList<String> monthLabel = new ArrayList<>();
		ArrayList<Integer> monthValue = new ArrayList<>();

		// Store all month labels and their respective values from dataFile to monthLabel and monthValue
		storeMonthLabelAndValue(dataFile, monthLabel, monthValue);

		// get the dataLabel (the year) from the first line of the file
		setAllDataLabels(dataFile);

		// create a Data object to add to the series for each month/value pair: series.getData().add(new Data(monthLabel, monthValue));
		addDataToGraph(series, monthLabel, monthValue);

		// calculate the min and max values seen and update yAxisMin and yAxisMax based on these values
		setYAxisMinMax(monthValue);
		
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

		// Check for array size equal to or less than 1. If not, no data points found and not valid file
		if (dataFile.size() <= 1) {
			errorMessage = String.format("Invalid file: %s\nNo data points found.", fileName);
			return false;
		}

		// Check all data for valid input. If not valid input, not valid file
		for (int i = 1; i < dataFile.size(); i++) {
			String data = dataFile.get(i);


			if (!data.matches(validDataPattern)) {
				errorMessage = String.format("Invalid file: %s\n%s on line %d not valid data.", fileName, data, i + 1);
				return false;
			}
		}

		return true;
	}

	private void storeMonthLabelAndValue(ArrayList<String> dataFile, ArrayList<String> monthLabel, ArrayList<Integer> monthValue) {
		for(String element:dataFile){
			if(element.contains(",")) {
				String[] arr = element.split(",");
				monthLabel.add(arr[0]);
				monthValue.add(Integer.parseInt(arr[1]));
			}
		}
		System.out.printf("DATA INPUT\n%s\n%s", monthLabel, monthValue);
	}

	private void setAllDataLabels(ArrayList<String> dataFile) {
		// Data label stored in 0th index of dataFile list
		dataLabel = dataFile.get(0);
	}

	private void addDataToGraph(Series series, ArrayList<String> monthLabel, ArrayList<Integer> monthValue) {
		for (int i = 0; i < monthLabel.size(); i++) {
			series.getData().add(new XYChart.Data(monthLabel.get(i), monthValue.get(i)));
		}
	}

	private void setYAxisMinMax(ArrayList<Integer> monthValue) {
		yAxisMin = Collections.min(monthValue);
		yAxisMax = Collections.max(monthValue);
	}

	private int getVisuallyAppealingAxisValue(int value) {
		return (value + (TICK_INTERVAL-1)) / TICK_INTERVAL  * TICK_INTERVAL;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}