package user;

import java.sql.*;

//�����ͺ��̽� ���� ��ü
public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO(){
		
		try{ //DB����
			String URL = "jdbc:mysql://localhost:3306/bbs";
			String USER = "root";
			String PWD = "12345";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(URL,USER,PWD);
		}catch(Exception e){
			e.printStackTrace(); //���� ���
		}//catch
	}//UserDAO()
	
	public int login(String userID, String userPWD){
		String sql = "select userPWD from user where userID = ?";
		
		try{ //�α��� �õ�
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(1).equals(userPWD))
					return 1; //�α��� ����
				else
					return 0; //��й�ȣ ����ġ
			}//if
			return -1; //���̵� ������
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -2; //�����ͺ��̽� ����
	}//login()
	
	
}//class
