import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sqlite.util.StringUtils;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JInternalFrame;
import java.awt.Panel;
import java.awt.Font;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	//Connections
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rsl = null;
	PreparedStatement pstl = null;
	
	public static void main(String[] args) {
		
		Login frame = new Login();
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
								
			}
		});
	}
	

	public Login() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./img/login.png"));
		lblNewLabel.setBounds(290, 86, 80, 62);
		contentPane.add(lblNewLabel);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(6, 94, 80, 16);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(6, 132, 61, 16);
		contentPane.add(passwordLabel);
		
		JLabel signupLabel = new JLabel("New here?");
		signupLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signupLabel.setBounds(391, 156, 80, 16);
		contentPane.add(signupLabel);
		

		
		usernameField = new JTextField();
		usernameField.setBounds(98, 89, 170, 26);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		JButton signupButton = new JButton("");
		signupButton.setIcon(new ImageIcon("./img/signup.png"));
		signupButton.setBackground(UIManager.getColor("Button.select"));
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SignUp newFrame = new SignUp();
				newFrame.setLocationRelativeTo(null);
				newFrame.setVisible(true);
				
			}
		});
		signupButton.setBounds(382, 172, 100, 100);
		contentPane.add(signupButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(98, 127, 170, 26);
		contentPane.add(passwordField);
		
		//LOGIN 
		JButton loginButton = new JButton("Login");
		loginButton.setHorizontalAlignment(SwingConstants.RIGHT);
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";
				int entry = 0;
		
				 try {
			            pstl =conn.prepareStatement(sql);
			            pstl.setString(1, usernameField.getText()); //Reads data from jTextfields and prepares it for SQL

			            pstl.setString(2, passwordField.getText());

			            rsl= pstl.executeQuery();

			            //checks if user exists
			            
			            while(rsl.next()){
			            	String ID_Given = rsl.getString("UserID");
			            	String usernameGiven = rsl.getString("Username");
			            	String adminCheck = rsl.getString("adminCheck");
			            	//String adminCheck = rsl.getString("adminCheck");
			            	LoggedIn.loggedInUsername = usernameGiven;
			            	LoggedIn.loggedInID = ID_Given;
			            	LoggedIn.loggedInAdminCheck = adminCheck;
			            	entry+=1;
			            }//end while
			            //String adminCheck = "1";
			            //if user exsists
			            if (entry==1) {

			            	try {

			                   			                    
			            		//Admin LOGIN
			            		if(LoggedIn.loggedInAdminCheck.equals("1")) {
					            	dispose();
				                    Menu  menu = new Menu();
				                    menu.setVisible(true);
				                    
			            		}//end if
			            		
			            		//USER LOGIN	
			            		else {
				                    JOptionPane.showMessageDialog(null, "You're logging in.. Welcome, " + usernameField.getText());
					            	dispose();
					            	CustomerMenu cMenu = new CustomerMenu();
					            	cMenu.setLocationRelativeTo(null);
					            	cMenu.setVisible(true);
			            		}//end else


			            	}//end try
			            	
			            	
			            	catch(Exception v) {	
			            		v.getStackTrace();	
			            		//JOptionPane.showMessageDialog(null, "Something went wrong.");
			            		System.out.println(v);
			            		
			            	}//end catch

			            }//end if
			            
			            //If user records does not exist/match.
			            else if(entry==0) {
			            	JOptionPane.showMessageDialog(null, "Username or password is incorrect!");
			            }//end else

			     }//end try
				 
				 catch (Exception ex) {
			           ex.getStackTrace();
			           System.out.println(ex);
			     }//end catch
				 
				 finally{
			        try {
			        	//close the connections
			        	rsl.close();
			        	pstl.close();
			        }//end try
			        catch (SQLException ex) {
			        	JOptionPane.showMessageDialog(null, e);
			        }//end catch
			    }//end finally
			}
		});//END
		

		loginButton.setBounds(292, 94, 109, 50);
		contentPane.add(loginButton);
		
		JLabel nameField = new JLabel("Gaming Cart");
		nameField.setFont(nameField.getFont().deriveFont(nameField.getFont().getStyle() | Font.BOLD, nameField.getFont().getSize() + 8f));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setBounds(6, 19, 488, 34);
		contentPane.add(nameField);
		
		JLabel lblNewLabel_1 = new JLabel("Onur Ucar - CSCI24000 Final Project");
		lblNewLabel_1.setFont(new Font("Hiragino Sans", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(6, 256, 287, 16);
		contentPane.add(lblNewLabel_1);
		
	}//end constructor
	

}
