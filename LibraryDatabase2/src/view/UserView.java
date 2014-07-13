package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import model.CheckOutData;

import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.SpringLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

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
	private DefaultTableModel tableModel;
	private JTextField bookIDCheckoutTextField;
	private JTextField branchIDCheckoutTextField;
	private JTextField cardNumberCheckoutTextField;
	private JLabel errorMessageLabel;
	private JTabbedPane tabbedPane;
	private JFrame frame;
	private JButton checkOutButton;
	private JLabel checkOutInfoLabel;
	
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
	public void addTableRow(Object[] data){
		tableModel.addRow(data);
	}
	
	/*
	 * add action listeners for MVC property
	 */
	@Override
	public void addUserInputListener(ActionListener listenForUserInput){
		searchButton.addActionListener(listenForUserInput);
	}
	
	@Override
	public void addBookSelectListener(MouseListener getDoubleClick) {
		searchTable.addMouseListener(getDoubleClick);
	}
	
	@Override
	public void resetTable(){
		tableModel.setRowCount(0);
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
	
	/**
	 * Create the frame.
	 */
	public UserView() { 
		
		frame = this;
		this.setSize(getMaximumSize());
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
		tableModel = new DefaultTableModel(coulmnNames,0 );
		searchTable = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
        };
		};
		JScrollPane tableScrollPane = new JScrollPane(searchTable);
		tablePanel.add(tableScrollPane);
		
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
		checkInPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JButton btnNewButton_2 = new JButton("New button");
		checkInPanel.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		checkInPanel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("New button");
		checkInPanel.add(btnNewButton);
		
		/*
		 * Borrower panel
		 */
		JPanel borrowerPanel = new JPanel();
		tabbedPane.addTab("Borrower", null, borrowerPanel, null);
		setVisible(true);
		
	}
	
	private void resetText(){
		bookIDTextField.setText("");
		authorFirstNameTextField.setText("");
		authorLastNameTextField.setText("");
		authorMiddleInitialTextField.setText("");
		authorNameTextField.setText("");
		titleTextField.setText("");
		tableModel.setRowCount(0);
		bookIDCheckoutTextField.setText("");
		branchIDCheckoutTextField.setText("");
		cardNumberCheckoutTextField.setText("");
	}
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width, width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

}
