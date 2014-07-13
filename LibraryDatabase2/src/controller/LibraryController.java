package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;
import view.UserView;
import view.ViewInterface;

public class LibraryController {
	private ViewInterface theView;
	private DataBaseQueryGenerator qGen;
	
	public LibraryController(UserView view, DataBaseQueryGenerator generate) {
		this.theView = view;
		this.qGen = generate;
		
		this.theView.addUserInputListener(new PopulateData());
	}

	class TableClickData implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class PopulateData implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ResultSet booksSet;
			try{ //just a precaution for testing, probably will never throw an exception
				
				if(theView.getDetailedSelection()){
					booksSet = qGen.getBooks(theView.getBookID(), theView.getTitle(), theView.getAuthorFirstName(), 
							theView.getAuthorMiddleInitial(), theView.getAuthorLastName());
				}
				else{
					booksSet = qGen.getBooks(theView.getBookID(), theView.getTitle(), theView.getAuthorName());
				}
				PopulateTable(booksSet);
				
			}
			catch(Exception e1){
				
			}
			
		}
		
		private void PopulateTable(ResultSet booksSet){
			try{
				if(booksSet != null){
					while(booksSet.next()){
						Object[] rowData = {booksSet.getString("book_id"),booksSet.getString("title"),booksSet.getString("author_name"),
								booksSet.getString("branch_id"),booksSet.getString("no_of_copies"),booksSet.getString("available_copies")};
						theView.addTableRow(rowData);
						}
					}
				}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	 
}
