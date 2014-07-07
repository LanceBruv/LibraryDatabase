package library.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.TextField;
import java.awt.Label;

import javax.swing.JTable;

import java.awt.Checkbox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.model.DataBaseQueryGenerator;



public class TabbedInterface extends JFrame {

	//global 
	private JPanel contentPane;
	private Label label ;	
	private JTextField textField ;
	private Label label_1 ;
	private JTextField textField_1 ;	
	private Label label_2 ;	
	private JTextField textField_2 ;	
	private Label label_3 ;	
	private JTextField textField_3 ;	
	private Label label_5 ;	
	private JTextField textField_5 ;	
	private Label label_4 ;
	private JTextField textField_4 ;	
	private JCheckBox checkbox;
	private JPanel panel;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private JTextField textField_6;
	private JLabel lblNewLabel_1;
	private JTextField textField_7;
	private JLabel lblNewLabel_2;
	private JTextField textField_8;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private Button button;
	private Button button_1;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_9;
	private JLabel lblNewLabel_3;
	private JPanel panel_10;
	private JTable table;
	private JTabbedPane tabbedPane;
	private JPanel panel_2;
	private Button button_2;
	private Button button_3;
	private String fname,lname,minit,author_name,book_id,title,author_name_text_field,
	book_id_text_field,title_text_field,branch_id,no_of_copies,no_of_available;
	private DefaultTableModel model;
	private DataBaseQueryGenerator queryGenerate;
	private TableColumnModel tcm;

	/**
	 * Create the frame.
	 */
	public TabbedInterface() {
		try{
			queryGenerate = new DataBaseQueryGenerator();
			setBounds(100, 100, 1076, 481);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPane.add(tabbedPane);

			setUpPanel0();

			setUpPanel1();

			setUpPanel2();

			setUpPanel3();

			actionListeners();
			setUpFrame();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpPanel2()
	{
		panel_2 = new JPanel();
		tabbedPane.addTab("Check In", null, panel_2, null);
	}

	private void setUpPanel3()
	{

		panel_3 = new JPanel();
		tabbedPane.addTab("Edit Borrowers", null, panel_3, null);
	}


	private void actionListeners()
	{
		checkbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(label_2.isVisible()) //author is visible
				{
					label_2.setVisible(false);
					textField_2.setVisible(false);
					panel_6.setVisible(true);

				}
				else
				{
					panel_6.setVisible(false);
					label_2.setVisible(true);
					textField_2.setVisible(true);
				}
				panel.doLayout();
			}
		});

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				model.setRowCount(0);
				panel.doLayout();
			}
		});

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!checkbox.isSelected()) // not detailed
				{

					book_id_text_field = textField.getText().trim();
					title_text_field = textField_1.getText().trim();
					author_name_text_field = textField_2.getText().trim();
					ResultSet rs = queryGenerate.getBooks(book_id_text_field, title_text_field, author_name_text_field);
					try
					{
						model.setRowCount(0);
						if(rs != null)
						{
							while(rs.next())
							{
								book_id = rs.getString("book_id");
								title = rs.getString("title");
								author_name = rs.getString("author_name");
								no_of_copies = rs.getString("no_of_copies");
								branch_id = rs.getString("branch_id");
								no_of_available = rs.getString("available_copies");
								Object[] data = {book_id,title,author_name,branch_id,no_of_copies,no_of_available};
								model.addRow(data);
							}
						}

					}
					catch(SQLException ex) {
						System.out.println("Error in connection: " + ex.getMessage());
					}

				}
				else
				{
					book_id_text_field = textField.getText().trim();
					title_text_field = textField_1.getText().trim();
					fname = textField_3.getText().trim();
					minit = textField_5.getText().trim();
					lname  = textField_4.getText().trim();

					ResultSet rs = queryGenerate.getBooks(book_id_text_field, title_text_field, fname,minit,lname);
					try
					{
						model.setRowCount(0);
						if(rs != null)
						{
							while(rs.next())
							{
								book_id = rs.getString("book_id");
								title = rs.getString("title");
								author_name = rs.getString("author_name");
								no_of_copies = rs.getString("no_of_copies");
								branch_id = rs.getString("branch_id");
								no_of_available = rs.getString("available_copies");
								Object[] data = {book_id,title,author_name,branch_id,no_of_copies,no_of_available};
								model.addRow(data);
							}
						}

					}
					catch(SQLException ex) {
						System.out.println("Error in connection: " + ex.getMessage());
					}
				}
				panel.doLayout();
			}
		});
	}

	private void setUpPanel1()
	{





		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Check Out", null, panel_1, null);
		panel_1.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setBounds(0, 1, 990, 86);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		lblNewLabel = new JLabel("Book ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(121, 11, 105, 27);
		panel_4.add(lblNewLabel);

		textField_6 = new JTextField();
		textField_6.setBounds(192, 15, 134, 22);
		panel_4.add(textField_6);
		textField_6.setColumns(10);

		lblNewLabel_1 = new JLabel("Branch ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(346, 13, 80, 22);
		panel_4.add(lblNewLabel_1);

		textField_7 = new JTextField();
		textField_7.setBounds(422, 16, 124, 22);
		panel_4.add(textField_7);
		textField_7.setColumns(10);

		lblNewLabel_2 = new JLabel("Card Number");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(562, 19, 101, 19);
		panel_4.add(lblNewLabel_2);

		textField_8 = new JTextField();
		textField_8.setBounds(673, 16, 134, 22);
		panel_4.add(textField_8);
		textField_8.setColumns(10);

		panel_9 = new JPanel();
		panel_9.setBounds(0, 145, 990, 144);
		panel_1.add(panel_9);
		panel_9.setLayout(null);

		button = new Button("Check In");
		button.setBackground(new Color(192, 192, 192));
		button.setBounds(347, 5, 78, 34);
		panel_9.add(button);

		button_1 = new Button("Reset");
		button_1.setBackground(new Color(192, 192, 192));
		button_1.setBounds(568, 5, 100, 34);
		panel_9.add(button_1);

		panel_8 = new JPanel();
		panel_8.setBounds(0, 225, 990, 208);
		panel_1.add(panel_8);

		lblNewLabel_3 = new JLabel("New label");
		panel_8.add(lblNewLabel_3);

		lblNewLabel_3.setVisible(false);
	}
	
	private void setUpPanel0()
	{

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.addTab("Search", null, panel, null);
		panel.setLayout(null);

		panel_5 = new JPanel();
		panel_5.setBounds(10, 1, 1025, 42);
		panel.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		label = new Label("Book ID");
		panel_5.add(label);

		textField = new JTextField();
		panel_5.add(textField);
		textField.setColumns(10);

		label_1 = new Label("Title");
		panel_5.add(label_1);

		textField_1 = new JTextField();
		panel_5.add(textField_1);
		textField_1.setColumns(15);

		label_2 = new Label("Author");
		panel_5.add(label_2);

		textField_2 = new JTextField();
		panel_5.add(textField_2);
		textField_2.setColumns(15);

		panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		label_3 = new Label("First Name");
		panel_6.add(label_3);

		textField_3 = new JTextField();
		panel_6.add(textField_3);
		textField_3.setColumns(10);

		label_5 = new Label("Middle Initial");
		panel_6.add(label_5);

		textField_5 = new JTextField();
		panel_6.add(textField_5);
		textField_5.setColumns(10);

		label_4 = new Label("Last Name");
		panel_6.add(label_4);

		textField_4 = new JTextField();
		panel_6.add(textField_4);
		textField_4.setColumns(10);

		panel_7 = new JPanel();
		panel_7.setBounds(10, 43, 1025, 40);
		panel.add(panel_7);
		panel_7.setLayout(null);

		button_2 = new Button("Search");
		button_2.setBounds(263, 6, 89, 25);
		button_2.setBackground(new Color(192, 192, 192));
		panel_7.add(button_2);

		button_3 = new Button("Reset");

		button_3.setBounds(455, 6, 89, 25);
		button_3.setBackground(new Color(192, 192, 192));
		panel_7.add(button_3);

		checkbox = new JCheckBox("Detailed search");
		checkbox.setBounds(635, 10, 173, 24);
		checkbox.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel_7.add(checkbox);
		checkbox.setSelected(true);

		panel_10 = new JPanel();
		panel_10.setBounds(10, 91, 1012, 291);
		panel.add(panel_10);
		textField_2.setVisible(false);

		UpdatePanel0Table();
		
		//invisible by default
		label_2.setVisible(false);
		table = new JTable(model);

		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		JScrollPane scrollPane = new JScrollPane(table);
		panel_10.add(scrollPane);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		

	}

	private void setUpFrame()
	{

		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//center the frame
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width/2) - (this.getWidth() /2);
		int yPos = (dim.height /2) - (this.getHeight() /2);
		this.setLocation(xPos-this.getLocation().x, yPos);


		this.setTitle("Library Database");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	private void UpdatePanel0Table()
	{
		Object[] columnNames = {"Book ID",
				"Title",
				"Authors",
				"Branch ID",
				"Total Books",
		"Available"};

		model = new DefaultTableModel(columnNames,0 );


	}
}
