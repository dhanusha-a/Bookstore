import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class InsertBook extends JFrame{

	Container c = getContentPane();
	JPanel jp1= new JPanel(); 
	JLabel l1, l2, l3,l4, l5, l6, l7, l8, l9;
	JTextField isbn, name, publ, price, edi;
	JComboBox lang,author, cat; 
	JButton submit, reset;
	Connection con;
	
	public InsertBook(int x) {
		
	}
	
	public InsertBook(){	
		//Don't touch
		setVisible(true);
		setTitle("Book Details");
		jp1.setLayout(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		setBounds(50, 50, 750,700);
		c.add(jp1);
		
		//design
		jp1.setBackground(Color.decode("#eddaa8"));		
		l1 = new JLabel("Insert Book Details");
		l1.setFont(new Font("Segoe UI", Font.BOLD,20));
		l1.setBounds(250,20,400,30);
		jp1.add(l1);
		
		//labels
		l2= new JLabel("ISBN NO :");		l2.setBounds(200,70,150,30); 	jp1.add(l2);
		l3= new JLabel("BOOK NAME :");		l3.setBounds(200,110,150,30); 	jp1.add(l3);
		l4= new JLabel("AUTHOR :");		l4.setBounds(200,150,150,30); 	jp1.add(l4);
		l5= new JLabel("CATEGORY :");	l5.setBounds(200,190,150,30); 	jp1.add(l5);
		l6= new JLabel("PUBLISHER :");		l6.setBounds(200,230,150,30); 	jp1.add(l6);
		l7= new JLabel("LANGUAGE :");		l7.setBounds(200,270,150,30); 	jp1.add(l7);
		l8= new JLabel("EDITION :");		l8.setBounds(200,310,150,30); 	jp1.add(l8);
		l9= new JLabel("PRICE :");			l9.setBounds(200,350,150,30); 	jp1.add(l9);
		
		//real fields
		isbn = new JTextField();			isbn.setBounds(350, 70, 200, 30);	jp1.add(isbn);
		name = new JTextField();			name.setBounds(350, 110, 200, 30);  jp1.add(name);		
		publ = new JTextField();			publ.setBounds(350, 230, 200, 30);  jp1.add(publ);
		edi = new JTextField();				edi.setBounds(350, 310, 200, 30);	jp1.add(edi);
		price = new JTextField();			price.setBounds(350, 350, 200, 30);  jp1.add(price);		
		
		String language[] = {"English","French","Spanish","Japanese"};
		lang = new JComboBox<>(language);	lang.setBounds(350,270,200,30); 	jp1.add(lang);
		
		//buttons
		submit = new JButton("Insert");		submit.setBounds(250, 450, 100, 35);		jp1.add(submit);
		reset = new JButton("Reset");		reset.setBounds(400, 450, 100, 35);		jp1.add(reset);
		
		//connection
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");		
					System.out.println("Connected");
				}
				catch(Exception er) {
					System.out.println("Not Connected");
					System.out.println(er);
				}	
		
		//retriving dymamic data for combobox
		author = new JComboBox();	author.setBounds(350,150,200,30); 	jp1.add(author);
				
		try {
			ResultSet rs1 = con.createStatement().executeQuery("select author_name from author");
			while(rs1.next()) {
				String x = rs1.getString("author_name");
				author.addItem(x);				
			}
			rs1.close();
		}
		catch(Exception e2) {
			System.out.println(e2);
		}	
		
		
		//------------
		cat = new JComboBox<>();		cat.setBounds(350, 190, 200, 30);  jp1.add(cat);
		try {
			ResultSet rs2 = con.createStatement().executeQuery("select category_name from category");
			while(rs2.next()) {
				String x = rs2.getString("category_name");
				cat.addItem(x);				
			}
			rs2.close();
		}
		catch(Exception e2) {
			System.out.println(e2);
		}
			
		
		//insertion on insert button
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//getting input data
				String s1 = isbn.getText();
				String s2 = name.getText();
				int s3 = author.getSelectedIndex()+1; 
				int s4 = cat.getSelectedIndex()+1;
				String s5 = publ.getText();
				String s6 = (String)lang.getSelectedItem();
				int s7 = Integer.parseInt(edi.getText());
				int s8 = Integer.parseInt(price.getText());
				
				//System.out.println(s1+s2+s3+s4+s5);				
					
					//incrementing id
					try {
					int id=0;
					ResultSet rs= con.createStatement().executeQuery("Select * from book order by book_id desc limit 1");					
					rs.next();
					id=rs.getInt("book_id");
					System.out.println(id);
					id=id+1;
					rs.close();
					
					//insert
					PreparedStatement ps = con.prepareStatement("insert into book values (?,?,?,?,?,?,?,?,?)");
					ps.setInt(1,id);
					ps.setString(2,s1);
					ps.setString(3,s2);
					ps.setInt(4,s3);
					ps.setInt(5,s4);
					ps.setString(6,s5);
					ps.setString(7,s6);
					ps.setInt(8,s7);
					ps.setInt(9,s8);
					
					ps.executeUpdate();					
					System.out.println("Inserted");
					ps.close();				
				}
					catch(Exception e1) {
						System.out.println(e1);
					}
			}});	
		
		//reset
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isbn.setText("");
				name.setText("");
				publ.setText("");
				price.setText("");
				edi.setText("");
				author.setSelectedIndex(0);
				lang.setSelectedIndex(0);
				cat.setSelectedIndex(0);				
			}
		});
	}
	
	public void upd() {
		JFrame update= new JFrame();
		update.setVisible(true);
		update.setLayout(null);
		update.setTitle("Update Book Details");
		update.setBounds(100,100,500,500);
		
		JLabel updtxt = new JLabel("Enter Book ID for updation:");
		updtxt.setBounds(100,100,150,30); update.add(updtxt);
		JTextField bid = new JTextField();
		bid.setBounds(250, 100, 200, 30);  update.add(bid);
		
		JButton getbtn = new JButton("Get data");
		getbtn.setBounds(200, 200, 100, 30);
		update.add(getbtn);
		
		getbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int s7 = Integer.parseInt(bid.getText());
				new InsertBook();
				l1.setText("Update Information");
				
				try {
					String sql = "select isbn,book_name,author_id,category_id,publisher,language,edition,price from book where book_id = ?	";
					PreparedStatement ps3 = con.prepareStatement(sql);
					ps3.setInt(1, s7);
					ps3.setInt(1,s7);
					
					ResultSet rs = ps3.executeQuery(sql);
					if(rs.next()) {
						isbn.setText(rs.getString(2));						
					}					
					
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		
		
		
	}

	
	public static void main(String args[]) {
		new InsertBook();		
	}
}
