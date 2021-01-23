# Known Errors and Typos

This is a list of known errors and typos with the uploaded project. The typos are not corrected as the project has already been submitted. 
However, those who wish to learn or use the code in this project should be aware of these and correct them before use.

## CreditCard.java
https://github.com/ToothlessTheNightFury/CIS-255/blob/ff1ed638c7a6ac3080ec944f450e2f928fa0d151/Project%201%20(Submission)/CreditCard.java#L389

`newDigit = newDigit < 9 ? newDigit : 1 + (newDigit % 10);` should be `newDigit = newDigit <= 9 ? newDigit : 1 + (newDigit % 10);`

If the digit is 9, the validCheckSum method will incorrectly believe 9 has two digits, and thus, will do 1 + 9 = 10. Changing the < to a <= sign will fix this.
