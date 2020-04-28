import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;


public class Store extends javax.swing.JFrame {

	//CONNECTIONS
	Connection conn=DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	//CONSTRUCTOR
    public Store() {

        initialize();
        store_table();
    }
    //Create the table
    public void store_table(){
    	
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

        StoreQuery mq = new StoreQuery();
        ArrayList<GamesInTable> list = mq.BindTable();
        String[] columnName = {"ID","Name","Platform","Category","Score","Price","Stock", "Image"};
        

        Object[][] rows = new Object[list.size()][8];
        int c=0;
        for(int i = 0; i < list.size(); i++){
        	
        	
        	//HIDING ROW OPERATIONS!!!!									   
        	
        	//if(GameSelection.selectedCategory.equals(list.get(i).getG_Category()) && GameSelection.selectedPlatform.equals(list.get(i).getG_Platform())){
        	
        	rows[i][0] = list.get(i).getGameID();
        	rows[i][1] = list.get(i).getG_Name();
            rows[i][2] = list.get(i).getG_Platform();
            rows[i][3] = list.get(i).getG_Category();
            rows[i][4] = list.get(i).getMetascore();
            rows[i][5] = list.get(i).getPrice();
            rows[i][6] = list.get(i).getStock();
            
     
            System.out.println(list.get(i).getG_Name());
            if(list.get(i).getImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getImage()).getImage().getScaledInstance(90, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][7] = image;
            }//end if
            else{
                rows[i][7] = null;
            }//end else 
    }//end for
        
        //ALLIGNMENTS
        
        ImageInTable model = new ImageInTable(rows, columnName);
        jTable1.setModel(model);
        jTable1.setRowHeight(100);
       
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
        
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(15);
       
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(20);
        jTable1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
    }//end constructor
    @SuppressWarnings("unchecked")
    private void initialize() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTable1.setAutoCreateRowSorter(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            } 
        ));
        
        //Making rows and cells clickable 
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int col = jTable1.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                	
                	    if(jTable1.columnAtPoint(evt.getPoint()) == 0){
                		//System.out.println(jTable1.getValueAt(row,col).toString());
                		//System.out.println(jTable1.getValueAt(row, 1).toString());
                		String clickedGame = (String) jTable1.getValueAt(row,1).toString(); //Clicked game name
                		String clickedID = (String) jTable1.getValueAt(row, 0).toString();//ID of clicked game
                		String price = (String) jTable1.getValueAt(row, 5).toString();//Get price
                		
                		int clickedStock = (int) jTable1.getValueAt(row, 6); 
                		//STOCK OPERATIONS!
                    
					   int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to add "+clickedGame+" to your shopping cart?","Adding to shopping cart...",JOptionPane.YES_NO_OPTION);
				       //Add to shopping cart
					   if(confirmation==0) {
						   StockOperations.id = clickedGame;
						   StockOperations.inStock = clickedStock;
						   	System.out.println(GameSelection.selectedPlatform+" "+GameSelection.selectedCategory );			   
							   try {
								   //if there is 0 ... in the stock..
								   if(clickedStock==0) {   
									   JOptionPane.showMessageDialog(null, "We currently out of stock of this game.");
								   }//end if
								   //else, execute
								   else {
									   
									 //  StockOperations.sold();
								   
								   	   String sql = "INSERT INTO Shopping_Cart(GameID, G_Name, Price, UserID) VALUES(?,?,?,?)";
								       
									   pst=conn.prepareStatement(sql);
				                
									   pst.setString(1, clickedID);
									   pst.setString(2, clickedGame);
									   pst.setString(3, price);
									   pst.setString(4, LoggedIn.loggedInID);
									   
									   //pst.execute();					 
									   
									   System.out.println("Game: [" + clickedGame + "] added to your shopping cart ");
									   JOptionPane.showMessageDialog(null, "Game: " + clickedGame + " added to shopping cart!");
									   //pst.close();
								   }//end else	   
							   }//end try					   
							   catch(Exception e) {
								   
								   e.getStackTrace();
							   }//end catch
						   try {
							   pst.execute();
							   pst.close();
						   }//end try
						   catch(Exception c) {}
					   }//end if
					   
					   //Cancel adding
					   else {
						   System.out.println("User cancelled adding to cart.");
					   }//end else
                    }//end if
                	//Clicked other
                	else {
                		System.out.println("User didn't clicked to ID.");
                	}//end else
                }
            }
        }); //END 
        
        jScrollPane1.setViewportView(jTable1);
        
        //SHOPPING CART BUTTON
        cartButton = new JButton("Click here to proceed.");
        cartButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        cartButton.setIcon(new ImageIcon("./img/basket.png"));
        cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//close the current jframe and open the new one
				dispose();
				ShoppingCart cart;
				try {
					cart = new ShoppingCart();
					cart.setLocationRelativeTo(null);
					cart.setVisible(true);
				}//end try
				catch (SQLException es) {
					// TODO Auto-generated catch block
					es.printStackTrace();
				}//end catch
			}//end ap
		});//end al
        
        //MENU BUTTON
        JButton btnNewButton = new JButton("Menu");
        btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        btnNewButton.setIcon(new ImageIcon("./img/back.png"));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		CustomerMenu cm = new CustomerMenu();
        		cm.setLocationRelativeTo(null);
        		cm.setVisible(true);
        	}
        });//END BUTTON
        
        
        //Auto-generated code from the internet (I used this table layout since my computer doesn't run netbeans properly)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
        					.addComponent(cartButton, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cartButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(256, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);
        
        //TABLE LAYOUT END

        pack();
    }// </editor-fold>                                                            

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Store frame = new Store();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private JButton cartButton;
	}


