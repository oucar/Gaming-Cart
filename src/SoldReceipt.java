import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class SoldReceipt {
	public static void output(String transactionNumber) {
		
		 try {	
			 	String fileName = transactionNumber + ".txt"; //Naming the file for each user
			    PrintWriter writer = new PrintWriter(fileName, "UTF-8"); 
				Connection conn = DatabaseConnection.establishConn();
				ResultSet rs=null;
				Statement st=null;
				
				Date date = new Date(); //DATE
				String dateString = date + ""; 
			 
			 st = conn.createStatement();
			 String sql = "SELECT * FROM Orders WHERE TransactionNumber='"+transactionNumber+"'";
			 //String sql = "SELECT * FROM Orders WHERE UserID='1'"; //Test
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
			writer.println("\n\n\n Date printed: " + dateString + "\n\n\n Thank you!");
			writer.close();
			
			
				
		  	 
		 }//end try 
		 catch(Exception ee) {
			 ee.printStackTrace();	 
		 }//end catch
		
	}

}
