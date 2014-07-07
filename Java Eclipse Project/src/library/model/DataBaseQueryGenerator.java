package library.model;

import java.sql.*;

import javax.print.attribute.DateTimeSyntax;

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
		String resultQuery="SELECT book_id,title,author_name,branch_id,no_of_copies, available_copies FROM "
				+ "(temp_author NATURAL JOIN book_copies) "
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
				resultQuery+= "author_name like '%"+author+"%' AND ";
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
		String resultQuery="SELECT DISTINCT book_copies.book_id,title,temp_author.author_name,branch_id,no_of_copies, available_copies FROM "
				+ "((temp_author LEFT JOIN book_copies ON temp_author.book_id = book_copies.book_id)"
				+ " LEFT JOIN book_authors ON temp_author.book_id = book_authors.book_id)  "
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
			ResultSet rs = stmt.executeQuery("SELECT book_id from BOOK where book_id ="+book_id+"");
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
			ResultSet rs = stmt.executeQuery("SELECT branch_id from library_branch where branch_id ="+branch_id+"");
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
			ResultSet rs = stmt.executeQuery("SELECT card_no from borrower where card_no ="+card_no+"");
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
			ResultSet rs = stmt.executeQuery("SELECT card_no from borrower where card_no ="+card_no+"");
			if(rs.next())
			{
				int no_ofCheckouts = Integer.parseInt(rs.getString("no_borrowed"));
				return(no_ofCheckouts <3);
			}
			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
		return false;
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
			ResultSet rs = stmt.executeQuery("SELECT no_available from book_copies where book_id ="+book_id+" "
					+ "AND branch_id ="+branch_id+"");
			if(rs.next())
			{
				int num_available = Integer.parseInt(rs.getString("no_available"));
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
	public void CheckOutBook(String book_id,String branch_id,String card_no)
	{
		try
		{			Statement stmt = conn.createStatement();
			String statement = "INSERT INTO book_loans (book_id,branch_id,card_no) "
					+ "VALUES ("+book_id+","+branch_id+","+card_no+");";
			stmt.execute(statement);
			//update due date
			statement = "UPDATE book_loans SET due_date = due_date+ INTERVAL 14 DAY WHERE book_id = '"+book_id+"'"
					+ " AND branch_id ='"+branch_id+"' AND card_no = '"+card_no+"';";
			stmt.execute(statement);
			//update number of books available
			statement = "UPDATE book_copies SET available_copies = available_copies - 1 WHERE book_id = '"+book_id+"'"
					+ " AND branch_id ='"+branch_id+"';";
			stmt.execute(statement);
			statement = "UPDATE borrower SET no_borrowed = no_borrowed +1 WHERE card_no = '"+card_no+"';";
			stmt.execute(statement);
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
}
