package user;

import java.sql.*;

//데이터베이스 접근 객체
public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO(){
		
		try{ //DB접속
			String URL = "jdbc:mysql://localhost:3306/bbs";
			String USER = "root";
			String PWD = "12345";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(URL,USER,PWD);
		}catch(Exception e){
			e.printStackTrace(); //오류 출력
		}//catch
	}//UserDAO()
	
	public int login(String userID, String userPWD){
		String sql = "select userPWD from user where userID = ?";
		
		try{ //로그인 시도
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(1).equals(userPWD))
					return 1; //로그인 성공
				else
					return 0; //비밀번호 불일치
			}//if
			return -1; //아이디가 없을때
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -2; //데이터베이스 오류
	}//login()
	
	public int join(User user){
		String sql = "insert into user values(?,?,?,?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPWD());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //데이터베이스 오류
		
	}//join()
	
}//class
