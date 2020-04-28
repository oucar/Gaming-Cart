import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Orders extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public Orders() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 47, 838, 242);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton menuButton = new JButton("Menu"); //MENU
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu menu = new Menu();
				menu.setVisible(true);
			}
		});
		menuButton.setBounds(6, 6, 117, 29);
		contentPane.add(menuButton);
		
		
		JButton refreshButton = new JButton("Refresh"); //REFRESH
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					dispose();
					Orders refresh = new Orders();
					refresh.setVisible(true);
				}//end try
				catch(Exception aa) {
					aa.getStackTrace();
				}//end catch
				
			}
		});
		refreshButton.setBounds(6, 301, 117, 29);
		contentPane.add(refreshButton);
		
		JLabel dateLabel = new JLabel("{date}");
		dateLabel.setBounds(595, 28, 249, 16);
		Date date = new Date(); //java.util
		String dateString = date + ""; //converting it to the string format
		dateLabel.setText(dateString);
		contentPane.add(dateLabel);
		
		JLabel totalLabel = new JLabel("Total of sold items: $");
		totalLabel.setBounds(457, 304, 145, 16);
		contentPane.add(totalLabel);
		
		JLabel totalOfRefunded = new JLabel("Total of refunded items: $");
		totalOfRefunded.setBounds(457, 328, 164, 16);
		contentPane.add(totalOfRefunded);
		

		
		orders_table();
		stats_sold();
		stats_refund_approved();
	}
	
	//create the table
	public void orders_table() {
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	String sql="SELECT * FROM Orders";
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
	    		rs.close();
	    	    pst.close();
	    	         
	    	}//end try
	    	catch(Exception e){
	    		e.printStackTrace();   	
	    	}//end catch
	    }//end finally
	}//end orders_table();
	
	
	//print out some stats like the addition of total sold items
	public void stats_sold() {
		
		try {
			Statement st;
			st = conn.createStatement();
			String sql = "SELECT SUM(TotalAmount) FROM Orders WHERE Refund='NO' OR Refund='REQ'"; //total amount
			rs = st.executeQuery(sql);
			float totalSold = rs.getFloat(1);
			String totalSoldString = totalSold + ""; //Convert to string

			JLabel totalSoldLabel = new JLabel("{totalSold}");
			totalSoldLabel.setBounds(592, 304, 187, 16);
			totalSoldLabel.setText(totalSoldString);
			contentPane.add(totalSoldLabel);

			st.close();
			rs.close();

	    }//end try
		catch(Exception e){
	        e.getStackTrace();
		}

	}
	
	
	public void stats_refund_approved() throws SQLException {
		
		Statement pst;
		pst = conn.createStatement();
		String sql = "SELECT SUM(TotalAmount) FROM Orders WHERE Refund='APPROVED'"; //total amount of refunded
		rs = pst.executeQuery(sql);
		float totalRefunded = rs.getFloat(1);
		String totalRefundedString = totalRefunded + ""; //Convert to string
		
		
		JLabel refundedLabel = new JLabel("{totalRefunded}") ;
		refundedLabel.setBounds(623, 328, 130, 16);
		refundedLabel.setText(totalRefundedString);
		contentPane.add(refundedLabel);
		
		JButton saveLabel = new JButton("Save table as .txt");
		saveLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OrderOutput.output();
					JOptionPane.showMessageDialog(null, "Table saved as 'orders.txt'.");
				}catch(Exception eo) {
					eo.getStackTrace();
					
				}
			}
		});
		saveLabel.setBounds(6, 330, 145, 29);
		contentPane.add(saveLabel);
		
		pst.close();
		rs.close();
		
		
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orders frame = new Orders();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
