package library.model;

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
}
