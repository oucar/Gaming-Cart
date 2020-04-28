import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Categories extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTable table;
	
	//Connections
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	//Constructor
	public Categories() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Category name");
		nameLabel.setBounds(33, 65, 97, 16);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(154, 60, 165, 26);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton addButton = new JButton("Add!");
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				try {
					
	                String sql ="INSERT INTO Categories (CName) VALUES (?)";
	                pst = conn.prepareStatement(sql);
	                pst.setString(1, nameField.getText());

	                //check for empty entries
	                if(nameField.getText().equals("")) {
	                	JOptionPane.showMessageDialog(null,"Please make sure that text field is not empty.");
	                	System.out.println("user left the asked text field empty.");
	                }//end if

	                else {
	                	pst.execute();					                
			            System.out.println("Category: [" + nameField.getText() +"] added succesfully!");
			            JOptionPane.showMessageDialog(null, "Category  added succesfully!");
	                }//end else

				}//end try
				catch (Exception ex){
					ex.getStackTrace();
					System.out.println("Something went wrong, data couldn't be saved.");
				}//end catch		
			}//end actionPerformed
		});//end actionListener
		
		addButton.setBounds(329, 60, 97, 29);
		contentPane.add(addButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 131, 839, 133);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 int row = table.rowAtPoint(e.getPoint());
	             int col = table.columnAtPoint(e.getPoint());
	            	
	             String clickedAttribute = (String) table.getValueAt(row, col).toString(); //Get the clicked attribute
	             System.out.println(clickedAttribute); //check it in terminal
	             
	             String clickedId = (String) table.getValueAt(row, 0).toString(); //clickedId
	             String clickedName = (String) table.getValueAt(row, 1).toString(); //Clicked name
	             

	                 try {
	                	 //info
	                	 int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + clickedName+ "?", "Deleting category..", JOptionPane.OK_CANCEL_OPTION);
	    	             if (option == JOptionPane.OK_OPTION)
	    	             {
	       
		                	 String sql = "DELETE FROM Categories WHERE CategoryID=?";
		                	 pst = conn.prepareStatement(sql);
		                	 
		                	 pst.setString(1, clickedId);
		                	 pst.execute();
		                	 System.out.println("Category deleted");
		                	 JOptionPane.showMessageDialog(null, "Category deleted! Please refresh the page.");
	    	             }//end if 
	    	             else {
	    	            	 System.out.println("Task has been cancelled.");
	    	             }//end else
	                	   	 
	                 }//end try
	                 
	                 catch(Exception ea) {
	                	 System.out.println(ea);
	                	 ea.getStackTrace();
	                 }//end catch
	                 

	           }//end mouseClicked
		});//end mouseAdapter
		
		JLabel lblNewLabel_1 = new JLabel("Click on a category to delete.");
		lblNewLabel_1.setBounds(6, 103, 253, 16);
		contentPane.add(lblNewLabel_1);
		
		JButton refreshButton = new JButton("Refresh"); //REFRESH
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.setIcon(new ImageIcon("/img/refresh.png"));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Categories refresh = new Categories();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});
		
		refreshButton.setBounds(6, 276, 143, 47);
		contentPane.add(refreshButton);
		
		JButton btnNewButton = new JButton("Menu"); //MENU
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton.setIcon(new ImageIcon("./img/back.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu menu = new Menu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});
		
		btnNewButton.setBounds(6, 6, 116, 47);
		contentPane.add(btnNewButton);
		
		categories_table();
	}//End constructor
	
	
	public void categories_table() {
		
	    try{
	    	String sql="SELECT * FROM Categories";
	    	pst=conn.prepareStatement(sql);
	    	rs=pst.executeQuery();
	    	table.setModel(DbUtils.resultSetToTableModel(rs));
	    	table.setDefaultEditor(Object.class, null); //MAKES TABLE NOT EDITABLE BY DOUBLE-CLICKING!
	    	table.setAutoCreateRowSorter(true); //AUTO SORTER
	    }//end try
	    catch(Exception e){
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }//end catch
	    finally{
	    	try{
	    		//CLOSE THE CONNECTIONS
	    		rs.close();
	    	    pst.close();
	    	         
	    	}//end try
	    	catch(Exception e){
	    		e.printStackTrace();   	
	    	}//end catch
	    }//end finally
	}//end categories_table();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Categories frame = new Categories();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
