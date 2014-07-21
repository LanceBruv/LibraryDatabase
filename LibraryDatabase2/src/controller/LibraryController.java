package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;

import model.*;
import view.UserView;
import view.ViewInterface;

public class LibraryController {
	private ViewInterface theView;
	private DataBaseQueryGenerator qGen;
	
	public LibraryController(UserView view, DataBaseQueryGenerator generate) {
		this.theView = view;
		this.qGen = generate;
		
		this.theView.searchBooksInputListener(new PopulateData());
		this.theView.addBookSelectListener(new TableClickData());
		this.theView.addCheckOutListener(new CheckOutClickData());
		this.theView.searchCheckInsInputListener(new CheckInSearchData());
		this.theView.addCheckInListener(new CheckInToDatabase());
		this.theView.addBorrowerListener(new BorrowerInsertData());
		this.theView.getFinesListener(new UpdateFinesData());
		this.theView.payFineListener(new FinePayment());
	}

	class FinePayment implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String card_no = theView.finePaymentData();
			if(card_no==null){
				theView.payFineInfo("Please make a choice");
				return;
			}
			else if(qGen.doesUserHaveCheckout(card_no)){
				//check if the card_no has any check outs
				theView.payFineInfo("Cannot pay! User has checkouts");
			}
			else{
				qGen.makePayment(card_no);
				theView.payFineInfo("Payment completed");
			}
		}

	}

	class UpdateFinesData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		qGen.updateFinesTable();
		ResultSet fineSet;
		fineSet = qGen.getFinesData(theView.getUnpaidOnlyCheckBox());
		try{
			theView.resetDisplay();
				while(fineSet.next()){
					Object[] rowData = {fineSet.getString("card_no"),fineSet.getString("total_fine"),fineSet.getString("has_paid")};
					theView.addFinesTableRow(rowData); 
				}
			}
			catch(SQLException e1){
				System.out.println(e1.getMessage());
			}
		}
		
	}
	
	class BorrowerInsertData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			BorrowerData data = theView.getBorrowerData();
			if(data.getFname().equals("") || data.getLname().equals("") || data.getAddress().equals("")){
				theView.borrowerPanelInfo("First, Last name and Address required");
				return;
			}
			else if(qGen.CheckIfBorrowerExists(data.getFname(), data.getLname(), data.getAddress())){
				theView.borrowerPanelInfo("User already exists");
				return;
			}
			else{
				
				if(qGen.AddBorrower(data.getFname(), data.getLname(), data.getAddress(), data.getCity(), data.getState(), data.getPhone())){
					theView.borrowerPanelInfo("Successfully added");
				}
				else{
					theView.borrowerPanelInfo("Errro adding user");
				}
			}
			
		}
		
	}
	
	class CheckInToDatabase implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CheckInData data = theView.getCheckInData();
			if(data != null){
				if(qGen.CheckInBook(data.getLoan_ID(), data.getCheckInDate(), data.getBook_id(), data.getBranch_id())){
					theView.checkInInfo("Successfully checked in");
					theView.resetDisplay();
				}
				else{
					theView.checkInInfo("Error checking in");
				}
			}
			
		}
		
	}
	
	
	/*
	 * Check out book
	 */
	class CheckOutClickData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CheckOutData data = theView.getCheckOutData();
			if(!qGen.isValidBookID(data.getBook_id())){
				theView.setCheckOutPaneInfo("Invalid Book ID");
				return;
			}
			else if(!qGen.isValidBranchID(data.getBranch_id())){
				theView.setCheckOutPaneInfo("Invalid Branch ID");
				return;
			}
			else if(!qGen.isValidCardNo(data.getCardNumber())){
				theView.setCheckOutPaneInfo("Invalid Card Number");
				return;
			}
			else if(!qGen.isValidCheckOut(data.getCardNumber())){
				theView.setCheckOutPaneInfo("Maximum checkouts exceeded");
				return;
			}
			if(qGen.CheckOutBook(data.getBook_id(), data.getBranch_id(), data.getCardNumber())){
				theView.setCheckOutPaneInfo("Successfully checked out");
				theView.resetTable();
				return;
			}
			theView.setCheckOutPaneInfo("Error checking out");
			
		}
		
	}

	/*
	 * table click
	 */
	class TableClickData implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent me) {

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
		public void mousePressed(MouseEvent me) {
			JTable table =(JTable) me.getSource();
	        Point p = me.getPoint();
	        int row = table.rowAtPoint(p);
	        if((int)(table.getModel().getValueAt(row, 5)) ==0){
	        	theView.getInfo("           Sorry no book is available at that branch");
	        }
	        else{
	        	theView.getInfo("");
	        	CheckOutData data = new CheckOutData();
	        	data.setBook_id((String)table.getModel().getValueAt(row, 0));
	        	data.setBranch_id((String)table.getModel().getValueAt(row, 3));
	        	theView.sendDataToCheckOut(data);
	        }
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * get data from database using query
	 */
	class PopulateData implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			theView.resetTable();
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
				System.out.println(e1.getMessage());
			}
			
		}
		
		private void PopulateTable(ResultSet booksSet){
			try{
				if(booksSet != null){
					while(booksSet.next()){
						Object[] rowData = {booksSet.getString("book_id"),booksSet.getString("title"),booksSet.getString("author_list"),
								booksSet.getString("branch_id"),booksSet.getString("no_of_copies"),booksSet.getInt("no_available")};
						theView.addSearchTableRow(rowData);
						}
					}
				}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	class CheckInSearchData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			theView.resetTable();
			ResultSet checkInSet;
			try{
				CheckInData data = theView.getCheckInSearchData();
				checkInSet = qGen.getCheckIns(data.getBook_id(), data.getCardNumber(),data.getBorrowerName());
				while(checkInSet.next()){
					Object[] rowData = {checkInSet.getString("loan_id"),checkInSet.getString("book_id"),checkInSet.getString("branch_id"),
							checkInSet.getString("card_no"),checkInSet.getString("borrower_name")};
					theView.addCheckInTableRow(rowData);
				}
			}
			catch(Exception e1){

			}
		}
		
	}
	
	 
}
