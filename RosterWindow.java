package classroster;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RosterWindow
{
	private JFrame frame;
	private JFrame updateLastStudentframe;
	private JFrame updateFirstStudentframe;
	private JFrame updateNickStudentframe;
	private JFrame updateEmailStudentframe;
	private JFrame updateIDStudentframe;
	private JFrame updatePicStudentframe;
	private JTextField textField;
	private JTextField textField_1;
	private Roster roster;
	private JFrame addStudentframe;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtNickName;
	private JTextField txtIdNumber;
	private JTextField txtEmail;
	private JTextField txtPicFile;
	private JFrame removeStudentframe;
	private JTextField txtIdremoveNumber;
	private JFrame newclassframe;
	private JTextField textFieldclassname;
	private JFrame fullStudentframe;
	private JTextField fullstudenttextField;
	//--------------------------------------------------------------------------
	// Launch the application.
	//--------------------------------------------------------------------------
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow classmain = new RosterWindow();
					classmain.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	//--------------------------------------------------------------------------
	// Create the application
	//--------------------------------------------------------------------------
	public RosterWindow()
	{
		roster = new Roster("Class");
		initialize();
	}

	//--------------------------------------------------------------------------
	// Initialize the contents to the frame
	//--------------------------------------------------------------------------
	public void initialize()
	{
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				roster.writeToFile();
			}
		});
		frame.setBounds(100, 100, 562, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try
		{
			roster.readFromFile();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//--------------------------------------------------------------------------
		// Label for displaying pictures
		//--------------------------------------------------------------------------
		JLabel lblPicture = new JLabel("");
		lblPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblPicture.setBounds(270, 139, 250, 252);
		frame.getContentPane().add(lblPicture);
				
		JLabel lblStudentNameHere = new JLabel("Student Name Here");
		lblStudentNameHere.setFont(new Font("Dialog", Font.BOLD, 12));
		lblStudentNameHere.setForeground(Color.WHITE);
		lblStudentNameHere.setBackground(Color.WHITE);
		lblStudentNameHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentNameHere.setBounds(292, 103, 199, 24);
		frame.getContentPane().add(lblStudentNameHere);
		//--------------------------------------------------------------------------
		// List Creation
		//--------------------------------------------------------------------------
		List list = new List();
		list.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		list.setBackground(Color.LIGHT_GRAY);
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String select = list.getSelectedItem();
				String[] fields = select.split(" ");
				Student student = roster.getStudent(fields[2]);
				ImageIcon bgImage= new ImageIcon(student.getPicFile());
				bgImage.setImage(getScaledImage(bgImage.getImage(), 250, 252));
				String last = student.getLast();
				String nick = student.getNickname();
				lblStudentNameHere.setText(nick+" "+last);
				lblPicture.setIcon(bgImage);
			}
		});
		list.add(roster.getClassName() + "\n");
		for(int i =0; i < roster.numberOfStudents(); i++)
		{
			list.add(roster.getNextStudent().toString());
		}
		list.setBounds(0, 10, 250, 300);
		frame.getContentPane().add(list);
		//--------------------------------------------------------------------------
		// Create the menu bar
		//--------------------------------------------------------------------------
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		//--------------------------------------------------------------------------
		// Menu bar File[save, load]
		//--------------------------------------------------------------------------
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFrame myJFrame = new JFrame();
				FileDialog fd = new FileDialog(myJFrame, "Choose a file", FileDialog.LOAD);
				fd.setDirectory("C:\\");
				fd.setFile(".txt");
				fd.setVisible(true);
				fd.getFile();
				try
				{
					roster.readFromFile(fd);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				roster.writeToFile();
			}
		});
		//--------------------------------------------------------------------------
		// Menu bar Edit[Add Student, Remove Student, Modify Student]
		//--------------------------------------------------------------------------
		JMenu mnEdit_1 = new JMenu("Edit");
		menuBar.add(mnEdit_1);
		
		// Add Student
		JMenuItem mntmAddStudent = new JMenuItem("Add Student");
		mntmAddStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addStudentWindow();
			}
		});
		
		JMenuItem mntmClassName = new JMenuItem("Class Name");
		mntmClassName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				updateClassNameWindow();
			}
		});
		mnEdit_1.add(mntmClassName);
		mnEdit_1.add(mntmAddStudent);
		
		// Remove Student
		JMenuItem mntmRemoveStudent = new JMenuItem("Remove Student");
		mntmRemoveStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				removeStudentWindow();
			}
		});
		mnEdit_1.add(mntmRemoveStudent);
		//-------------------------------
		// Modify Student
		//-------------------------------
		JMenu mnModifyStudent = new JMenu("Modify Student");
		mnEdit_1.add(mnModifyStudent);
		//Update Student Last Name
		JMenuItem mntmUpdateLastName = new JMenuItem("Update Last Name");
		mntmUpdateLastName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdateLastWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateLastName);
		//Update Student First Name
		JMenuItem mntmUpdateFirstName = new JMenuItem("Update First Name");
		mntmUpdateFirstName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdateFirstWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateFirstName);
		//Update Student Nickname
		JMenuItem mntmUpdateNickname = new JMenuItem("Update Nickname");
		mntmUpdateNickname.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdateNickWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateNickname);
		//Update Student ID 
		JMenuItem mntmUpdateStudentId = new JMenuItem("Update Student I.D.");
		mntmUpdateStudentId.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdateIDWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateStudentId);
		//Update Student Picture
		JMenuItem mntmUpdateStudentPicture = new JMenuItem("Update Student Picture");
		mntmUpdateStudentPicture.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdatePicWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateStudentPicture);
		//Update Student Email
		JMenuItem mntmUpdateStudentEmail = new JMenuItem("Update Student Email");
		mntmUpdateStudentEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addUpdateEmailWindow();
			}
		});
		mnModifyStudent.add(mntmUpdateStudentEmail);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmFullStudent = new JMenuItem("Full Student");
		mntmFullStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				displayFullStudentWindow();
			}
		});
		mnView.add(mntmFullStudent);
		//--------------------------------------------------------------------------
		// Formatting
		//--------------------------------------------------------------------------
		JMenuItem menuItem = new JMenuItem("");
		menuBar.add(menuItem);
		frame.getContentPane().setLayout(null);
		//--------------------------------------------------------------------------
		// Button for selecting call on question and displaying pics
		//--------------------------------------------------------------------------
		JButton button = new JButton("Select Student");
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Student student = roster.getNextStudent();
				String last = student.getLast();
				String nick = student.getNickname();
				ImageIcon bgImage= new ImageIcon(student.getPicFile());
				bgImage.setImage(getScaledImage(bgImage.getImage(), 250, 252));
				lblStudentNameHere.setText(nick+" "+last);
				lblPicture.setIcon(bgImage);
			}
		});
		button.setBounds(334, 24, 126, 24);
		frame.getContentPane().add(button);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
////				list.removeAll();
////				try
////				{
////					roster.readFromFile();
////				} catch (IOException e1)
////				{
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////				list.add(roster.getClassName() + "\n");
////				for(int i =0; i < roster.numberOfStudents(); i++)
////				{
////					list.add(roster.getNextStudent().toString());
////				}
				// Reset the window to refresh screen
				frame.setVisible(false);
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							// Create window on startup
							RosterWindow classmain = new RosterWindow();
							classmain.frame.setVisible(true);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnRefresh.setBounds(334, 412, 126, 24);
		frame.getContentPane().add(btnRefresh);
	}
	//------------------------------------------------------------------------------
	// Initializations of Windows
	//------------------------------------------------------------------------------
	//------------------------------------------------------------------------------
	// View Full Student Window
	//------------------------------------------------------------------------------
	public void displayFullStudentWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					RosterWindow fullStudent = new RosterWindow();
					fullStudent.initializeFullStudentWindow();
					fullStudent.fullStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeFullStudentWindow()
	{
		fullStudentframe = new JFrame();
		fullStudentframe.setBounds(100, 100, 543, 434);
		try
		{
			fullStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblNewLabel = new JLabel("Full Student Profile");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(102, 11, 324, 46);
		fullStudentframe.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Student ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(216, 78, 87, 19);
		fullStudentframe.getContentPane().add(lblNewLabel_1);
		
		fullstudenttextField = new JTextField();
		fullstudenttextField.setBounds(217, 102, 86, 20);
		fullStudentframe.getContentPane().add(fullstudenttextField);
		fullstudenttextField.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setBounds(10, 145, 110, 24);
		fullStudentframe.getContentPane().add(lblFirstName);
		
		JLabel lblDisplayFirst = new JLabel(" ");
		lblDisplayFirst.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayFirst.setForeground(Color.WHITE);
		lblDisplayFirst.setBounds(10, 180, 110, 24);
		fullStudentframe.getContentPane().add(lblDisplayFirst);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(130, 145, 110, 24);
		fullStudentframe.getContentPane().add(lblLastName);
		
		JLabel lblDisplayLast = new JLabel(" ");
		lblDisplayLast.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayLast.setForeground(Color.WHITE);
		lblDisplayLast.setBounds(130, 180, 110, 24);
		fullStudentframe.getContentPane().add(lblDisplayLast);
		
		JLabel lblNickName = new JLabel("Nickname");
		lblNickName.setHorizontalAlignment(SwingConstants.CENTER);
		lblNickName.setForeground(Color.WHITE);
		lblNickName.setBounds(130, 229, 110, 24);
		fullStudentframe.getContentPane().add(lblNickName);
		
		JLabel lblDisplayNick = new JLabel(" ");
		lblDisplayNick.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayNick.setForeground(Color.WHITE);
		lblDisplayNick.setBounds(130, 264, 110, 24);
		fullStudentframe.getContentPane().add(lblDisplayNick);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(10, 229, 110, 24);
		fullStudentframe.getContentPane().add(lblEmail);
		
		JLabel lblDisplayEmail = new JLabel(" ");
		lblDisplayEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayEmail.setForeground(Color.WHITE);
		lblDisplayEmail.setBounds(10, 264, 125, 24);
		fullStudentframe.getContentPane().add(lblDisplayEmail);
		
		JLabel lblPicture = new JLabel("");
		lblPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblPicture.setForeground(Color.WHITE);
		lblPicture.setBounds(257, 145, 249, 239);
		fullStudentframe.getContentPane().add(lblPicture);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = fullstudenttextField.getText();
				Student student = roster.getStudent(id);
				lblDisplayLast.setText(student.getLast());
				lblDisplayFirst.setText(student.getFirst());
				lblDisplayEmail.setText(student.getEmail());
				lblDisplayNick.setText(student.getNickname());
				ImageIcon bgImage= new ImageIcon(student.getPicFile());
				bgImage.setImage(getScaledImage(bgImage.getImage(), 249, 239));
				lblPicture.setIcon(bgImage);
			}
		});
		btnNewButton.setBounds(69, 76, 89, 23);
		fullStudentframe.getContentPane().add(btnNewButton);
	}
	//------------------------------------------------------------------------------
	// Get scaled image
	//------------------------------------------------------------------------------
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	//------------------------------------------------------------------------------
	// Add student window and method
	//------------------------------------------------------------------------------
	public void initializeAddStudent()
	{
		addStudentframe = new JFrame();
		addStudentframe.setBounds(600, 200, 483, 351);
		try
		{
			addStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblNewLabel = new JLabel("Add Student");
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 22));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(118, 41, 240, 51);
		addStudentframe.getContentPane().add(lblNewLabel);
		
		txtFirstName = new JTextField();
		txtFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		txtFirstName.setText("First Name");
		txtFirstName.setBounds(6, 117, 137, 34);
		addStudentframe.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setText("Last Name");
		txtLastName.setHorizontalAlignment(SwingConstants.CENTER);
		txtLastName.setBounds(169, 117, 137, 34);
		addStudentframe.getContentPane().add(txtLastName);
		txtLastName.setColumns(10);
		
		txtNickName = new JTextField();
		txtNickName.setText("Nick Name");
		txtNickName.setHorizontalAlignment(SwingConstants.CENTER);
		txtNickName.setBounds(324, 117, 137, 34);
		addStudentframe.getContentPane().add(txtNickName);
		txtNickName.setColumns(10);
		
		txtIdNumber = new JTextField();
		txtIdNumber.setText("ID Number");
		txtIdNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdNumber.setBounds(6, 208, 137, 34);
		addStudentframe.getContentPane().add(txtIdNumber);
		txtIdNumber.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setBounds(169, 208, 137, 34);
		addStudentframe.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtPicFile = new JTextField();
		txtPicFile.setText("Pic File");
		txtPicFile.setHorizontalAlignment(SwingConstants.CENTER);
		txtPicFile.setBounds(324, 208, 137, 34);
		addStudentframe.getContentPane().add(txtPicFile);
		txtPicFile.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String fn = txtFirstName.getText();
				String ln = txtLastName.getText();
				String nn = txtNickName.getText();
				String id = txtIdNumber.getText();
				String email = txtEmail.getText();
				String pic = txtPicFile.getText();
				roster.add(ln, fn, nn, id, pic, email);
				roster.writeToFile();
				addStudentframe.setVisible(false);
			}
		});
		btnNewButton.setBounds(182, 267, 137, 39);
		addStudentframe.getContentPane().add(btnNewButton);
	}
	
	public void addStudentWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow addStudent = new RosterWindow();
					addStudent.initializeAddStudent();
					addStudent.addStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Remove Student Initialization and method call
	//------------------------------------------------------------------------------
	public void initializeRemoveStudent()
	{
		removeStudentframe = new JFrame();
		removeStudentframe.setBounds(600, 200, 424, 299);
		try
		{
			removeStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblNewLabel = new JLabel("Remove Student");
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(78, 25, 240, 60);
		removeStudentframe.getContentPane().add(lblNewLabel);
		
		txtIdremoveNumber = new JTextField();
		txtIdremoveNumber.setText("ID Number");
		txtIdremoveNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdremoveNumber.setBounds(134, 114, 137, 34);
		removeStudentframe.getContentPane().add(txtIdremoveNumber);
		txtIdremoveNumber.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = txtIdremoveNumber.getText();
				roster.remove(id);
				roster.writeToFile();
				removeStudentframe.setVisible(false);
			}
		});
		btnNewButton.setBounds(134, 195, 137, 34);
		removeStudentframe.getContentPane().add(btnNewButton);
	}

	public void removeStudentWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow removeStudent = new RosterWindow();
					removeStudent.initializeRemoveStudent();
					removeStudent.removeStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Update Last Name
	//------------------------------------------------------------------------------
	public void initializeUpdateLastName()
	{
		updateLastStudentframe = new JFrame();
		updateLastStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updateLastStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updateLastStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updateLastStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updateLastStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updateLastStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New Last Name");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updateLastStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updateLastName(id, updateinfo);
				roster.writeToFile();
				updateLastStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updateLastStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update First Name
	//------------------------------------------------------------------------------
	public void initializeUpdateFirstName()
	{
		updateFirstStudentframe = new JFrame();
		updateFirstStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updateFirstStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updateFirstStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updateFirstStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updateFirstStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updateFirstStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New First Name");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updateFirstStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updateFirstName(id, updateinfo);
				roster.writeToFile();
				updateFirstStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updateFirstStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update Nickname
	//------------------------------------------------------------------------------
	public void initializeUpdateNickName()
	{
		updateNickStudentframe = new JFrame();
		updateNickStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updateNickStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updateNickStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updateNickStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updateNickStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updateNickStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New Nickname");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updateNickStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updateNickname(id, updateinfo);
				roster.writeToFile();
				updateNickStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updateNickStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update ID
	//------------------------------------------------------------------------------
	public void initializeUpdateID()
	{
		updateIDStudentframe = new JFrame();
		updateIDStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updateIDStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updateIDStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updateIDStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updateIDStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updateIDStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New ID(XXXX)");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updateIDStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updateID(id, updateinfo);
				roster.writeToFile();
				updateIDStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updateIDStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update Email
	//------------------------------------------------------------------------------
	public void initializeUpdateEmail()
	{
		updateEmailStudentframe = new JFrame();
		updateEmailStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updateEmailStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updateEmailStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updateEmailStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updateEmailStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updateEmailStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New Email");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updateEmailStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updateEmail(id, updateinfo);
				roster.writeToFile();
				updateEmailStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updateEmailStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update Pic
	//------------------------------------------------------------------------------
	public void initializeUpdatePic()
	{
		updatePicStudentframe = new JFrame();
		updatePicStudentframe.setBounds(600, 200, 450, 300);
		try
		{
			updatePicStudentframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Label to show that this menu is for Modifying a student 
		JLabel lblModifyStudent = new JLabel("Modify Student");
		lblModifyStudent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 19));
		lblModifyStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyStudent.setForeground(Color.WHITE);
		lblModifyStudent.setBounds(51, 21, 314, 33);
		updatePicStudentframe.getContentPane().add(lblModifyStudent);
		
		// Text field that will take the Id number of the student to update
		textField = new JTextField();
		textField.setBounds(66, 108, 86, 20);
		updatePicStudentframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Text field that will take the new data for the update 
		textField_1 = new JTextField();
		textField_1.setBounds(231, 108, 134, 20);
		updatePicStudentframe.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		// Label showing the user where to enter the Student ID
		JLabel lblStudentId = new JLabel("Student ID (XXXX)");
		lblStudentId.setLabelFor(textField);
		lblStudentId.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentId.setForeground(Color.WHITE);
		lblStudentId.setBounds(34, 71, 142, 20);
		updatePicStudentframe.getContentPane().add(lblStudentId);
		
		// Label showing the user where to enter the updated information
		JLabel lblUpdatedInformation = new JLabel("New Pic Filename");
		lblUpdatedInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatedInformation.setForeground(Color.WHITE);
		lblUpdatedInformation.setBounds(231, 70, 123, 23);
		updatePicStudentframe.getContentPane().add(lblUpdatedInformation);
		
		// Button to save new data to updated student
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = textField.getText();
				String updateinfo = textField_1.getText();
				roster.updatePicFile(id, updateinfo);
				roster.writeToFile();
				updatePicStudentframe.setVisible(false);
			}
		});
		btnSubmit.setBounds(128, 190, 151, 33);
		updatePicStudentframe.getContentPane().add(btnSubmit);
	}
	//------------------------------------------------------------------------------
	// Update Class name
	//------------------------------------------------------------------------------
	private void initializeNewClassName()
	{
		newclassframe = new JFrame();
		newclassframe.setBounds(600, 200, 376, 278);
		try
		{
			newclassframe.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblNewClassName = new JLabel("New Class Name");
		lblNewClassName.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClassName.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 21));
		lblNewClassName.setForeground(Color.WHITE);
		lblNewClassName.setBounds(47, 36, 259, 43);
		newclassframe.getContentPane().add(lblNewClassName);
		
		textFieldclassname = new JTextField();
		textFieldclassname.setText("New Classname");
		textFieldclassname.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldclassname.setBounds(108, 114, 125, 24);
		newclassframe.getContentPane().add(textFieldclassname);
		textFieldclassname.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roster.setClassName(textFieldclassname.getText());
				roster.writeToFile();
				newclassframe.setVisible(false);
			}
		});
		btnNewButton.setBounds(108, 170, 125, 24);
		newclassframe.getContentPane().add(btnNewButton);
	}
	//------------------------------------------------------------------------------
	// Update Class Name
	//------------------------------------------------------------------------------
	public void updateClassNameWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					RosterWindow updateClassName = new RosterWindow();
					updateClassName.initializeNewClassName();
					updateClassName.newclassframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Last Name
	//------------------------------------------------------------------------------
	public void addUpdateLastWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdateLastName();
					modifyStudent.updateLastStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// First Name
	//------------------------------------------------------------------------------
	public void addUpdateFirstWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdateFirstName();
					modifyStudent.updateFirstStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Nickname
	//------------------------------------------------------------------------------
	public void addUpdateNickWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdateNickName();
					modifyStudent.updateNickStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// ID
	//------------------------------------------------------------------------------
	public void addUpdateIDWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdateID();
					modifyStudent.updateIDStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Email
	//------------------------------------------------------------------------------
	public void addUpdateEmailWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdateEmail();
					modifyStudent.updateEmailStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	//------------------------------------------------------------------------------
	// Pic 
	//------------------------------------------------------------------------------
	public void addUpdatePicWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Create window on startup
					RosterWindow modifyStudent = new RosterWindow();
					modifyStudent.initializeUpdatePic();
					modifyStudent.updatePicStudentframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
