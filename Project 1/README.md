# Project 1: Credit Card Number

Write a program to read in and verify a credit card number and expiration date input by a user. I have provided some of the code and you will complete the missing code and methods.

You are encouraged to work in groups of up to four students. All group members will receive the same score and feedback. You can earn up to 20 extra credit project points for working in a group (10 points per project, max of 20). 

## Project Requirements

Your program must compile. Programs that do not compile will receive 0 points.

Your program will read in a credit card number and expiration date from the user and validate the data. The program will continue to ask for user input until a valid number and date are entered. The rules for what is valid are specified below.

1. **(20 points)** Complete the readYearFromUser() and readMonthFromUser(int) methods.
   * Each method should contain a loop so the code repeats until a valid month or year is entered by the user.
   * If the user enters an invalid year or month, print an error message and ask them to try again.
   * The readMonthFromUser(int) method reads in the month as a number. A valid month is any number between 1 and 12 (inclusive).
   * A valid year is 2021 or later.
   * Note that you can ignore the parameter in the readMonthFromUser(int) for the requirements- you only need to use this if you are completing the extra credit.
	 
2. **(20 points)** Complete the readCreditCardNumberFromUser() method.
   * This method should contain a loop so the code repeats until a valid credit card number is entered by the user.
   * A number is valid if it passes three tests:
   * It is 16 digits long.
   * It starts with a 4, 5, or 6 as the first digit.
   * It passes the checkSum test (described next).
   * If the user enters an invalid number, print an error message describing the reason why the number is invalid and ask them to try again.

3. **(20 points)** Complete the passesCheckSum(String) method.
   * You will invoke this method from the readCreditCardNumberFromUser() method.
   * This method examines a String that you have already confirmed contains 16 digits. 
   * The method determines if those numbers pass the checkSum test, which is described on the Check Sum Algorithm page.

4. **(10 points)** Print the valid card and date information back to the user.
   * Display the credit card number in four-digit groupings with a space in between each grouping (e.g., 0000 0000 0000 0000).
   * Display the month and year in "month/year" format (e.g., 12/2020, 4/2021).

**Code Design (30 points)**

* Follow Java coding conventions and best practices.
* Follow naming conventions for variables, methods, and constants.
* Use constants to improve readability and maintainability.
* Properly indent your code.
* Design your code (including loops and conditionals) to reduce repeated or duplicated code.
* Choose conditional and loop structures that are the best logical match to the task.
* Write helper methods when it will help clarify the code or reduce repeated code.

## Provided Files
CreditCardValidator.java

## Extra Credit
**(15 points)** Modify the readMonthFromUser(int) method to determine if the month is valid for the current year. Pass the year entered by the user to the readMonthFromUser(int) method as a parameter.

If the year is the current year:
* Include an additional criteria for whether the month is valid. The month is only valid if it is the current month or later.
* If the month is the current month, print a warning message to the user ("Warning: your card expires this month!").

To determine the current year and month, use the java.time.LocalDate class. We have not covered this- that is what makes it extra credit! :) The Oracle tutorial on the LocalDate (Links to an external site.) class is a great resource and you can google other help as well.

## Notes
* We have not yet covered arrays and you do not need to use arrays for this project.
* Do not use arrays in the program. You will lose some points if you use arrays.
* You should use String processing, conditionals, and loops.
* Your program does not have to account for the user entering non-numeric input or empty input.
* It's okay if your program crashes or behaves unexpectedly in these cases- we'll eventually learn how to handle this!
* I've provided some helper methods that you can use to convert between chars, Strings, and ints. We haven't yet covered this (we will in Module 4!), so for now you can just these methods. Don't worry if they don't quite make sense yet. If you need other conversions that are not provided, post to the discussion board!
* To read numeric integer values from the user, I recommend using the following code.

``` int userNumber = Integer.parseInt(scan.nextLine());```

* If you use the scan.nextInt() method instead, that you might need to add an extra scan.nextLine() in between the reading of integer input and character input. I do not recommend this. (We'll learn more about this in Module 4!)

## Example Program and Test Cases
Below is a video of my interacting with a working program with extra credit.

[Private Video Here]

Here is a list of valid numbers that you can use for testing:

4111111111111111

4012888888881881

5019717010103742

5555555555554444

5610591081018250

6011111111111117

6011000990139424

Here are some invalid numbers that are the right length and have the correct starting digit, but fail the sumCheck test:

6610591081018250

5610591081018251

4610591081018250

When testing invalid numbers and values, make sure to test for:
* credit card numbers that are too short or too long
* credit card numbers that do not start with the required first digit
* credit card numbers that start with the required digit but do not pass the sumCheck test
* months that are outside the valid range (1-12, inclusive)
* years that outside the valid range (>= 2021)
* if completing the extra credit: months that are valid for the current year (>= the current month)

## Project Submission

To submit your project:
* Upload CreditCardValidator.java.
* Be sure to submit .java files, not .class files!
* Watch the video below for an example of where to find .java files on a PC. If you are unsure where to find the files on your machine, post to the discussion board! [Private Video Here]

If submitting as a group:
* Have one group member submit the assignment files. Only one submission should be uploaded for each group.
* List the names of all group members in Canvas comment box below the file upload box.
