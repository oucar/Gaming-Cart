import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class EditPlatform extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	//CONNECTIONS
	Connection conn = DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public EditPlatform() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 99, 838, 116);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 int row = table.rowAtPoint(e.getPoint());
	             int col = table.columnAtPoint(e.getPoint());
	            	
	             String clickedPlatformID = (String) table.getValueAt(row, 0).toString();//ID of the platform
	             String clickedAttribute = (String) table.getValueAt(row, col).toString(); //Get the clicked attribute
	             System.out.println(clickedAttribute);
	             
	             JTextField name = new JTextField();
	             JTextField code = new JTextField();
	             JTextField proc = new JTextField();
	             JTextField cpu = new JTextField();
	             JTextField gpu = new JTextField();
	             JTextField memo = new JTextField();
	             JTextField cons = new JTextField();
	             
	             String clickedName = (String) table.getValueAt(row, 1).toString();
	             String clickedCode = (String) table.getValueAt(row, 2).toString();
	             String clickedProcessor = (String) table.getValueAt(row, 3).toString();
	             String clickedCPU = (String) table.getValueAt(row, 4).toString();
	             String clickedGPU = (String) table.getValueAt(row, 5).toString();
	             String clickedMemory = (String) table.getValueAt(row, 6).toString();
	             String clickedConsumption = (String) table.getValueAt(row, 7).toString();
	             
	             name.setText(clickedName);
	             code.setText(clickedCode);
	             proc.setText(clickedProcessor);
	             cpu.setText(clickedCPU);
	             gpu.setText(clickedGPU);
	             memo.setText(clickedMemory);
	             cons.setText(clickedConsumption);

	             Object[] attributes = {
	                 "Platform Name:", name,
	                 "Product Code:", code,
	                 "Processor:", proc,
	                 "CPU:", cpu,
	                 "GPU:", gpu,
	                 "Memory:", memo,
	                 "Consumption:", cons,
	                 
	             };//end attributes
	             
	             int confirmation = JOptionPane.showConfirmDialog(null, attributes, "Updating platform..", JOptionPane.OK_CANCEL_OPTION);
	             //confirmation
	             if (confirmation == JOptionPane.OK_OPTION){
	                 String nameGiven = name.getText();
	                 String codeGiven = code.getText();
	                 String processorGiven = proc.getText();
	                 String cpuGiven = cpu.getText();
	                 String gpuGiven = gpu.getText();
	                 String memoryGiven = memo.getText();
	                 String consumptionGiven = cons.getText();
	                 try {
	                	// String sql = "UPDATE Platforms SET(Platform_Name, Product_Code, Processor, CPU, GPU, Memory, Consumption) "
	                	// 		+ "VALUES(?,?,?,?,?,?,?) WHERE PlatformID=? ";
	                	 
	                	 //CHECK FOR THE EMPTY TEXTFIELDS!
	                	 if(nameGiven.equals("") || codeGiven.equals("") || processorGiven.equals("") || cpuGiven.equals("") || gpuGiven.equals("") 
	                			 || memoryGiven.equals("") || consumptionGiven.equals("") ){
	                		 
	                		 JOptionPane.showMessageDialog(null, "You left one of the asked text fields empty!");
	                	 }//end if
	                	 
	                	 //Else, execute.
	                	 else {
		                	 String sql = "UPDATE Platforms SET Platform_Name=?, Product_Code=?, Processor=?, CPU=?, GPU=?, Memory=?, Consumption=? WHERE PlatformID=?";
		                	 pst = conn.prepareStatement(sql);
		                	 
		                	 pst.setString(1, nameGiven);
		                	 pst.setString(2, codeGiven);
		                	 pst.setString(3, processorGiven);
		                	 pst.setString(4, cpuGiven);
		                	 pst.setString(5, gpuGiven);
		                	 pst.setString(6, memoryGiven);
		                	 pst.setString(7, consumptionGiven);
		                	 pst.setString(8, clickedPlatformID);
		                	 pst.executeUpdate();
		                	 System.out.println("asdasdads");
	                	 }//end if 
	                	 
	                 }//end try
	                 catch(Exception ea) {
	                	 System.out.println(ea);
	                	 ea.getStackTrace();
	                 }//end catch
	             }//end if 
	             //String input = JOptionPane.showInputDialog("Editing",clickedAttribute);
			}
		});//END BUTTON
		
		scrollPane.setViewportView(table);
		
		//MENU BUTTON
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
		
		menuButton.setBounds(7, 6, 124, 57);
		contentPane.add(menuButton);
		
		JLabel infoLabel = new JLabel("Click on a platform to edit:");
		infoLabel.setBounds(7, 75, 205, 16);
		contentPane.add(infoLabel);
		
		//REFRESH BUTTON
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		refreshButton.setIcon(new ImageIcon("./img/refresh.png"));
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				EditPlatform refresh = new EditPlatform();
				refresh.setLocationRelativeTo(null);
				refresh.setVisible(true);
			}
		});//END BUTTON 
		
		refreshButton.setBounds(7, 227, 136, 50);
		contentPane.add(refreshButton);
		
		JButton platformsButton = new JButton("Go back to platforms");
		platformsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Platforms back = new Platforms();
				back.setVisible(true);
			}
		});
		platformsButton.setIcon(new ImageIcon("./img/editfolder.png"));
		platformsButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		platformsButton.setBounds(143, 6, 242, 57);
		contentPane.add(platformsButton);
		
		platforms_table();
	}//end constructor


	public void platforms_table() {
	    try{
	    	String sql="SELECT * FROM Platforms";
	    	pst=conn.prepareStatement(sql);
	    	rs=pst.executeQuery();
	    	table.setModel(DbUtils.resultSetToTableModel(rs));
	    	table.setDefaultEditor(Object.class, null); //MAKES TABLE NOT EDITABLE BY DOUBLE-CLICKING!
	    	table.setAutoCreateRowSorter(true); //AUTO SORTER
	    }//end try
	    catch(Exception e){
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }//end catch
	    finally{
	    	try{
	    		rs.close();
	    	    pst.close();
	    	         
	    	}//end try
	    	catch(Exception e){
	    		e.printStackTrace();   	
	    	}//end catch
	    }//end finally
	}//end platforms_table();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPlatform frame = new EditPlatform();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
