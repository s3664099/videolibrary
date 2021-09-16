import java.util.InputMismatchException;
import java.util.Scanner;

public class ManageLibrary {
	
	//define class variables
	//codeInUse used to determine if a code for a particular item exists
	//itemLocated used when an item is found using the search function
	
	private boolean codeInUse = false;
	private Item[] Item;
	private Scanner entry;
	private int itemLocated=0;

	
	public ManageLibrary(Item[] item, Scanner entry) {
		
		Item = item;
		this.entry = entry;
		
	}
	
	//Method for adding an item to the library
	//Throws an exception if there is an ID that is already being used
	// A part of the assignment required us to submit and 'algorithm' with the
	// Final code.
	
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

	public void addItem() throws IdException, ArrayFullException
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
						// Sets Code - movie codes begin with an M
						correct=true;
						code = "M_"+code; 
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

						// Sets code - game codes begin with a G
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
				
			//Throws an exception if the array is full	
			} 
		}
		throw new ArrayFullException ("Unable to add item, array is full");
	}

	// Borrow item method. 
	//Throws one of two exceptions - either unable to borrow or there is a problem 
	//with the ID
	public void borrowItem() throws BorrowException, IdException
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
	// like the above methods, has one of two exceptions - borrow problem and
	// id problem
	public void returnItem() throws BorrowException, IdException
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
					System.err.println("Error - please enter a " +
							"number of 0 or higher");
				} else{
					validEntry=true;
				}

			} catch (InputMismatchException nfe) {
				System.err.println("Error - please enter a number");
			}
			entry.nextLine();
			
		}while (!validEntry);
		return days;
	}
	
	// Display movie info
	public void displayItem()
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
				System.err.println("Error - Please enter Y or N");
			}
		}while(!validEntry);
		
		return Query;
	}
	
}
