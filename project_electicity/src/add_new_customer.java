import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.mysql.jdbc.ResultSet;

import java.sql.*;

public class add_new_customer extends Frame implements ActionListener {
	
	JLabel name, meter_no, address, email, ph_no, image,password;
	JTextField name_txt, meter_no_txt, address_txt, email_txt, ph_no_txt;
	JButton submit, cancel;
	JPasswordField password_text;
	
	public add_new_customer() throws SQLException,NullPointerException{
        
		super("Add Customer");
        
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        
        p1.setLayout(new GridLayout(10,2));
        p1.setBackground(Color.WHITE);

        name = new JLabel("Name");
        name_txt = new JTextField();
        p1.add(name);
        p1.add(name_txt);
        
        meter_no = new JLabel("Meter No");
        meter_no_txt = new JTextField();
        p1.add(meter_no);
        p1.add(meter_no_txt);
        
        address = new JLabel("Address");
        address_txt = new JTextField();
        p1.add(address);
        p1.add(address_txt);
        
        email = new JLabel("Email");
        email_txt = new JTextField();
        p1.add(email);
        p1.add(email_txt);
        ph_no = new JLabel("Phone Number");
        ph_no_txt = new JTextField("");
        p1.add(ph_no);
        p1.add(ph_no_txt);
        
        password = new JLabel("Password");
        p1.add(password);
        password_text = new JPasswordField();
        p1.add(password_text);
        
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);

        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        
        p2.add(submit);
        p2.add(cancel);
        
        ImageIcon ic1 = new ImageIcon(ClassLoader.getSystemResource("images/hicon2.jpg"));
        Image i3 = ic1.getImage().getScaledInstance(150, 280,Image.SCALE_DEFAULT);
        ImageIcon ic2 = new ImageIcon(i3);
        image = new JLabel(ic2);
        
        setLayout(new BorderLayout());
        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);
        add(p3,BorderLayout.WEST);
        p3.add(image,"West");

        submit.addActionListener(this);
        cancel.addActionListener(this);
        
        setSize(640,540);
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	public void add_random_transactions(String meterID) throws SQLException{
		Connection conn = DB.connection;
		for(int i=1;i<=5;i++) {
			String sql = "insert into transactions(meterid,units,amount,date,status) values(?,?,?,?,?)";
			int units = (int)(Math.random()*i*100);
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, meterID);
			st.setInt(2,units);
			st.setInt(3,units*8);
			st.setString(4,String.valueOf(Calendar.getInstance().DATE)+" - "+String.valueOf(Calendar.getInstance().MONTH)+" - "+String.valueOf(Calendar.getInstance().YEAR));
			if(i==5)
				st.setString(5,"pending");
			else 
				st.setString(5,"paid");
			st.executeUpdate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit) {
			System.out.println("submit pressed");
			Connection c = DB.connection;
			
			String sql = "insert into customers(meterid,name,password,address,email,phone) values(?,?,?,?,?,?);";
			
			try {
				PreparedStatement s = c.prepareStatement(sql);
				
				char[] pass =password_text.getPassword();
				String password = String.valueOf(pass);
				
				s.setString(1,meter_no_txt.getText());
				s.setString(2,name_txt.getText());
				s.setString(3,password);
				s.setString(4,address_txt.getText());
				s.setString(5,email_txt.getText());
				s.setString(6,ph_no_txt.getText());
				s.execute();

				add_random_transactions(meter_no_txt.getText());
				
				JOptionPane.showMessageDialog(this, "CREATED ACCOUNT SUCCESSFULLY");
				System.out.println("done");
				this.dispose();
				new Authentication();
			
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("error");
				JOptionPane.showMessageDialog(this, "ERROR");
			}
		}
		if(e.getSource()==cancel) {
			this.dispose();
			new Authentication();
		}
	}

}
