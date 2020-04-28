import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

public class RefundRequests extends JFrame {
	
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;


	private JPanel contentPane;
	private JTable table;


	
	public void OrderTable() {
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	//String sql="SELECT * FROM Orders WHERE UserID='1'"; //TEST
	    	String sql="SELECT * FROM Orders WHERE UserID='"+LoggedIn.loggedInID+"'";
	    	pst=conn.prepareStatement(sql);
	    	rs=pst.executeQuery();
	    	table.setModel(DbUtils.resultSetToTableModel(rs));
	    	table.setDefaultEditor(Object.class, null); //MAKES TABLE NOT EDITABLE BY DOUBLE-CLICKING!
	    	table.setAutoCreateRowSorter(true); //AUTO SORTER
	    	}
	    	catch(Exception e){
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    	}
	    	finally{
	    	    try{
	    	        rs.close();
	    	        pst.close();
	    	         
	    	    }//end try
	    	    catch(Exception e){
	    	    	e.printStackTrace();
	    	    	
	    	    }//end catch
	    	}//end finally
	 
	}//end table function
	
	
	
	public RefundRequests(){
		
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 139, 827, 114);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
	    DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); //for refreshing the page.
	    

		
            JButton menuButton = new JButton("Menu");
            menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            menuButton.setIcon(new ImageIcon("./img/back.png"));
            menuButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
            		dispose();
            		CustomerMenu menu  = new CustomerMenu();
            		menu.setLocationRelativeTo(null);
            		menu.setVisible(true);
            	}
            });
            menuButton.setBounds(17, 6, 117, 47);
            contentPane.add(menuButton);
            
            JLabel infoLabel = new JLabel("Here is your previous orders. You can also request refund clicking on the ID.");
            infoLabel.setBounds(17, 111, 498, 16);
            contentPane.add(infoLabel);
            
            JLabel loggedInUsername = new JLabel("{username}");
            loggedInUsername.setBounds(763, 24, 81, 16);
            loggedInUsername.setText(LoggedIn.loggedInUsername);
            contentPane.add(loggedInUsername);
            
            JButton refreshButton = new JButton("Refresh");
            refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            refreshButton.setIcon(new ImageIcon("./img/refresh.png"));
            refreshButton.setHorizontalAlignment(SwingConstants.TRAILING);
            refreshButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
           		 dispose();
        		 
           		 RefundRequests refresh = new RefundRequests();
           		 refresh.setLocationRelativeTo(null);
           		 refresh.setVisible(true);
           		 
            	}
            });
            refreshButton.setBounds(17, 265, 117, 59);
            contentPane.add(refreshButton);
            
            JButton saveButton = new JButton("Save your orders as .txt");
            saveButton.setIcon(new ImageIcon("./img/save.png"));
            saveButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
            saveButton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
            		OrdersCustomerOutput.output();
    				JOptionPane.showMessageDialog(null, "Your transaction has been saved as "+LoggedIn.loggedInUsername +".txt.");
            	}
            });
            saveButton.setBounds(146, 265, 223, 56);
            contentPane.add(saveButton);
            
            JLabel lblNewLabel = new JLabel("Logged in as:");
            lblNewLabel.setBounds(669, 24, 89, 16);
            contentPane.add(lblNewLabel);
            
            JLabel lblNewLabel_1 = new JLabel("");
            lblNewLabel_1.setIcon(new ImageIcon("./img/user1.png"));
            lblNewLabel_1.setBounds(619, 0, 97, 70);
            contentPane.add(lblNewLabel_1);
            
            OrderTable();
            table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
            	
            	String clickedOrderID = (String) table.getValueAt(row, 0).toString();//ID of clicked order
            	
            	 int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to request refund for OrderID: "
            	 		+ " "+clickedOrderID+"?","Request Refund",JOptionPane.YES_NO_OPTION);
            	 
            	 if(confirmation==0) {
            		 try {
            			 
            			 
            				Connection conn = DatabaseConnection.establishConn();
            				ResultSet rs=null;
            				PreparedStatement pst=null;
            			 
            			 
            		 String sql = "UPDATE Orders SET Refund=? WHERE OrderID=?";
            		 pst = conn.prepareStatement(sql);

            		 pst.setString(1, "REQ");
            		 pst.setString(2, clickedOrderID);
            		 pst.executeUpdate();
            		 JOptionPane.showMessageDialog(null, "Your refund request has been sent!");

            		 }
            		 
            		 catch(Exception ee) {
            			 ee.printStackTrace(); 
            		 }
 
            	 }//end if
            	 else {	 
            	 }//end else
            }
        });
	}//end constructor
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					RefundRequests frame = new RefundRequests();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
