package library.controller;

import java.sql.*;

import library.model.DataBaseQueryGenerator;
import library.model.Normalize;
import library.view.TabbedInterface;

public class MainThread {

		public static void main(String[] args) {

			TabbedInterface interFace = new TabbedInterface();
			DataBaseQueryGenerator qGen = new DataBaseQueryGenerator();
			qGen.CheckOutBook("1861003730", "1", "9002");
		}
}