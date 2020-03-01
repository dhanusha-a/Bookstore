import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;



public class BookFrame extends JInternalFrame implements ActionListener{	
	Container c;
	JLabel title;
	JTable booktb;
	JPanel jp;
	JButton insert, update, delete, refresh;
	Connection con;
	
	Vector colname = new Vector();
	Vector data = new Vector();
	
	public BookFrame(){	
		//dont touch
		setVisible(true);
		setTitle("Book Details");	
		setClosable(true);
		
		setBackground(Color.decode("#26becc"));
		
		setBounds(200, 20, 900,650);
		c=getContentPane();
		
		jp = new JPanel();
		jp.setLayout(null);
		c.add(jp);
		
		//design
		jp.setBackground(Color.decode("#26becc"));	
		title= new JLabel("Book Details");
		title.setFont(new Font ("Monotype Corsiva",Font.BOLD,20));
		 
		jp.add(title);		
		
		//connection
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");		
			System.out.println("Connected");			
		}
		catch (Exception er) {
			System.out.println("Not Connected");
			System.out.println(er);
		}			
		
		jtb_mt();
		jp.add(booktb);
		
		//button
		insert = new JButton("Insert");
		update = new JButton("Update");
		delete = new JButton("Delete");
		refresh = new JButton("Refresh");
		
		jp.add(insert);
		jp.add(update);
		jp.add(delete);
		jp.add(refresh);	
		
		
		//layout setting
		title.setBounds(400, 20, 250, 20);
		booktb.setBounds(20, 65, 900, 450);
		insert.setBounds(80, 550, 100, 30);
		update.setBounds(190, 550, 100, 30);
		delete.setBounds(300, 550, 100, 30);
		refresh.setBounds(410, 550, 100, 30);
		
		//action on button
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InsertBook();
			}
		});
		
		//delete
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getting selected row data
				int gtrow = booktb.getSelectedRow();
				int del = Integer.parseInt(booktb.getModel().getValueAt(gtrow, 0).toString());
				System.out.println(del);
				
				try {
					PreparedStatement ps_del = con.prepareStatement("Delete from book where book_id = ?");
					ps_del.setInt(1, del);
					ps_del.executeUpdate();
					System.out.println("Deleted");
				}
				catch(Exception ondel) {
					System.out.println(ondel);
				}
			}
		});
		
		//refresh
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dm = (DefaultTableModel)booktb.getModel();
	            dm.getDataVector().removeAllElements();
	            System.out.println("Refreshed");
				jtb_mt();				
			}
		});
		
		//update
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertBook ib1 = new InsertBook(1);
				ib1.upd();
			}
		});
	}
	
	public void jtb_mt() {
		try {
		//read data
		Statement stmt = con.createStatement();
		ResultSet rs= stmt.executeQuery("Select * from book"); 
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		
		//get colname
		for(int i = 1; i <= cols; i++ ) {
			colname.addElement(rsmd.getColumnName(i));
		}
		
		//get row data
		data.addElement(colname);
		while (rs.next()){
			Vector row = new Vector(cols);
			
			for (int i =1; i<=cols; i++) {
				row.addElement(rs.getObject(i));
			}				
			data.addElement(row);
		} 
		
		//adding data to Jtable		
		booktb = new JTable(data,colname);		
		booktb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		
		catch(Exception tb) {
			System.out.println(tb);
			
		}			
	}	
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}