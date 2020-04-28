import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import javax.swing.ImageIcon;
import java.awt.Font;

public class UserShoppingCart extends JFrame {

	private JPanel contentPane;
	
	//connections
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	private JTable table;

	
	public UserShoppingCart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//MENU BUTTON
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		menuButton.setIcon(new ImageIcon("./img/back.png"));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CustomerMenu menu = new CustomerMenu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});//end button
		
		menuButton.setBounds(6, 6, 117, 56);
		contentPane.add(menuButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 109, 838, 171);
		contentPane.add(scrollPane);
		
		//TABLE CLICKING OPERATIONS
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
            	
            	String clickedID = (String) table.getValueAt(row, 0).toString();//ID of clicked order
            	
            	 int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to remove [CartID:  "
            	 		+ ""+clickedID+"]?","Confirm Removal",JOptionPane.YES_NO_OPTION);
            	 
            	 if(confirmation==0) {
        
            		 try {
            			 

            			 String sql = "DELETE FROM Shopping_Cart WHERE CartID=? AND UserID=?";
            			 pst = conn.prepareStatement(sql);
            			 System.out.println(clickedID);

            			 pst.setString(1, clickedID);
            			 pst.setString(2, LoggedIn.loggedInID);
            			 pst.executeUpdate();
            			 JOptionPane.showMessageDialog(null, "Item has removed from your shopping cart, please refresh the page..");
            			 pst.close();
            		  	 
            		 }//end try 
            		 catch(Exception ee) {
            			 ee.printStackTrace();	 
            		 }//end catch
            	 }//end if
            	 
            	 //leave it as is
            	 else if(confirmation==1) {
            		 System.out.println("item still in the shopping cart.");
            	 }//end elif
            	
            	 else {
            		 System.out.println("Tab closed");
            	 }//end else
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Click on an item to remove it from your cart.");
		lblNewLabel.setBounds(6, 81, 566, 16);
		contentPane.add(lblNewLabel);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.setIcon(new ImageIcon("./img/refresh.png"));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserShoppingCart refresh = new UserShoppingCart();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});//end

		refreshButton.setBounds(6, 292, 136, 50);
		contentPane.add(refreshButton);
		
		shopping_cart_table();
	}//end constructor
	
	public void shopping_cart_table() {
		
		try{
	    	String sql="SELECT * FROM Shopping_Cart WHERE UserID=?";
	    	pst=conn.prepareStatement(sql);
	    	pst.setString(1, LoggedIn.loggedInID);
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
	    		rs.close();
	    	    pst.close();

	    	}//end try
	    	catch(Exception e){
	    		e.printStackTrace();	
	    	}//end catch
	    }
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserShoppingCart frame = new UserShoppingCart();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
