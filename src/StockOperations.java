import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockOperations {
	
	static Connection connect=DatabaseConnection.establishConn();
	static PreparedStatement pstStock=null;
	
	public static int inStock;
	public static String id; 

	public static void sold() throws SQLException {
		
		int newStock = inStock - 1;
		
		String sql = "UPDATE Games SET STOCK=? WHERE GameID=?";
		pstStock = connect.prepareStatement(sql);
		pstStock.setInt(1, newStock);
		pstStock.setString(2, id);
		pstStock.executeUpdate();
		pstStock.close();
		
		
	}//end sold
	
	public static void refunded(String x) throws SQLException {
		
		String sql = "UPDATE Games SET STOCK=STOCK+1 WHERE GameID=?";
		pstStock = connect.prepareStatement(sql);
		pstStock.setString(1, x);
		pstStock.executeUpdate();
		pstStock.close();
				
	}//end refunded

}//end class
