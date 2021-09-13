/* CLASS: Game
 * DESCRIPTION: Subclass of Item that manages Games
 * AUTHOR: David Sarkies - s366409
 * Version: 2.0
 */

public class Game extends Item {
	
	private String platform;
	
	//Flag for whether extended hire has been selected
	private boolean extended=false; 
	private double RENTAL_FEE=15.00; //constant for rental fee
	private int GAME_RENTAL_PERIOD = 15;
	private int LATE_FEE_SURCHARGE=5;
	private int EXTENDED_DISCOUNT=2;
	private int SURCHARGE_DAYS=7;
	private int LATE_FEE_MULTIPLIER=1;
	
	//Constructor that creates the new game object
	public Game(String id, String title, String description,
			String genre, String platform)
	{
		super(id, title, description, genre);
		
		//Sets the platform parameter
		this.platform=platform;
	}
	
	/*
	 * ALGORITHM
	 * BEGIN
	 * 	GET Return Date and Rental Period and daily late charge
	 * 	CHECKS if on loan
	 * 		IF Not
	 * 			Returns Error
	 * 	CHECKS for conflict
	 * 		IF Conflicts with another hire
	 * 			Returns Error
	 * 		IF Returned before Borrow Date
	 * 			Returns error
	 * 	GET Borrow Date
	 * 		CALCULATE days between borrow and return
	 * 		SUBTRACTS Hire Period
	 * 			IF Less that zero
	 * 				MAKES number zero
	 * 		MULTIPLIES by daily late charge
	 * 		DIVIDE Late Period by seven
	 * 			FINDS the Floor of the number
	 * 			MULTIPLIES by five
	 * 		ADDS to Late Fee
	 * 		CHECKS if Extended
	 * 			IF Extended
	 * 				DIVIDES Late Fee by two
	 * 	UPDATES Hiring Record
	 * 	RETURNS late Fee
	 * 	END
	 * 
	 * TEST
	 * 	Please enter the three letter item code: srm
	 * Enter number of days on loan?
	 * Include any advance hire
	 * 2
	 * Error - item not on loan
	 * 
	 * RETURN DATE TO CONFLICT WITH ANOTHER BORROW DATE
	 * Please enter the three letter item code: srm
	 * Enter number of days on loan?
	 * Include any advance hire: 8
	 * Error - Hiring Date Conflict
	 * 
	 * RETURN DATE BEFORE HIRE DATE
	 * Please enter the three letter item code: srm
	 * Enter number of days on loan?
	 * Include any advance hire 2
	 * Error - cannot return prior to borrow date
	 * 
	 *  RETURN ON TIME
	 *  Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire: 3
	 * The total late fee payable is $0.00
	 * 
	 * RETURN TWO DAYS LATE NO EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire: 18
	 * The total late fee payable is $2.00
	 * 
	 * RETURN TEN DAYS LATE NO EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire: 25
	 * The total late fee payable is $15.00
	 * 
	 * RETURN TWENTY DAYS LATE NO EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire 35
	 * The total late fee payable is $30.00
	 * 
	 * RETURN TWO DAYS LATE EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire 17
	 * The total late fee payable is $1.00
	 * 
	 * RETURN TEN DAYS LATE EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire 25
	 * The total late fee payable is $7.50
	 * 
	 * RETURN TWENTY DAYS LATE NO EXTENDED
	 * Please enter the three letter item code: civ
	 * Enter number of days on loan?
	 * Include any advance hire 35
	 * The total late fee payable is $15.00
	 */
	
	//Method for returning a game
	public double returnItem(DateTime returnDate) 
			throws BorrowException
	{	
		
		double lateFee;
		int hirePeriod=GAME_RENTAL_PERIOD;
		
		//Checks whether item on loan
		super.checkOnLoan();
	
		//checks for conflicting dates
		super.checkConflict(returnDate);
		super.checkReturn(returnDate);
				
		//Gets date item borrowed	
		DateTime borrowDate = currentlyBorrowed.getBorrowDate();
		int days = DateTime.diffDays(returnDate, borrowDate);
		
		//calculate late fee
		double latePeriod = days - hirePeriod;
		
		//If late period is negative, a borrow exception is thrown
		if(latePeriod<0)
		{
			throw new BorrowException("Error - late period less than 0");
		}
		
		//calculates late fee
		lateFee = latePeriod*LATE_FEE_MULTIPLIER;
		
		// for every seven days, $5.00 is added
		double lateFeeSurcharge = latePeriod/SURCHARGE_DAYS;
		
		//Calculates late fee
		lateFeeSurcharge = Math.floor(lateFeeSurcharge)
				*LATE_FEE_SURCHARGE;
		lateFee = lateFee+lateFeeSurcharge;
		
		// if extended is true, then late fee is halved
		if (extended)
		{
			lateFee = lateFee/EXTENDED_DISCOUNT;
			extended = false;
		}
		
		//update record
		super.updateRecord(lateFee, returnDate);

		return lateFee;
	}
	
	//Places object details into a string to be printed to the console
	public String getDetails()
	{
		
		String details = super.getDetails();
		
		details=String.format("%s%-15s%s%.2f\n%-15s%s\n%-15s%s\n%-15s", details,
				"Rental Fee","$",RENTAL_FEE, 
				"Platforms:",platform,
				"Rental Period:","15 days",
				"On Loan");
		if (extended)
		{
			details=String.format("%s%s\n", details,"Extended");

		} else if (currentlyBorrowed != null)
		{
			details=String.format("%s%s\n",details, "Yes");
		} else {
			details=String.format("%s%s\n",details, "No");
		}
		String hireRecord = getHireRecord();
		details = String.format("%s%s", details, hireRecord);
		
		return details;
	}
	
	//Creates a machine readable string of the object
	public String toString()
	{
		String gameRecord = super.toString();
		
		gameRecord=gameRecord+RENTAL_FEE+":"+platform+":";
		
		if (extended)
		{
			gameRecord = gameRecord + "E";
		} else if (currentlyBorrowed==null){
			gameRecord=gameRecord + "N";
		} else {
			gameRecord = gameRecord + "Y";
		}

		return gameRecord;
		// return as to string
	}
	
	//Sets the extended variable if option selected
	public void setExtended()
	{
		extended = true;
	}
	
	//Obtains the rental fee, and also calculates it if extended selected
	public double getRentalFee()
	{
		double currentRental = RENTAL_FEE;
		if (extended)
		{
			currentRental = RENTAL_FEE+5;
		}
		return currentRental;
	}
	
	//Get rental period to determine return date in movie master
	public int getRentalPeriod()
	{
		return GAME_RENTAL_PERIOD;
	}

}
