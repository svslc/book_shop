package form;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import admin.AdminMainForm;
import connect.Connect;
import main.Account;
import user.UserMainForm;


public class LoginForm extends JFrame implements ActionListener, MouseListener{

	JPanel upperPanel, lowerPanel, mainPanel, btnPanel, lblPanel, titlePanel;
	JLabel usernameLbl, passLbl, titleLbl, signupLbl;
	JButton loginBtn;
	JTextField usernameTf;
	JPasswordField passPf;
	Connect con = new Connect();
	Account acc = new Account();

	public LoginForm() {
		configureFrame();
		initFrame();
	}

	private void initFrame() {
		setTitle("Login Form");
		setSize(400, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void configureFrame() {

		setLayout(new BorderLayout());

		//title 
		titlePanel = new JPanel();
		titleLbl = new JLabel("Login Book Shop");
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 24));
		titleLbl.setBorder(new EmptyBorder(20, 0, 20, 0));
		titlePanel.add(titleLbl);

		//init text field and password field
		usernameLbl = new JLabel("Username");
		passLbl = new JLabel("Password");
		usernameTf = new JTextField();
		passPf = new JPasswordField();

		//init panel for username field and password field
		upperPanel = new JPanel(new GridLayout(2,2,25,25));
		upperPanel.setBorder(new EmptyBorder(25, 40, 25, 40));
		upperPanel.add(usernameLbl);
		upperPanel.add(usernameTf);
		upperPanel.add(passLbl);
		upperPanel.add(passPf);

		//init panel for login btn and sign up lbl
		lowerPanel = new JPanel(new BorderLayout());
		btnPanel = new JPanel();
		lblPanel = new JPanel();

		loginBtn = new JButton("LOGIN");
		loginBtn.addActionListener(this);
		btnPanel.add(loginBtn);
		signupLbl = new JLabel("Sign Up Here");
		signupLbl.addMouseListener(this);
		lblPanel.add(signupLbl);

		lowerPanel.add(btnPanel, BorderLayout.NORTH);
		lowerPanel.add(lblPanel, BorderLayout.SOUTH);
		lowerPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

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

	@Override
	public void actionPerformed(ActionEvent e) {
		
		boolean flag = false;
		
		if (e.getSource() == loginBtn) {

			String pw = String.valueOf(passPf.getPassword());
			String sql = "select * from account where username='"+ usernameTf.getText() +"' and password='"+ pw +"'";
			con.rs = con.execQuery(sql);
			
			try {
				while(con.rs.next()) {
					if(usernameTf.getText().equals(con.rs.getString("username"))&&pw.equals(con.rs.getString("password"))) {
						JOptionPane.showMessageDialog(this, "Login Success!  Welcome, "+ con.rs.getString("username"),"Message",JOptionPane.INFORMATION_MESSAGE);
						flag = true;

						Account.setAccountID(con.rs.getString("accountID"));
						try {
							if(con.rs.getString("role").equals("Admin")) {
								this.dispose();
								new AdminMainForm();
								
							}else if(con.rs.getString("role").equals("User")) {
								new UserMainForm();
								this.dispose();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}	
						break;
					}
				}	
				if (flag==false) {
					JOptionPane.showMessageDialog(this, "Login Failed!","Warning Message", JOptionPane.WARNING_MESSAGE);
				} 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()==signupLbl) {
			this.dispose();
			new RegisterForm();
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