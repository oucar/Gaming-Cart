import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;

public class SoldItems extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JButton btnRefresh;
	private JLabel lblNewLabel_1;
	
	public SoldItems() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 101, 838, 192);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("Menu");
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
		btnNewButton.setBounds(17, 6, 110, 55);
		contentPane.add(btnNewButton);
		
		lblNewLabel = new JLabel("These are the items you sold so far:");
		lblNewLabel.setBounds(17, 73, 249, 16);
		contentPane.add(lblNewLabel);
		
		JLabel dateLabel = new JLabel("{date}"); //DATE
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setBounds(577, 317, 267, 16);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date(); //java.util
		String dateString = date + ""; //converting it to the string format
		dateLabel.setText(dateString);
		contentPane.add(dateLabel);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnRefresh.setIcon(new ImageIcon("./img/refresh.png"));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SoldItems refresh = new SoldItems();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});
		btnRefresh.setBounds(17, 301, 154, 49);
		contentPane.add(btnRefresh);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("/Users/onurucar/Desktop/PROJECT/img/time16.png"));
		lblNewLabel_1.setBounds(591, 317, 21, 16);
		contentPane.add(lblNewLabel_1);
		

		sold_table();
	}//end constructor
	
	
	//Create the table using rs2xml.jar
	public void sold_table() {
		
		Connection conn = DatabaseConnection.establishConn();
		ResultSet rs=null;
		PreparedStatement pst=null;
		
	    try{
	    	String sql="SELECT * FROM Orders WHERE Refund='NO' OR Refund='REQ'";
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
	}//end sold_table()

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SoldItems frame = new SoldItems();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
