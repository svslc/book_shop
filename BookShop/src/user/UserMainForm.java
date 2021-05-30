package user;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import admin.ManageBookForm;
import form.LoginForm;

public class UserMainForm extends JFrame implements MouseListener {

	ImageIcon gmbr;
	JPanel panelGambar;
	JLabel gmbrLbl;
	JMenuBar menuBar;
	JMenu buyMenu, historyMenu, logoutMenu;
	JInternalFrame iframe;
	
	public UserMainForm() {
		
		iframe = new JInternalFrame();
		menuBar = new JMenuBar();
		buyMenu = new JMenu("Buy Books");
		logoutMenu = new JMenu("Logout");
		historyMenu = new JMenu("Transaction History");
		menuBar.add(buyMenu);
		menuBar.add(historyMenu);
		menuBar.add(logoutMenu);
		setJMenuBar(menuBar);
		
		//add actionlistener
		buyMenu.addMouseListener(this);
		historyMenu.addMouseListener(this);
		logoutMenu.addMouseListener(this);
		
		//add background picture
		panelGambar = new JPanel();
		gmbr= new ImageIcon("bookimage.jpg");
		gmbrLbl = new JLabel(gmbr);
		panelGambar.add(gmbrLbl);
		add(panelGambar);
		
		setTitle("Main Form");
		setSize(700,600);
//		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==logoutMenu) {
			this.dispose();
			new LoginForm();
		}else if(e.getSource()==buyMenu) {
			BuyBookForm mbf = new BuyBookForm();
			setContentPane(mbf.iFrame);
		}else if(e.getSource()==historyMenu) {
			TransactionForm trf = new TransactionForm();
			setContentPane(trf.iframe);
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
