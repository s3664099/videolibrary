/* Class: Item
 * Description: Class representing hireable items
 * Author: David Sarkies - 3664099
 */

public abstract class Item {

	private String id; // string - Item id
	private String title; // string - Item title
	private String description; // string - short description
	private String genre; // string - genre
	private double rentalFee;
	HiringRecord currentlyBorrowed;
	
	// Array Hiring Record - hiring history
	HiringRecord[] hireHistory = new HiringRecord[10]; 
			
	//Constructor for the item object
	public Item(String id, String title, String description, String genre)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.genre = genre;
	}

	/* ALGORITHM
	 * 
	 * BEGIN
	 * 	GET CustomerID and Hire Date
	 * 		CHECK if CustomerID has 3 letters
	 * 			IF Not
	 * 				Return IdException
	 * 	CREATE HireID with movieID plus CustomerID
	 * 		CHECK if item on loan
	 * 			IF True
	 * 				RETURN borrowException
	 * 		CHECK if dates conflict
	 * 			IF True
	 * 				Return borrowException
	 * 	SEARCH for blank Record
	 * 		IF Found
	 * 			NOTE Record Location
	 * 		IF Not Found
	 * 			SEARCH For Oldest Record
	 * 			WHEN FOUND
	 * 				NOTE overwrite record
	 * 		GET Rental Fee for Item
	 * 		CREATE New Hire Record
	 * 		RETURN Hire Fee
	 * END
	 * 
	 * TEST
	 * Create Record
	 * Record 0 created
	 * Record 1 created
	 * 
	 * Record 0 created
	 * Record 1 created
	 * Record 2 created
	 * Record 3 created
	 * Record 4 created
	 * Record 5 created
	 * Record 6 created
	 * Record 7 created
	 * Record 8 created
	 * Record 9 created
	 * Record 0 created
	 * Record 1 created
	 * 
	 * Hire Item
	 * Please enter the three letter item code: tlj
	 * Enter member ID: tp
	 * Advance Hire: 1
	 * Error - invalid Customer ID
	 * 
	 * Please enter the three letter item code: tlj
	 * Enter member ID: tommy
	 * Advance Hire: 1
	 * Error - invalid Customer ID
	 * 
	 * Please enter the three letter item code: tlj
	 * Enter member ID: tom
	 * Advance Hire: 1
	 * Record 0 created
	 * The fee for the item is $5.00
	 * The due date is 21/05/2018
	 * 
	 * Please enter the three letter item code: tlj
	 * Enter member ID: tom
	 * Advance Hire: 1
	 * Error - item already on loan
	 * 
	 * Please enter the three letter item code: tlj
	 * Enter member ID: fly
	 * Advance Hire: 4
	 * Error - Hiring Date Conflict
	 * 
	 * Please enter the three letter item code: trm
	 * Enter member ID: mep
	 * Advance Hire: 1
	 * The fee for the item is $3.00
	 * The due date is 27/05/2018
	 * 
	 * Please enter the three letter item code: civ
	 * Enter member ID: tom
	 * Advance Hire: 1
	 * Extended Hire? y
	 * The fee for the item is $20.00
	 * The due date is 04/06/2018
	 * 
	 * Please enter the three letter item code: civ
	 * Enter member ID: pet
	 * Advance Hire: 10
	 * Extended Hire? n
	 * The fee for the item is $15.00
	 * The due date is 04/06/2018
	 */
	
	
	// Hire Item method
	public double hireItem(String customerID, DateTime date) throws BorrowException, IdException
	{
		if (customerID.length()!=3)
		{
			throw new IdException ("Error - invalid Customer ID");
		}
		
		int i=0;
		boolean foundRecord=false;
		String hireID = id+"_"+customerID;
		
		//borrow exception - not on loan
		if (currentlyBorrowed != null)
		{
			throw new BorrowException("Error - item with id number: "+id+" already on loan");
		}
		
		//checks for conflicting dates
		checkConflict(date);
		
		do
		{	
			//searches record for a blank record
			if (hireHistory[i]==null)
			{
			foundRecord=true;
			} else {
				i++;
			}

			// if no blank record found, finds oldest record
			if (i==hireHistory.length)
			{
				i=findOldest();
				if(i==-1)
				{
					//if none found, first record the oldest
					i=0;	
				}
				foundRecord=true;
			}
		} while (!foundRecord);
		
		rentalFee = getRentalFee();
		
		//create a new Hire record
		currentlyBorrowed = new HiringRecord(hireID, rentalFee, date);
		hireHistory[i] = currentlyBorrowed;
		return rentalFee;
	}
	
	
	/* ALGORITHM
	 * BEGIN
	 * 	REQUEST Date
	 * 	SEARCH Hire Record
	 * 		COMPARE Date to Hire Record Date
	 * 			IF DATE greater than borrow date and less then return date
	 * 				THROWS BorrowException
	 * 			ELSE
	 * 				CLEARS Borrow
	 * 	END
	 * 
	 * TEST
	 * NO Hire History:
	 * ID:            M_TRM
	 * Title:         The Terminator
	 * Genre:         Romance
	 * Description:   A soldier goes back in time to save the woman he loves
	 * Rental Fee     $3.00
	 * Movie Type:    Weekly
	 * Rental Period: 7 days
	 * On Loan:       N
     *           ------------------------------
     *          BORROWING RECORD
     *           Hire ID:        M_TRM_MYF_01032018
     *           Borrow date:    01/03/2018
     *           Date Returned:  03/03/2018
     *           Fee:            $3.00
     *           Late Fee:       $0.00
     *           Total Fee:      $3.00
     *           ------------------------------
     *           
	 * Has Hire History
	 * 
	 * ID:            M_TRM
	 * Title:         The Terminator
	 * Genre:         Romance
	 * Description:   A soldier goes back in time to save the woman he loves
	 * Rental Fee     $3.00
	 * Movie Type:    Weekly
	 * Rental Period: 7 days
	 * On Loan:       N
     *           ------------------------------
     *           BORROWING RECORD
     *           Hire ID:        M_TRM_MYF_05032018
     *           Borrow date:    05/03/2018
     *           Date Returned:  07/03/2018
     *           Fee:            $3.00
     *           Late Fee:       $0.00
     *           Total Fee:      $3.00
     *           ------------------------------
     *           Hire ID:        M_TRM_MYF_01032018
     *           Borrow date:    01/03/2018
     *           Date Returned:  03/03/2018
     *           Fee:            $3.00
     *           Late Fee:       $0.00
     *           Total Fee:      $3.00
     *           ------------------------------
	 * 
	 * Conflicting Date (borrow date 02/03/2018
	 *		 borrowException: Error - Hiring Date Conflict
	 *		 at Item.checkConflict(Item.java:167)
	 *		 at Item.hireItem(Item.java:47)
	 * 		 at Driver.main(Driver.java:37)
	 * 
	 * Conflucting Date (return date 02/03/2018
	 * 		borrowException: Error - Hiring Date Conflict
	 * 		at Item.checkConflict(Item.java:171)
	 * 		at Movie.returnItem(Movie.java:48)
	 * 		at Driver.main(Driver.java:43)
	 */
	
	//Searches Hire Record to confirm dates not conflicting
	public void checkConflict(DateTime date) throws BorrowException
	{
		//searches hire record
		for (int i=0;i<hireHistory.length-1;i++)
		{
			if (hireHistory[i]==null)
			{
				continue;
			} else {
				
				//confirms that hire record has return date
				if (hireHistory[i].toString().endsWith("none"))
				{
					continue;
				} else {
					
					//checks if date falls within hire record
					DateTime date1 = hireHistory[i].getBorrowDate();
					DateTime date2 = hireHistory[i].getReturnDate();
					
					if (date.toString().compareTo(date1.toString())>0 &&
							date.toString().compareTo(date2.toString())<0)
					{	
						//if so, returns borrow exception
						throw new BorrowException("Error - Hiring Date Conflict");
					}
				}
			}
		}
	}
	
	//checks to see if item is on loan
	public void checkOnLoan() throws BorrowException
	{
		if (currentlyBorrowed == null)
		{
			throw new BorrowException("Error - item with id: "+id+" is NOT currently on loan");
		}
	}
	
	//method for sorting hire records
	public int findOldest()
	{
		//search record for oldest date
		for (int j=1; j<hireHistory.length-1; j++)
		{
			if (hireHistory[j]==null)
			{
				continue;
			}else{
				DateTime date1 = hireHistory[j].getBorrowDate();
				DateTime date2 = hireHistory[j-1].getBorrowDate();
				// once found, breaks out of while loop
				if (date1.toString().compareTo(date2.toString())<0)
				{
					return j;
					
				}
			}
		}
		int j=-1;
		return j;
	}
	
	//checks to see if return date prior to borrow date
	//throws exception if that is the case
	public void checkReturn(DateTime returnDate) throws BorrowException
	{
		DateTime borrowDate = currentlyBorrowed.getBorrowDate();
		if (returnDate.toString().compareTo(borrowDate.toString())<0)
		{
			throw new BorrowException("Error - cannot return prior to borrow date ");
		}
	}

	//return item method
	public abstract double returnItem(DateTime returnDate) throws BorrowException;
	
	//return movie
	public void updateRecord(double lateFee, DateTime returnDate)
	{	
			
		// locate hiring record
		boolean itemFound=false;
		int i=0;
		do
		{
			if (hireHistory[i].toString().endsWith("none"))
			{
				itemFound=true;
			} else {
				i++;
			}
		} while(!itemFound);
			
		// update hiring record and return any late fee
		hireHistory[i].itemReturn(lateFee, returnDate);
		
		currentlyBorrowed = null; //sets currently hired to null
	}
	
	// print movie details
	public String getDetails()
	{	
		// create string and return string
		String details = String.format(
				"%-15s%s\n%-15s%s\n%-15s%s\n%-15s%s\n",
				"ID:", id,
				"Title:",title,
				"Genre:",genre,
				"Description:",description);
		
		return details;
	}
	
	//Method to get details of item's hire record
	public String getHireRecord()
	{
		String line = lines();
		String details=String.format("%16s%s%16s%s\n", " ",
				line," ","BORROWING RECORD");
		int i=0; // date counters
		
		//is the first record blank?
		if (hireHistory[0]==null) 
		{	
			details=String.format("%s%16s%s\n", details," ",
					"NO HIRE HISTORY");
			return details;
		} else {
			
			//Are all the records full? 
			//Checks last item blank - if so returns hire history
			if (hireHistory[hireHistory.length-1]==null)
			{
				details = constructHireRecord(hireHistory.length-1, 
						0, details, line);
				return details;
			}
			
			//Search for oldest record
			//search record for oldest date
			i=findOldest();
			i-=1;
			
			//Now we know the newest we can print the record
			details = constructHireRecord(i, 0, details, line);
			details = constructHireRecord(hireHistory.length-1, i+1, 
					details, line);
			
		}
		return details;
	}
	
	//method to construct hire record
	public String constructHireRecord(int start, int end, String details, 
			String line)
	{	
		for (int i=start; i>=end; i--)
		{
			if (hireHistory[i]==null)
			{
				continue;
			} else {
				details=String.format("%s%16s%s%16s%s", 
					details," ",hireHistory[i].getDetails()," ", line);
			}
		}
		return details;
	}

	// to String method
	public String toString()
	{
		String itemRecord = id+":"+title+":"+description+":"+genre+":";
		
		return itemRecord;
		// return as to string
	}

	//prints a list of lines
	public String lines()
	{
		String lines = String.format("%30s\n","-").replace(" ", "-");
		return lines;
	}
	
	//retrieves and returns item's rental fee
	public abstract double getRentalFee();
	
	public abstract int getRentalPeriod();
}

