import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordCField;
	
	Connection conn=DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./img/login2.png"));
		lblNewLabel.setBounds(342, 211, 61, 55);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("./img/register.png"));
		lblNewLabel_1.setBounds(354, 71, 49, 55);
		contentPane.add(lblNewLabel_1);
		
		usernameField = new JTextField();
		usernameField.setBounds(136, 47, 179, 26);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 85, 179, 26);
		contentPane.add(passwordField);
		
		passwordCField = new JPasswordField();
		passwordCField.setBounds(136, 123, 179, 26);
		contentPane.add(passwordCField);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(18, 52, 73, 16);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(18, 88, 61, 16);
		contentPane.add(passwordLabel);
		
		JLabel passwordCLabel = new JLabel("Confirm password");
		passwordCLabel.setBounds(18, 125, 130, 16);
		contentPane.add(passwordCLabel);
		
		
		//SIGN UP
		JButton signupButton = new JButton("Sign-up!");
		signupButton.setHorizontalAlignment(SwingConstants.RIGHT);
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String sql ="INSERT INTO Users (Username, Password, adminCheck) VALUES (?,?,?) ";
					boolean keepGoing = true;
				               
					pst = conn.prepareStatement(sql);
				               
					pst.setString(1, usernameField.getText());
					pst.setString(2, passwordField.getText());
					pst.setString(3, "0"); 
					
					if(usernameField.getText().equals("") || passwordField.getText().equals("") ) {
						JOptionPane.showMessageDialog(null,"Username and Password can't be empty.");
						System.out.println("user left one of the asked text fields empty.");
					}//end if
					
					else if(passwordCField.getText().equals(passwordField.getText())){
						
						try {
							System.out.println("1");
							pst.execute();
							System.out.println("2");
							JOptionPane.showMessageDialog(null, "Welcome to the jungle, "+usernameField.getText() +". :)");
							System.out.println("username: [" + usernameField.getText() + "] registered succesfully!");
							//JOptionPane.showMessageDialog(null, "User ["+ usernameField +"] registered succesfully!");
						}//end try
									
						catch (SQLException ex){
							System.out.println("3");
							JOptionPane.showMessageDialog(null, "Username already exists! Please choose another one.");
							System.out.println("Username already exists!");
							
						}
					}//end else if
					
					else {
						JOptionPane.showMessageDialog(null, "Passwords don't match!");
						System.out.println("Passwords don't match.");
					}//end else
					
				}//end try
				catch (Exception ew) {
					System.out.println("something went wrong");
				}//end catch
		        
			   finally {
				   try{
					   //connections
					   rs.close();
					   pst.close();
					   
				   }//end try
				   catch(Exception v){
					   
				   }//end catch
			   }//end finally
				
			}//end finally
		});
		signupButton.setBounds(342, 61, 141, 76);
		contentPane.add(signupButton);
		
		JButton loginButton = new JButton("Login page");
		loginButton.setHorizontalAlignment(SwingConstants.RIGHT);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Login newFrame = new Login();
				newFrame.setLocationRelativeTo(null);
				newFrame.setVisible(true);
				
			}
		});
		loginButton.setBounds(332, 207, 162, 65);
		contentPane.add(loginButton);
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}
