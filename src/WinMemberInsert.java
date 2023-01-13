import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
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

public class WinMemberInsert extends JFrame {

	private JPanel contentPane;
	private JTextField idtextField;
	private JTextField pwField;
	private JTextField nametextField;
	JButton joinButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberInsert frame = new WinMemberInsert();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WinMemberInsert() {
		setTitle("Member \uD68C\uC6D0\uAC00\uC785");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLabel = new JLabel("ID : ");
		idLabel.setBounds(28, 59, 57, 15);
		contentPane.add(idLabel);
		
		JLabel pwLabel = new JLabel("PW : ");
		pwLabel.setBounds(28, 98, 57, 15);
		contentPane.add(pwLabel);
		
		JLabel nameLabel = new JLabel("\uC774\uB984 : ");
		nameLabel.setBounds(28, 143, 57, 15);
		contentPane.add(nameLabel);
		
		idtextField = new JTextField();
		idtextField.setBounds(122, 56, 116, 21);
		contentPane.add(idtextField);
		idtextField.setColumns(10);
		
		pwField = new JTextField();
		pwField.setEnabled(false);
		pwField.setColumns(10);
		pwField.setBounds(122, 95, 116, 21);
		contentPane.add(pwField);
		
		nametextField = new JTextField();
		nametextField.setEnabled(false);
		nametextField.setColumns(10);
		nametextField.setBounds(122, 140, 116, 21);
		contentPane.add(nametextField);
		JButton overlapButton = new JButton("\uC911\uBCF5\uAC80\uC0AC");
		overlapButton.setBounds(273, 55, 97, 23);
		contentPane.add(overlapButton);
		
		joinButton = new JButton("\uAC00\uC785");
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert 구문
				InsertLogin();
			}
		});
		joinButton.setEnabled(false);
		joinButton.setBounds(122, 194, 97, 23);
		contentPane.add(joinButton);
		
	
		overlapButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
			if(isNothing()) {//만약 id가 가입되어 있지 않으면
				pwField.setEnabled(true);
				nametextField.setEnabled(true);
				joinButton.setEnabled(true);
				pwField.requestFocus();
			}else {
				System.out.println("id가 중복되었습니다");
				idtextField.requestFocus();
			}
			}
		});
	}
	

	protected void InsertLogin() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
            System.out.println("DB 연결 완료");               
            Statement statement=conn.createStatement();
            //=============================================
            String sql = "insert into loginTBL value('" + idtextField.getText() + "','";
            sql = sql + pwField.getText() + "','" + nametextField.getText() + "')";
            ResultSet rs = statement.executeQuery(sql);
            System.out.println(sql);
            if(statement.executeUpdate(sql) > 0) {
            	System.out.println("회원 가입 완료");
            	setVisible(false);
            }else {
            	System.out.println("회원 가입 실패");
            }
            	
			}catch (ClassNotFoundException e1) {
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
             String sql = "select * from loginTBL where id='" + idtextField.getText() + "'";
             ResultSet rs = statement.executeQuery(sql);
             if(rs.next())//중복
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
