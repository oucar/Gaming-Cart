
public class GamesInTable {
		
		//definitons
	  	private String GameID;
	    private String G_Name;
	    private String G_Platform;
	    private String G_Category;
	    private String Metascore;
	    private float Price;
	    private int Stock;
	    private byte[] Image;
	    
	    public GamesInTable(){}
	    
	    public GamesInTable(String gameId, String gameName, String gamePlatform, String gameCategory, String metascore, float price, int stock, byte[] image){
	    
	    	this.GameID = gameId;
	    	this.G_Name = gameName;
	    	this.G_Platform = gamePlatform;
	    	this.G_Category = gameCategory;
	    	this.Metascore = metascore;
	    	this.Price = price;
	    	this.Stock = stock;
	    	this.Image = image;
	       
	    }
	    
	    
	    public String getGameID(){
	      return GameID;
	    }
	    
	    public void setGameID(String gameId){
	        this.GameID = gameId;
	    }
	    
	    public String getG_Name(){
	        return G_Name;
	    }
	    
	    public void setG_Name(String gameName){
	        this.G_Name = gameName;
	    }
	    
	    public String getG_Platform(){
	        return G_Platform;
	    }
	    
	    public void setG_Platform(String gamePlatform){
	        this.G_Platform = gamePlatform;
	    }
	    
	    public String getG_Category(){
	        return G_Category;
	    }
	    
	    public void setG_Category(String gameCategory){
	        this.G_Category = gameCategory;
	    }
	    
	    public String getMetascore(){
	        return Metascore;
	    }
	    
	    public void setMetascore(String metascore){
	        this.Metascore = metascore;
	    }
	    
	    public float getPrice(){
	        return Price;
	    }
	    
	    public void setPrice(float price){
	        this.Price = price;
	    }
	    
	    public int getStock() {
	    	return Stock; 
	    }
	    
	    public void setStock(int stock) {
	    	this.Stock = stock;
	    }
 
	    public byte[] getImage(){
	        return Image;
	    }
}


