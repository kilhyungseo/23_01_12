import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class WinMemberInsert1 extends JDialog {
   private JTextField tfID;
   private JTextField tfPw;
   private JTextField tfName;
   private JButton btnInsert;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinMemberInsert dialog = new WinMemberInsert();
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
   public WinMemberInsert1() {
      setTitle("회원가입창");
      setBounds(100, 100, 431, 233);
      getContentPane().setLayout(null);
      
      JLabel lblID = new JLabel("ID:");
      lblID.setBounds(42, 36, 57, 15);
      getContentPane().add(lblID);
      
      tfID = new JTextField();
      tfID.setBounds(141, 33, 116, 21);
      getContentPane().add(tfID);
      tfID.setColumns(10);
      
      tfPw = new JTextField();
      tfPw.setEnabled(false);
      tfPw.setColumns(10);
      tfPw.setBounds(141, 64, 116, 21);
      getContentPane().add(tfPw);
      
      JLabel lblPw = new JLabel("PW:");
      lblPw.setBounds(42, 67, 57, 15);
      getContentPane().add(lblPw);
      
      tfName = new JTextField();
      tfName.setEnabled(false);
      tfName.setColumns(10);
      tfName.setBounds(141, 95, 116, 21);
      getContentPane().add(tfName);
      
      JLabel lblName = new JLabel("Name");
      lblName.setBounds(42, 98, 57, 15);
      getContentPane().add(lblName);
      
      JButton btnDup = new JButton("중복확인");
      btnDup.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if( isNothing() ){// 만약 id가 가입되어 있지 않으면            
               tfPw.setEnabled(true);
               tfName.setEnabled(true);
               btnInsert.setEnabled(true);
               tfPw.requestFocus();
            }else {
               System.out.println("ID가 중복되었습니다.");
               tfID.requestFocus();
            }
         }
      });
      btnDup.setBounds(269, 32, 97, 23);
      getContentPane().add(btnDup);
      
      btnInsert = new JButton("가입");
      btnInsert.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // insert 구문
            InsertLogin();
         }
      });
      btnInsert.setEnabled(false);
      btnInsert.setBounds(141, 146, 97, 23);
      getContentPane().add(btnInsert);

   }

   protected void InsertLogin() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
         System.out.println("DB 연결 완");               
         Statement statement=conn.createStatement();
         //=============================================
         String sql = "insert into loginTBL value('" + tfID.getText() + "','";
         sql = sql + tfPw.getText() + "','" + tfName.getText() + "')";
         System.out.println(sql);
         
         if(statement.executeUpdate(sql) > 0) {
            System.out.println("회원 가입 완료");
            setVisible(false);
         }
         else
            System.out.println("회원 가입 실패");
         //==============================================
      } catch (ClassNotFoundException e1) {
         System.out.println("JDBC 드라이버 로드 에러");
      } catch (SQLException e1) {
         System.out.println("DB 연결 오류");
      }      
   }

   protected boolean isNothing() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
         System.out.println("DB 연결 완료");               
         Statement statement=conn.createStatement();
         //=============================================
         String sql = "select * from loginTBL where id='" + tfID.getText() + "'";
         ResultSet rs = statement.executeQuery(sql);
         if(rs.next()) // 중복
            return false;
         else
            return true;
         //==============================================
      } catch (ClassNotFoundException e1) {
         System.out.println("JDBC 드라이버 로드 에러");
      } catch (SQLException e1) {
         System.out.println("DB 연결 오류");
      }
      return false;
   }
}