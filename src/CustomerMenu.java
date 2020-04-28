import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class CustomerMenu extends JFrame {

	private JPanel contentPane;
	
	//connections
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	public CustomerMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("./img/orders.png"));
		lblNewLabel_4.setBounds(16, 107, 99, 55);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("./img/password.png"));
		lblNewLabel_3.setBounds(4, 216, 77, 46);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("./img/exit.png"));
		lblNewLabel_2.setBounds(708, 5, 55, 55);
		contentPane.add(lblNewLabel_2);
		
		JLabel loggedInLabel = new JLabel("Logged in as:");
		loggedInLabel.setBounds(74, 22, 89, 16);
		contentPane.add(loggedInLabel);
		
		JLabel usernameLabel = new JLabel("{username}");
		usernameLabel.setBounds(161, 22, 77, 16);
		contentPane.add(usernameLabel);
		usernameLabel.setText(String.valueOf(LoggedIn.loggedInUsername).toString());

		//PLATFORM BUTTON
		JComboBox platformCombo = new JComboBox();
		platformCombo.setBounds(388, 233, 133, 27);
		contentPane.add(platformCombo);
		
		platformCombo.addItem("Any");
		try {
			Statement st;
			st = conn.createStatement();
			String sql = "SELECT * FROM Platforms";
			rs = st.executeQuery(sql);
	        	while(rs.next()){
	        		platformCombo.addItem(rs.getString("Platform_Name")); //fills up the combobox
	        	}//end while
	    }//end try
		catch(Exception e){
	        JOptionPane.showMessageDialog(null, "Error fetching platform names");
	        e.getStackTrace();
		}//end catch
		
		
		//CATEGORY BUTTON
		JComboBox categoryCombo = new JComboBox();
		categoryCombo.setBounds(548, 233, 137, 27);
		contentPane.add(categoryCombo);
		
		categoryCombo.addItem("Any");
		try {
			Statement st;
			st = conn.createStatement();
			String sql = "SELECT * FROM Categories";
			rs = st.executeQuery(sql);
	        	while(rs.next()){
	        		categoryCombo.addItem(rs.getString("CName"));//fills up the combobox
	        	}//end while
	    }//end try
		catch(Exception e){
	        JOptionPane.showMessageDialog(null, "Error fetching category names");
	        e.getStackTrace();
		}//end catch
		
		//SERACH BUTTON
		JButton searchButton = new JButton("Search!");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//platform selection
        		String platformFromCombo = platformCombo.getSelectedItem().toString();
        		GameSelection.selectedPlatform = platformFromCombo;
        		System.out.println(GameSelection.selectedPlatform + " selected!" );
        		
        		//category selection
         		String categoryFromCombo = categoryCombo.getSelectedItem().toString();
        		GameSelection.selectedCategory = categoryFromCombo;
	        	System.out.println(GameSelection.selectedCategory + " selected!" );
				
	        	dispose();
				Store store = new Store();
				store.setLocationRelativeTo(null);
				store.setVisible(true);
				
				try {
					//close the connections and resultset (which i used for jComboBox)
					pst.close();
					rs.close();
				}//end try
				catch(Exception eas) {
				}//end catch
			}//end actionEvent
		});//end ActionListener
		
		searchButton.setBounds(489, 286, 103, 38);
		contentPane.add(searchButton);
		

		JButton refundButton = new JButton("Your Orders"); //REFUNDS, YOUR ORDERS 
		refundButton.setHorizontalAlignment(SwingConstants.TRAILING);
		refundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RefundRequests request = new RefundRequests();
				request.setLocationRelativeTo(null);
				request.setVisible(true);
				dispose();
				
			}//end actionevent
		});//end actionListener
		
		refundButton.setBounds(6, 94, 195, 88);
		contentPane.add(refundButton);
		
		//Change password
		JButton passwordButton = new JButton("Change Password");
		passwordButton.setHorizontalAlignment(SwingConstants.TRAILING);
		passwordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ChangePassword change = new ChangePassword();
				change.setLocationRelativeTo(null);
				change.setVisible(true);
			}
		});//end button
		
		passwordButton.setBounds(6, 197, 195, 88);
		contentPane.add(passwordButton);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "You successfully logged out!");
				
				LoggedIn.loggedInID = null;
				LoggedIn.loggedInUsername = null;
	
				dispose();
				Login login = new Login();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				
			}//end actionevent
		});//end actionlistener
		
		
		btnNewButton.setBounds(698, 8, 133, 52);
		contentPane.add(btnNewButton);
		
		JButton yourCartTableButton = new JButton("");
		yourCartTableButton.setIcon(new ImageIcon("./img/cart.png"));
		yourCartTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserShoppingCart sc = new UserShoppingCart();
				sc.setLocationRelativeTo(null);
				sc.setVisible(true);
			}
		});
		yourCartTableButton.setBounds(570, 8, 116, 52);
		contentPane.add(yourCartTableButton);
		
		JLabel labelTry = new JLabel("Are you looking for some cool video games?");
		labelTry.setFont(new Font("Kohinoor Telugu", Font.PLAIN, 13));
		labelTry.setHorizontalAlignment(SwingConstants.CENTER);
		labelTry.setBounds(359, 181, 364, 16);
		contentPane.add(labelTry);
		
		JLabel lblNewLabel = new JLabel("Platform                             Category");
		lblNewLabel.setBounds(413, 209, 234, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("./img/user1.png"));
		lblNewLabel_1.setBounds(26, 5, 55, 55);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setIcon(new ImageIcon("./img/controller.png"));
		lblNewLabel_5.setBounds(489, 258, 103, 27);
		contentPane.add(lblNewLabel_5);
		
	}//end constructor
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMenu frame = new CustomerMenu();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
