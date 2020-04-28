import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime; //LocalDateTime
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ShoppingCart extends JFrame {
	//Conenctions
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	ResultSet rs2=null;
	PreparedStatement pst=null;
	PreparedStatement pst2=null;

	private JPanel contentPane;

	private void finalize_stock() throws SQLException {
		
		Connection connect=DatabaseConnection.establishConn();
		PreparedStatement pstStock=null;
		String sql2 = "UPDATE Games SET Stock=? WHERE GameID=?";
		pstStock = connect.prepareStatement(sql2);
		pstStock.setInt(1, StockOperations.inStock);
		pstStock.setString(2, StockOperations.id);
		pstStock.executeUpdate();
		pstStock.close();
		
	}//end finalize stock
	
	public ShoppingCart() throws SQLException {
		
		Statement st;
		st = conn.createStatement();

		String sql = "SELECT * FROM Shopping_Cart WHERE UserID='"+LoggedIn.loggedInID+"'";
		rs = st.executeQuery(sql);
		
		//UIManager.put("OptionPane.yesButtonText", "I want it!");
		//UIManager.put("OptionPane.noButtonText", "I think I'll pass.");
		
		String a;
		int price = 0;
		int addition = 0;
		

				
		while(rs.next()) {
			
			price = rs.getInt("Price");
			a = rs.getString("GameID");
			StockOperations.id = a;
			String gameName = rs.getString("G_Name");

			System.out.println(price); //see how it goes in the terminal.
			addition +=price;
			int option = JOptionPane.showConfirmDialog(null,"Game: [ID:"+a+", "+gameName + "] for $"+price, "Virtual Cashier",JOptionPane.YES_NO_OPTION);	
			//pst.close();
			//rs.close();
			//IF USER CLICKS YES, Add it to Orders, remove it from user's shopping cart.
			if(option == 0) {
				System.out.println("User decided to buy the item.");
				try {

	                LocalDateTime date = LocalDateTime.now();
	                LocalDate date1 = LocalDate.now();
	                String todaysDate = date1 + "";
	                float totalForOrders = (float) (addition + addition*0.07);
	                String totalForOrdersS = totalForOrders + "";

	                String transaction = LoggedIn.loggedInUsername + date  ; // onurucar127.03.2000 etc..
	                //StockOperations.sold();
	                
	                StockOperations.inStock = StockOperations.inStock - 1; //?
	                
	                String sql2 = "INSERT INTO Orders (UserID, TransactionNumber, ReceiptDate, TotalAmount, GameID, Refund) VALUES(?,?,?,?,?,?)";
	                pst2 = conn.prepareStatement(sql2);
	                
	        		pst2.setString(1, LoggedIn.loggedInID); //UserID
	        		pst2.setString(2, transaction); //TransactionNumber
                	pst2.setString(3, todaysDate); //ReceiptDate
                	pst2.setString(4, totalForOrdersS); //TotalAmount 
                	pst2.setString(5, a);//GameID
                	pst2.setString(6, "NO");
                	pst2.execute();
                	pst2.close();
                	
                	SoldReceipt.output(transaction); //Prints the receipt as {transactionNumber}.txt 
					
					String sql1 ="DELETE from Shopping_Cart WHERE GameID=? AND UserID=?";
	                pst=conn.prepareStatement(sql1);
	                pst.setString(1, a);
	                pst.setString(2, LoggedIn.loggedInID);
	                pst.execute();
	                pst.close();
	                
	                //if price is not empty, execute.
	
				}//end try 
				catch(Exception e) {
					System.out.println(e);
					
				}//end catch		
				
			}//end if
			//Remove it from the shopping cart.
			else {
				System.out.println("User cancelled the operation");
			}//end else
		
		}//end while
		
        float tax = (float) (addition*(0.07));
        float totalAmount = tax + addition;
        JOptionPane.showMessageDialog(null,"Your total is :$"+totalAmount+"   -"+     "Items: $" + addition + "    Tax: $" +tax +" (7% in Indiana)");
		System.out.println(addition);
		rs.close();
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//MAIN MENU
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		menuButton.setIcon(new ImageIcon("./img/home.png"));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				CustomerMenu menu = new CustomerMenu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);

				
			}
		});
		menuButton.setBounds(288, 106, 274, 165);
		contentPane.add(menuButton);
		
		finalize_stock();
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ShoppingCart frame = new ShoppingCart();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
