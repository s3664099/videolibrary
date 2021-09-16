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

## Executing the Code

Once the source code has been downloaded, navigate to the directory and enter the following:

*javac \*.java*

This will compile the code (and if there are any errors, please notify me). Once the code is compiled
you run the code as follows:

*java Driver*

You should get a screen as follows:

![Menu Screen](https://raw.githubusercontent.com/s3664099/videolibrary/master/img/menu.png)

The options are self explanatory, with the exception of 'Seed Data', which will fill the program with pre-determined, hard-coded data.

## Upgrades

1. ~~The Code isn't able to load the data from the .txt files When the program is first run, it fails, but works afterwards.~~

2. ~~There seems to be some hard-coded numbers~~

3. ~~We can probably reduce the size of some of the methods~~

4. ~~In MovieMaster, addItem() throw an exception for when the list is full~~

## Notes

We were not allowed to use array lists, or any of the other lists, for this particular assignment, which means that
we could only use arrays, which are immutable once they have been set up. I won't be changing this since I want to leave this
code as close to what I originally submitted, and only make minor changes to make the code more readable, or resiliant.

Some of the files include psuedo-code. This was a requirement in the assignment, and is actually something that I should do more
often as opposed to just jumping in and coding. It would make things a lot easier. Then again, old habits die very hard, especially
since I picked up some of these bad habits as a kid.

## Code Walkthrough

While there are notes on each of the classes, I'll give a brief rundown of each of them here, which will also
help with me going over it and tidying things up.

### Classes

**Driver Class**

This class simply starts the program running. The reason for its existence is that our lecturer indicated that
we needed to get away from the static part of the main program loop. Mind you, this is one of the reasons
that I don't like Java, and there are many many more to come.

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
- enumerateDate() - turns a date that is a string into a date.

**MovieMaster.java**

This is the main class for the program, and is where the bulk of the operations are called from.

An array with 30 spaces is created to store the movies/games

- public void movieMaster() - This method is constructor method for the Movie Master object which is called when the object is created (everything in Java is an object, another thing I really don't like about it). When the method is executed it will attempt to load the datafile. It then checks to see if the arrays have been filled, and if not (meaning that the load failed), it will attempt to load the backup file. If that failed (ie, the item array is empty), then the user is informed. The menu method is then called.

- private void menu() - This runs the main menu for the program. The menu gives the user a number of options, and upon selecting the option, the method relating to the option is then called. The menu uses the switch/case to execute the selection. I would prefer to just use if/else statements, but I believe this was required in the assignment.

**ManageLibrary Class**

This class was created to handle the methods that manage the item array. The array is passed through to the class and is stored here, and since the list is passed by reference, and alterations to the list outside the class is also made to the array in this class.

- private void addItem() - This method adds a new item to the array, if there is any space left in the array. The first task is that it asks for an ID for the item, and will throw a custom exception if the ID is being used by another item. Exception is caught in the menu. The program then looks for an extra space, and if it has one, it will then ask initial questions, and then whether it is a game, or not. Based on the answer, it will ask specific questions. It will then create either a new movie, or game, and add it to the item list.

- borrowItem() - This method will perform checks to see if it can be borrowed, and will then change the status of the item from available to on loan. The method will also add the details to the item regarding the loan, who has borrowed it, and any fees that are applicable, as well as item specific varibles (such as extended loan for games).

- returnItem() - This method is designed to return the status of the item from on loan to available. Performs checks to see if the item exists, and is on load, and if it is on loan, it then reverts the status back to available. It will also determine if any late fees apply, and how much they are, and they will then record the late fees (the system isn't so sophisticated that late fees paid are recorded, but it can be extended to do it).

- getDays() - This method is an input method that is used to take a numerical input from the user regarding the number of days that the item has been on loan. The main reason for the method is to make sure that a correct numerical input is entered and to prevent the program from crashing is a non-integer is entered, or a number less that 0 is entered.

- displayItem() - This method will display the details of the items in the array. It goes through the array, and if an item is recorded in the array it will then display it to the screen.

- enterId() - This method is used to validate the three letter id that is entered for the item. It is designed to catch any inproper entry and prevent the program from crashing. It also makes sure that only three letter ids are entered. This method also converts the id to upper case, to make the entry case insensitive.

- yesOrNo() - This method is used to capture a yes or no answer. It only takes the first letter, and it will ignore the case so that the user can enter it in either upper case or lower case.

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

**SetUp class**
This class exists to simply set up the item list, and also contains the load/save methods (or file handing). The class takes the item array, which is passed by reference, meaning that it does not need to be passed back.
- seedMovies() - this method takes a boolean which is used to determine whether the list has already been seeded. It basically generates a number of items and populates the array. It is basically used for testing. If the array has already been seeded then an exception is thrown.
- fileWrite() - this method will take the name of the file that is to be created, a boolean indicating whether the array has been seeded or not, and a backup file name. It will then take the item array and convert the information into a string which will be written to file as a text file. The text file is set up in a way so that the fileLoad method can then read the information on the document and populate the array.
- output() - this is a helper method that is used to convert the information in the array into a string format that can then be reloaded back into the program when the program is started up again.
- fileLoad() - this method takes the name of the file that is to be loaded, as well as a boolean that will indicate whether the array has been seeded or not (though at this stage this is unlikely to be the case). It will load the file, and then proceed to populate the array of items with the information that has been loaded. It will also take a boolean that will indicate whether the file has been seeded or not.

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
- 16 Sept 21 - Removed methods from the movieMaster class and created a class to manage the item list, and a class to setUp and save
  the item list. Completed the documentation.

## Expansions
Make some of the methods smaller, particularly the fileLoad method, and some of the methods in the manageLibrary class
have the methods in the manage library class pass the results back to the movieMaster class as strings where the results are displayed
Include other subclass as DVD, CDs and game consoles
Create an arraylist to store the data as opposed to an array
Create a class that manages the customers, which includes details of name, address, a list of items borrowed, late fees owing, late fees accumulated, and whether a suspension is in force


