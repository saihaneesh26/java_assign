
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Authentication extends JFrame implements ActionListener {
	 JLabel user_name,password;
	 JTextField user_name_txt;
	 JPasswordField password_text;
	 JButton login,pay_by_meter_no,signup;
	 JPanel p2,p3;
	 JLabel img;
	 Connection c = DB.connection;
	public Authentication() {
		
		super("LOGIN PAGE");

		
		user_name = new JLabel("USER-NAME: ");
		password = new JLabel("PASSWORD: ");
		
		  user_name_txt = new JTextField(15);
	      password_text = new JPasswordField(15);
	      
	        login = new JButton("Login");
	        signup = new JButton("SignUp");
	        pay_by_meter_no=new JButton("Pay by Meter Number");
	        
	        ImageIcon ic3=new ImageIcon(ClassLoader.getSystemResource("images/shock1.jpg"));
	        Image i3=ic3.getImage().getScaledInstance(340,370,Image.SCALE_DEFAULT);
	        ImageIcon icc3=new ImageIcon(i3);
	        img=new JLabel(icc3);
	        
	        setLayout(new BorderLayout());
	        setVisible(true);
	        
	        p2 = new JPanel();
	        p3 = new JPanel();
	        
	        add(img,BorderLayout.WEST);
	        
	        p2.add(user_name);
	        p2.add(user_name_txt);
	        p2.add(password);
	        p2.add(password_text);
	        add(p2,BorderLayout.CENTER);
	        
	        p3.add(login);
	        p3.add(pay_by_meter_no);
	        p3.add(signup);
	        login.addActionListener((ActionListener)this);
	        signup.addActionListener(this);
	        pay_by_meter_no.addActionListener((ActionListener)this);
	        add(p3,BorderLayout.SOUTH);
	        setSize(640,540);
	        setVisible(true);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public String verifyUser(String username,String password) throws SQLException {
		//implement verification
		//return true if match exits
		//else false
		String sql = "select name,password from customers; ";
		Statement st = c.createStatement();
		ResultSet res = st.executeQuery(sql);
		Boolean user=false;
		Boolean pass = false;
		while(res.next()) {
			if(res.getString("name").equals(username)) {
				user = true;
				if(res.getString("password").equals(password)) {
					pass = true;
					break;
				}
			}
		}
		if(user&&pass) {
			return "true";
		}
		else if(user&&!pass) {
			return "wrong_password";
		}
		else {
			return "no_user_found";
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login) {
			String res;
			try {
				res = verifyUser( user_name_txt.getText(),String.valueOf(password_text.getPassword()));
			} catch (SQLException e1) {
				e1.printStackTrace();
				res="error";
			}
			System.out.println(res);
			switch(res) {
				case "true": {
					//login
					JOptionPane.showMessageDialog(this, "TRUE LOGIN");
					this.dispose();
					new profile(user_name_txt.getText());
					break;
				}
				case "wrong_password": {
					JOptionPane.showMessageDialog(this, "Wrong password");
					break;
				}
				case "no_user_found": {
					int option = JOptionPane.showConfirmDialog(this,"NO USER FOUND\nSIGNUP NOW!",res, 2);
					if(option == 0)//signup
					{
						this.dispose();
						try {
							new add_new_customer();
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
					}
					break;
				}
				case "error":{
					JOptionPane.showMessageDialog(this, "ERROR");
					break;
				}
				default:{
					System.out.println("default");
					break;
				}
			}
		}
		if(e.getSource()==pay_by_meter_no) {
			this.dispose();
			new meter_auth();
		}
		if(e.getSource()==signup) {
			this.dispose();
			try {
				new add_new_customer();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "ERROR");
			} 
		}
	}

}
