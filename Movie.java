/* CLASS: Movie
 * DESCRIPTION: Subclass of item that manages the hireable item movie
 * AUTHOR: David Sarkies s3664099
 * Version: 2.0
 */

public class Movie extends Item {

	private boolean isNewRelease; 
	private double HIRE_COST = 3.00;
	private double NEW_RELEASE_SURCHARGE = 2.00;
	private int WEEKLY_HIRE_PERIOD = 7;
	private int NEW_RELEASE_HIRE_PERIOD = 1;
	private int LATE_FEE_DIVISOR=2;
	private double rentalFee;

	//Create movie entry - id, title, description, genre, new release
	public Movie(String id, String title, String description, 
			String genre, boolean isNewRelease)
	{	
		super(id, title, description, genre);
	
		//Set movie parameters
		this.isNewRelease=isNewRelease;
	
		// checks if it is a new release
		if (isNewRelease)
		{
			rentalFee = (HIRE_COST+NEW_RELEASE_SURCHARGE);
		} else {
			rentalFee = HIRE_COST;
		}
	}

	// Gets the rental fee for the movie
	public double getRentalFee()
	{
		return rentalFee;
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
	 * 			IF Less than zero
	 * 				MAKES number zero
	 * 		MULTIPLIES by daily late charge
	 * 	UPDATES Hiring Record
	 * 	RETURNS late Fee
	 * 	END
	 * 
	 * TEST
	 * ITEM NOT ON LOAN
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 3
	 * Error - item not on loan
	 * 
	 * DATES CONFLICT
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 7
	 * Error - Hiring Date Conflict
	 * 
	 * RETURN BEFORE HIRE
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 2
	 * Error - cannot return prior to borrow date 
	 * 
	 * RETURN ON TIME WEEKLY
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 1
	 * The total late fee payable is $0.00
	 * 
	 * RETURN TWO DAYS LATE WEEKLY
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 9
	 * The total late fee payable is $3.00
	 * 
	 * RETURN TEN DAYS LATE WEEKLY
	 * Please enter the three letter item code: trm
	 * Enter number of days on loan?
	 * Include any advance hire 17
	 * The total late fee payable is $15.00
	 * 
	 * RETURN ON TIME NEW RELEASE
	 * Please enter the three letter item code: tlj
	 * Enter number of days on loan?
	 * Include any advance hire 1
	 * The total late fee payable is $0.00
	 * 
	 * RETURN TWO DAYS LATE NEW RELEASE
	 * Please enter the three letter item code: tlj
	 * Enter number of days on loan?
	 * Include any advance hire 3
	 * The total late fee payable is $5.00
	 *
	 * RETURN TEN DAYS LATE NEW RELEASE
	 * Please enter the three letter item code: tlj
	 * Enter number of days on loan?
	 * Include any advance hire 11
	 * The total late fee payable is $25.00
	 */
	
	//Item return method of class Movie
	public double returnItem(DateTime returnDate) throws BorrowException
	{	
		//borrow exception - not on loan
		super.checkOnLoan();
		
		//checks for conflicting dates
		super.checkConflict(returnDate);
		super.checkReturn(returnDate);
		
		//calculate late fee
		double lateFee = rentalFee/LATE_FEE_DIVISOR;
		int hirePeriod;
		
		//checks if a new release and remembers period
		if (isNewRelease)
		{
			hirePeriod = NEW_RELEASE_HIRE_PERIOD;
		}else{
			hirePeriod = WEEKLY_HIRE_PERIOD;
		}
		
		//Gets date item borrowed	
		DateTime borrowDate = currentlyBorrowed.getBorrowDate();
		
		//determines days between borrow and return
		int days = DateTime.diffDays(returnDate, borrowDate);
		
		//determines any late period
		double latePeriod =  days - hirePeriod;
		
		//if less than 0, makes it 0
		if(latePeriod<0)
		{
			latePeriod = 0;
		}
		
		//calculates late fee
		lateFee *= latePeriod;
		
		super.updateRecord(lateFee, returnDate);

		return lateFee;
	}

	// Returns details for movie when request made
	public String getDetails()
	{
		String hirePeriod;
		String rentalPeriod;
		String details = super.getDetails();
	
		String movieDetails=String.format("%s%-15s%s%.2f\n", details,
			"Rental Fee","$",rentalFee);
	
		// Determines whether movie is new release or over night
		if (isNewRelease)
		{
			hirePeriod="New Release";
			rentalPeriod="1 night";
		} else {
			hirePeriod="Weekly";
			rentalPeriod="7 days";
		}
		
		//Constructs String to be returned
		movieDetails=String.format("%s%-15s%s\n%-15s%s\n%-15s", movieDetails,
				"Movie Type:",hirePeriod,
				"Rental Period:",rentalPeriod,
				"On Loan:");
		if (currentlyBorrowed != null)
		{
			movieDetails=String.format("%s%s\n",movieDetails, "Yes");
		} else {
			movieDetails=String.format("%s%s\n",movieDetails, "No");
		}
		
		//obtains the hire record details
		String hireRecord = getHireRecord();
		movieDetails = String.format("%s%s", movieDetails, hireRecord);
		
		return movieDetails;
	}

	// provides machine readable code
	public String toString()
	{
		String movieRecord = super.toString();
	
		movieRecord=movieRecord+rentalFee+":";
	
		//Determined whether New Release or Weekly
		if (isNewRelease == true)
		{
			movieRecord = movieRecord + "NR:";
		} else {
			movieRecord = movieRecord + "WK:";
		}
	
		//Determines whether borrowed.
		if (currentlyBorrowed!=null)
		{
			movieRecord = movieRecord + "Y";
		} else {
			movieRecord = movieRecord + "N";
		}
	
		//	return as to string
		return movieRecord;
	
	}
	
	public int getRentalPeriod()
	{
		if (isNewRelease)
		{
			return NEW_RELEASE_HIRE_PERIOD;
		} else {
			return WEEKLY_HIRE_PERIOD;
		}
	}

}
	

