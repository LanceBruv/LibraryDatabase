package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import model.CheckOutData;

public interface ViewInterface {

	public String getBookID();

	public String getTitle();

	public String getAuthorName();

	public String getAuthorFirstName();

	public String getAuthorMiddleInitial();

	public String getAuthorLastName();

	public boolean getDetailedSelection();

	public void addTableRow(Object[] data);

	public void addUserInputListener(ActionListener action);
	
	public void addBookSelectListener(MouseListener getDoubleClick);
	
	public void resetTable();
	
	public void getInfo(String info);
	
	public void getCheckOutData(CheckOutData data);
}
