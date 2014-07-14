package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import model.CheckInData;
import model.CheckOutData;

public interface ViewInterface {

	public String getBookID();

	public String getTitle();

	public String getAuthorName();

	public String getAuthorFirstName();

	public String getAuthorMiddleInitial();

	public String getAuthorLastName();

	public boolean getDetailedSelection();

	public void addSearchTableRow(Object[] data);
	
	public void addCheckInTableRow(Object[] data);

	public void searchBooksInputListener(ActionListener action);
	
	public void addBookSelectListener(MouseListener getDoubleClick);
	
	public void resetTable();
	
	public void getInfo(String info);
	
	public void sendDataToCheckOut(CheckOutData data);
	
	public void addCheckOutListener(ActionListener action);
	
	public void addCheckInListener(ActionListener action);
	
	public CheckOutData getCheckOutData();
	
	public void setCheckOutPaneInfo(String info);
	
	public void searchCheckInsInputListener(ActionListener action);
	
	public CheckInData getCheckInData();
	
	public void checkInInfo(String info);
	
	public CheckInData getCheckInSearchData();
}
