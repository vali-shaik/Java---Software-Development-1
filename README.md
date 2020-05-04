# Java---Software-Development-1

Java program to decomposing a problem, creating a design for a program, and implementing and testing a program.

Problem
==============
Write a class that stores a matrix of data and can have a set of operations invoked on the data.
A “main” program that will then let a user invoke the operations on the data.
The normal use of your class will have the following sequence:
- Read data from a file
- Do any of the following, in any order, a number of times
o Create new columns for data
o Set a column’s data values based on a restricted form of equation (more on the
restriction later)
o Show the top 5 rows of data
o Show all of the data
o Write the data to a file

Each column of data in the incoming data file will have a column name. References to columns
will be by the column names.
The equations that can change the value of a column will have at most one binary operator.
The two operands of the operator will be either a constant or a column name. For example, if
we have columns called “baseprice”, “price”, “quantity”, “tax”, and “total” then we might have
any of the following equations:
- tax = price * .15
- total = price + tax
- quantity = 1
- price = baseprice
We would _not_ have equations like
- total = (price + tax)
- total = baseprice * quantity * .15
The only binary operators that will be used are +, -, *, and /. More specifically, all equations will
have one of the following forms:
- <columnName> = <value> <operator> <value>
- <columnName> = <value>
Where <columnName> is a column name, <value> is either an integer or a column name, and
<operator> is one of + (addition), - (subtraction), * (multiplication), or / (division).
We will not add rows to the data interactively.
Your class will be called “DataTransformer” and will include the following methods:
- boolean clear() – erase all data from your object. Return True if the object is empty
after the operation.
- Integer read( String filename ) – read in the contents of the file to the object. Return
the number of data rows read. The data should replace any information already in the
class. Columns will be separated by tab characters.
- boolean newColumn( String columnName ) – create a new column with the given name.
The column will only store an integer and will begin with a value of 0. Return True if a
new column was created and is ready to be used.
- Integer calculate( String equation ) – apply the given equation to the data in the object
and return the number of rows that were calculated for the equation. If a calculated
value will have decimal places then round the value to the nearest integer (a value of .5
rounds up).
- void top() – print the first 5 rows to the screen. Include a starting row with the column
names. Separate the columns with a tab character. The order of the columns should be
the same as in the input file, with new columns appearing afterward in the order of
creation.
- void print() – print all rows to the screen. . Include a starting row with the column
names. Separate the columns with a tab character. The order of the columns should be
the same as in the input file, with new columns appearing afterward in the order of
creation.
- Integer write( String filename ) – write the contents of the object to the given file.
Include a first row with the names of the columns. Separate the columns with a tab
character. Return the number of data rows written to the file.

Inputs
==========
An input data file for your class will have all columns separated by a tab character. The first line
of the file will have the column names. Every line after that will contain data for your object.
Each data entry in the file will be either an alphabetic string (possibly with spaces) or an integer.
Example:
name baseprice quantity
bottled water 5 3
bread 2 5
rice 3 3
Sample sequence of transformations
read: (filename of the file shown in “Inputs” section)
newColumn: value
newColumn: tax
newColumn: total
calculate: value = baseprice * quantity
calculate: tax = value * 15
calculate: tax = tax / 15
calculate: total = value + tax
print:
Output of print would then be:
name baseprice quantity value tax total
bottled water 5 3 15 2 17
bread 2 5 10 2 12
rice 3 3 9 1 10

