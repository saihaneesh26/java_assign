import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class profile extends Frame implements ActionListener{
	JTable table;
	String username;
	JButton pay,logout;
	String[] columnsNames = {"Tranasaction ID","Date","UNITS Consumed","Amount","Status"};
	public profile(String username) {
		super("PROFILE");
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		setLayout(new BorderLayout());
		
		this.username = username;
		Object[][] list = fetchData();
		
		p3.setLayout(new GridLayout(2,1));
		p3.add(new JLabel("HELLO "+username));
		p3.add(new JLabel("Tranascations"));
		add(p3,BorderLayout.NORTH);
		
		table = new JTable(list,columnsNames);
		p1.setLayout(new BorderLayout(2,2));
		p1.add(table);
		JScrollPane sp = new JScrollPane(table);
        p1.add(sp);


		setSize(640,540);
        setVisible(true);
        
		add(p1,BorderLayout.CENTER);
		
		pay = new JButton("PAY");
		pay.addActionListener(this);
		logout = new JButton("LOGOUT");
		logout.addActionListener(this);
		
		p2.add(pay);
		p2.add(logout);
		
		add(p2,BorderLayout.SOUTH);
        
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
        
	}
	
	public Object[][] fetchData() {
		//database fetch data
		Connection conn = DB.connection;
		String sql = "select * from transactions where meterid=(select meterid from customers where name=?)";
		PreparedStatement st;
		ArrayList<Object> a = new ArrayList<Object>();
		try {
			st = conn.prepareStatement(sql);
		
			st.setString(1,this.username);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()){
					Object[] transaction = {res.getString("TransactionID"),res.getString("date"),
							res.getString("units"),res.getString("amount"),res.getString("status")} ;
					a.add(transaction);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object[][] l = new Object[a.size()][];
		for(int i=0;i<a.size();i++)
			l[i] = (Object[]) a.get(i);
		return l;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pay) {
			try {
				dispose();
				new generate_Bill(username);
			} catch (SQLException e1) {
				new profile(username);
				JOptionPane.showMessageDialog(this,"NO PENDING PAYMENTS");
			}
		}
		if(e.getSource()==logout) {
			try {
				dispose();
				new Authentication();
			}catch(Exception er) {
				JOptionPane.showMessageDialog(this,"ERROR"+er.toString());
			}
		}
	}
}
