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

1. The Code isn't able to load the data from the .txt files

2. There seems to be some hard-coded numbers

3. We can probably reduce the size of some of the methods

## Notes

We were not allowed to use array lists, or any of the other lists, for this particular assignment, which means that
we could only use arrays, which are immutable once they have been set up. I won't be changing this since I want to leave this
code as close to what I originally submitted, and only make minor changes to make the code more readable, or resiliant.

## Code Walkthrough

While there are notes on each of the classes, I'll give a brief rundown of each of them here, which will also
help with me going over it and tidying things up.

**Driver.java**
This class simply starts the program running. The reason for its existence is that our lecturer indicated that
we needed to get away from the static part of the main program loop. Mind you, this is one of the reasons
that I don't like Java, and there are many many more to come.

**MovieMaster.java**


