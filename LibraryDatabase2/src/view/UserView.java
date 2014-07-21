package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import model.BorrowerData;
import model.CheckInData;
import model.CheckOutData;
import java.awt.GridLayout;
import javax.swing.JComboBox;

public class UserView extends JFrame implements ViewInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JTextField bookIDTextField;
	private JTextField titleTextField;
	private JTextField authorNameTextField;
	private JTextField authorFirstNameTextField;
	private JTextField authorMiddleInitialTextField;
	private JTextField authorLastNameTextField;
	private JCheckBox detailedSelectionCheckBox;
	private JPanel detailedSearchPanel;
	private JPanel normalSearchPanel;
	private JButton searchButton;
	private JTable searchTable;
	private DefaultTableModel serachTableModel;
	private JTextField bookIDCheckoutTextField;
	private JTextField branchIDCheckoutTextField;
	private JTextField cardNumberCheckoutTextField;
	private JLabel errorMessageLabel;
	private JTabbedPane tabbedPane;
	private JFrame frame;
	private JButton checkOutButton;
	private JLabel checkOutInfoLabel;
	private JTable checkInTable;
	private DefaultTableModel checkInTableModel;
	private JTextField checkInCardNoTextField;
	private JTextField checkInBookIDTextField;
	private JTextField checkInBorrowerNameTextField;
	private JButton checkInSearchButton;
	private JButton checkInButton;
	private JComboBox<String> dateCombo;
	private JComboBox<String> monthCombo;
	private JComboBox<Integer> yearCombo;
	private JLabel checkInInfoLabel;
	private JTextField borrowerFnameTextField;
	private JTextField borrowerlnameTextField;
	private JTextField borrowerAddressTextField;
	private JTextField borrowerCityTextField;
	private JTextField borrowerStateTextField;
	private JTextField borrowerPhoneNumberTextField;
	private JButton borrowerAddUserButton;
	private JLabel borrowerInfoLabel1;
	private DefaultTableModel finesTableModel;
	private JTable finesInTable;
	private JButton getFinesButton;
	private JLabel fineInfoLabel;
	private JButton payFineButton;
	private JCheckBox AllFInesCheckBox;

	@Override
	public String getBookID() {
		return bookIDTextField.getText();
	}

	@Override
	public String getTitle() {
		return titleTextField.getText();
	}

	@Override
	public String getAuthorName() {
		return authorNameTextField.getText();
	}

	@Override
	public String getAuthorFirstName() {
		return authorFirstNameTextField.getText();
	}

	@Override
	public String getAuthorMiddleInitial() {
		return authorMiddleInitialTextField.getText();
	}

	@Override
	public String getAuthorLastName() {
		return authorLastNameTextField.getText();
	}

	@Override
	public boolean getDetailedSelection() {
		return detailedSelectionCheckBox.isSelected();
	}

	@Override
	public void addSearchTableRow(Object[] data){
		serachTableModel.addRow(data);
	}

	@Override
	public void addCheckInTableRow(Object[] data){
		checkInTableModel.addRow(data);
	}

	/*
	 * add action listeners for MVC property
	 */
	@Override
	public void searchBooksInputListener(ActionListener listenForUserInput){
		searchButton.addActionListener(listenForUserInput);
	}

	@Override
	public void searchCheckInsInputListener(ActionListener action){
		checkInSearchButton.addActionListener(action);
	}

	@Override
	public void addBookSelectListener(MouseListener getDoubleClick) {
		searchTable.addMouseListener(getDoubleClick);
	}

	@Override
	public void resetTable(){
		serachTableModel.setRowCount(0);
		checkInTableModel.setRowCount(0);
	}

	@Override
	public void getInfo(String info){
		errorMessageLabel.setText(info);
	}

	@Override
	public void sendDataToCheckOut(CheckOutData data){
		tabbedPane.setSelectedIndex(1);
		bookIDCheckoutTextField.setText(data.getBook_id());
		branchIDCheckoutTextField.setText(data.getBranch_id());
	}

	@Override
	public void addCheckOutListener(ActionListener action){
		checkOutButton.addActionListener(action);
	}

	@Override
	public void addCheckInListener(ActionListener action){
		checkInButton.addActionListener(action);
	}

	@Override
	public void addBorrowerListener(ActionListener action){
		borrowerAddUserButton.addActionListener(action);
	}

	@Override
	public CheckOutData getCheckOutData(){
		CheckOutData data = new CheckOutData();
		data.setBook_id(bookIDCheckoutTextField.getText());
		data.setBranch_id(branchIDCheckoutTextField.getText());
		data.setCardNumber(cardNumberCheckoutTextField.getText());
		return data;
	}

	@Override
	public void setCheckOutPaneInfo(String info){
		checkOutInfoLabel.setText(info);
	}

	@Override
	public CheckInData getCheckInData(){
		int row = checkInTable.getSelectedRow();
		if( row == -1){
			checkInInfoLabel.setText("Please make a selection");
		}
		else{
			CheckInData data = new CheckInData();
			data.setBook_id((String)checkInTable.getModel().getValueAt(row, 1));
			data.setBranch_id((String)checkInTable.getModel().getValueAt(row, 2));
			data.setLoan_ID((String)checkInTable.getModel().getValueAt(row, 0));
			String date_in = yearCombo.getSelectedItem()+"-"+monthCombo.getSelectedItem()+"-"+dateCombo.getSelectedItem()+"";
			data.setCheckInDate(date_in);
			return data;
		}
		return null;
	}

	@Override 
	public CheckInData getCheckInSearchData(){
		CheckInData data = new CheckInData();
		data.setCardNumber(checkInCardNoTextField.getText());
		data.setBook_id(checkInBookIDTextField.getText());
		data.setBorrowerName(checkInBorrowerNameTextField.getText());
		return data;
	}

	@Override
	public void checkInInfo(String info){
		checkInInfoLabel.setText(info);
	}

	@Override
	public void borrowerPanelInfo(String info){
		borrowerInfoLabel1.setText(info);
	}

	@Override
	public BorrowerData getBorrowerData(){
		BorrowerData data = new BorrowerData();
		data.setFname(borrowerFnameTextField.getText());
		data.setLname(borrowerlnameTextField.getText());
		data.setAddress(borrowerAddressTextField.getText());
		data.setCity(borrowerCityTextField.getText());
		data.setState(borrowerStateTextField.getText());
		data.setPhone(borrowerPhoneNumberTextField.getText());
		return data;
	}

	@Override
	public void getFinesListener(ActionListener action){
		getFinesButton.addActionListener(action);
	}

	@Override
	public void addFinesTableRow(Object[] data){
		finesTableModel.addRow(data);
	}

	@Override
	public String finePaymentData(){
		int row = finesInTable.getSelectedRow();
		if( row == -1){
			fineInfoLabel.setText("Please make a selection");
		}
		else{
			return (String)finesInTable.getModel().getValueAt(row, 0);
		}
		return null;
	}

	@Override
	public void payFineListener(ActionListener action){
		payFineButton.addActionListener(action);
	}

	@Override
	public void payFineInfo(String info){
		fineInfoLabel.setText(info);
	}

	@Override
	public void resetDisplay(){
		resetText();
	}
	
	@Override
	public boolean getUnpaidOnlyCheckBox(){
		return AllFInesCheckBox.isSelected();
	}
	/**
	 * Create the frame.
	 */
	public UserView() { 

		frame = this;
		this.setTitle("Library");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0,0));
		setContentPane(contentPanel);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if(index == 0){
					frame.setSize(getMaximumSize());
				}
				else if(index == 1){
					frame.setSize(400,250);
				}
				else if(index == 2 || index == 4){
					frame.setSize(700,600);
				}
				else if(index == 3){
					frame.setSize(400,400);
				}
			}
		});
		contentPanel.add(tabbedPane, BorderLayout.CENTER);

		/*
		 * Search panel
		 */
		JPanel searchPanel = new JPanel();
		tabbedPane.addTab("Search", null, searchPanel, null);

		JPanel searchOptoinsPanel = new JPanel();
		searchOptoinsPanel.setBorder(BorderFactory.createTitledBorder("Enter criteria"));
		searchPanel.add(searchOptoinsPanel);

		JPanel selectOptionsPanel = new JPanel();
		selectOptionsPanel.setBorder(BorderFactory.createTitledBorder("Search or reset"));
		searchPanel.add(selectOptionsPanel);

		JPanel messageBoardpanel = new JPanel();
		messageBoardpanel.setBorder(BorderFactory.createTitledBorder("Messages"));
		searchPanel.add(messageBoardpanel);

		JLabel infoLabel = new JLabel("Click on a book/row to chek it out");
		infoLabel.setAlignmentX(LEFT_ALIGNMENT);
		messageBoardpanel.add(infoLabel);

		errorMessageLabel = new JLabel("");
		errorMessageLabel.setAlignmentY(RIGHT_ALIGNMENT);
		messageBoardpanel.add(errorMessageLabel);

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createTitledBorder("Table"));
		tablePanel.setLayout(new BorderLayout(0,0));
		searchPanel.add(tablePanel);

		/*
		 * Set up table
		 */
		Object[] coulmnNames = {"Book ID", "Title", "Author", "Branch ID","Total copies","Available"};
		serachTableModel = new DefaultTableModel(coulmnNames,0 );
		searchTable = new JTable(serachTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
				return false;               
			};
		};
		JScrollPane searchScrollPane = new JScrollPane(searchTable);
		tablePanel.add(searchScrollPane);

		searchButton = new JButton("Search");
		selectOptionsPanel.add(searchButton);

		JButton resetButton = new JButton("Reset fields");
		selectOptionsPanel.add(resetButton);

		//set box layout for vertical alignment of search options, buttons and table output
		searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));

		JLabel bookIDLabel = new JLabel("Book ID");
		searchOptoinsPanel.add(bookIDLabel);

		bookIDTextField = new JTextField();
		searchOptoinsPanel.add(bookIDTextField);
		bookIDTextField.setColumns(10);

		JLabel titleLabel = new JLabel("Title");
		searchOptoinsPanel.add(titleLabel);

		titleTextField = new JTextField();
		searchOptoinsPanel.add(titleTextField);
		titleTextField.setColumns(10);

		normalSearchPanel = new JPanel();
		searchOptoinsPanel.add(normalSearchPanel);

		JLabel authorNameLabel = new JLabel("Author Name");
		normalSearchPanel.add(authorNameLabel);

		authorNameTextField = new JTextField();
		normalSearchPanel.add(authorNameTextField);
		authorNameTextField.setColumns(10);

		detailedSearchPanel = new JPanel();
		searchOptoinsPanel.add(detailedSearchPanel);
		detailedSearchPanel.setVisible(false);

		JLabel authorFirstNameLabel = new JLabel("First Name");
		detailedSearchPanel.add(authorFirstNameLabel);

		authorFirstNameTextField = new JTextField();
		detailedSearchPanel.add(authorFirstNameTextField);
		authorFirstNameTextField.setColumns(10);

		JLabel authorMiddleInitialLabel = new JLabel("Initial");
		detailedSearchPanel.add(authorMiddleInitialLabel);

		authorMiddleInitialTextField = new JTextField();
		detailedSearchPanel.add(authorMiddleInitialTextField);
		authorMiddleInitialTextField.setColumns(10);

		JLabel authorLastNameLabel = new JLabel("Last Name");
		detailedSearchPanel.add(authorLastNameLabel);

		authorLastNameTextField = new JTextField();
		detailedSearchPanel.add(authorLastNameTextField);
		authorLastNameTextField.setColumns(10);

		detailedSelectionCheckBox = new JCheckBox("Detailed");
		searchOptoinsPanel.add(detailedSelectionCheckBox);

		detailedSelectionCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(normalSearchPanel.isVisible()){
					normalSearchPanel.setVisible(false);
					detailedSearchPanel.setVisible(true);
				}
				else if(detailedSearchPanel.isVisible()){
					normalSearchPanel.setVisible(true);
					detailedSearchPanel.setVisible(false);
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				resizeColumnWidth(searchTable);
			}
		});

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetText();
			}
		});


		/*
		 * Check out panel
		 */
		JPanel checkOutPanel = new JPanel();
		tabbedPane.addTab("Check Out", null, checkOutPanel, null);
		checkOutPanel.setLayout(new BorderLayout());

		JPanel checkoutOptionsPanel = new JPanel();
		//checkoutOptionsPanel.setLayout(new BoxLayout(checkoutOptionsPanel,BoxLayout.Y_AXIS ));
		checkOutPanel.add(checkoutOptionsPanel);
		checkoutOptionsPanel.setLayout(new GridLayout(5, 6, 0, 0));

		JLabel bookIDCheckOutLabel = new JLabel("Book ID");
		checkoutOptionsPanel.add(bookIDCheckOutLabel);

		bookIDCheckoutTextField = new JTextField();
		bookIDCheckoutTextField.setSize(new Dimension(this.getWidth(),2));
		checkoutOptionsPanel.add(bookIDCheckoutTextField);
		bookIDCheckoutTextField.setColumns(10);

		JLabel branchIDCheckoutLabel = new JLabel("Branch ID");
		checkoutOptionsPanel.add(branchIDCheckoutLabel);

		branchIDCheckoutTextField = new JTextField();
		branchIDCheckoutTextField.setSize(new Dimension(this.getWidth(),1));
		checkoutOptionsPanel.add(branchIDCheckoutTextField);
		branchIDCheckoutTextField.setColumns(10);

		JLabel cardNoCheckoutLabel = new JLabel("Card Number");
		checkoutOptionsPanel.add(cardNoCheckoutLabel);

		cardNumberCheckoutTextField = new JTextField();
		cardNumberCheckoutTextField.setSize(new Dimension(this.getWidth(),1));
		checkoutOptionsPanel.add(cardNumberCheckoutTextField);
		cardNumberCheckoutTextField.setColumns(10);

		JPanel checkOutButtonPanel1 = new JPanel();
		checkoutOptionsPanel.add(checkOutButtonPanel1);
		checkOutButtonPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		checkOutButton = new JButton("Check Out");
		checkOutButtonPanel1.add(checkOutButton);

		JButton checkOutResetButton = new JButton("Reset");
		checkOutButtonPanel1.add(checkOutResetButton);

		JPanel checkOutInfoPanel = new JPanel();
		checkoutOptionsPanel.add(checkOutInfoPanel);
		checkOutInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		checkOutInfoLabel = new JLabel("");
		checkOutInfoPanel.add(checkOutInfoLabel);

		checkOutResetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetText();
			}
		});

		/*
		 * Check In panel
		 */
		JPanel checkInPanel = new JPanel();
		tabbedPane.addTab("Check In", null, checkInPanel, null);
		checkInPanel.setLayout(new BoxLayout(checkInPanel,BoxLayout.Y_AXIS));

		JPanel checkInFieldsPanel = new JPanel();
		checkInPanel.add(checkInFieldsPanel);
		checkInFieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel checkInCardNoLabel = new JLabel("Card Number");
		checkInFieldsPanel.add(checkInCardNoLabel);

		checkInCardNoTextField = new JTextField();
		checkInFieldsPanel.add(checkInCardNoTextField);
		checkInCardNoTextField.setColumns(10);

		JLabel checkInBookIDLabel = new JLabel("Book ID");
		checkInFieldsPanel.add(checkInBookIDLabel);

		checkInBookIDTextField = new JTextField();
		checkInFieldsPanel.add(checkInBookIDTextField);
		checkInBookIDTextField.setColumns(10);

		JLabel checkInBorrowerNameLabel = new JLabel("Borrower");
		checkInFieldsPanel.add(checkInBorrowerNameLabel);

		checkInBorrowerNameTextField = new JTextField();
		checkInFieldsPanel.add(checkInBorrowerNameTextField);
		checkInBorrowerNameTextField.setColumns(10);

		JPanel checkInSearchButtonsPanel = new JPanel();
		checkInPanel.add(checkInSearchButtonsPanel);
		checkInSearchButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		checkInSearchButton = new JButton("Search");
		checkInSearchButtonsPanel.add(checkInSearchButton);

		JButton checkInResetButton = new JButton("Reset");
		checkInSearchButtonsPanel.add(checkInResetButton);

		checkInInfoLabel = new JLabel("Select value from table, select date and check in");
		checkInSearchButtonsPanel.add(checkInInfoLabel);

		JScrollPane checkInscrollPane = new JScrollPane();
		checkInPanel.add(checkInscrollPane);

		Object[] checkInColumnNames = {"Loan ID","Book ID", "Branch_ID", "Card Number", "Borrower Name"};
		checkInTableModel = new DefaultTableModel(checkInColumnNames,0 );

		checkInTable = new JTable(checkInTableModel);
		checkInscrollPane.setViewportView(checkInTable);

		JPanel dateSelectPanel = new JPanel();
		checkInPanel.add(dateSelectPanel);
		dateSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel checkInDateInLabel = new JLabel("Select Check in Date:");
		dateSelectPanel.add(checkInDateInLabel);

		JPanel checkInButtonsPanel = new JPanel();
		checkInPanel.add(checkInButtonsPanel);
		checkInButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Calendar now = Calendar.getInstance();
	
		String months[] ={"01","02","03","04","05","06","07","08","09","10","11","12"};
		monthCombo = new JComboBox<String>(months);
		monthCombo.setSelectedIndex(now.get(Calendar.MONTH));
		dateSelectPanel.add(monthCombo);

		String Dates[] ={"01","02","03","04","05","06","07","08","09","10","11","12",
				"13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		dateCombo = new JComboBox<String>(Dates);
		dateCombo.setSelectedIndex(now.get(Calendar.DAY_OF_MONTH)-1);
		dateSelectPanel.add(dateCombo);

		Integer years[] ={2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025};
		yearCombo = new JComboBox<Integer>(years);
		yearCombo.setSelectedItem(now.get(Calendar.YEAR));
		dateSelectPanel.add(yearCombo);
	 
		checkInButton = new JButton("Check In");
		checkInButtonsPanel.add(checkInButton);

		checkInSearchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resizeColumnWidth(checkInTable);
				checkInInfoLabel.setText("Select value from table, select date and check in");
			}
		});

		checkInResetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetText();
			}
		});

		/*
		 * Borrower panel
		 */


		JPanel borrowerPanel = new JPanel();
		tabbedPane.addTab("Borrower", null, borrowerPanel, null);
		borrowerPanel.setLayout(new GridLayout(8, 2, 0, 0));

		JLabel borrowerFnameLabel = new JLabel(" First Name");
		borrowerPanel.add(borrowerFnameLabel);

		borrowerFnameTextField = new JTextField();
		borrowerPanel.add(borrowerFnameTextField);
		borrowerFnameTextField.setColumns(10);

		JLabel borrowerLastNameLabel = new JLabel(" Last Name");
		borrowerPanel.add(borrowerLastNameLabel);

		borrowerlnameTextField = new JTextField();
		borrowerPanel.add(borrowerlnameTextField);
		borrowerlnameTextField.setColumns(10);

		JLabel borrowerAddressLabel = new JLabel(" Address");
		borrowerPanel.add(borrowerAddressLabel);

		borrowerAddressTextField = new JTextField();
		borrowerPanel.add(borrowerAddressTextField);
		borrowerAddressTextField.setColumns(10);

		JLabel borrowerPhoneLabel = new JLabel(" Phone Number");
		borrowerPanel.add(borrowerPhoneLabel);

		borrowerPhoneNumberTextField = new JTextField();
		borrowerPanel.add(borrowerPhoneNumberTextField);
		borrowerPhoneNumberTextField.setColumns(10);

		JLabel borrowerStateLabel = new JLabel(" State");
		borrowerPanel.add(borrowerStateLabel);

		borrowerStateTextField = new JTextField();
		borrowerPanel.add(borrowerStateTextField);
		borrowerStateTextField.setColumns(10);

		JLabel borrowerCityLabel = new JLabel(" City");
		borrowerPanel.add(borrowerCityLabel);

		borrowerCityTextField = new JTextField();
		borrowerPanel.add(borrowerCityTextField);
		borrowerCityTextField.setColumns(10);

		borrowerInfoLabel1 = new JLabel("");
		borrowerPanel.add(borrowerInfoLabel1);

		JLabel borrowerInfoLabel2 = new JLabel("");
		borrowerPanel.add(borrowerInfoLabel2);

		borrowerAddUserButton = new JButton("Add");
		borrowerPanel.add(borrowerAddUserButton);

		JButton borrowerResetButton = new JButton("Reset");
		borrowerPanel.add(borrowerResetButton);

		borrowerResetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetText();

			}
		});

		/*
		 * Fines table
		 */

		JPanel finesPanel = new JPanel();
		tabbedPane.addTab("Fines", null, finesPanel, null);
		finesPanel.setLayout(new BoxLayout(finesPanel, BoxLayout.Y_AXIS));

		JPanel finesOptionsPanel = new JPanel();
		finesPanel.add(finesOptionsPanel);
		finesOptionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		getFinesButton = new JButton("Get Fines / Refresh");
		finesOptionsPanel.add(getFinesButton);

		AllFInesCheckBox = new JCheckBox("Show only unpaid");
		finesOptionsPanel.add(AllFInesCheckBox);
		setVisible(true);

		JPanel fineTablePane = new JPanel();
		finesPanel.add(fineTablePane);

		Object[] finesColumnNames = {"Card Number","Fine Amount", "Paid/Not paid"};
		finesTableModel = new DefaultTableModel(finesColumnNames,0 );

		finesInTable = new JTable(finesTableModel);

		JScrollPane finesScrollPane = new JScrollPane(finesInTable);
		fineTablePane.add(finesScrollPane);

		JPanel fineOptionsPanel = new JPanel();
		finesPanel.add(fineOptionsPanel);

		payFineButton = new JButton("Pay Fine");
		fineOptionsPanel.add(payFineButton);

		fineInfoLabel = new JLabel("make a selection and hit pay fine");
		fineOptionsPanel.add(fineInfoLabel);
	}

	private void resetText(){
		bookIDTextField.setText("");
		authorFirstNameTextField.setText("");
		authorLastNameTextField.setText("");
		authorMiddleInitialTextField.setText("");
		authorNameTextField.setText("");
		titleTextField.setText("");
		serachTableModel.setRowCount(0);
		bookIDCheckoutTextField.setText("");
		branchIDCheckoutTextField.setText("");
		cardNumberCheckoutTextField.setText("");
		checkInTableModel.setRowCount(0);
		checkInBookIDTextField.setText("");
		checkInBorrowerNameTextField.setText("");
		checkInCardNoTextField.setText("");
		borrowerAddressTextField.setText("");
		borrowerCityTextField.setText("");
		borrowerFnameTextField.setText("");
		borrowerlnameTextField.setText("");
		borrowerPhoneNumberTextField.setText("");
		borrowerStateTextField.setText("");
		finesTableModel.setRowCount(0);
		checkOutInfoLabel.setText("");
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Minimum width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

}
