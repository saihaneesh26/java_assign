//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//public class pay_bill extends JFrame{
//    pay_bill(){
//        JEditorPane j = new JEditorPane();
//        j.setEditable(false);
//
//        try {
//        	j.setContentType("text/html");
//            j.setPage("https://www.bescom.co.in/SCP/MyAccount/QuickPayment.aspx");
//        }catch (Exception e) {
//            j.setContentType("text/html");
//            j.setText("<html>Could not load</html>");
//            System.out.println(e);
//        }
//
//        JScrollPane scrollPane = new JScrollPane(j);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().add(scrollPane);
//        add(j);
//        setPreferredSize(new Dimension(800,600));
//        setSize(800,800);
//        setLocation(250,120);
//        setVisible(true);
//    }
//    public static void main(String[] args){
//        new pay_bill().setVisible(true);
//    }
//}
