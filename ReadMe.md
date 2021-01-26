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

![Menu Screen](https://raw.githubusercontent.com/s3664099/videolibrary/master/img/menu.png)



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
