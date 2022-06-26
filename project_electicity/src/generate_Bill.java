
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class generate_Bill extends Frame implements ActionListener {

	JLabel name, address ,ph_no, state, city, meter_no, email;
	JButton print,exit,pay;
	String username,transid;
	public static boolean meter_flag = false;
	public generate_Bill(String meterid) throws SQLException {
		
		String sql = "select * from customers c,transactions t "
				+"1where c.meterid = t.meterid "
				+"and t.status='pending' "
				+"and c.meterid = ?";
				
		PreparedStatement st = DB.connection.prepareStatement(sql);
		st.setString(1, meterid);
		ResultSet res = st.executeQuery();
		if(res.next())
		{
			this.meter_flag = true;
			System.out.println(this.meter_flag);
			new generate_Bill(res.getString("TransactionID"),res.getString("name"),res.getString("meterid"),res.getString("address"),res.getString("email"),res.getString("phone"),res.getString("date"),String.valueOf(res.getInt("units")),String.valueOf(res.getInt("amount")),res.getString("status"));
		}	
		else
			throw new SQLException("No Pending Bills");
	}
	
	public generate_Bill(String transid,String Name,String Meter_Number,String Address,String Mail,String Ph_num,String date,String units,String amount,String status){
		
		super("Generate BILL");
		System.out.println(this.meter_flag);
		this.transid = transid;
		this.username = Name;
		JPanel p =new JPanel();
		
		p.add(new JLabel("Transaction ID"));
	    p.add(new JLabel(transid));
	    
		p.add(new JLabel("DATE OF BILL GENERATED:"));
	    p.add(new JLabel(date));
	    
		name = new JLabel("NAME :");
		p.add(name);
		p.add(new JLabel(Name));
	    
		meter_no = new JLabel("METER NUMBER :");
	    p.add(meter_no);	    
	    p.add(new JLabel(Meter_Number));
	    
	    address = new JLabel("ADDRESS : ");
	    p.add(address);
	    p.add(new JLabel(Address));
	    
	    city = new JLabel("CITY :");
	    p.add(city);
	    p.add(new JLabel("Bangalore"));
	    
	    JLabel state = new JLabel("STATE :");	    
	    p.add(state);
	    p.add(new JLabel("Karnataka"));
	    
	    email = new JLabel("EMAIL :");
	    p.add(email);
	    p.add(new JLabel(Mail));
	    
	    ph_no = new JLabel("PHONE NUMBER :");
	    p.add(ph_no);
	    p.add(new JLabel(Ph_num));
	    
	    p.add(new JLabel("UNITS:"));
	    p.add(new JLabel(units));
	    
	    p.add(new JLabel("Amount:"));
	    p.add(new JLabel(amount));
	    
	    pay = new JButton("PAY");
        exit = new JButton("CANCEL");
		
        pay.addActionListener(this);
        exit.addActionListener(this);
        p.add(pay);
        p.add(exit);
        if(status.equals("pending")) {
        	add(p);
        }
        else {
        	add(new JLabel("PAID"));
        }
        
        p.setLayout(new GridLayout(12,2));
        setSize(640,540);
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
}
	public void updatePay() throws SQLException {
		Connection conn = DB.connection;
		String sql = "update transactions set status = 'paid' where TransactionID=?";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,this.transid);
			st.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this,"ERROR");
			e.printStackTrace();
		}	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pay) {
			//update in database 
			try {
				updatePay();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(pay,"Paid Successfully");
			dispose();
			System.out.println(generate_Bill.meter_flag);
			if(!generate_Bill.meter_flag)
				new profile(username);
			else
				new meter_auth();
		}
		if(e.getSource()==exit) {
			this.dispose();
			new Authentication();
		}
	}
}
