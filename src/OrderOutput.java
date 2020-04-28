import java.io.*;
import java.sql.*;
import java.util.Date;


public class OrderOutput {
	
	public static void output() {
		 try {	
			    PrintWriter writer = new PrintWriter("orders.txt", "UTF-8");
				Connection conn = DatabaseConnection.establishConn();
				ResultSet rs=null;
				Statement st=null;
				
				Date date = new Date(); //DATE
				String dateString = date + ""; 
			 
			 st = conn.createStatement();
			 String sql = "SELECT * FROM Orders";
			 rs = st.executeQuery(sql);
			 
			 int i = 0;

			while(rs.next()){
				i++;
				
				writer.print(i+"- ");
				writer.print(" ");
				writer.print(rs.getString("OrderID"));
				writer.print(" ");
				writer.print(rs.getString("UserID"));
				writer.print(" ");
				writer.print(rs.getString("TransactionNumber"));
				writer.print(" ");
				writer.print(rs.getString("ReceiptDate"));
				writer.print(" ");
				writer.print(rs.getString("TotalAmount"));
				writer.print(" ");
				writer.print(rs.getString("GameID"));
				writer.print(" ");
				writer.print(rs.getString("Refund"));
				writer.println("");
					
     	}//end while
			writer.println("\n\n\n Date printed: " + dateString);
			writer.close();
		  	 
		 }//end try 
		 catch(Exception ee) {
			 ee.printStackTrace();	 
		 }//end catch
		
	}
	
}