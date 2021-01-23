# Known Errors and Typos

This is a list of known errors and typos with the uploaded project. The typos are not corrected as the project has already been submitted. 
However, those who wish to learn or use the code in this project should be aware of these and correct them before use.

## CreditCard.java
https://github.com/ToothlessTheNightFury/CIS-255/blob/764ac7820ba4886f1a05d411fc1c26382e751dcc/Project%201/CreditCard.java#L410

`newDigit = newDigit < 9 ? newDigit : 1 + (newDigit % 10);` should be `newDigit = newDigit <= 9 ? newDigit : 1 + (newDigit % 10);`

If the digit is 9, the validCheckSum method will incorrectly believe 9 has two digits, and thus, will do 1 + 9 = 10. Changing the < to a <= sign will fix this.
