import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class meter_auth extends Frame implements ActionListener{
	
	JLabel meter_no,img;
	JTextField meter_no_txt;
	JButton pay,Login_page,signup;
	JPanel p1,p2;
	
	public meter_auth(){
		
		super("meter_auth");
		setLayout(new BorderLayout());
		setSize(640,540);
        setVisible(true);
		meter_no = new JLabel("Meter Number");
		meter_no_txt = new JTextField(15);
		
		Login_page = new JButton("LoginPage");
		signup = new JButton("SignUp");
		pay = new JButton("Pay");
		
		Login_page.addActionListener(this);
		pay.addActionListener(this);
		signup.addActionListener(this);
		
		p1 = new JPanel();
		p2 = new JPanel();
	
		p1.add(meter_no);
		p1.add(meter_no_txt);

		p2.add(pay); 
		p2.add(Login_page);
		p2.add(signup);
		
		
		
        ImageIcon ic3=new ImageIcon(ClassLoader.getSystemResource("images/shock1.jpg"));
        Image i3=ic3.getImage().getScaledInstance(340,370,Image.SCALE_DEFAULT);
        ImageIcon icc3=new ImageIcon(i3);
        img=new JLabel(icc3);
		
        add(img,BorderLayout.WEST);
        add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
					
        addWindowListener(new WindowAdapter() {
            
        	@Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==pay) {
			//redirect to paybill here generate_bill.java
			try {
				String sql = "select * from transactions "
						+"where meterid = ?";
						
				PreparedStatement st = DB.connection.prepareStatement(sql);
				st.setString(1, meter_no_txt.getText());
				ResultSet res = st.executeQuery();

				if(res.next()) {
					dispose();
					new generate_Bill(res.getString("meterid"));
					return;
				}
				else throw new Exception("METERID doesn't Exist");
			} catch (Exception e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(this, e1.getMessage().toString());
			}
		}
		
		if(e.getSource()==Login_page) {
			this.dispose();
			new Authentication();
		}
	}
}
