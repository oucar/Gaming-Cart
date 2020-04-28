import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Stock extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public Stock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 96, 838, 179);
		contentPane.add(scrollPane);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.setIcon(new ImageIcon(".img/refresh.png"));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //REFRESH
				
				dispose();
				Stock stock = new Stock();
				stock.setLocationRelativeTo(null);
				stock.setVisible(true);
			}
		});
		refreshButton.setBounds(6, 287, 130, 48);
		contentPane.add(refreshButton);
		
		//Table operations
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Click on a game to update its quantitiy in stock.");
		lblNewLabel.setBounds(6, 66, 323, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Menu");
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
		btnNewButton.setBounds(6, 6, 130, 48);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Note: '5' adds 5 to stock, '-5' removes 5 from the stock.");
		lblNewLabel_1.setBounds(465, 302, 365, 16);
		contentPane.add(lblNewLabel_1);
		
		stock_table();
		
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
            	
            	String clickedGameID = (String) table.getValueAt(row, 0).toString();//ID of clicked entry
            	String clickedOrderStock = (String) table.getValueAt(row, 6).toString();
            	
            	System.out.println(clickedGameID + " "+clickedOrderStock);
            	
            	int inStock = Integer.parseInt(clickedOrderStock);
            	
            	
            	int numberGiven = Integer.parseInt(JOptionPane.showInputDialog(null, "Adding stock of [GameID: "+clickedGameID+"]"));
            	 
        
            		 try {
            			 
            			 int totalStock = (numberGiven+inStock);
            			 
            			 if(totalStock <= 0) {
            				 JOptionPane.showMessageDialog(null, "Quantity cannot be less than 0.");
            			 }//end if
            			 else {
            			 
            				 Connection conn = DatabaseConnection.establishConn();
            				 PreparedStatement pst=null;

            				 String sql = "UPDATE Games SET Stock=? WHERE GameID=?";
            				 pst = conn.prepareStatement(sql);

            				 pst.setInt(1, totalStock);
            				 pst.setString(2, clickedGameID);
            				 pst.executeUpdate();
            				 JOptionPane.showMessageDialog(null, "Stock has been updated for [GameID: "+clickedGameID+"].\n"+totalStock+" in stock");
            				 pst.close();
            			 }//end else
            		  	 
            		 }//end try 
            		 catch(Exception ee) {
            			 ee.printStackTrace();	 
            		 }//end catch
            }

        });
		
		
	}//end constructor
	
	
	public void stock_table() {
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	String sql="SELECT GameID, G_Name, G_Platform, G_Category, Metascore, Price, Stock FROM Games";
	    	pst=conn.prepareStatement(sql);
	    	rs=pst.executeQuery();
	    	table.setModel(DbUtils.resultSetToTableModel(rs));
	    	table.setDefaultEditor(Object.class, null); //MAKES TABLE NOT EDITABLE BY DOUBLE-CLICKING!
	    }//end try
	    catch(Exception e){
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }//end catch
	    finally{
	    	try{
	    		rs.close();
	    	    pst.close();
	    	         
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	    	
	    	}
	    }
	 }//end stock_table()
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stock frame = new Stock();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
