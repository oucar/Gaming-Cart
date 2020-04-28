import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Menu extends JFrame {
	
	private JPanel contentPane;

	public Menu() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel loggedInInfo = new JLabel("Logged in as: ");
		loggedInInfo.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		loggedInInfo.setBounds(53, 20, 117, 16);
		contentPane.add(loggedInInfo);
		
		//GAMES
		JButton gamesButton = new JButton("Games");
		gamesButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		gamesButton.setIcon(new ImageIcon("./img/game.png"));
		gamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Games game = new Games();
				game.setLocationRelativeTo(null);
				game.setVisible(true);
			}
		});
		gamesButton.setBounds(6, 77, 172, 85);
		contentPane.add(gamesButton);
		
		//Platforms
		JButton platformButton = new JButton("Platforms");
		platformButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		platformButton.setIcon(new ImageIcon("./img/controllerhd.png"));
		platformButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Platforms platform = new Platforms();
				platform.setLocationRelativeTo(null);
				platform.setVisible(true);
			}
		});
		platformButton.setBounds(6, 174, 172, 85);
		contentPane.add(platformButton);
		
		//Categories
		JButton categoriesButton = new JButton("Categories");
		categoriesButton.setIcon(new ImageIcon("./img/category.png"));
		categoriesButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		categoriesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Categories category = new Categories();
				category.setLocationRelativeTo(null);
				category.setVisible(true);
			}
		});
		categoriesButton.setBounds(6, 271, 172, 85);
		contentPane.add(categoriesButton);
		
		//Sold Items
		JButton soldButton = new JButton("Sold items");
		soldButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		soldButton.setIcon(new ImageIcon("./img/paid80.png"));
		soldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SoldItems sold = new SoldItems();
				sold.setLocationRelativeTo(null);
				sold.setVisible(true);
			}
		});
		soldButton.setBounds(235, 271, 232, 85);
		contentPane.add(soldButton);
		
		//Refunds
		JButton refundButton = new JButton("Refunds Pending");
		refundButton.setIcon(new ImageIcon("./img/inspect.png"));
		refundButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Refunds refunds = new Refunds();
				refunds.setLocationRelativeTo(null);
				refunds.setVisible(true);

			}
		});
		refundButton.setBounds(235, 77, 232, 85);
		contentPane.add(refundButton);
		
		//Stork
		JButton stockButton = new JButton("Stock");
		stockButton.setIcon(new ImageIcon("./img/stock.png"));
		stockButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		stockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Stock stock = new Stock();
				stock.setLocationRelativeTo(null);
				stock.setVisible(true);
			}
		});
		stockButton.setBounds(529, 175, 153, 85);
		contentPane.add(stockButton);
		
		JLabel usernameLabel = new JLabel("{username}");
		usernameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		usernameLabel.setBounds(147, 20, 142, 16);
		contentPane.add(usernameLabel);
		
		usernameLabel.setText(String.valueOf(LoggedIn.loggedInUsername).toString());
		
		//Logout
		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		logoutButton.setIcon(new ImageIcon("./img/exit.png"));
		logoutButton.setHorizontalAlignment(SwingConstants.TRAILING);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LoggedIn.loggedInID = null;
				LoggedIn.loggedInUsername = null;
				JOptionPane.showMessageDialog(null, "You successfully logged out!");
				dispose();
				Login login = new Login();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
			}
		});
		logoutButton.setBounds(715, 3, 129, 50);
		contentPane.add(logoutButton);
		
		JButton btnNewButton = new JButton("Refunded items");
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton.setIcon(new ImageIcon("./img/refunded80.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Approved item = new Approved();
				item.setLocationRelativeTo(null);
				item.setVisible(true);
			}
		});
		btnNewButton.setBounds(235, 174, 232, 85);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./img/user1.png"));
		lblNewLabel.setBounds(6, 6, 56, 50);
		contentPane.add(lblNewLabel);
		

	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
