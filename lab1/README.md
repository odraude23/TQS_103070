### 1.1 J

The solution to the stack problem is simple, so i can't think on a scenario where the stack will fail. But this doesn't mean there isn't one. In simple solutions like this, code coverage should cover most of all the scenarios but, in more elaborated solutions, we cannot rely only on code coverage.

### 1.2 C

CouponEuromillions does not offer coverage for the format() method, nor countDips().
The DemoMain class doesn't have any tests for it's methods.
Not all branches of Dip class have coverage (because of the disabled tests).

![alt text](<print1.png>)

After enabling the tests and fixing some of them, the coverage value increased.

![alt text](<print2.png>)

Not all branches have coverage in the BoundedSetOfNaturals class.

![alt text](<print3.png>)

### 1.2 D

Added more tests to the add() method and implement new tests to the intersects() method (implemented in this exercise).

### 1.2 E

The coverage increased after the priveous exercise.

![alt text](<print4.png>)

### 1.2 F

![alt text](<print5.png>)