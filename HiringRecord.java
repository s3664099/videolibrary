/*
 * CLASS - Hiring Record
 * DESCRIPTION - represents a single record for any
 * 				 item that can be hired
 * AUTHOR - David Sarkies s3664099
 * VERSION - 2.0
 */

public class HiringRecord {

	private String id; // hire ID - String
	private double rentalFee; // rental Fee - Double
	private double lateFee; // late fee - Double
	private DateTime borrowDate; // Date borrowed - Date/Time
	private DateTime returnDate; // actual date returned
	
	// Create Hiring Record - calls movie id and member id
	public HiringRecord(String id, double rentalFee, DateTime borrowDate) 
	{	

		// Set hire date to current date and flag hired
		this.borrowDate = borrowDate;
		this.rentalFee = rentalFee;
					
		// Set id - movie id + member id + borrowDate (8 digit)
		this.id = id+"_"+borrowDate.getEightDigitDate();
	}
	
	//calculate late fee owing and update hire record
	public void itemReturn(double lateFee, DateTime date)
	{
		returnDate = date;
		
		//Confirms late fee is not negative
		if (lateFee<0)
		{
			lateFee=0;
		}
		
		this.lateFee= lateFee;
	}
	
	/*ALGORITHM
	* BEGIN
	* 	Creates String Record
	* 	CHECKS if return date is null
	* 		IF null
	* 			CREATES String With Hire ID and Borrow Date
	* 		IF not null
	* 			CREATES String With:
	* 				Hire ID
	* 				Borrow Date
	* 				Return Date
	* 				Rental Fee
	* 				Late Fee
	* 				Total Fee
	* 	RETURNS Record
	* 	END
	* 
	* TEST
	* ITEM RETURN
	* ------------------------------
    * BORROWING RECORD
    * Hire ID:        G_SRM_TRI_31032018
    * Borrow date:    31/03/2018
    * Date Returned:  05/04/2018
    * Fee:            $15.00
    * Late Fee:       $0.00
    * Total Fee:      $15.00
    * ------------------------------
	* 
	* ITEM NOT RETURNED
	* ------------------------------
    * BORROWING RECORD
    * Hire ID:        G_COD_PET_10052018
    * Borrow date:    10/05/2018
    * ------------------------------
	*/
	
	// Get Hiring Record Details
	public String getDetails()
	{
		// Creates string record
		String record="";
		
		// if item on hire
		if (returnDate==null)
		{	
			//     - Add hire id and borrow date
			record = String.format("%-16s%s\n%-16s%-16s%s\n",
					"Hire ID:",id,
					" ","Borrow date:",borrowDate.getFormattedDate());
			
		} else{
			
			// if on hire false	
			// Adds Hire ID, Rental Fee, Late fee (if any), 
			// Date Borrowed and date returned
			double totalFee = rentalFee+lateFee;
			String rental = String.format("$%.2f", rentalFee);
			String late = String.format("$%.2f",lateFee);
			String total = String.format("$%.2f", totalFee);
			record = String.format("%-16s%s\n%-16s%-16s%s\n" +
					"%-16s%-16s%s\n%-16s%-16s%s\n%-16s%-16s%s\n%-16s%-16s%s\n",
					"Hire ID:",id,
					" ","Borrow date:",borrowDate.getFormattedDate(),
					" ","Date Returned:",returnDate.getFormattedDate(),
					" ","Fee:",rental,
					" ","Late Fee:",late,
					" ","Total Fee:",total);
		}
		
		return record; 
	}
	
	// Implement toSting Method	
	public String toString()
	{	
		
	// Returns string id:borrowDate:returnDate:fee:lateFee
		String record="";
		if (returnDate==null)
		{
			record = id+":"+borrowDate+":none:none:none";
		}else{
			record = id+":"+borrowDate+":"+returnDate+":"+rentalFee+":"
					+lateFee;
		}
		return record.toString();
	}

	
	//Returns the borrow date for the return item method
	public DateTime getBorrowDate()
	{
		return borrowDate;
	}
	
	//Returns the return date for the return item method
	public DateTime getReturnDate()
	{
		return returnDate;
	}

}