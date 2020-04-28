import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DatabaseConnection {
	static Connection conn=null;
	public static Connection establishConn() {
		
        try {
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:./resources/database.db");
            System.out.println("Connection status: Connected");
            System.out.println(" ");
            return conn;
            
        }//end try
        catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
              System.out.println("Connection status: Disconnected.");
              
              return null;
        }//end catch
        
	}//end function
}//end class
