import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Refunds extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton refreshButton;
	private JLabel infoLabel;
	private JLabel loginInfoLabel;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JLabel dateLabel;
	
	//CONNECTIONS
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	

	public Refunds() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 108, 838, 133);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		//REFRESH
		refreshButton = new JButton("Refresh");
		refreshButton.setIcon(new ImageIcon("./img/refresh.png"));
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Refunds refresh = new Refunds();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});//END
		
		refreshButton.setBounds(6, 248, 122, 50);
		contentPane.add(refreshButton);
		
		infoLabel = new JLabel("Click on an entry to update its status.");
		infoLabel.setBounds(6, 80, 343, 16);
		contentPane.add(infoLabel);
		
		loginInfoLabel = new JLabel("Logged in as: *ADMIN* ");
		loginInfoLabel.setBounds(600, 26, 148, 16);
		contentPane.add(loginInfoLabel);
		
		lblNewLabel = new JLabel("{username}");
		lblNewLabel.setIcon(new ImageIcon("./img/user1.png"));
		lblNewLabel.setBounds(549, 6, 50, 57);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(LoggedIn.loggedInUsername); //Logged in as..
		
		//MENU
		btnNewButton = new JButton("Menu");
		btnNewButton.setIcon(new ImageIcon("./img/back.png"));
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Menu menu = new Menu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});//END BUTTON
		
		btnNewButton.setBounds(6, 6, 108, 57);
		contentPane.add(btnNewButton);
		
		//DATE!
		dateLabel = new JLabel("{date}");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setBounds(561, 253, 283, 16);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); //DATE
		Date date = new Date(); //java.util
		String dateString = date + ""; //converting it to the string format
		dateLabel.setText(dateString);
		contentPane.add(dateLabel);
		
		JLabel timeIcon = new JLabel("");
		timeIcon.setIcon(new ImageIcon("/Users/onurucar/Desktop/PROJECT/img/time16.png"));
		timeIcon.setBounds(583, 254, 16, 16);
		contentPane.add(timeIcon);
		
		JLabel usernameLabel = new JLabel("{username}");
		usernameLabel.setBounds(747, 26, 97, 16);
		usernameLabel.setText(LoggedIn.loggedInUsername);
		contentPane.add(usernameLabel);
		
		UIManager.put("OptionPane.yesButtonText", "Yes.");
		UIManager.put("OptionPane.noButtonText", "No.");
		
		
		//TABLE OPERATIONS
		
		refund_table();
		
		//APPROVE OR REJECT
		table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
            	
            	String clickedOrderID = (String) table.getValueAt(row, 0).toString();//ID of clicked order
            	String clickedId = (String) table.getValueAt(row, 5).toString();
            	
            	 int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to confirm the refund request for [OrderID:  "
            	 		+ ""+clickedOrderID+"]?","Confirm Refund",JOptionPane.YES_NO_OPTION);
            	 
            	 if(confirmation==0) {
        
            		 try {
            			 
            			 StockOperations.refunded(clickedId);
                   		 Connection conn = DatabaseConnection.establishConn();
                	     PreparedStatement pst=null;

            			 String sql = "UPDATE Orders SET Refund=? WHERE OrderID=?";
            			 pst = conn.prepareStatement(sql);
            			 System.out.println(clickedOrderID);

            			 pst.setString(1, "APPROVED");
            			 pst.setString(2, clickedOrderID);
            			 pst.executeUpdate();
            			 JOptionPane.showMessageDialog(null, "Refund has been approved for OrderID: "+clickedOrderID+".");
            			 pst.close();
            		  	 
            		 }//end try 
            		 catch(Exception ee) {
            			 ee.printStackTrace();	 
            		 }//end catch
            	 }//end if
            	 
            	 else if(confirmation==1) {
            		 
            		 try {
                    	//Connection conn = DatabaseConnection.establishConn();
                    	//PreparedStatement pst=null;

            			 String sql = "UPDATE Orders SET Refund=? WHERE OrderID=?";
            			 pst = conn.prepareStatement(sql);

            			 pst.setString(1, "REJECTED");
            			 pst.setString(2, clickedOrderID);
            			 pst.executeUpdate();
            			 JOptionPane.showMessageDialog(null, "Refund has been rejected for OrderID: "+clickedOrderID+".");
            			 pst.close();
            		  	 
            		 }//end try 
            		 catch(Exception ee) {
            			 ee.printStackTrace();	 
            		 }//end catch

            	 }//end elif
            	
            	 else {
            		 System.out.println("Tab closed");
            	 }//end else
            }
       });
	}//end constructor
	
	public void refund_table(){
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	String sql="SELECT * FROM Orders WHERE Refund='REQ'";
	    	pst=conn.prepareStatement(sql);
	    	rs=pst.executeQuery();
	    	table.setModel(DbUtils.resultSetToTableModel(rs));
	    	table.setDefaultEditor(Object.class, null); //MAKES TABLE NOT EDITABLE BY DOUBLE-CLICKING!
	    	table.setAutoCreateRowSorter(true); //AUTO SORTER
    		rs.close();
    	    pst.close();
	    }//end try
	    catch(Exception e){
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }//end catch
	    finally{
	    	try{
	    		

  	         
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();	
	    	}
	    }
	 }//end refund_table()
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Refunds frame = new Refunds();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
