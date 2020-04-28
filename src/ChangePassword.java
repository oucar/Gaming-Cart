import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JTextField oldPasswordField;
	private JTextField newPasswordField;
	private JTextField newPasswordCField;
	
	//Connections
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs = null;
	PreparedStatement pst = null;

	//constructor
	public ChangePassword() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//MENU
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		menuButton.setIcon(new ImageIcon("/Users/onurucar/Desktop/PROJECT/img/back.png"));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CustomerMenu menu = new CustomerMenu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});//END BUTTON
		
		menuButton.setBounds(7, 6, 117, 48);
		contentPane.add(menuButton);
		
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBounds(231, 102, 130, 26);
		contentPane.add(oldPasswordField);
		oldPasswordField.setColumns(10);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(231, 132, 130, 26);
		contentPane.add(newPasswordField);
		newPasswordField.setColumns(10);
		
		newPasswordCField = new JPasswordField();
		newPasswordCField.setBounds(231, 160, 130, 26);
		contentPane.add(newPasswordCField);
		newPasswordCField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Old password:");
		lblNewLabel.setBounds(123, 107, 117, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New password:");
		lblNewLabel_1.setBounds(108, 137, 117, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Confirm new password:");
		lblNewLabel_2.setBounds(73, 165, 167, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Change your password");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(7, 73, 487, 16);
		contentPane.add(lblNewLabel_3);
		
		//CHANGE BUTTON
		JButton changeButton = new JButton("Change it!");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
				
				try {
					String sql = "SELECT UserID, Username, Password FROM Users WHERE UserID='"+LoggedIn.loggedInID+"'";
					pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					
					while(rs.next()) {
						LoggedIn.loggedInPassword = rs.getString("Password");
					}

		            rs.close();
		            pst.close();
				}
					catch(Exception ea) {
						ea.getStackTrace();
						System.out.println(ea + " 1");
						
					}//end catch
				
				
				//new connection
				Connection conn2 = DatabaseConnection.establishConn();
				ResultSet rs2 = null;
				PreparedStatement pst2 = null;
				
				
		           // String passwordGiven = "Asdasd";
		            //check if givenPassword and old password match
		            if(oldPasswordField.getText().equals(LoggedIn.loggedInPassword)) {
		            	
		            	//check if new passwords match
		            	if(newPasswordCField.getText().equals(newPasswordField.getText())) {
		            		
		            		//check if new passwords are empty or not 
		            		if(!newPasswordCField.getText().equals("")) {			
		            			//Change the password
		            			try {
			            			String newSql = "UPDATE Users SET Password='"+newPasswordField.getText()+"' WHERE Username='"+LoggedIn.loggedInUsername+"'";
			            			pst2 = conn2.prepareStatement(newSql);
			            			pst2.executeUpdate();
			            			pst2.close();
			            			JOptionPane.showMessageDialog(null, "Password changed!");
		            			}
		            			catch(Exception asd) {
		            				System.out.println(asd);
		            				asd.getStackTrace();
		            			}
		            		}//end if
		            		else {
		            			JOptionPane.showMessageDialog(null, "Your new password cannot be empty!");
		            		}//end else	
		            	}//end if
		            	else {
		            		JOptionPane.showMessageDialog(null, "Your new passwords don't match!");
		            	}//end else
		            }
		            else {
		            	JOptionPane.showMessageDialog(null, "Your old password is incorrect!");
		            	}//end else

			}
		});
		changeButton.setBounds(206, 205, 117, 29);
		contentPane.add(changeButton);
	}//end constructor
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
