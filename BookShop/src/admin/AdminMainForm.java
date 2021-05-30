package admin;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import form.LoginForm;

public class AdminMainForm extends JFrame implements MouseListener{

	ImageIcon gmbr;
	JPanel panelGambar;
	JLabel gmbrLbl;
	JMenuBar menuBar;
	JMenu manageMenu, logoutMenu;
	JInternalFrame iframe;
	
	public AdminMainForm() {
		
		iframe = new JInternalFrame();
		menuBar = new JMenuBar();
		manageMenu = new JMenu("Manage Books");
		logoutMenu = new JMenu("Logout");
		menuBar.add(manageMenu);
		menuBar.add(logoutMenu);
		setJMenuBar(menuBar);
		
		//add actionlistener
		manageMenu.addMouseListener(this);
		logoutMenu.addMouseListener(this);
		
		//add background picture
		panelGambar = new JPanel();
		gmbr= new ImageIcon("bookimage.jpg");
		gmbrLbl = new JLabel(gmbr);
		panelGambar.add(gmbrLbl);
		add(panelGambar);
		
		setTitle("Main Form");
		setSize(700,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==logoutMenu) {
			this.dispose();
			new LoginForm();
		}else if(e.getSource()==manageMenu) {
			ManageBookForm mbf = new ManageBookForm();
			setContentPane(mbf.iframe);
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
