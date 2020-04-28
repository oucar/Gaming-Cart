import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JDesktopPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;

public class Games extends JFrame {
	
	private JPanel contentPane;
	private JTextField nameField;
	private JComboBox categoryCombo;
	private JComboBox platformCombo;
	private ImageIcon format = null;
	String fileName = null;
	byte[] gameImg = null;
	private JTextField priceField;
	private JTextField metascoreField;
	private JTextField searchField;
	
	//CONNECTIONS
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;

	public Games() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(10, 88, 77, 16);
		contentPane.add(nameLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		categoryLabel.setBounds(10, 165, 77, 16);
		contentPane.add(categoryLabel);
		
		JLabel platformLabel = new JLabel("Platform");
		platformLabel.setHorizontalAlignment(SwingConstants.CENTER);
		platformLabel.setBounds(10, 127, 77, 16);
		contentPane.add(platformLabel);
		
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel.setBounds(333, 88, 57, 16);
		contentPane.add(priceLabel);
		
		JLabel metascoreLabel = new JLabel("Metascore");
		metascoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		metascoreLabel.setBounds(10, 205, 77, 16);
		contentPane.add(metascoreLabel);
		
		priceField = new JTextField();
		priceField.setBounds(389, 83, 150, 26);
		contentPane.add(priceField);
		priceField.setColumns(10);
		
		metascoreField = new JTextField();
		metascoreField.setBounds(99, 200, 168, 26);
		contentPane.add(metascoreField);
		metascoreField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(99, 83, 168, 26);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JComboBox platformCombo = new JComboBox();
		platformCombo.setBounds(99, 123, 168, 27);	
		contentPane.add(platformCombo);
		
		//PLATFORM BOX
		try {
			Statement st;
			st = conn.createStatement();
			String sql = "SELECT * FROM Platforms";
			rs = st.executeQuery(sql);
	        	while(rs.next()){
	        		platformCombo.addItem(rs.getString("Platform_Name"));
	        	}//end while
	    }//end try
		catch(Exception e){
	        JOptionPane.showMessageDialog(null, "Error fetching platform names");
		}//end catch
		
		
		
		JComboBox categoryCombo = new JComboBox();
		categoryCombo.setBounds(99, 161, 168, 27);
		contentPane.add(categoryCombo);
		
		//CATEGORY BOX
		try {
			Statement st;
			st = conn.createStatement();
			String sql = "SELECT * FROM Categories";
			rs = st.executeQuery(sql);
	        	while(rs.next()){
	        		categoryCombo.addItem(rs.getString("CName"));
	        	}//end while
	    }//end try
		catch(Exception e){
	        JOptionPane.showMessageDialog(null, "Error fetching category names");
		}//end category
		
		//ADD BUTTON
		JButton addButton = new JButton("Add");
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		addButton.setIcon(new ImageIcon("./img/add60.png"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					   int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to add following game?\n\nName:"
			        			+nameField.getText() +"\n"
			        			+"Platform: "+ platformCombo.getSelectedItem().toString()+ "\n"
			        			+"Category: "+categoryCombo.getSelectedItem().toString() + "\n"
			        			+"Metascore: "+metascoreField.getText()+"\n"
			        			+"Price: " +priceField.getText(),"Add Game",JOptionPane.YES_NO_OPTION);
				        if(confirmation==0) {

				        		String sql ="INSERT INTO Games (G_Name, G_Platform, G_Category, Metascore,"
			                		+ "Price, Image) VALUES (?,?,?,?,?,?) ";

				        		pst = conn.prepareStatement(sql);
	
				        		pst.setString(2, platformCombo.getSelectedItem().toString());
			                	pst.setString(3, categoryCombo.getSelectedItem().toString());
			                	pst.setString(4, metascoreField.getText());
			                	pst.setString(5, priceField.getText());
			                	pst.setBytes(6, gameImg);
			                
			                	if(nameField.getText().equals("") || platformCombo.getSelectedItem().toString().equals("")
			                		|| categoryCombo.getSelectedItem().toString().equals("")
			                		|| metascoreField.getText().equals("") || priceField.getText().equals("")) {
			                		JOptionPane.showMessageDialog(null,"Please make sure that text fields are not empty.");
			                		System.out.println("user left one of the asked text fields empty.");
			                	}//end if

			                	else {
			                		pst.execute();					                
			                		System.out.println("Game: [" + nameField.getText() + "] added succesfully!");
			                		JOptionPane.showMessageDialog(null, "Game: " + nameField.getText() + " added!");
			                		pst.close();
			                	}//end else
				        	}//end if
				        
				        else {
				        	System.out.println("Game adding operation has cancelled.");
				        }//end else
	                

				}//end try
				catch (Exception ex){
					System.out.println("Something went wrong, data couldn't be saved.");
				}//end catch
				
			}//end add
		});//END BUTTON
		
		addButton.setBounds(402, 264, 140, 54);
		contentPane.add(addButton);

		JDesktopPane desktopPaneImg = new JDesktopPane();
		desktopPaneImg.setBackground(Color.LIGHT_GRAY);
		desktopPaneImg.setBounds(709, 90, 110, 131);
		contentPane.add(desktopPaneImg);
		
		JLabel img = new JLabel("Image");
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setBounds(6, 6, 98, 119);
		desktopPaneImg.add(img);
		
		//ATTACH 
		JButton attachButton = new JButton("Attach");
		attachButton.setHorizontalAlignment(SwingConstants.LEFT);
		attachButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		attachButton.setIcon(new ImageIcon("./img/attach48.png"));
		attachButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//File choosing ?
				JFileChooser chooseImg = new JFileChooser();
				chooseImg.showOpenDialog(null);
				File file = chooseImg.getSelectedFile();
				
				fileName = file.getAbsolutePath();
				ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT));
				
				img.setIcon(imageIcon);
				
				try {
					File image = new File(fileName);
					FileInputStream fix = new FileInputStream(image);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					
					byte[] buff = new byte[1024];
					
					for(int number;(number = fix.read(buff)) !=-1;){
						
						bos.write(buff,0,number);
						
					}//end for
					
					gameImg = bos.toByteArray();	
					
				}//end try
				
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Something went wrong.");
					ex.getStackTrace();
				}//end catch
			}
		});//END
		
		
		attachButton.setBounds(706, 233, 120, 54);
		contentPane.add(attachButton);
		
		
		//SEARCH
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try{
					//read and fill the jTextFields
		            String sql ="SELECT * FROM Games WHERE GameID = ? ";

		            pst=conn.prepareStatement(sql);
		            pst.setString(1, searchField.getText());
		            rs=pst.executeQuery();
		            
		            String searchName =rs.getString("G_Name"); //Text
		            nameField.setText(searchName);

		            String searchPlatform =rs.getString("G_Platform"); //JComboBox
	                platformCombo.setSelectedItem(searchPlatform);

	                String searchCategory =rs.getString("G_Category"); //JComboBox
	                categoryCombo.setSelectedItem(searchCategory);	
	                
	                String searchMetascore =rs.getString("Metascore"); //Text
	                metascoreField.setText(searchMetascore);

	                String searchPrice =rs.getString("Price"); //Text
	                priceField.setText(searchPrice);

		                
	                byte[] image = rs.getBytes("Image");
	                ImageIcon imageIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH));
	                img.setIcon(imageIcon);

		        }//end try
				catch(Exception ew){
					ew.getStackTrace();
		        }//end catch
				
		        finally {
		            try{
		                rs.close();
		                pst.close();
		            }//end try
		            catch(Exception ea){
		            }//end catch
		         }//end finally
			}
		}); //END BUTTON
		
		
		searchField.setBounds(741, 31, 40, 26);
		contentPane.add(searchField);
		searchField.setColumns(10);
		

		//SEARCH BAR (DELETE/SEARCH/UPDATE..)
		JLabel searchDeleteLabel = new JLabel("Search / Delete ID: ");
		searchDeleteLabel.setHorizontalAlignment(SwingConstants.LEFT);
		searchDeleteLabel.setBounds(621, 36, 179, 16);
		contentPane.add(searchDeleteLabel);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		deleteButton.setIcon(new ImageIcon("./img/delete60.png"));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 try{
					   //ask for confirmation
					   int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
							   +nameField.getText()+"?","Delete Game",JOptionPane.YES_NO_OPTION);
				        if(confirmation==0){
			                String sql ="DELETE from Games WHERE GameID=? ";
			                pst=conn.prepareStatement(sql);
			                pst.setString(1, searchField.getText());
			                pst.execute();

			                System.out.println(nameField.getText() + " has deleted.");
			                nameField.setText(null);
							metascoreField.setText(null);
							priceField.setText(null);
							img.setIcon(null); //Clears the image
							searchField.setText(null); //Clears the Search and Delete field
							platformCombo.setSelectedItem(null);
							categoryCombo.setSelectedItem(null);
			                JOptionPane.showMessageDialog(null,"Game deleted.");

				        }//end if 
				        else {
				        	System.out.println("Game deleting operation has cancelled.");
				        }//end else
		            }//end try
				    catch(Exception es){
		                JOptionPane.showMessageDialog(null, e);
		            }//end catch
			   	    finally {
		                try{
		                    rs.close();
		                    pst.close();
		                }//end try
		                catch(Exception er){
		                }//end catch
		            }//end finally
			}
		});//END BUTTON
		
		deleteButton.setBounds(64, 262, 140, 59);
		contentPane.add(deleteButton);
		
		
		//CLEAR BUTTON
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		clearButton.setIcon(new ImageIcon("./img/eraser41.png"));
		clearButton.addActionListener(new ActionListener() {
			
			//clear
			public void actionPerformed(ActionEvent e) {
				
				nameField.setText(null);
				metascoreField.setText(null);
				priceField.setText(null);
				img.setIcon(null); //Clears the image
				searchField.setText(null); //Clears the Search and Delete field
				
				platformCombo.setSelectedItem(null);
				categoryCombo.setSelectedItem(null);

			}//end action performed
		});
		clearButton.setBounds(399, 142, 140, 54);
		contentPane.add(clearButton);
		
		//UPDATE
		JButton updateButton = new JButton("Update");
		updateButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		updateButton.setIcon(new ImageIcon("./img/update60.png"));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try {					
					//Check for empty text fields
					if(nameField.getText().equals("")||platformCombo.getSelectedItem().toString().equals("")
							||categoryCombo.getSelectedItem().toString().equals("") || metascoreField.getText().equals("")
							||priceField.getText().equals("")) {
						
						JOptionPane.showMessageDialog(null, "You left one of the asked text fields empty!");
					}//end if 

					//If not, execute the query 
					else {
						String sql = "UPDATE Games SET G_Name=?, G_Platform=?, G_Category=?, Metascore=?, Price=? WHERE GameID=?";
						pst = conn.prepareStatement(sql);
						pst.setString(1, nameField.getText());
						pst.setString(2, platformCombo.getSelectedItem().toString());
						pst.setString(3, categoryCombo.getSelectedItem().toString());
						pst.setString(4, metascoreField.getText());
						pst.setString(5, priceField.getText());
						pst.setString(6, searchField.getText());
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Game succesfully updated!");
					}//end else
				}//end try
				catch(SQLException el) {
				     el.getStackTrace();
					 System.out.println(el);
				}//end catch

			}
		});//END BUTTON
		updateButton.setBounds(235, 264, 140, 54);
		contentPane.add(updateButton);
		
		//MENU
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		menuButton.setIcon(new ImageIcon("./img/back.png"));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu menu = new Menu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});//END BUTTON
		
		menuButton.setBounds(10, 6, 128, 51);
		contentPane.add(menuButton);
		
		JButton gamesButton = new JButton("All games");
		gamesButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		gamesButton.setIcon(new ImageIcon("./img/table45.png"));
		gamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GamesTable gamesTable = new GamesTable();
				gamesTable.setLocationRelativeTo(null);
				gamesTable.setVisible(true);
			}
		});
		gamesButton.setBounds(150, 6, 150, 51);
		contentPane.add(gamesButton);

	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Games frame = new Games();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
