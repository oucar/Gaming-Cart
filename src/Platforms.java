import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Platforms extends JFrame {
	
	//CONNECTIONS
	Connection conn=DatabaseConnection.establishConn();
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField codeField;
	private JTextField processorField;
	private JTextField cpuField;
	private JTextField gpuField;
	private JTextField memoryField;
	private JTextField consumptionField;
	

	public Platforms() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel platformNameLabel = new JLabel("Platform name");
		platformNameLabel.setBounds(35, 100, 91, 16);
		contentPane.add(platformNameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(138, 95, 159, 26);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		codeField = new JTextField();
		codeField.setBounds(138, 130, 159, 26);
		contentPane.add(codeField);
		codeField.setColumns(10);
		
		
		
		JLabel productCodeLabel = new JLabel("Product code");
		productCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productCodeLabel.setBounds(35, 135, 91, 16);
		contentPane.add(productCodeLabel);
		
		JLabel processorLabel = new JLabel("Processor");
		processorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		processorLabel.setBounds(35, 170, 91, 16);
		contentPane.add(processorLabel);
		
		processorField = new JTextField();
		processorField.setBounds(138, 168, 159, 26);
		contentPane.add(processorField);
		processorField.setColumns(10);
		
		JLabel cpuLabel = new JLabel("CPU");
		cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cpuLabel.setBounds(361, 105, 68, 16);
		contentPane.add(cpuLabel);
		
		cpuField = new JTextField();
		cpuField.setBounds(435, 100, 159, 26);
		contentPane.add(cpuField);
		cpuField.setColumns(10);
		
		JLabel gpuLabel = new JLabel("GPU");
		gpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gpuLabel.setBounds(361, 138, 68, 21);
		contentPane.add(gpuLabel);
		
		gpuField = new JTextField();
		gpuField.setBounds(435, 135, 159, 26);
		contentPane.add(gpuField);
		gpuField.setColumns(10);
		
		JLabel memoryLabel = new JLabel("Memory");
		memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		memoryLabel.setBounds(361, 175, 68, 16);
		contentPane.add(memoryLabel);
		
		memoryField = new JTextField();
		memoryField.setBounds(435, 170, 159, 26);
		contentPane.add(memoryField);
		memoryField.setColumns(10);
		
		JLabel powerConsumptionLabel = new JLabel("P. consumption");
		powerConsumptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		powerConsumptionLabel.setVerticalAlignment(SwingConstants.TOP);
		powerConsumptionLabel.setBounds(29, 208, 115, 16);
		contentPane.add(powerConsumptionLabel);
		
		consumptionField = new JTextField();
		consumptionField.setBounds(138, 203, 159, 26);
		contentPane.add(consumptionField);
		consumptionField.setColumns(10);
		
		
		//ADD BUTTON
		JButton addButton = new JButton("Add");
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		addButton.setIcon(new ImageIcon("./img/add60.png"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				try {
	                String sql ="INSERT INTO Platforms (Platform_Name, Product_Code, Processor,"
	                		+ "Consumption, CPU, GPU, Memory) VALUES (?,?,?,?,?,?,?) ";

	                pst = conn.prepareStatement(sql);
	                
	                pst.setString(1, nameField.getText());
	                pst.setString(2, codeField.getText());
	                pst.setString(3, processorField.getText());
	                pst.setString(4, consumptionField.getText());
	                pst.setString(5, cpuField.getText());
	                pst.setString(6, gpuField.getText());
	                pst.setString(7, memoryField.getText());
	                
	                if(nameField.getText().equals("") || codeField.getText().equals("")
	                		|| processorField.getText().equals("") || consumptionField.getText().equals("")
	                		|| cpuField.getText().equals("") || gpuField.getText().equals("")
	                		|| memoryField.getText().equals("")) {
	                	JOptionPane.showMessageDialog(null,"Please make sure that text fields are not empty.");
	                	System.out.println("user left one of the asked text fields empty.");
	                }//end if
	                
	                else {
	                	pst.execute();					                
			            System.out.println("Platform: [" + nameField.getText() + " ," + codeField.getText() +"] added succesfully!");
	                }//end else

				}//end try
				catch (Exception ex){
					System.out.println("Something went wrong, data couldn't be saved.");
				}//end try
	        
			}
		});//END BUTTON
		
		
		addButton.setBounds(621, 109, 132, 69);
		contentPane.add(addButton);
		
		//CLEAR BUTTON
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		clearButton.setIcon(new ImageIcon("./img/eraser41.png"));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nameField.setText(null);
				codeField.setText(null);
				processorField.setText(null);
				consumptionField.setText(null);
				cpuField.setText(null);
				gpuField.setText(null);
				memoryField.setText(null);
		
			}//end clear
		});
		clearButton.setBounds(435, 203, 159, 55);
		contentPane.add(clearButton);
		
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
		});
		menuButton.setBounds(12, 14, 115, 55);
		contentPane.add(menuButton);
		
		//EDIT A PLATFORM BUTTON
		JButton editPlatforms = new JButton("Edit a platform");
		editPlatforms.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		editPlatforms.setIcon(new ImageIcon("./img/editfolder.png"));
		editPlatforms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				EditPlatform edit = new EditPlatform();
				edit.setLocationRelativeTo(null);
				edit.setVisible(true);
			}
		});
		editPlatforms.setBounds(102, 252, 195, 55);
		contentPane.add(editPlatforms);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Platforms frame = new Platforms();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
