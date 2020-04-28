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

public class Approved extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton refreshButton;
	private JLabel infoLabel;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JLabel dateLabel;
	
	//CONNECTIONS
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	private JLabel timeIcon;
	

	public Approved() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 102, 839, 133);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		//REFRESH
		refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.setIcon(new ImageIcon("./img/refresh.png"));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Approved refresh = new Approved();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});//END
		
		refreshButton.setBounds(7, 247, 139, 50);
		contentPane.add(refreshButton);
		
		infoLabel = new JLabel("These are the items customers refunded.");
		infoLabel.setBounds(7, 74, 343, 16);
		contentPane.add(infoLabel);
		
		lblNewLabel = new JLabel("{username}");
		lblNewLabel.setBounds(604, 6, 69, 16);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(LoggedIn.loggedInUsername); //Logged in as..
		
		//MENU
		btnNewButton = new JButton("Menu");
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton.setIcon(new ImageIcon("//img/back.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Menu menu = new Menu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});//END BUTTON
		
		btnNewButton.setBounds(6, 6, 113, 50);
		contentPane.add(btnNewButton);
		
		//DATE!
		dateLabel = new JLabel("{date}");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setBounds(562, 247, 283, 16);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); //DATE
		Date date = new Date(); //java.util
		String dateString = date + ""; //converting it to the string format
		dateLabel.setText(dateString);
		contentPane.add(dateLabel);
		
		timeIcon = new JLabel("");
		timeIcon.setIcon(new ImageIcon("/Users/onurucar/Desktop/PROJECT/img/time16.png"));
		timeIcon.setBounds(583, 247, 24, 16);
		contentPane.add(timeIcon);

		//TABLE OPERATIONS
		
		approved_table();
		
	}
	
	public void approved_table(){
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	String sql="SELECT * FROM Orders WHERE Refund='APPROVED'";
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
	    	e.getStackTrace();
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
