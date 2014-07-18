package controller;

import model.DataBaseQueryGenerator;
import view.UserView;

public class Library {

	public static void main(String args[]){
		UserView view = new UserView();
		DataBaseQueryGenerator qGen = new DataBaseQueryGenerator();
 		@SuppressWarnings("unused")
		LibraryController controller = new LibraryController(view, qGen);
		//qGen.resetToInitialState();
	}
}
