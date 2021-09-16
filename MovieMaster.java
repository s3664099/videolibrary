/* CLASS - MovieMaster
 * DESCRIPTION - Main Class from which the program runs
 * AUTHOR - David Sarkies s3664099
 * VERSION - 2.0
 */

import java.util.Scanner;
import java.io.*;

public class MovieMaster {
	
	Scanner entry = new Scanner(System.in);
	
	private int itemArraySize = 30;
	private boolean seeded = false;
	
	// Strings and Variables for persistence writing
	private String fileName = "Movie_Master.txt";
	private String backupFileName = "Movie_Master_backup.txt";
	
	//Sets the main program loop running
	private boolean exitFlag=false; 
	
	// create array for movies (limited to 30, since we were not allowed
	// to use array lists in this assignment
	private Item[] Item = new Item[itemArraySize];
	
	//main method
	public void movieMaster()
	{	
		
		SetUp setup = new SetUp(Item);
		
		try {
			//attempts to load backup file
			seeded = setup.fileLoad(seeded, fileName);
			System.out.println("File Load 1");
		} catch (AlreadySeededException | FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		//if first attempt fails, attempt to load backup
		//checks if there is a single item. If there is no item there
		// (namely because it is null) then it assumes that the backup load
		// failed so attempts to load a backup of the backup file
		// Enclosed in a try/catch statement to handle the errors
		try {
			if (Item[0]==null)
			{
				seeded = setup.fileLoad(seeded, backupFileName);
				System.out.println("File Load 1");
			}
		} catch (AlreadySeededException e) {
			System.err.println(e.getMessage());
		}	catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.err.println("Error - Program executed " +
					"without any pre-loaded objects");
			
		}

		//Creates the class to manage the library.
		ManageLibrary library = new ManageLibrary(Item, entry);
		
		//executes the menu method, which is the main method
		//for the system
		menu(library, setup);

	}
		
	//main program loop
	private void menu(ManageLibrary library, SetUp setup)
	{
		// select option
		do  
		{
				
			System.out.println("*** Movie Master System Menu ***");
			System.out.println("");
			System.out.println("Add Item                 A");
			System.out.println();
			System.out.println("Borrow Item              B");
			System.out.println();
			System.out.println("Return Item              C");
			System.out.println();
			System.out.println("Display details          D");
			System.out.println();
			System.out.println("Seed Data                E");
			System.out.println();
			System.out.println("Exit Program             X");
			System.out.println();
			System.out.println("Enter Selection: ");
			String s = entry.nextLine();
			s=s.toLowerCase();
			
			switch(s)
			{
			
			//Adds Item
			case "a":
				try {
					library.addItem();
				} catch (IdException | ArrayFullException e) {
					System.err.println(e.getMessage());
				}
				break;
				
			//Borrow Item
			case "b":
				try {
					library.borrowItem();
				} catch (BorrowException | IdException e) {
					System.err.println(e.getMessage());
				}
				break;
			
			//Return Item
			case "c":
				try {
					library.returnItem();
				} catch (BorrowException | IdException e) {
					System.err.println(e.getMessage());
				}
				break;
			
			//Display Item
			case "d":
				library.displayItem();
				break;
			
			//Seed Movies
			case "e":
				try {
					setup.seedMovies(seeded);
					seeded = true;
				} catch (ArrayFullException | AlreadySeededException e) {
					System.err.println(e.getMessage());
				} 
				break;
			
			//Exit System
			case "x": 
				
				//sets the exit flag so that the system will break out of the
				//while loop.
				exitFlag=true;
				
				//writes all data to file when the system is exited.
				setup.fileWrite(seeded, fileName, backupFileName);
				System.out.println("Glad to be of service.");
				break;
			default:
				System.out.println("Error - invalid Entry");
			}	
		}while (!exitFlag);
	}
}	

