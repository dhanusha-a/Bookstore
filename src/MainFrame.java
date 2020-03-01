import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MainFrame extends JFrame{
	JMenuBar menubar = new JMenuBar();
	 JDesktopPane jdp = new JDesktopPane();
	
	public MainFrame(String title){
		//initial code, dont touch
		super(title);
		setVisible(true);
		setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//for internal frame		
		this.add(jdp);
		
		//menu		
		setJMenuBar(menumt());		
	}	
	
	//method for menu
	private JMenuBar menumt(){
		//menu for books
		JMenu book = new JMenu("Books");
		menubar.add(book);
		
		JMenuItem bview = new JMenuItem("View");
		book.add(bview);
		
		//to display internal frame with menu
		bview.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				BookFrame bf = new BookFrame();
				jdp.add(bf);
				jdp.moveToFront(bf);				
			}
		});
		
		JMenuItem bsearch = new JMenuItem("Search");
		book.add(bsearch);
		
		//menu for customer
		JMenu customer = new JMenu("Customer");
		menubar.add(customer);
		
		JMenuItem cview = new JMenuItem("View");
		customer.add(cview);
		
		JMenuItem csearch = new JMenuItem("Search");
		customer.add(csearch);
		
		return menubar; 
	}
	

	public static void main(String[] args) {
		new MainFrame("Book Store");
	}

}
