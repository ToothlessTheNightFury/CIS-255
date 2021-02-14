import java.util.*;

public class m4_21 {

    public static int findModeWithoutSorting (int[] numbers) {

        int modeNum = 0;
        int modeCount = 0;

        HashMap<Integer,Integer> numMap = new HashMap<Integer,Integer>();

        for (int i = 0; i < numbers.length; i++) {

            if (!numMap.containsKey(numbers[i])) {
                numMap.put(numbers[i], 1);
            }
            else {
                numMap.put(numbers[i], numMap.get(numbers[i]) + 1);
            }
        }

        for (int key : numMap.keySet()) {

            if (numMap.get(key) > modeCount) {

                modeNum = key;
                modeCount = numMap.get(key);
            }
        }

        printNumMap(numMap); // Demonstration; comment out when using in application

        return modeNum;
    }

    public static void main (String[] args) {

        int[] arr1 = { 0, 4, 2, 7, 2, 4, 2 }; // mode: 2 (3 times)
        int[] arr2 = { 1, 8, 2, 7, 8, 2, 4, 8, 2, 8, 8 }; // mode: 8 (5 times)

        System.out.printf("\n%s\nMode: %d\n\n", Arrays.toString(arr1), findModeWithoutSorting(arr1));
        System.out.printf("\n%s\nMode: %d\n\n", Arrays.toString(arr2), findModeWithoutSorting(arr2));
    }

    public static void printNumMap (HashMap<Integer,Integer> numMap) {

        System.out.printf("%-10s%s\n", "Key", "Value");

        for (int key : numMap.keySet()) {
            System.out.printf("%-10d%d\n", key, numMap.get(key));
        }
    }
}
