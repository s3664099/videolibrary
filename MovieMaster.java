/* CLASS - MovieMaster
 * DESCRIPTION - Main Class from which the program runs
 * AUTHOR - David Sarkies s3664099
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class MovieMaster {
	
	Scanner entry = new Scanner(System.in);
	
	//define class variables
	//codeInUse used to determine if a code for a particular item exists
	//itemLocated used when an item is found using the search function
	
	private boolean codeInUse = false;
	private int itemLocated=0;
	
	// Strings and Variables for persistence writing
	private String fileName = "Movie_Master.txt";
	private String backupFileName = "Movie_Master_backup.txt";
	
	//Sets the main program loop running
	private boolean exitFlag=false; 
	
	// create array for movies (limited to 30, since we were not allowed
	// to use array lists in this assignment
	private Item[] Item = new Item[30];
	
	//main method
	public void movieMaster()
	{	
		
		//attempts to load backup file
		fileLoad(fileName);
		
		//if first attempt fails, attempt to load backup
		//checks if there is a single item. If there is no item there
		// (namely because it is null) then it assumes that the backup load
		// failed so attempts to load a backup of the backup file
		
		if (Item[0]==null)
		{
			fileLoad(backupFileName);
		}
		
		//if backup attempt fails, based on a null value in the item collection
		//informs the user that there will be no preloaded objects in the system
		
		if (Item[0]==null)
		{
			System.out.println("Error - Program executed " +
					"without any pre-loaded objects");
		}
		
		//executes the menu method, which is the main method
		//for the system
		
		menu();

	}
		
	//main program loop
	private void menu()
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
			case "a":
				try {
					addItem();
				} catch (IdException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "b":
				try {
					borrowItem();
				} catch (BorrowException | IdException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "c":
				try {
					returnItem();
				} catch (BorrowException | IdException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "d":
				displayItem();
				break;
			case "e":
				seedMovies();
				break;
			case "x": // Exit system
				
				//sets the exit flag so that the system will break out of the
				//while loop.
				exitFlag=true;
				
				//writes all data to file when the system is exited.
				fileWrite();
				System.out.println("Glad to be of service.");
				break;
			default:
				System.out.println("Error - invalid Entry");
			}	
		}while (!exitFlag);
	}
	
	// Add Item method
	
	/*
	 * ALGORITHM
	 * BEGIN
	 * 	REQUEST Item ID
	 * 	CHECKS if ID Being Used
	 * 		IF Being Used
	 * 			RETURNS error and advises user
	 * 			SEND User back to main menu
	 * 		IF Not Being Used
	 * 			SETS ID
	 * 	REQUEST Title
	 * 	REQUEST Description
	 * 	REQUEST Genre
	 * 	ASKS If Movie or Game (M/G)
	 * 		CHECKS To see if M or G entered
	 * 			IF NOT returns Error and asks again
	 * 		ELSE
	 * 			Continues
	 * 		IF Movies
	 * 			ADDS 'M_' to front of code
	 * 			ASKS Whether New Release or Weekly
	 * 			CONFIRMS Whether Y or N entered
	 * 				IF NOT returns error and asks again
	 * 			ELSE
	 * 				Continues
	 * 			SETS is New Release flag to either true or false
	 * 		IF Game
	 * 			ADDS 'G_' to front of ID
	 * 			ASKS Platform
	 * 		CREATES new Item (Movie or Game)
	 * 	END
	 * 
	 * TEST
	 * 		Enter ID: DPL
	 *		Enter Title: Deadpool
	 *		Enter Description: A mutant chases down his maker
	 * 		Enter Genre: Comedy
	 * 		Movie or Game (M/G): m
	 * 		Is New Release (Y/N): n
	 * 		Movie Deadpool created
	 * 
	 * 		Enter ID: dpl
	 * 		Error - ID for DPL is already in use.
	 * 		Enter ID: dp2
	 * 		Enter Title: Deadpool 2
	 * 		Enter Description: The Merc with a mouth returns
	 * 		Enter Genre: Comedy
	 * 		Movie or Game (M/G): m
	 * 		Is New Release (Y/N): y
	 * 		Movie Deadpool 2 created
	 * 
	 * 		Enter ID: SPI
	 * 		Enter Title: Space Invaders
	 * 		Enter Description: The original shootem-up
	 * 		Enter Genre: Shoot-em up
	 * 		Movie or Game (M/G): g
	 * 		Please enter the game's platform: PC, SNES, X-Box
	 * 		Game Space Invaders created
	 * 		Enter ID: ty
	 *		Error - please enter a three letter code
	 *		type
	 * 		Error - please enter a three letter code
	 * 		typ
	 * 		Enter Title: Typhoon
	 * 		Enter Description: A movie I made up
	 * 		Enter Genre: Action
	 * 		Movie or Game (M/G): m
	 * 		Is New Release (Y/N): n
	 * 		Movie Typhoon created
	 */
	
	private void addItem() throws IdException
	{
		String code;
		
		//ask for item details and store in variable/strings
		//create items by passing details into constructor
		
		//Enter ID code
		System.out.print("Enter ID: ");
		code = enterID();
			
		//checks to see if valid entry or code in use
		//prints error if not and asks again
		if ((codeInUse))
		{
			throw new IdException ("Error - ID for "+code+" already in the system");
		}

		//Search for blank array
		for (int i=0; i<Item.length; i++)
		{
			// if entry blank
			if (Item[i] == null) 
			{
				//Enter Info required - title, description, genre, new release
				System.out.print("Enter Title: ");
				String title = entry.nextLine();
				System.out.print("Enter Description: ");
				String description = entry.nextLine();
				System.out.print("Enter Genre: ");
				String genre = entry.nextLine();
				
				// set up either movie or game
				boolean correct = false;
				do {
					System.out.print("Movie or Game (M/G): ");
					String type = entry.nextLine();

					// For Movies
					if (type.equalsIgnoreCase("m"))
					{
						correct=true;
						code = "M_"+code; // Sets Code
						boolean isNewRelease=false;
						
						// Sets whether New Release or Weekly
						System.out.print("Is New Release (Y/N): ");
						String newRelease = yesOrNo();
						if (newRelease.compareTo("y")==0)
						{
							isNewRelease=true;
						} else if (newRelease.compareTo("n")==0){
								isNewRelease=false;
						} else if (newRelease.equals("")){
							System.out.println("Exiting to main menu");
							return;
						}
						
						//Creates a new movie object
						Item m = new Movie(code, title, description, genre, 
								isNewRelease);
						Item[i] = m;
						System.out.println("New movie successfully added for movie ID: "+code);
						i=Item.length-1;
							
						// for games
					} else if (type.equalsIgnoreCase("g")) {

						// Sets code
						code = "G_"+code; 
						correct=true;
								
						//Enter Platform
						System.out.print("Please enter the game's platform: ");
						String platform = entry.nextLine();
								
						//Creates a new game object
						Item g = new Game(code, title, description, genre, 
								platform);
						Item[i]=g;
						System.out.println("New game successfully added for game ID: "+code);
						i = Item.length-1;
					} else if (type.equals("")){
						System.out.println("Exiting to main menu.");
						return;
					}else {
						System.out.println("Error - invalid entry");
					}
				} while (!correct);
			}
		}
	}

	// Borrow item method
	private void borrowItem() throws BorrowException, IdException
	{
		String code;
		
		// Enter item code
		System.out.print("Please enter the three letter item code: ");
		code = enterID();
		
		//checks to see if valid entry or item found
		//prints error if not and asks again
		if ((!codeInUse))
		{
			throw new IdException ("Error - the ID with item number "
					+code+", not found");
		}
		
		System.out.print("Enter member ID: ");
		String customerID = entry.nextLine();
		
		//Advance hire function for testing purposes
		System.out.print("Advance Hire: ");
		int advanceHire = getDays();
		
		//Checks to see if the item is a game
		if (Item[itemLocated] instanceof Game)
		{
			//If the item is a game, requests the extended hire option
			System.out.print("Extended Hire: ");
			String extendedHire = yesOrNo();
			
			if (extendedHire.contentEquals("y"))
			{
				((Game) Item[itemLocated]).setExtended();
				
			} else if (extendedHire.equals("")){
				
				System.out.println("Exiting to main menu");
				return;
			}
				
		}
		
		// displays due date and fee payable
		DateTime date = new DateTime(Item[itemLocated].getRentalPeriod());
				
		// Sets item hire and returns hire fee and return date
		double fee = Item[itemLocated].hireItem(customerID, 
				new DateTime(advanceHire));
		
		System.out.printf("The fee for the item is $%.2f\n",fee);
		System.out.println("The due date is "+date.getFormattedDate());
	}
		
	// return item method
	private void returnItem() throws BorrowException, IdException
	{
		String code;

		// Enter item code
		System.out.print("Please enter the three letter item code: ");
		code = enterID();
		
		//checks to see if valid entry or item found
		//prints error if not and asks again
		if ((!codeInUse))
		{
			throw new IdException ("Error - the ID with item number "
					+code+", not found");
		}
		
		// User to enter number of days item was borrowed
		System.out.println("Enter number of days on loan.");
		System.out.print("Include any advance hire: ");
		int days = getDays();

		DateTime returnDate = new DateTime(days);
		
		// print late fee (if any)
		double fee = Item[itemLocated].returnItem(returnDate);
		System.out.printf("The total late fee payable is $%.2f\n",fee);
	}
	
	//method for entering number of days for hire and return item
	private int getDays()
	{
		boolean validEntry=false;
		int days=0;
		do
		{
			//Try-Catch block designed to make sure that a number is entered
			//this prevents an Input Mismatch exception from crashing the system
			try {
				days = entry.nextInt();
				if (days<0)
				{
					System.out.println("Error - please enter a " +
							"number of 0 or higher");
				} else{
					validEntry=true;
				}

			} catch (InputMismatchException nfe) {
				System.out.println("Error - please enter a number");
			}
			entry.nextLine();
			
		}while (!validEntry);
		return days;
	}
	
	// Display movie info
	private void displayItem()
	{
		for (int i=0; i<Item.length-1; i++) 
		{
			// Checks to see it item contains data and prints it if it does
			// Otherwise it skips the entry
			if (Item[i]==null)
			{
				continue;
			} else {
			System.out.println(Item[i].getDetails());
			}
		}			
	}
	
	// Seed Movies
	private void seedMovies()
	// checks to see if movies already seeded
	{

		if (Item[0] !=null)	
		{	
			// if seeded prints error message
			System.out.println("Error: The movies have already been seeded");
			return;
		} 
		
		try {
		// creates 10 movies and 4 games as requested
		Item m = new Movie("M_TRM", "The Terminator", 
				"A soldier goes back in time to save the woman he loves", 
				"Romance", false);
		Item[0] = m;
		m = new Movie("M_MTX", "The Matrix", 
				"A depressed office worker joins a cult " +
				"and destabilises the government", 
				"Sci-Fi", false);
		Item[1] = m;
		Item[1].hireItem("MYF", new DateTime(-1));
		m = new Movie("M_TKN", "Taken", "A dad goes to pick " +
				"up his daughter", "Action", false);
		Item[2] = m;
		Item[2].hireItem("TOM", new DateTime(-15));
		DateTime returnDate = new DateTime(-10);
		Item[2].returnItem(returnDate);
		m = new Movie("M_SHN", "The Shining", 
				"A family's first AirBNB experience goes horribly wrong",
				"Thriller", false);
		Item[3] = m;
		Item[3].hireItem("PET", new DateTime(-35));
		returnDate = new DateTime(-25);
		Item[3].returnItem(returnDate);
		m = new Movie("M_RBC", "Robo-cop", 
				"A pure-hearted man is murdered and days later is " +
				"resurrected with a message for mankind",
				"Sci-fi", false);
		Item[4] = m;
		Item[4].hireItem("FRK", new DateTime(-60));
		returnDate = new DateTime(-50);
		Item[4].returnItem(returnDate);
		Item[4].hireItem("CRL", new DateTime(-20));
		m = new Movie("M_TLJ", "Star Wars - The Last Jedi", 
				"An aging wizard comes out of hiding to " +
				"play a prank on his nephew", "Sci-fi", true);
		Item[5] = m;
		m= new Movie("M_ICP", "Inception", 
				"An hour and a half of watching people sleep", 
				"Sci-fi", true);
		Item[6] = m;
		Item[6].hireItem("ALR", new DateTime(-3));
		m = new Movie("M_TRG", "Thor - Ragnarok", 
				"A man destroys his planet because he does not " +
				"want to share it with his sister after she broke his " +
				"favourite toy",
				"Sci-Fi", true);
		Item[7] = m;
		Item[7].hireItem("JHN", new DateTime(-6));
		returnDate = new DateTime(-5);
		Item[7].returnItem(returnDate);
		m = new Movie("M_AIW", "Avengers - Infinity War", 
				"A team of misfits try to stop a guy from over-blinging his glove",
				"Sci-fi", true);
		Item[8] = m;
		Item[8].hireItem("CRL", new DateTime(-9));
		returnDate = new DateTime(-6);
		Item[8].returnItem(returnDate);
		m = new Movie("M_AAU", "Avengers - Age of Ultron", 
				"A team of misfits try to solve the world's problems " +
				"by creating new ones",
				"Sci-fi", true);
		Item[9] = m;
		Item[9].hireItem("ERN", new DateTime(-10));
		returnDate = new DateTime(-7);
		Item[9].returnItem(returnDate);
		Item[9].hireItem("FRK", new DateTime(-2));
		Game g = new Game("G_CIV", "Civilisation VII", 
				"Develop your management skills",
				"Strategy", "PC");
		Item[10] = g;
		g = new Game("G_COD", "Call of Duty V", 
				"Experience the Army",
				"Shoot-em-up", "X-Box, PC, Playstation");
		Item[11] = g;
		Item[11].hireItem("PET", new DateTime(-10));
		((Game) Item[11]).setExtended();
		g = new Game("G_ZRK", "Zork Trilogy", 
				"Experience gaming in the 80s",
				"Old-Skool", "PC");
		Item[12] = g;
		Item[12].hireItem("ERN", new DateTime(-30));
		returnDate = new DateTime(-11);
		Item[12].returnItem(returnDate);
		g = new Game("G_SRM", "Sky-Rim", 
				"Escape from reality",
				"RPG", "PC, Playstation");
		Item[13] = g;
		Item[13].hireItem("TRI", new DateTime(-50));
		((Game) Item[13]).setExtended();
		returnDate = new DateTime(-45);
		Item[13].returnItem(returnDate);
		} catch (BorrowException | IdException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Method for entering three letter ID
	private String enterID() throws IdException
	{
		//Enter ID code
		codeInUse=false;
		String code = entry.nextLine();
			if (code.length() != 3)
			{
				throw new IdException ("Error - ID "+code+
						" is invalid. Please enter a 3 digit id");
			}

		code = code.toUpperCase();

		for (int i=0; i<Item.length;i++)
		{
			//checks to see whether code is in use		
			if (Item[i]==null)
			{
				//code not found
				continue;
			} else {
				String[] codeCheck = Item[i].toString().split(":");
				if (codeCheck[0].endsWith(code)) 
				{		
					// code is found 
					codeInUse=true;
					itemLocated =i;
					return code;
				}
			}
		}
		return code;
	}

	
	// method for asking yes/no question
	private String yesOrNo()
	{	
		boolean validEntry=false;
		String Query;
		do
		{	
			Query = entry.nextLine();
			if ((Query.equalsIgnoreCase("y")) || (Query.equalsIgnoreCase("n")) || 
					(Query.equals("")))
			{
				return Query;
			} else {
				System.out.println("Error - Please enter Y or N");
			}
		}while(!validEntry);
		
		return Query;
	}
	
	// Create persistent file upon closing program
	private void fileWrite()
	{
		PrintWriter outputStream=null;
		String output="";
		
		//creates string to write to file
		try {
			
			//cycles through the collection of items
			for (int i=0;i<Item.length;i++)
			{
				//if there is no item, it breaks out of the loop
				if (Item[i]==null)
				{
					continue;
				} else {
					
					output = output + Output(i);
					
				}
			} 
			
			//sets flag indicating no more data
			output=output+"Stop";
			
			//Creates Movie_Master.txt file
			outputStream = new PrintWriter 
					(new FileOutputStream(fileName));
			outputStream.write (output);
			outputStream.close();
			
			//Creates Movie_Master_Backup.txt file
			outputStream = new PrintWriter 
					(new FileOutputStream(backupFileName));
			outputStream.write(output);
			outputStream.close();
			System.out.println("File written without error");
			
		} catch (FileNotFoundException e) {
			
			System.out.println(e.getMessage());
			return;	
		}
	}
	
	//private helper method for creating an output string
	//to write to the text file
	private String Output(int i)
	{
		//Turns the item into a string
		String output = Item[i].toString()+"\t\n:";
		
		//Cycles through the item's hire history
		for (int j=0;j<Item[i].hireHistory.length; j++)
		{
			
			//If there is no more hire history, breaks out of the loop
			if (Item[i].hireHistory[j]==null)
			{
				return output;
			} else {
				
				//Turns the hire record into a string
				output = output + "#"+ Item[i].hireHistory[j]
						.toString()+"\t\n:";
			}
		}
		
		return output;
	}
	
	//Reload data from persistent file
	private void fileLoad(String fileToLoad)
	{
		Scanner inputStream = null; // create object variable
		
		try
		{ //create Scanner object & assign to variable
			inputStream = new Scanner (new File(fileToLoad));
			inputStream.useDelimiter(":");
			
		} catch (FileNotFoundException e)
		{
			//if no file found, an error is thrown
			System.out.println("Error - file "+fileToLoad+" not found");
			return;
		}
		
		int i=0;
		
		//String to dump data saved but not required
		String Nil=""; 
		String Platform="";
		
		while (inputStream.hasNextLine())
		{
			String code = inputStream.next();
			
			//Checks if it is a hire code or item code
			//if the code begins with '#' then it is a hire record
			if (code.startsWith("#"))
			{
				//Hire Code
				code = code.replace("#","");
				
				//Creates customer code
				String customerCode = code.substring(6, 9); 
				
				//hire date
				String date=inputStream.next(); 
			
				//if item a game, checks to see if extended hire selected
				if (Item[i-1] instanceof Game)
				{
					if (Item[i-1].toString().endsWith("E"))
					{
						((Game) Item[i-1]).setExtended();
					}
				}
				
				try {
					
				//Sets borrow date
				DateTime hireDate = enumerateDate(date); 
				
				//Creates hiring record
				Item[i-1].hireItem(customerCode, hireDate); 
				} catch (BorrowException | IdException e) {
					System.out.println(e.getMessage());
				}
				
				//return date
				date=inputStream.next();
				
				try {
					if (!date.contains("none"))
					{
						DateTime returnDate = enumerateDate(date); 
						//sets date item was returned
						Item[i-1].returnItem(returnDate); 
					}
				} catch (BorrowException e){
					System.out.println(e.getMessage());
				}

				Nil=inputStream.next();
				Nil=inputStream.next();

			//Terminates method as no further data
			}else if (code.contentEquals("Stop")){ 
				
			}else{//item code
				
				String title = inputStream.next();
				String description = inputStream.next();
				String genre = inputStream.next();
				Nil = inputStream.next();
				
				//determines whether movie or game
				if (code.startsWith("M"))
				{
					Nil=inputStream.next();
					boolean isNewRelease=false;
					
					//determines new release or weekly
					if (Nil.contains("NR"))
					{
						isNewRelease=true;
					} else {
						isNewRelease=false;
					}
					
					Nil=inputStream.next();
					
					//instantiates new movie object
					Movie m = new Movie(code,title,description,
							genre,isNewRelease);
					Item[i]=m;
				} else {
					Platform = inputStream.next();
					Nil=inputStream.next();
					
					//instantiates new game object
					Game g = new Game(code,title,description,
							genre,Platform);
					Item[i]=g;
				}
				i++;
			}
		}
		inputStream.close();
	}
	
	//turns toString date to integer
	public DateTime enumerateDate(String dateString)
	{
		String year=dateString.substring(0,4);
		String month=dateString.substring(5,7);
		String day=dateString.substring(8,10);
		int yr = Integer.parseInt(year);
		int mth = Integer.parseInt(month);
		int dy = Integer.parseInt(day);
		
		DateTime date = new DateTime(dy, mth, yr);
		
		return date;
	}
}	

