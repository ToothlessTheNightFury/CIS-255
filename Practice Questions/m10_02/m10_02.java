import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class m10_02 {

    /**
     * Prompts the user for a file name, then reads that file and stores all
     * integers into an ArrayList. File can have comma-separated values
     * and/or multiple lines.
     *
     * Then, prints contents of ArrayList, the average of the list, as well as
     * the number of non-integer values in the file.
     *
     * @return an ArrayList of all comma-separated integers in the file
     */
    public static ArrayList<Integer> readIntsFromFile() {

        ArrayList<Integer> ints = new ArrayList<Integer>();

        String fileName = promptFileName();
        int nonInts = readFile(fileName, ints);

        if (nonInts != -1) {
            printIntsFromFileInfo(ints, nonInts);
        }

        return ints;
    }

    /**
     * Prompts the user for a file name to read.
     *
     * @return the file name
     */
    public static String promptFileName() {

        System.out.println("Please input the file name, including the extension (ex. ints.txt)");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Reads a file and stores all comma-separated integers in an ArrayList.
     * Returns the number of comma-separated non-integer values in the file.
     *
     * @param fileName the file name to read
     * @param ints the ArrayList to store values in
     * @return the number of non-integer values in the file
     */
    public static int readFile (String fileName, ArrayList<Integer> ints) {

        int nonInts = 0;
        String filePath = getFilePath(fileName);
        System.out.printf("\nAttempting to open %s\n", filePath);

        try {
            File myFile = new File(fileName);
            Scanner scanner = new Scanner(myFile);

            nonInts = readLines(scanner, ints);
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.printf("ERR: Unable to open file %s. Check if the file exists in the indicated path.\n", filePath);
            return -1;
        }

        return nonInts;
    }

    /**
     * Gets the file path of the specified file name.
     *
     * @param fileName the file name
     * @return the file path of the file
     */
    public static String getFilePath (String fileName) {
        String filePath = System.getProperty("user.dir") + "\\" + fileName;
        return filePath;
    }

    /**
     * Reads all the lines in Scanner object. Stores all comma-separated integers
     * in an ArrayList. Returns the number of comma-separated non-integer values
     * in the file.
     *
     * @param scanner the scanner object to read from
     * @param ints the ArrayList to store in
     * @return the number of non-integer values in the Scanner
     */
    public static int readLines (Scanner scanner, ArrayList<Integer> ints) {

        int nonInts = 0;

        while (scanner.hasNextLine()) {
            nonInts += readLine(ints, scanner.nextLine());
        }

        return nonInts;
    }

    /**
     * Reads a comma-separated String and splits it into a String[]. Iterates
     * through the array and reads the individual values. Counts the number of
     * non-integer values in the line.
     *
     * @param ints the ArrayList to store in
     * @param line the line of text to read from
     * @return the number of non-integer values in the line
     */
    public static int readLine (ArrayList<Integer> ints, String line) {
        int nonInts = 0;

        String[] lineArr = line.split(",");

        for (String str : lineArr) {
            nonInts += readStr(ints, str);
        }

        return nonInts;
    }

    /**
     * Reads a value. If the value is an integer, adds it to ArrayList. Returns
     * 0 if the value is an integer (for zero non-integer values found), or 1 if the value
     * is a non-integer (for one non-integer value found).
     *
     * @param ints the ArrayList to store in
     * @param str the value to read
     * @return the number of non-integer values found
     */
    public static int readStr (ArrayList<Integer> ints, String str) {

        if (isInt(str)) {
            ints.add(Integer.parseInt(str));
            return 0;
        }
        return 1;
    }

    /**
     * Returns true if the string is an integer, otherwise false.
     *
     * @param str the string to check
     * @return true if str is an integer, otherwise false
     */
    public static boolean isInt (String str) {

        if (str == null) {
            return false;
        }

        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            System.out.printf("ERR: '%s' is not an integer.\n", str);
            return false;
        }

        return true;
    }

    /**
     * Prints the contents of the ArrayList, the average, as well as the number of
     * non-integer values.
     *
     * @param ints the ArrayList to print info about
     * @param nonInts the number of non-integer values found
     */
    public static void printIntsFromFileInfo (ArrayList<Integer> ints, int nonInts) {

        System.out.printf(
                "\nArray: %s\n" +
                "Average: %.2f\n" +
                "Number of Non-Ints: %d\n",
                ints, getAverage(ints), nonInts);
    }

    /**
     * Gets the average of the ArrayList.
     *
     * @param ints the ArrayList to get the average of
     * @return the average
     */
    public static double getAverage (ArrayList<Integer> ints) {
        double sum = 0.0;
        if (ints.isEmpty()) {
            return sum;
        }

        for (double num : ints) {
            sum += num;
        }

        return sum / ints.size();
    }

    public static void main (String[] args) {
        ArrayList<Integer> ints = readIntsFromFile();
    }
}
