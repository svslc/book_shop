package user;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import connect.Connect;
import main.Account;

public class TransactionForm extends JFrame implements MouseListener{
	
	String data;
	String[] columnHeader={"TransactionID", "AccountID","Transaction Date"};
	String[] columnDetail={"TransactionID","BookID","Book Name","Book Genre","Book Price","Quantity","Subtotal"};
	Vector<Object> contentHeader, contentDetail;
	JPanel panelUtama, panelAtas, panelBawah, panelSelectedID, panelGrandTotal, panelBorderSelected, panelBorderGrand;
	JLabel titleLbl, selectedIDLbl, grandTotalLbl;
	JTable headerTable, detailTable;
	JScrollPane headerSpane, detailSpane;
	JTextField selectedIDTf, grandTotalTf;
	DefaultTableModel headerDTM, detailDTM;
	public JInternalFrame iframe;
	JDesktopPane dpane;
	Connect connect = new Connect();
	Account acc = new Account();
	
	public TransactionForm() {
		initialize();
		addComponentsToPanel();
		showDataHeader();
		headerTable.addMouseListener(this);
	}
	
	private void initialize() {
		panelUtama = new JPanel(new BorderLayout());
		panelAtas = new JPanel(new BorderLayout());
		panelBawah = new JPanel(new BorderLayout());
		panelSelectedID = new JPanel(new FlowLayout());
		panelGrandTotal = new JPanel(new FlowLayout());
		panelBorderSelected = new JPanel(new BorderLayout());
		panelBorderGrand = new JPanel(new BorderLayout());
		
		//set background
		panelUtama.setBackground(new Color(255, 234, 167));
		panelAtas.setBackground(new Color(255, 234, 167));
		panelBawah.setBackground(new Color(255, 234, 167));
		panelSelectedID.setBackground(new Color(255, 234, 167));
		panelGrandTotal.setBackground(new Color(255, 234, 167));
		panelBorderSelected.setBackground(new Color(255, 234, 167));
		panelBorderGrand.setBackground(new Color(255, 234, 167));
		
		titleLbl = new JLabel("Transaction History");
		titleLbl.setFont(new Font("Ariel", Font.PLAIN,24));
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		selectedIDLbl = new JLabel("Selected ID");
		grandTotalLbl = new JLabel("Grand Total");
		
		headerDTM = new DefaultTableModel(columnHeader, 0);
		detailDTM = new DefaultTableModel(columnDetail, 0);
		headerTable = new JTable(headerDTM);
		detailTable = new JTable(detailDTM);
		headerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		headerSpane = new JScrollPane(headerTable);
		detailSpane = new JScrollPane(detailTable);

		selectedIDTf = new JTextField();
		grandTotalTf = new JTextField();
		selectedIDTf.setPreferredSize(new Dimension(180,20));
		grandTotalTf.setPreferredSize(new Dimension(180,20));
		selectedIDTf.setEditable(false);
		grandTotalTf.setEditable(false);
		
		iframe = new JInternalFrame();
		dpane = new JDesktopPane();
	}
	
	private void addComponentsToPanel() {
		//panelAtas
		panelAtas.add(titleLbl, BorderLayout.NORTH);
		panelAtas.add(headerSpane, BorderLayout.CENTER);
		panelAtas.setPreferredSize(new Dimension(300,200));
		
		//panel selectedID
		panelSelectedID.add(selectedIDLbl);
		panelSelectedID.add(selectedIDTf);
		panelBorderSelected.add(panelSelectedID, BorderLayout.WEST);
		
		//panel grandtotal
		panelGrandTotal.add(grandTotalLbl);
		panelGrandTotal.add(grandTotalTf);
		panelBorderGrand.add(panelGrandTotal, BorderLayout.EAST);
		
		//panelBawah
		panelBawah.add(panelBorderSelected, BorderLayout.NORTH);
		panelBawah.add(detailSpane, BorderLayout.CENTER);
		panelBawah.add(panelBorderGrand, BorderLayout.SOUTH);
		panelBawah.setPreferredSize(new Dimension(300,300));
		
		//panelUtama
		panelUtama.add(panelAtas, BorderLayout.NORTH);
		panelUtama.add(panelBawah, BorderLayout.SOUTH);
		
		//configure iframe
		iframe.setTitle("Transaction History Form");
		iframe.setSize(600,600);
		iframe.setClosable(true);
		iframe.setVisible(true);
		iframe.add(panelUtama);
		dpane.add(iframe);
		setContentPane(dpane);
	}
	
	private void showDataHeader() {
		try {
			data ="Select * from headertransaction";
			connect.rs = connect.execQuery(data);
			while(connect.rs.next()) {
				contentHeader = new Vector<Object>();
				for(int i =1;i<=connect.rsmd.getColumnCount();i++) {
					contentHeader.add(connect.rs.getObject(i)+"");
				}
				headerDTM.addRow(contentHeader);
			}
			headerTable.setModel(headerDTM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(headerTable.getSelectedRow()!=-1) {
			selectedIDTf.setText(headerTable.getValueAt(headerTable.getSelectedRow(), 0).toString());
			showDataDetail();
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
	
	private void showDataDetail() {
		for(int i = detailDTM.getRowCount()-1;i>-1;i--) {
			detailDTM.removeRow(i);
		}
		try {
			data ="Select dt.transactionID, dt.bookID, bookName, bookGenre, bookPrice, quantity, (bookPrice*quantity) from detailtransaction dt join book b on dt.bookID = b.bookID join headertransaction ht on ht.transactionID = dt.transactionID where dt.transactionID like '"+ selectedIDTf.getText() +"' and accountID like '"+ Account.getAccountID() +"'";
			connect.rs = connect.execQuery(data);
			
			while(connect.rs.next()) {
				contentDetail = new Vector<Object>();
				for(int i =1;i<=connect.rsmd.getColumnCount();i++) {
					contentDetail.add(connect.rs.getObject(i)+"");
				}
				detailDTM.addRow(contentDetail);
			}
			detailTable.setModel(detailDTM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		detailDTM.fireTableDataChanged();
		
		int total=0;
		for(int i =0;i<detailDTM.getRowCount();i++) {
			total += Integer.valueOf(detailTable.getValueAt(i, 6).toString());
		}
		grandTotalTf.setText(String.valueOf(total));
	}
}

