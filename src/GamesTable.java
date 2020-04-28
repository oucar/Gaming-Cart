import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class GamesTable extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	//CONNECTIONS
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	public GamesTable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//MENU
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		menuButton.setIcon(new ImageIcon("./img/back.png"));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu menu = new Menu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});
		menuButton.setBounds(6, 6, 118, 55);
		contentPane.add(menuButton);
		
		//DASHBOARD
		JButton dashboardButton = new JButton("Go back to game panel");
		dashboardButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		dashboardButton.setIcon(new ImageIcon("./img/editfolder.png"));
		dashboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Games games = new Games();
				games.setLocationRelativeTo(null);
				games.setVisible(true);
			}
		});
		dashboardButton.setBounds(148, 6, 255, 54);
		contentPane.add(dashboardButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 80, 834, 142);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		games_table();
	}
	
public void games_table(){
		
	    try{
	    	String sql="SELECT GameID, G_Name, G_Platform, G_Category, Metascore, Price, Stock FROM Games";
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
	 }//end games_table()
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GamesTable frame = new GamesTable();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
