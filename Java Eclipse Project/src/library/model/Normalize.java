package library.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.StringTokenizer;

public class Normalize {

	static Connection conn = null;
	/**
	 * @param args
	 */
	public static void normalizeAuthorsTable() {

		// Initialize variables for fields by data type
		String book_id;
		String author_name;
		String title;
		String[] authorTokens;
		String[] nameTokens;
		String lname;
		String minit;
		String fname;
		boolean firstInitial; //check if the name starts with an initial
		
	try {
		// Create a connection to the local MySQL server, with the "company" database selected.
		//		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
		// Create a connection to the local MySQL server, with the NO database selected.
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "abcd1234");

		// Create a SQL statement object and execute the query.
		Statement stmt = conn.createStatement();
		Statement query = conn.createStatement();
	
		// Set the current database, if not already set in the getConnection
		// Execute a SQL statement
		stmt.execute("use library;");

		// Execute a SQL query using SQL as a String object
		ResultSet rs = stmt.executeQuery("SELECT book_id, author_name, title FROM temp_author;");

		// Iterate through the result set using ResultSet class's next() method
		while (rs.next()) {
			// Populate field variables
			
			book_id = rs.getString("book_id");
			author_name = rs.getString("author_name");
			title = rs.getString("title");
			
			//parse author
			authorTokens = author_name.split(",");
			for (String currentAuthorName:authorTokens) 
			{
				firstInitial = true;
				nameTokens = currentAuthorName.split(" ");
				fname = "";
				lname ="";
				minit="";
				currentAuthorName = currentAuthorName.trim();
				for(String u:nameTokens)
				{
					if(firstInitial && u.endsWith(".")) //name starts with an initial
					{
						fname += u; 
					}
					else
					{
						firstInitial = false;
						if(fname.isEmpty())
						{
							fname = u;
						}
						else if(minit.isEmpty() && u.endsWith("."))
						{
							minit += u.substring(0,1);
						}
						else if(lname.isEmpty() && !u.trim().isEmpty())
						{
							
							lname = u.trim();
						}
					}
					
				}
				
				if(!minit.isEmpty())
				{
					query.execute("INSERT INTO book_authors VALUES ('"+book_id+"','"+currentAuthorName+"','"+fname+"','"+minit+"','"+lname+"');");
				}
				else if(!lname.isEmpty())
				{
					query.execute("INSERT INTO book_authors(book_id,author_name,fname,lname) VALUES ('"+book_id+"','"+currentAuthorName+"','"+fname+"','"+lname+"');");
				}
				else
				{
					query.execute("INSERT INTO book_authors(book_id,author_name,fname) VALUES ('"+book_id+"','"+currentAuthorName+"','"+fname+"');");
				}
			}
			if(title.contains("'")) //check for quotes within the string
			{
				title = title.replace("'", "\\'");
			}
			query.execute("INSERT INTO book(book_id,title) VALUES ('"+book_id+"','"+title+"');");

		} // End while(rs.next())

		// Always close the recordset and connection.
		rs.close();
		conn.close();
		System.out.println("Success!!");
	} 
	catch(SQLException ex) {
		System.out.println("Error in connection: " + ex.getMessage());
	}
}

/*
 *
 */
static void newln() {
	System.out.println();
}
}