import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SetUp {
	
	private Item[] Item;
	
	public SetUp(Item[] Item) {
		this.Item = Item;
	}
	// Seed Movies
	public void seedMovies(boolean seeded) throws ArrayFullException, AlreadySeededException
	// checks to see if movies already seeded
	{

		if (seeded)	
		{	
			// if seeded throws an error
			throw new AlreadySeededException ("Error - list already seeded");
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
			returnDate = new DateTime(-30);
			Item[13].returnItem(returnDate);
		} catch (BorrowException | IdException e) {
			System.err.println(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayFullException ("Unable to add item, array is full");
		}
	}
	

	// Create persistent file upon closing program
	public void fileWrite(boolean seeded, String fileName, String backupFileName)
	{
		
		//Creates the output stream and records the seeded value
		PrintWriter outputStream=null;
		String output="Seeded: "+seeded+"\t\n:";
				
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
					
					//Calls function to write output
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
	public boolean fileLoad(boolean seeded, String fileToLoad) throws AlreadySeededException, FileNotFoundException
	{
		Scanner inputStream = null; // create object variable
		DateTime enumerateDateTime = new DateTime();
		
		try
		{ //create Scanner object & assign to variable
			inputStream = new Scanner (new File(fileToLoad));
			inputStream.useDelimiter(":");
			
		} catch (FileNotFoundException e) {
			//if no file found, an error is thrown
			throw new FileNotFoundException ("Error - file "+fileToLoad+" not found");
			
		}
		
		int i=0;
		
		//String to dump data saved but not required
		String Nil=""; 
		String Platform="";
		
		while (inputStream.hasNextLine())
		{
			String code = inputStream.next();
			
			//Checks the seeded value that is stored at the beginning with the seeded value
			if (code.equals("Seeded")) {
				
				String seededvalue = inputStream.next();
				
				//If it is true, and program has already been seeded, then an error is thrown
				if (seeded && seededvalue == "true") {
					throw new AlreadySeededException ("Error - unable to load file as already seeded");
				} else {
					
					//Otherwise it records the value in the seeded variable.
					if (seededvalue == "true") {
						seeded = true;
					} else {
						seeded = false;
					}
				}
			
			
			//Checks if it is a hire code or item code
			//if the code begins with '#' then it is a hire record
			} else if (code.startsWith("#")) {
				
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
				DateTime hireDate = enumerateDateTime.enumerateDate(date); 
				
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
						DateTime returnDate = enumerateDateTime.enumerateDate(date); 
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
		
		return seeded;
	}
	

}
