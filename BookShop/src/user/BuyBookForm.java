package user;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import form.LoginForm;
import main.Account;
import connect.Connect;

public class BuyBookForm extends JFrame implements MouseListener, ActionListener{
	
	int stocksisa;
	String data;
	String[] headersBook = {"BookID","Book Name","Book Genre","Book Price","Book Stock"};
	String[] headersCart = {"BookID","Book Name","Book Genre","Book Price","Quantity", "SubTotal"};
	Vector<Object> content, contentcart;
	public JInternalFrame iFrame;
	JDesktopPane dpane;
	JTable tableBook, tableCart;
	DefaultTableModel dtm, dtmcart;
	JPanel panelUtama, panelAtas, panelTengah, panelBawah, panelTengahKiri, panelTengahKanan, panelTengahKananAtas, panelAddToCart, panelTigaButton;
	JScrollPane bookSpane, cartSpane;
	JLabel titleLbl, bookIDLbl, bookNameLbl, bookGenreLbl, bookPriceLbl, bookStockLbl, bookQtyLbl, removeIDLbl;
	JTextField bookIDTf, bookNameTf, bookGenreTf, bookPriceTf, bookStockTf, bookQtyTf, removeIDTf;
	JSpinner quantitySpinner;
	SpinnerNumberModel snm;
	JButton addcartBtn, removeBookBtn, clearCartBtn, checkoutBtn;
	ResultSet rs;
	Connect connect = new Connect();
	Account acc = new Account();

	public BuyBookForm() {
		initializeComponents();
		addComponentToPanel();
		connectBookData();
		cartTable();
		configureIFrame();
		addListener();
	}

	private void addListener() {
		tableBook.addMouseListener(this);
		tableCart.addMouseListener(this);
		clearCartBtn.addActionListener(this);
		addcartBtn.addActionListener(this);
		removeBookBtn.addActionListener(this);
		checkoutBtn.addActionListener(this);
	}
	
	private void initializeComponents() {

		dtm = new DefaultTableModel(headersBook, 0);
		dtmcart = new DefaultTableModel(headersCart, 0);
		tableBook = new JTable(dtm);
		tableCart = new JTable(dtmcart);
		tableBook.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookSpane = new JScrollPane(tableBook);
		cartSpane = new JScrollPane(tableCart);
		
		panelUtama = new JPanel(new BorderLayout());
		panelAtas = new JPanel(new BorderLayout());
		panelTengah = new JPanel(new GridLayout(1,2, 15, 15));
		panelTengah.setBorder(new EmptyBorder(15, 10, 15, 10));
		panelTengahKiri = new JPanel(new GridLayout(4,2,10,10));
		panelTengahKanan = new JPanel(new BorderLayout());
		panelTengahKananAtas = new JPanel(new GridLayout(3,2,10,10));
		panelAddToCart = new JPanel(new FlowLayout());
		panelAddToCart.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBawah = new JPanel(new BorderLayout());
		panelTigaButton = new JPanel(new FlowLayout());
		
		panelUtama.setBackground(new Color(255, 234, 167));
		panelAtas.setBackground(new Color(255, 234, 167));
		panelTengah.setBackground(new Color(255, 234, 167));
		panelTengahKiri.setBackground(new Color(255, 234, 167));
		panelTengahKanan.setBackground(new Color(255, 234, 167));
		panelTengahKananAtas.setBackground(new Color(255, 234, 167));
		panelAddToCart.setBackground(new Color(255, 234, 167));
		panelBawah.setBackground(new Color(255, 234, 167));
		panelTigaButton.setBackground(new Color(255, 234, 167));

		titleLbl = new JLabel("Buy Book");
		bookIDLbl = new JLabel("Book ID");
		bookNameLbl = new JLabel("Book Name");
		bookGenreLbl = new JLabel("Book Type");
		bookPriceLbl = new JLabel("Book Price");
		bookStockLbl = new JLabel("Book Stock");
		bookQtyLbl = new JLabel("Quantity");
		removeIDLbl = new JLabel("Remove ID");

		bookIDTf = new JTextField();
		bookNameTf = new JTextField();
		bookGenreTf = new JTextField();
		bookPriceTf = new JTextField();
		bookStockTf = new JTextField();
		removeIDTf = new JTextField();

		bookIDTf.setEditable(false);
		bookNameTf.setEditable(false);
		bookGenreTf.setEditable(false);
		bookPriceTf.setEditable(false);
		bookStockTf.setEditable(false);
		removeIDTf.setEditable(false);

		snm = new SpinnerNumberModel(0, 0, 1000, 1); 
		quantitySpinner = new JSpinner(snm);

		addcartBtn= new JButton("Add to Cart");
		removeBookBtn= new JButton("Remove Book");
		clearCartBtn= new JButton("Clear Cart");
		checkoutBtn = new JButton("Checkout");
	}
	
	private void addComponentToPanel() {
		//panel atas
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		titleLbl.setFont(new Font("Ariel", Font.PLAIN,24));
		panelAtas.add(titleLbl, BorderLayout.NORTH);
		panelAtas.add(bookSpane, BorderLayout.CENTER);
		panelAtas.setPreferredSize(new Dimension(300,150));

		//panel tengah kiri
		panelTengahKiri.add(bookIDLbl); panelTengahKiri.add(bookIDTf);
		panelTengahKiri.add(bookNameLbl); panelTengahKiri.add(bookNameTf);
		panelTengahKiri.add(bookGenreLbl); panelTengahKiri.add(bookGenreTf);
		panelTengahKiri.add(removeIDLbl); panelTengahKiri.add(removeIDTf);

		//panel tengah Kanan (atas + addcartBtn)
		panelTengahKananAtas.add(bookPriceLbl); panelTengahKananAtas.add(bookPriceTf);
		panelTengahKananAtas.add(bookStockLbl); panelTengahKananAtas.add(bookStockTf);
		panelTengahKananAtas.add(bookQtyLbl); panelTengahKananAtas.add(quantitySpinner);
		panelAddToCart.add(addcartBtn);
		panelTengahKanan.add(panelTengahKananAtas, BorderLayout.CENTER);
		panelTengahKanan.add(panelAddToCart, BorderLayout.SOUTH);

		//panelTengah
		panelTengah.add(panelTengahKiri); panelTengah.add(panelTengahKanan);
		panelTengah.setPreferredSize(new Dimension(300,200));

		//panelBawah
		panelTigaButton.add(removeBookBtn);panelTigaButton.add(clearCartBtn);panelTigaButton.add(checkoutBtn);
		panelBawah.add(cartSpane, BorderLayout.CENTER);
		panelBawah.add(panelTigaButton, BorderLayout.SOUTH);
		panelBawah.setPreferredSize(new Dimension(300, 150));

		//add panels to panelUtama
		panelUtama.add(panelAtas, BorderLayout.NORTH);
		panelUtama.add(panelTengah, BorderLayout.CENTER);
		panelUtama.add(panelBawah, BorderLayout.SOUTH);
	}
	
	private void configureIFrame() {
		iFrame = new JInternalFrame();
		iFrame.setTitle("Buy Book Form");
		iFrame.setSize(600,600);
		iFrame.setVisible(true);
		iFrame.setClosable(true);
		iFrame.add(panelUtama);
		dpane = new JDesktopPane();
		dpane.add(iFrame);
		setContentPane(dpane);
	}
	
	private void connectBookData() {
		
		for(int i=dtm.getRowCount()-1;i>-1;i--) {
			dtm.removeRow(i);
		}
		try {
			data = "select * from Book";
			connect.rs = connect.execQuery(data);
			while(connect.rs.next()) {
				content = new Vector<Object>();
				for(int i =1;i<=connect.rsmd.getColumnCount();i++) {
					content.add(connect.rs.getObject(i)+"");
				}
				dtm.addRow(content);
			}
			tableBook.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dtm.fireTableDataChanged();
	}
	
	private void cartTable() {
		contentcart = new Vector<Object>();
		for(int i=dtmcart.getRowCount()-1;i>-1;i--) {
			dtmcart.removeRow(i);
		}
		try {
			String dataTampilan = "Select c.bookID, bookName, bookGenre, bookPrice, quantity, (bookPrice*quantity) from cart c join book b on c.BookID = b.BookID";
			connect.rs = connect.execQuery(dataTampilan);
			while(connect.rs.next()) {
				contentcart = new Vector<Object>();
				for(int i =1;i<=connect.rsmd.getColumnCount();i++) {
					contentcart.add(connect.rs.getObject(i)+"");
				}
				dtmcart.addRow(contentcart);
			}
			tableCart.setModel(dtmcart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dtmcart.fireTableDataChanged();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if(tableBook.getSelectedRow()!=-1) {
			bookIDTf.setText(tableBook.getValueAt(tableBook.getSelectedRow(), 0).toString());
			bookNameTf.setText(tableBook.getValueAt(tableBook.getSelectedRow(), 1).toString());
			bookGenreTf.setText(tableBook.getValueAt(tableBook.getSelectedRow(), 2).toString());
			bookPriceTf.setText(tableBook.getValueAt(tableBook.getSelectedRow(), 3).toString());
			bookStockTf.setText(tableBook.getValueAt(tableBook.getSelectedRow(), 4).toString());
		}else if(tableCart.getSelectedRow()!=-1) {
			removeIDTf.setText(tableCart.getValueAt(tableCart.getSelectedRow(), 0).toString());
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
		
		if(e.getSource()==addcartBtn) {
			if(bookIDTf.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please choose a book first!", "Warning Message", JOptionPane.WARNING_MESSAGE);
			}else if((Integer) snm.getValue() == 0  || (Integer) snm.getValue() > Integer.valueOf(bookStockTf.getText())) {
				JOptionPane.showMessageDialog(this, "Quantity must not be 0 or more than book stock!","Warning Message", JOptionPane.WARNING_MESSAGE);
			}else if(Integer.valueOf(bookStockTf.getText()) == 0) {
				JOptionPane.showMessageDialog(this, "There is no more stock for this book!","Warning Message", JOptionPane.WARNING_MESSAGE);
			}else {
				boolean flag=true;
				for (int i = 0; i < dtmcart.getRowCount(); i++) {
					if (dtmcart.getValueAt(i, 0).equals(bookIDTf.getText())) {
						// update data cart (UPDATE QTY)
						int updateQty = Integer.valueOf(dtmcart.getValueAt(i, 4).toString())+(Integer) snm.getValue();
						String updateQuery = "update Cart set Quantity ="+ updateQty+" where accountID like'"+ Account.getAccountID()+"' and BookID like '"+bookIDTf.getText()+"'";
						connect.execUpdate(updateQuery);
						updateStockBook();
						connectBookData();
						cartTable();
						flag=false;
						break;
					}
				}			
				if(flag==true) {
					// data cart baru (INSERT)
					//masukin data cart ke db
					data = "Insert into Cart(accountID, bookID, Quantity) values ('" + Account.getAccountID() + "','"
							+ bookIDTf.getText() + "'," + (Integer) snm.getValue() + ")";
					connect.execUpdate(data);
					// update book stock table Book db				
					updateStockBook();
					//refresh table book dan cart
					connectBookData();
					cartTable();
				}
			}
			bookIDTf.setText("");
			bookNameTf.setText("");
			bookGenreTf.setText("");
			bookPriceTf.setText("");
			bookStockTf.setText("");
			quantitySpinner.setValue(0);
			
		}else if(e.getSource()==removeBookBtn) {
			if(!removeIDTf.getText().isEmpty()) {
				int removeBook;
				removeBook = JOptionPane.showConfirmDialog(this, "Are you sure want to remove the book?","Confirmation Message",JOptionPane.YES_NO_OPTION);
				if(removeBook == JOptionPane.YES_OPTION) {
					//buat refresh stock
					updateStockBookBuatRemoveBook();
					//refresh table book dan cart
					connectBookData();
					cartTable();
				}
			}
		}else if(e.getSource()==clearCartBtn) {
			int clearCart;
			clearCart = JOptionPane.showConfirmDialog(this, "Are you sure want to clear the cart?","Confirmation Message",JOptionPane.YES_NO_OPTION);
			if(clearCart==JOptionPane.YES_OPTION) {
				clearCart();
				connectBookData();
				cartTable();
			}
		}else if(e.getSource()==checkoutBtn) {
			int checkout;
			checkout = JOptionPane.showConfirmDialog(this, "Are you sure want to checkout?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
			if(checkout==JOptionPane.YES_OPTION) {
				
				JOptionPane.showMessageDialog(this, "Checkout Success!", "Message",JOptionPane.INFORMATION_MESSAGE);
				
				//query buat header transaction
				String insertHeader;
				int idTransaksi = 0;
				String nomorTransaksi="";
				
				//cari tau jumlah transaksi yang pernah ada di db
				String countJumlahTransaksi;
				try {
					countJumlahTransaksi = "select * from HeaderTransaction";
					connect.rs = connect.execQuery(countJumlahTransaksi);
					while(connect.rs.next()) {
						idTransaksi++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(idTransaksi<10) {
					nomorTransaksi="HT00"+ (idTransaksi+1)+"";
				}else if(idTransaksi<100) {
					nomorTransaksi="HT0"+(idTransaksi+1)+"";
				}else if(idTransaksi<999) {
					nomorTransaksi ="HT"+(idTransaksi+1)+"";
				}
				
				DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDateTime local = LocalDateTime.now();
				insertHeader ="Insert into HeaderTransaction(transactionID, accountID, transactionDate) values ('"+nomorTransaksi+"','"+Account.getAccountID()+"','"+ formatDateTime.format(local)+"')";
				connect.execUpdate(insertHeader);

				// query buat detail transaction
				for(int i =0;i<dtmcart.getRowCount();i++) {
					String insertDetail;
					insertDetail ="Insert into DetailTransaction(transactionID, bookID, quantity) values ('"+ nomorTransaksi+"','"+dtmcart.getValueAt(i, 0)+"','"+dtmcart.getValueAt(i, 4)+"')";
					connect.execUpdate(insertDetail);
				}
			}
			//delete semua data from cart
			for(int i =dtmcart.getRowCount()-1;i>-1;i--) {
				data = "Delete from Cart where accountID like '"+ Account.getAccountID()+"'and bookID like '"+dtmcart.getValueAt(i, 0)+"'" ;
				connect.execUpdate(data);
			}
			cartTable();
		}
	}
	
	private void updateStockBook() {
		stocksisa = (Integer.valueOf(bookStockTf.getText()) - (Integer) snm.getValue());
		data = "Update Book set BookStock ="+ stocksisa +" where BookID like '"
				+ tableBook.getValueAt(tableBook.getSelectedRow(), 0).toString() + "'";
		connect.execUpdate(data);
	}
	
	private void updateStockBookBuatRemoveBook() {
		for(int i =0;i<dtm.getRowCount();i++) {
			if(dtm.getValueAt(i, 0).equals(tableCart.getValueAt(tableCart.getSelectedRow(), 0).toString())) {
				//update stock book pada table book
				stocksisa = Integer.valueOf(tableCart.getValueAt(tableCart.getSelectedRow(), 4).toString()) +Integer.valueOf(tableBook.getValueAt(i, 4).toString());
				data = "Update Book set BookStock ="+ stocksisa +" where BookID like '" + tableCart.getValueAt(tableCart.getSelectedRow(), 0).toString() + "'";
				connect.execUpdate(data);
				//delete data from cart
				data = "Delete from Cart where BookID like '"+ tableCart.getValueAt(tableCart.getSelectedRow(), 0).toString()+"' and accountID like '"+ acc.getAccountID()+"'";
				connect.execUpdate(data);
				removeIDTf.setText("");
			}
		}
	}
	
	private void clearCart() {
		for(int i=0;i<dtmcart.getRowCount();i++) {
			for(int j=0;j<dtm.getRowCount();j++) {
				if(dtmcart.getValueAt(i, 0).equals(dtm.getValueAt(j, 0))) {
					//update stock book pada table book
					stocksisa = Integer.valueOf(tableCart.getValueAt(i, 4).toString()) +Integer.valueOf(tableBook.getValueAt(j, 4).toString());
					data = "Update book set bookStock ="+ stocksisa +" where bookID like '"+ tableCart.getValueAt(i, 0).toString() + "'";
					connect.execUpdate(data);
					break;
				}
			}
		}
		//delete semua data from cart
		data = "Delete from Cart where accountID like '"+ Account.getAccountID()+"'";
		connect.execUpdate(data);
	}
}
