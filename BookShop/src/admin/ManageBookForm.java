package admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connect.Connect;

public class ManageBookForm extends JFrame implements ActionListener, MouseListener{

	boolean flag = true;
	int pilihan;
	String query, generateBookID;
	String[] headerTable = {"BookID","Book Name","Book Genre","Book Price","Book Stock"};
	String[] listBookGenre = {"--Choose One--","Comedy","Romance","Horror"};
	String[] newListBookGenre = {"--Choose One--","Comedy","Romance","Horror"};
	Vector<Object> content, header;
	JPanel panelUtama, panelAtas, panelBawah, panelBawahKiri, panelBawahKanan, panelStock, panelBawahKananAtas, panelBawahKananBawah;
	JLabel titleLbl, bookIDLbl, bookNameLbl, bookGenreLbl, bookPriceLbl, bookStockLbl, addStockLbl, newBookIDLbl, newBookNameLbl, newBookGenreLbl, newBookPriceLbl, newBookStockLbl;
	JTable bookTable;
	DefaultTableModel bookDTM;
	JScrollPane bookSpane;
	JTextField bookIDTf, bookNameTf, bookPriceTf, bookStockTf, newBookIDTf, newBookNameTf, newBookPriceTf;
	JComboBox<String> bookGenreCb, newBookGenreCb;
	JSpinner addStockSpinner, newBookStockSpinner;
	JButton updateBookBtn, removeBookBtn, addStockBtn, insertBookBtn, resetBtn;
	SpinnerNumberModel addStockSNM, newBookStockSNM;
	public JInternalFrame iframe;
	JDesktopPane dpane;
	Connect connect = new Connect();
	
	public ManageBookForm() {
		initialize();
		addComponentsToPanel();
		connectBookData();
		addListener();
	}

	private void addListener() {
		bookTable.addMouseListener(this);
		updateBookBtn.addActionListener(this);
		removeBookBtn.addActionListener(this);
		addStockBtn.addActionListener(this);
		insertBookBtn.addActionListener(this);
		resetBtn.addActionListener(this);
	}
	
	private void initialize() {
		content = new Vector<>();
		header = new Vector<>();
		
		panelUtama = new JPanel(new BorderLayout());
		panelAtas = new JPanel(new BorderLayout());
		panelBawah = new JPanel(new GridLayout(1,2, 10, 10));
		panelBawahKiri = new JPanel(new GridLayout(7,2,10,10));
		panelStock = new JPanel(new GridLayout(1,3,10,10));
		panelBawahKanan = new JPanel(new BorderLayout());
		panelBawahKananAtas = new JPanel(new GridLayout(5,2,10,10));
		panelBawahKananBawah = new JPanel(new GridLayout(2,1,10,10));
		
		//set warna
		panelUtama.setBackground(new Color(255, 234, 167));
		panelAtas.setBackground(new Color(255, 234, 167));
		panelBawah.setBackground(new Color(255, 234, 167));
		panelBawahKiri.setBackground(new Color(255, 234, 167));
		panelStock.setBackground(new Color(255, 234, 167));
		panelBawahKanan.setBackground(new Color(255, 234, 167));
		panelBawahKananAtas.setBackground(new Color(255, 234, 167));
		panelBawahKananBawah.setBackground(new Color(255, 234, 167));

		titleLbl = new JLabel("Manage Book");
		titleLbl.setFont(new Font("Ariel", Font.PLAIN,24));
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		bookIDLbl = new JLabel("Book ID");
		bookNameLbl = new JLabel("Book Name");
		bookGenreLbl = new JLabel("Book Genre");
		bookPriceLbl = new JLabel("Book Price");
		bookStockLbl = new JLabel("Book Stock");
		addStockLbl = new JLabel("Add Stock");
		newBookIDLbl = new JLabel("New Book ID");
		newBookNameLbl = new JLabel("New Book Name");
		newBookGenreLbl = new JLabel("New Book Genre");
		newBookPriceLbl = new JLabel("New Book Price");
		newBookStockLbl = new JLabel("New Book Stock");

		bookIDTf = new JTextField();
		bookNameTf = new JTextField();
		bookPriceTf = new JTextField();
		bookStockTf = new JTextField();
		newBookIDTf = new JTextField();
		newBookNameTf = new JTextField();
		newBookPriceTf = new JTextField();
		bookIDTf.setEditable(false);
		bookStockTf.setEditable(false);
		newBookIDTf.setEditable(false);
		newBookIDTf.setText(generateBookID);
		
		bookDTM = new DefaultTableModel(headerTable, 0);
		bookTable = new JTable(bookDTM);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookSpane = new JScrollPane(bookTable);
		bookSpane.setSize(100, 100);
		bookSpane.setPreferredSize(new Dimension(200,200));
		
		bookGenreCb = new JComboBox<String>(listBookGenre);
		newBookGenreCb = new JComboBox<String>(newListBookGenre);
		addStockSNM = new SpinnerNumberModel(0, 0, 10000, 1);
		newBookStockSNM = new SpinnerNumberModel(0, 0, 10000, 1);
		addStockSpinner = new JSpinner(addStockSNM);
		newBookStockSpinner = new JSpinner(newBookStockSNM);
		
		updateBookBtn = new JButton("Update Book");
		removeBookBtn = new JButton("Remove Book");
		addStockBtn = new JButton("Add");
		insertBookBtn = new JButton("Insert Book");
		resetBtn = new JButton("Reset");
		iframe = new JInternalFrame();
		dpane = new JDesktopPane();
	}
	
	private void addComponentsToPanel() {
		
		//panelBawahKiriBawah
		panelStock.add(addStockSpinner);
		panelStock.add(addStockBtn);

		//panelBawahKiriAtas
		panelBawahKiri.add(bookIDLbl); panelBawahKiri.add(bookIDTf);
		panelBawahKiri.add(bookNameLbl); panelBawahKiri.add(bookNameTf);
		panelBawahKiri.add(bookGenreLbl); panelBawahKiri.add(bookGenreCb);
		panelBawahKiri.add(bookPriceLbl); panelBawahKiri.add(bookPriceTf);
		panelBawahKiri.add(bookStockLbl); panelBawahKiri.add(bookStockTf);
		panelBawahKiri.add(addStockLbl); panelBawahKiri.add(panelStock);
		panelBawahKiri.add(updateBookBtn); panelBawahKiri.add(removeBookBtn);

		//panelBawahKananAtas
		panelBawahKananAtas.add(newBookIDLbl);panelBawahKananAtas.add(newBookIDTf);
		panelBawahKananAtas.add(newBookNameLbl);panelBawahKananAtas.add(newBookNameTf);
		panelBawahKananAtas.add(newBookGenreLbl);panelBawahKananAtas.add(newBookGenreCb);
		panelBawahKananAtas.add(newBookPriceLbl);panelBawahKananAtas.add(newBookPriceTf);
		panelBawahKananAtas.add(newBookStockLbl);panelBawahKananAtas.add(newBookStockSpinner);
		
		//panelBawahKananBawah
		panelBawahKananBawah.add(insertBookBtn);
		panelBawahKananBawah.add(resetBtn);
		
		//panelBawahKanan
		panelBawahKanan.add(panelBawahKananAtas, BorderLayout.NORTH);
		panelBawahKanan.add(panelBawahKananBawah, BorderLayout.SOUTH);

		//panelBawah
		panelBawah.add(panelBawahKiri);
		panelBawah.add(panelBawahKanan);
		panelBawah.setBorder(new EmptyBorder(20, 20, 30 ,20));
		
		//panelAtas
		panelAtas.add(titleLbl, BorderLayout.NORTH);
		panelAtas.add(bookSpane, BorderLayout.CENTER);
		
		//panelUtama
		panelUtama.add(panelAtas, BorderLayout.NORTH);
		panelUtama.add(panelBawah, BorderLayout.SOUTH);
		
		//configure IFrame
		iframe.setTitle("Manage Book Form");
		iframe.setSize(600,600);
		iframe.setClosable(true);
		iframe.setVisible(true);
		iframe.add(panelUtama);
		dpane.add(iframe);
		setContentPane(dpane);
	}
	
	private void connectBookData() {
		for(int i = bookDTM.getRowCount()-1; i>-1;i--) {
			bookDTM.removeRow(i);
		}
		try {
			String data = "select * from Book";
			connect.rs = connect.execQuery(data);
			while(connect.rs.next()) {
				content = new Vector<Object>();
				for(int i=1;i<=connect.rsmd.getColumnCount();i++) {
					content.add(connect.rs.getObject(i)+"");
				}
				bookDTM.addRow(content);
			}
			bookTable.setModel(bookDTM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<headerTable.length;i++) {
			header.add(headerTable[i]);
		}	
		bookDTM.fireTableDataChanged();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(bookTable.getSelectedRow()!=-1) {
			bookIDTf.setText(bookTable.getValueAt(bookTable.getSelectedRow(), 0).toString());
			bookNameTf.setText(bookTable.getValueAt(bookTable.getSelectedRow(), 1).toString());
			bookGenreCb.setSelectedItem(bookTable.getValueAt(bookTable.getSelectedRow(), 2).toString());
			bookPriceTf.setText(bookTable.getValueAt(bookTable.getSelectedRow(), 3).toString());
			bookStockTf.setText(bookTable.getValueAt(bookTable.getSelectedRow(), 4).toString());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==updateBookBtn) {
			flag=true;
			validasiBookID();
			validasiBookName();
			validasiBookGenre();
			validasiBookPrice();
			if(flag==true) {
				pilihan = JOptionPane.showConfirmDialog(this, "Are you sure want to update the book data?","Confirmation Message",JOptionPane.YES_NO_OPTION);
				if(pilihan == JOptionPane.YES_OPTION) {
					query = "Update book set bookName = '"+ bookNameTf.getText() +"', bookGenre = '"+ bookGenreCb.getSelectedItem().toString() +"', bookPrice ="+ Integer.valueOf(bookPriceTf.getText()) +" where bookID like '"+ bookIDTf.getText()+"'";
					connect.execUpdate(query);
					connectBookData();
					refreshFieldKiri();
				}
			}
		}else if(e.getSource()==removeBookBtn) {
			flag = true;
			validasiBookID();
			
			if(flag==true) {
				pilihan = JOptionPane.showConfirmDialog(this, "Are you sure want to remove this book?","Confirmation Message",JOptionPane.YES_NO_OPTION);
				if(pilihan==JOptionPane.YES_OPTION) {
					query ="Delete from book where bookID like '"+ bookIDTf.getText() +"'";
					connect.execUpdate(query);
					connectBookData();
					refreshFieldKiri();
				}
			}
		}else if(e.getSource()==addStockBtn) {
			flag = true;
			validasiBookID();
			validasiStock();
			
			if(flag==true) {
				pilihan = JOptionPane.showConfirmDialog(this, "Are you sure want to add the book stock?", "Confirmation Message",JOptionPane.YES_NO_OPTION);
				if(pilihan == JOptionPane.YES_OPTION) {
					query ="Update Book set BookStock = "+ (Integer.valueOf(bookStockTf.getText().toString())+ Integer.valueOf(addStockSNM.getValue().toString()))+" where BookID like '"+ bookIDTf.getText()+"'";
					connect.execUpdate(query);
					connectBookData();
					refreshFieldKiri();
				}
			}
			
		}else if(e.getSource()==insertBookBtn) {
			flag=true;
			validasiNewBookName();
			validasiNewBookType();
			validasiNewBookPrice();
			validasiNewStock();
			if(flag==true) {
				pilihan = JOptionPane.showConfirmDialog(this, "Are you sure want to insert new book?","Confirmation Message",JOptionPane.YES_NO_OPTION);
				if(pilihan == JOptionPane.YES_OPTION) {
					Random rand = new Random();
					String newBookID = "BO" + (char) (rand.nextInt(26) + 'A') + (char) (rand.nextInt(26) + 'A') + (char) (rand.nextInt(26) + 'A') + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + "";
					query = "Insert into book(bookID, bookName, bookGenre, bookPrice, bookStock) values ('"+ newBookID +"','"+ newBookNameTf.getText()+"','"+ newBookGenreCb.getSelectedItem().toString() +"',"+ Integer.valueOf(newBookPriceTf.getText().toString())+","+ Integer.valueOf(newBookStockSNM.getValue().toString()) +")";
					connect.execUpdate(query);
					connectBookData();
					refreshFieldKanan();
				}
			}
		}else if(e.getSource()==resetBtn) {
			refreshFieldKiri();
			refreshFieldKanan();
		}
	}
	
	//validate
	//1. bookID not empty
	private void validasiBookID() {
		if(bookIDTf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please choose a book first!","Warning Message", JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//2. bookname not empty
	private void validasiBookName() {
		if(bookNameTf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Book name must not be empty!","Warning Message", JOptionPane.WARNING_MESSAGE);
			flag =false;
		}
	}
	
	//3. bookgenre not choosen
	private void validasiBookGenre() {
		if(bookGenreCb.getSelectedItem().equals("--Choose One--")) {
			JOptionPane.showMessageDialog(this, "Book genre must be choosen", "Warning Message",JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//4. bookprice	1000-1000000
	private void validasiBookPrice() {
		if(bookPriceTf.getText().isEmpty()||Integer.valueOf(bookPriceTf.getText().toString())<1000||Integer.valueOf(bookPriceTf.getText().toString())>1000000) {
			JOptionPane.showMessageDialog(this, "Book price must between 1000-1000000", "Warning Message",JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//5. Validasi Stock
	private void validasiStock() {
		if(Integer.valueOf(addStockSNM.getValue().toString())==0){
			JOptionPane.showMessageDialog(this, "Add stock!", "Warning Message", JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//1. Validasi new bookname
	private void validasiNewBookName() {
		if(newBookNameTf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "New book name must not be empty!","Warning Message", JOptionPane.WARNING_MESSAGE);
			flag =false;
		}
	}
	
	//2. Validasi new booktype
	private void validasiNewBookType() {
		if(newBookGenreCb.getSelectedItem().equals("--Choose One--")) {
			JOptionPane.showMessageDialog(this, "New book type must be choosen!", "Warning Message",JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//3. validasi new bookprice 1000-1000000
		private void validasiNewBookPrice() {
			if(newBookPriceTf.getText().isEmpty()||Integer.valueOf(newBookPriceTf.getText().toString())<1000||Integer.valueOf(newBookPriceTf.getText().toString())>1000000) {
				JOptionPane.showMessageDialog(this, "New bookprice must between 1000-1000000", "Warning Message",JOptionPane.WARNING_MESSAGE);
				flag=false;
			}
		}
		
	//4. Validasi new Stock
	private void validasiNewStock() {
		if(Integer.valueOf(newBookStockSNM.getValue().toString())==0){
			JOptionPane.showMessageDialog(this, "Add stock!", "Warning Message", JOptionPane.WARNING_MESSAGE);
			flag=false;
		}
	}
	
	//refresh field kiri
	private void refreshFieldKiri() {
		bookIDTf.setText("");
		bookNameTf.setText("");
		bookGenreCb.setSelectedItem("--Choose One--");
		bookPriceTf.setText("");
		bookStockTf.setText("");
	}
	
	//refresh field kanan
	private void refreshFieldKanan() {
		newBookIDTf.setText(generateBookID);
		newBookNameTf.setText("");
		newBookGenreCb.setSelectedItem("--Choose One--");
		newBookPriceTf.setText("");
		newBookStockSNM.setValue(1);
	}
}
