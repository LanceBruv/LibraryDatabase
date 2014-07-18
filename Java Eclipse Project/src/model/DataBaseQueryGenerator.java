package model;

import java.sql.*;

public class DataBaseQueryGenerator {

	static Connection conn = null;
	

	public DataBaseQueryGenerator() {
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "abcd1234");
			Statement stmt = conn.createStatement();
			stmt.execute("Use library;");
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
	
	
	/*
	 * Queries the library database to find books matching the given conditions
	 * @param: String book_id : book_id number,
	 * @param: String title : book title
	 * @param: String author : book author (could be first, last or middle name)
	 * @return: ResultSet The resulting query is returned 
	 */
	public ResultSet getBooks(String book_id,String title,String author)
	{
		ResultSet rs = null;
		String resultQuery="SELECT book_id,title,author_list,branch_id,no_of_copies,no_available FROM "
				+ "(book_copies NATURAL JOIN combined_authors) "
				+ "WHERE ";
		try{
			Statement stmt = conn.createStatement();
			if(book_id.isEmpty() && title.isEmpty() && author.isEmpty()) return null;
			
			if(!book_id.isEmpty())
			{
				resultQuery+= "book_id like '%"+book_id+"%' AND ";
			}
			if(!title.isEmpty())
			{
				resultQuery+= "title like '%"+title+"%' AND ";
			}
			if(!author.isEmpty())
			{
				resultQuery+= "author_list like '%"+author+"%' AND ";
			}
			
			resultQuery += "true ;";
			rs = stmt.executeQuery(resultQuery);

		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return rs;
	}
	
	/*
	 * Queries the library database to find books matching the given conditions
	 * @param: 
	 * String book_id : book_id number,
	 * String title : book title
	 * String fname : author first name
	 * String minit: author middle initials
	 * String lname: author last name
	 * @return: ResultSet The resulting query is returned 
	 */
	public ResultSet getBooks(String book_id,String title,String fname,String minit,String lname)
	{
		ResultSet rs = null;
		String resultQuery="SELECT DISTINCT book_copies.book_id,title,combined_authors.author_list,branch_id,no_of_copies, no_available FROM "
				+ "((combined_authors LEFT JOIN book_copies ON combined_authors.book_id = book_copies.book_id)"
				+ " LEFT JOIN book_authors ON combined_authors.book_id = book_authors.book_id)  "
				+ "WHERE ";
		try{
			Statement stmt = conn.createStatement();
			if(book_id.isEmpty() && title.isEmpty() && fname.isEmpty()  && minit.isEmpty() && lname.isEmpty()) return null;
			
			if(!book_id.isEmpty())
			{
				resultQuery+= "book_copies.book_id like '%"+book_id+"%' AND ";
			}
			if(!title.isEmpty())
			{
				resultQuery+= "title like '%"+title+"%' AND ";
			}
			if(!fname.isEmpty())
			{
				resultQuery+= "fname like '%"+fname+"%' AND ";
			}
			if(!minit.isEmpty())
			{
				resultQuery+= "minit like '%"+minit+"%' AND ";
			}
			if(!lname.isEmpty())
			{
				resultQuery+= "lname like '%"+lname+"%' AND ";
			}
			
			resultQuery += "true ;";
			rs = stmt.executeQuery(resultQuery);

		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return rs;
	}
	
	public ResultSet getCheckIns(String book_id,String card_no,String borrower_name)
	{
		ResultSet rs = null;
		String resultQuery="SELECT loan_id,book_id,branch_id,card_no,borrower_name FROM "
				+ "checkout_details WHERE date_in IS NULL AND ";
		try{
			Statement stmt = conn.createStatement();
			if(book_id.isEmpty() && card_no.isEmpty() && borrower_name.isEmpty()  ) return null;
			
			if(!book_id.isEmpty())
			{
				resultQuery+= "book_id like '%"+book_id+"%' AND ";
			}
			if(!card_no.isEmpty())
			{
				resultQuery+= "card_no like '%"+card_no+"%' AND ";
			}
			if(!borrower_name.isEmpty())
			{
				resultQuery+= "borrower_name like '%"+borrower_name+"%' AND ";
			}
			resultQuery += "true ;";
			rs = stmt.executeQuery(resultQuery);

		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return rs;
	}
	
	/*
	 * Check if the given book_id is valid
	 * @param String book_id 
	 * @return True if book_id is valid
	 */
	public boolean isValidBookID(String book_id)
	{
		
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT book_id from BOOK where book_id ='"+book_id+"'");
			if(rs.next())
			{
				return true;
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	/*
	 * Check if the given branch_id is valid
	 * @param String branch_id 
	 * @return True if branch_id is valid
	 */
	public boolean isValidBranchID(String branch_id)
	{
		
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT branch_id from library_branch where branch_id ='"+branch_id+"'");
			if(rs.next())
			{
				return true;
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	/*
	 * Check if the given card_no is valid
	 * @param String card_no 
	 * @return True if card_no is valid
	 */
	public boolean isValidCardNo(String card_no)
	{
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT card_no from borrower where card_no ='"+card_no+"'");
			if(rs.next())
			{
				return true;
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	/*
	 * Checks if the user has exceeded checkout capacity
	 * @param String card_no : User card ID
	 * @return : True is user has less than 3 checkouts
	 */
	public boolean isValidCheckOut(String card_no)
	{
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from user_checkouts where card_no ="+card_no+"");
			if(rs.next())
			{
				int no_ofCheckouts = Integer.parseInt(rs.getString("no_checkouts"));
				return(no_ofCheckouts <3);
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return true; //empty set
	}
	
	/*
	 * Checks if the book is in stock at the given branch
	 * @param String book_id : Book ISBN
	 * @param String branch_id : branch ID
	 * @return : True if the book is available at the given branch
	 */
	public boolean isBookAvailable(String book_id,String branch_id)
	{
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT available_copies from book_copies where book_id ="+book_id+" "
					+ "AND branch_id ="+branch_id+"");
			if(rs.next())
			{
				int num_available = Integer.parseInt(rs.getString("available_copies"));
				return(num_available > 0);
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	/*
	 * Check out the book, add a row to the loans column
	 * update the borrower table no_borrowed and no_of_copies table no_available
	 */
	public boolean CheckOutBook(String book_id,String branch_id,String card_no)
	{
		try
		{			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO book_loans (book_id,branch_id,card_no) "
					+ "VALUES ('"+book_id+"','"+branch_id+"','"+card_no+"');";
			stmt.execute(statement);
			//update due date
			statement = "UPDATE book_loans SET due_date = due_date+ INTERVAL 14 DAY WHERE book_id = '"+book_id+"'"
					+ " AND branch_id ='"+branch_id+"' AND card_no = '"+card_no+"';";
			stmt.execute(statement);
			//update number of books available
			statement = "UPDATE book_copies SET no_available = no_available - 1 WHERE book_id = '"+book_id+"'"
					+ " AND branch_id ='"+branch_id+"';";
			stmt.execute(statement);
			return true;
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	public boolean CheckInBook(String loan_id, String date_in,String book_id,String branch_id)
	{
		try
		{			Statement stmt = conn.createStatement();
			String statement = "UPDATE book_loans SET Date_in = '"+date_in+"' WHERE loan_id ="+loan_id+"";
			stmt.execute(statement);
			//update number of books available
			statement = "UPDATE book_copies SET no_available = no_available + 1 WHERE book_id = '"+book_id+"'"
					+ " AND branch_id ='"+branch_id+"';";
			stmt.execute(statement);
			return true;
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	public boolean AddBorrower(String fname,String lname, String address, String city, String state,String phone){
		try{
			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO borrower (fname,lname,address,city,state,phone)"
					+ " VALUES ('"+fname+"','"+lname+"','"+address+"','"+city+"','"+state+"','"+phone+"');";
			stmt.execute(statement);
			return true;
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	public boolean CheckIfBorrowerExists(String fname,String lname,String address){
		try{
			Statement stmt = conn.createStatement();
			String statement = "SELECT * FROM borrower WHERE fname ='"+fname+"' AND lname = '"+lname+"' AND address = '"+address+"';";
			ResultSet rs = stmt.executeQuery(statement);
			return (rs.next());
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
	}
	
	
	/*
	 * clear the book loans table and reset available books to initial total copies
	 */
	public void resetToInitialState()
	{
		try
		{
			Statement stmt = conn.createStatement();
			String statement = "UPDATE book_copies SET no_available = no_of_copies";
			stmt.execute(statement);
			statement = "DELETE FROM book_loans";
			stmt.execute(statement);
			statement = "ALTER TABLE book_loans AUTO_INCREMENT = 1";
			stmt.execute(statement);
			statement = "DELETE FROM borrower WHERE card_no > 9041";
			stmt.execute(statement);
			statement = "ALTER TABLE book_loans AUTO_INCREMENT = 9042";
			stmt.execute(statement);
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
	
	public void updateFinesTable()
	{
		try{
			Statement stmt = conn.createStatement();
			//new entries to be added into fines table
			String query = "INSERT INTO fines (loan_id,fine,paid) "
					+ "SELECT loan_id,delay*0.25,'0' FROM new_fines;";
			stmt.execute(query);
			//update entries
			query = "UPDATE fines AS table1, (select * from fines_to_update) as table2 "
					+ "SET fine =  0.25* datediff(ifNULL((table2.date_in),curdate()),table2.date_out) "
					+ "WHERE table1.loan_id = table2.loan_id;";
			stmt.execute(query);
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
	
	public ResultSet getFinesData(boolean unPaidOnly)
	{
		ResultSet rs = null;
		String query = "SELECT book_loans.card_no,SUM(fine) as total_fine,IF(paid = 1,'Paid','Not Paid') as has_paid "
				+ "FROM (fines NATURAL JOIN book_loans) ";
		if(unPaidOnly){
			query += "WHERE paid = 0 ";
		}
		query += " GROUP BY card_no,paid;";
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return rs;
	}
	
	public boolean doesUserHaveCheckout(String card_no){
		ResultSet rs = null;
		String query = "SELECT * FROM book_loans where card_no ='"+card_no+"' AND Date_in is NULL;";
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return (rs.next());
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;//won't read on good execution
	}
	
	
	public void makePayment(String card_no){
		String query = "UPDATE fines SET paid = 1 WHERE loan_id IN  (SELECT loan_id FROM book_loans WHERE card_no ='"+card_no+"');";
		try{
			Statement stmt = conn.createStatement();
			stmt.execute(query);
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
}
