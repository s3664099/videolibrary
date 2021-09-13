# Video Library

**Author**: David Sarkies
**Submitted**: May 2018
**Version**: 2

## Introduction

This is the second programming assignment that I did during my studies. The first one
doesn't work since it appears to require external code to be able to operate. However, this one
was written from scratch, the first time that we had to do this, though the instructions that
were required to do this was quite extensive.

The program is designed to mimic a video library lending system, and had to be completed using
object orientated programming techniques. Another aspect required us to not only use sub-classes,
but to also expand the code to include games as well as videos. However, there is no database connection
and the program runs by loading data that has been saved in a text file.

The program runs on a console, and was developed using Eclipse.

## Executing Code

Once the source code has been downloaded, navigate to the directory and enter the following:

*javac \*.java*

This will compile the code (and if there are any errors, please notify me). Once the code is compiled
you run the code as follows:

*java Driver*

You should get a screen as follows:

![Menu Screen](https://github.com/s3664099/videolibrary/edit/master/img/menu.png)



This is the second assignment for my Programming Techniques course.
The code isn't the best, especially since going over it I have noticed that I
have hard-coded a lot of numbers in to it.
It probably does need fixing, however I have fixed up pretty much all of the problems
that the marker identified and as such it is probably best to move on to another project.

This program runs on the console and is designed as a rental system for a video/game library.
It adds movies and games to the database, and also allows for borrowing and returning the items,
creating a hire record as it goes.

It is the first object orientated program that I have written, at least in an official capacity.

The program also allows for the data to be saved as a text file, and then reloaded back into
memory. The program, upon start up, will initially search for any files and if none are found
will then generate an empty database.

Since we were not allowed to use array lists in this assignment, we have had to use arrays, which
in Java are immutible once they are created.

## Classes

**Driver Class**

This class exists to run the utility. It creates an instance of the movie master, and then executes it.

**DateTime**

This is a helper class that contains date/time utilities. While there are java classes that do what this does, this was provided
to us by the lecturer for teaching purposes. The methods in this class are as follows:
- DateTime() - sets the current date
- DateTime(int setClockForwardInDays) - advances time from current date
- DateTime(startDate, setClockForward) - advances from a set date
- DateTime(day, month, year) - sets the date at a specific date
- getTime() - returns the current value saved in time
- toString() - common Java function that turns the date into a string
- getCurrentTime() - returns the current date as a string
- getFormattedDate() - returns the date in the Australian format
- getEightDigitDate() - returns the date with the full year & century
- diffDays() - returns the number of days between two dates
- setDate() - sets the current date as a specific date
- setAdvance() - sets the value for the days to be advanced (in seconds)

**Movie Master Class**


**Item Class**
This is and abstract class that forms the basis of the rentable items, namely movies & games.
The variables it contains are: id, title, description, genre, rental fee, and whether it currently borrowed. The object also stores an array which is the hiring record. While it should be a list, since the instructions for the assignment were that we use an array, an array was used.
- hireItem(customerId, date) - processes the hiring of the item. Checks that the id is valid, and the item is not on loan. It then goes through the hiring record to find a blank space, and if none is found, starts at the beginning and locates the oldest record, which it then overwrites. It determines the rental feel, and then marks the item as borrowed.
- checkConflict(date) - this method searches the hire record and makes sure that there is no conflict with the hiring dates. It gets the start and end dates and makes sure that it does not fall within.
- checkOnLoan() - makes sure that the item is not currently on loan, and if it is, will throw and error.
- findOldest() - Moves through the array, and compares each of the dates, and if it finds the one that is the least (that is the comparison is less than 0), then it has found the oldest date.
- checkReturn(returnDate) - used to validate the return date, namely it makes sure that the return date is not prior to the borrow date (you can't return an item before you have borrowed it).
- returnItem(returnDate) - this is an abstract method, and only exists to support the methods in the subclasses.
- updateRecord(lateFee, returnDate) - this method updates the hiring record, namely once the item is returned. It locates the hiring record, and once it is found, it updates it with the return date, and with any late fee.
- getDetails() - this returns the details of the item as a formatted string.
- getHireRecord() - this returns the hiring record as a formatted string. It checks if the first record is blank, and if it is them there is no hire history, otherwise it returns the history. It will find the oldest record, and display it from them.
- constructHireRecord(start,end, details, line) - this builds a hire record.
- lines() - this generates lines to separate records.
- getRentalFee() - an abstract method
- getRentalPeriod() - an abstract method

**Movie Class**
This is a subclass of the item class, and holds data related to movies. The extra variables it holds are isNewRelease and rentalFeel. It also holds some constants, including hire cost, surcharge, weekly and new release period, and late fee divisor
- Movie(id, title, description, genre, isNewRelease) - the constructor, which passes information to the abstract class. It also sets the rental fee based on whether it is a new release or not (new releases have surcharges added.)
- getRentalFee - returns the rental fee.
- returnItem(returnDate) - Returns the item. First checks whether the items is actually on load, and also checks whether there is a conflict with the date, or that it hasn't been returned. Once this is done, it calculates any late fees, if applicable, namely if it is after the hire period, and will the multiply the late fee by the number of days late. Once that is done, it updates the details.
- getDetails() - the same as the  method in the abstract class, except that it builds the strings with movie related details. It also builds the hire record here, the reason being is that further information is added to the string before the hire record is required.
- toString() - turns the object into a string and returns the details.
- getRentalPeriod() - returns the details of the rental period. Checks whether it is a new release or not, and returns the length of the rental period.

**Game**
This is the other subclass for the items. New variable is whether extended hire has been selected, namely that the borrower can pay extra to borrow the game for longer. A variable for the platform has also been included. There are also a number of constants including the rental fee, the rental period, the late fee surchange, the extended hire discount, the number of days for extended hire, and the late fee multiplier.
- Game(id, title, description, genre, platorn) - this is the constructor that builds the game object. It passes the generic information to the superclass, and assigns the platform variable.
- returnItem(returnDate) - This is similar to the return item method for the movie, except there are some differences. For every seven days that the game is late, a surcharge is added to the late fee. If extended hire has been selected, the late fee is reduced by half. Also, if extended hire has been selected, it resets it to false.
- getDetails() - like the movie class, this returns the details of the object, including the hire details. It also advises whether the extended hire has been selected.
- toString() - basically returns the details of the object as a string.
- setExtended() - sets the hire to extended hire if it has been selected. This flag is set back to false when the item is returned.
- getRentalFee() - returns the details of the rental fee upon hiring the object.
- getRentalPeriod() - returns the details of the rental period.

**Borrow Exception**
This is a custom exception that returns a message as an exception (as opposed to simply a string). This is generated when a item that can't be borrowed is attempted to be borrowed.

**ID Exception**
This is a custom exception that returns an exception (as opposed to simply a string). This is generated when an incorrect ID is used.

**Array Full Exception**
An exception that is generated if the user attempts to add an item to the array when the array is full, or the seed 

**Already Seeded Exception**
Honestly, it probably doesn't make much of a difference, but this is a custom exception that is thrown if the user tries to seed
more than once, or if the list has already been seeded when the data is loaded.

**No Valid File Exception**
Somewhat redundant now, but an exception that is thrown when the main, and backup, data file fails to load. However, this has been
overridden since if the file fails to load (or doesn't exist), a FileNotFoundException is thrown and caught.


## Updates
- 2 Sept 21 - Added ArrayFullException to catch an error if the array is full. Added the exception to the AddItem method, and also catch an ArrayIndexOutOfBoundsException to the seedItem method and throw an ArrayFullException instead.
- 9 Sept 21 - Added some more custom exceptions, and moved some of the try catches around, and well as the exceptions. Considered
  A yes/no exception, but that would break the user out of the loop that keeps them in until the correct response is ended. Change
  the printing of the error from System.out.println to System.err.println

## Expansions
Include other subclass as DVD, CDs and game consoles 