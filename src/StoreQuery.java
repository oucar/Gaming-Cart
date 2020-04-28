import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StoreQuery {
	
	//creates and arraylist, and then insert them into jTable.
	//The reason why I didn't prefer to use rs2xml or regular table structure is,
	//It is wore difficult to insert blob data type and fetch it back.
	//this makes it easier to use SQL codes (Otherwise it keeps giving error after the category and platform selections)
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	
    public ArrayList<GamesInTable> BindTable(){
        
   ArrayList<GamesInTable> list = new ArrayList<GamesInTable>();

   
   try {
	   
	   //ANY ANY FROM CUSTOMERMENU 
	   if(GameSelection.selectedCategory.equals("Any") && GameSelection.selectedPlatform.equals("Any")) {
		  String sql ="SELECT * FROM Games";
		  pst = conn.prepareStatement(sql);
		  System.out.println(GameSelection.selectedCategory + GameSelection.selectedPlatform);
	   }//end if
	   
	   //VALUE ANY FROM CUSTOMER MENU
	   else if (!GameSelection.selectedCategory.equals("Any") && GameSelection.selectedPlatform.equals("Any")) {
		   String sql ="SELECT * FROM Games WHERE G_Category='"+GameSelection.selectedCategory+"'";
			pst = conn.prepareStatement(sql);
			System.out.println(GameSelection.selectedCategory + GameSelection.selectedPlatform);
		   
	   }//end elif
	   
	   //ANY ANY FROM CUSTOMER MENU
	   else if (GameSelection.selectedCategory.equals("Any") && !GameSelection.selectedPlatform.equals("Any")) {
		   
		   String sql ="SELECT * FROM Games WHERE G_Platform='"+GameSelection.selectedPlatform+"'";
			pst = conn.prepareStatement(sql);
			System.out.println(GameSelection.selectedCategory + GameSelection.selectedPlatform);
		   
	   }//end elif
	   
	   
	   //VALUE VALUE FROM CUSTOMER MENU
	   
	   else if (!GameSelection.selectedCategory.equals("Any") && !GameSelection.selectedPlatform.equals("Any")) {
		   String sql ="SELECT * FROM Games WHERE G_Category='"+GameSelection.selectedCategory+"' AND G_Platform='"+GameSelection.selectedPlatform+"'";
			pst = conn.prepareStatement(sql);
			System.out.println(GameSelection.selectedCategory + GameSelection.selectedPlatform);  
	   }//end elif

	   //pst = conn.createStatement();
	   rs = pst.executeQuery();
   
	   GamesInTable sq;
	   while(rs.next()){
		   sq = new GamesInTable(
		   rs.getString("GameID"),
		   rs.getString("G_Name"),
		   rs.getString("G_Platform"),
		   rs.getString("G_Category"),
		   rs.getString("Metascore"),
		   rs.getFloat("Price"),
		   rs.getInt("Stock"),
		   rs.getBytes("Image")
		   );
		   list.add(sq);
	   }//end while
   
	   }//end try 
   	   catch (SQLException ex) {
   		   ex.getStackTrace();
	   }//end catch
	   return list;
	   }
	}