import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class WinLogin extends JDialog {
   private JTextField tfID;
   private JTextField tfPW;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinLogin dialog = new WinLogin();
               dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
               dialog.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the dialog.
    */
   public WinLogin() {
      setTitle("로그인창");
      setBounds(100, 100, 338, 141);
      
      JPanel panel = new JPanel();
      getContentPane().add(panel, BorderLayout.CENTER);
      panel.setLayout(new GridLayout(0, 2, 0, 0));
      
      JLabel lblID = new JLabel("ID:");
      panel.add(lblID);
      
      tfID = new JTextField();
      panel.add(tfID);
      tfID.setColumns(10);
      
      JLabel lblPW = new JLabel("PW:");
      panel.add(lblPW);
      
      tfPW = new JTextField();
      panel.add(tfPW);
      tfPW.setColumns(10);
      
      JLabel label = new JLabel("");
      panel.add(label);
      
      JButton btnLogin = new JButton("Login...");
      btnLogin.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
               System.out.println("DB 연결 완료");               
               Statement statement=conn.createStatement();
               //=============================================
               String sql = "select * from loginTBL where id='" + tfID.getText() + "' && pw='" + tfPW.getText() +"'";
               ResultSet rs = statement.executeQuery(sql);
               if(rs.next()) {
                  String sName= rs.getString(3);      
                  System.out.println("Login 성공 전달:" + sName);
                  WinMain winMain = new WinMain(sName);
                  winMain.setModal(true);
                  winMain.setVisible(true);
               }else
                  System.out.println("Login 실패");
               
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC 드라이버 로드 에러");
            } catch (SQLException e1) {
               System.out.println("DB 연결 오류");
            }
         }
      });
      panel.add(btnLogin);

   }

}