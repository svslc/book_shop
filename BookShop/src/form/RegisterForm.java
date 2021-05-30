package form;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import connect.Connect;
import main.Account;

public class RegisterForm extends JFrame implements ActionListener, MouseListener {

	JPanel upperPanel, lowerPanel, mainPanel, btnPanel, lblPanel, titlePanel;
	JLabel usernameLbl, passLbl, titleLbl, loginLbl, emailLbl, roleLbl;
	JButton signupBtn;
	JTextField usernameTf, emailTf;
	JPasswordField passPf;
	String[] listRole = {"-Choose One-","User", "Admin"};
	JComboBox<String> roleCb;
	boolean flag = true;
	Connect con = new Connect();
	Account acc = new Account();

	
	public RegisterForm() {
		configureFrame();
		initFrame();
	}

	private void initFrame() {
		setTitle("Register Form");
		setSize(600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void configureFrame() {

		setLayout(new BorderLayout());

		//title 
		titlePanel = new JPanel();
		titleLbl = new JLabel("Register Account");
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 24));
		titleLbl.setBorder(new EmptyBorder(20, 0, 20, 0));
		titlePanel.add(titleLbl);

		//init form components
		usernameLbl = new JLabel("Username");
		passLbl = new JLabel("Password");
		usernameTf = new JTextField();
		passPf = new JPasswordField();
		emailLbl = new JLabel("Email");
		emailTf = new JTextField();
		roleLbl = new JLabel("Choose Role");
		roleCb = new JComboBox<String>(listRole);
		
		//init panel for form components
		upperPanel = new JPanel(new GridLayout(4,2,25,25));
		upperPanel.setBorder(new EmptyBorder(25, 40, 25, 40));
		upperPanel.add(usernameLbl); upperPanel.add(usernameTf);
		upperPanel.add(emailLbl); upperPanel.add(emailTf);
		upperPanel.add(passLbl); upperPanel.add(passPf);
		upperPanel.add(roleLbl); upperPanel.add(roleCb); 

		//init panel for sign up btn and login lbl
		lowerPanel = new JPanel(new BorderLayout());
		btnPanel = new JPanel();
		lblPanel = new JPanel();

		signupBtn = new JButton("SIGN UP");
		signupBtn.addActionListener(this);
		btnPanel.add(signupBtn);
		loginLbl = new JLabel("Login Here");
		loginLbl.addMouseListener(this);
		lblPanel.add(loginLbl);

		lowerPanel.add(btnPanel, BorderLayout.NORTH);
		lowerPanel.add(lblPanel, BorderLayout.SOUTH);
		lowerPanel.setBorder(new EmptyBorder(30, 0, 30, 0));

		//add component to frame
		add(titlePanel, BorderLayout.NORTH);
		add(upperPanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);

		// set background  color
		titlePanel.setBackground(new Color(255, 234, 167));
		upperPanel.setBackground(new Color(255, 234, 167));
		lowerPanel.setBackground(new Color(255, 234, 167));
		btnPanel.setBackground(new Color(255, 234, 167));
		lblPanel.setBackground(new Color(255, 234, 167));
	}

	private void validateUsername() {
		
		String sql = "select * from account";
		con.rs = con.execQuery(sql);
		
		try {
			while(con.rs.next()) {
				if(usernameTf.getText().equals(con.rs.getString("username"))) {
					flag=false;
					JOptionPane.showMessageDialog(this, "Username has already been taken", "Warning Message", JOptionPane.WARNING_MESSAGE);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void validateEmail() {

		int countA =0, countTitik =0;
		for(int i =0;i<emailTf.getText().length();i++) {
			if(emailTf.getText().charAt(i)=='@') countA++;
			if(emailTf.getText().charAt(i)=='.') countTitik++;
		}

		int charA = emailTf.getText().indexOf('@');
		String sql = "select * from account";
		con.rs = con.execQuery(sql);

		if(countA!=1 || countTitik!=1 || emailTf.getText().charAt(charA+1)=='.'|| emailTf.getText().startsWith("@") || 
				emailTf.getText().endsWith(".")) {
			JOptionPane.showMessageDialog(this, "Please fill with valid email","Warning Message", JOptionPane.WARNING_MESSAGE);
			flag=false;
		}else {
			try {
				while(con.rs.next()) {
					if(emailTf.getText().equals(con.rs.getString("email"))) {
						flag=false;
						JOptionPane.showMessageDialog(this, "Please choose another email", "Warning Message", JOptionPane.WARNING_MESSAGE);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}

	private void validatePassword() {
		int angka=0;
		for(int i =0;i<passPf.getPassword().length;i++) {
			if(Character.isDigit(passPf.getPassword()[i])) {
				angka++;
			}
		}
		if(passPf.getPassword().length<8||passPf.getPassword().length>30||angka==0) {
			flag=false;
			JOptionPane.showMessageDialog(this, "Password must be 8-30 characters and contain digit", "Warning Message", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void validateRole() {
		if(roleCb.getSelectedIndex() == 0	) {
			flag=false;
			JOptionPane.showMessageDialog(this, "Role must be choose!", "Warning Message", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signupBtn) {
			flag = true;
			validateUsername();
			validateEmail();
			validatePassword();
			validateRole();
			
			if(flag==true) {
				Random rand = new Random();
				
				String id = "AC" + (char) (rand.nextInt(26) + 'A') + (char) (rand.nextInt(26) + 'A') + (char) (rand.nextInt(26) + 'A') + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + "";
				String sql = "Insert into account(accountID, username, email, password, role) values('"+ id +"','"+ usernameTf.getText()+"','"+emailTf.getText()+"','"+String.valueOf(passPf.getPassword())+"','"+ roleCb.getSelectedItem()+"')" ;
				con.execUpdate(sql);
				JOptionPane.showMessageDialog(this, "Register Success","Message",JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				new LoginForm();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == loginLbl) {
			this.dispose();
			new LoginForm();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
