package board;

import java.sql.*;

public class BoardDAO {

	private Connection conn;
	private ResultSet rs;
	
	public BoardDAO(){
		
		try{ //DB����
			String URL = "jdbc:mysql://localhost:3306/bbs";
			String USER = "root";
			String PWD = "12345";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(URL,USER,PWD);
		}catch(Exception e){
			e.printStackTrace(); //���� ���
		}//catch
	}//BoardDAO()
	
	public String getDate(){
		String sql = "select now()";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return ""; //�����ͺ��̽� ����
	}//getDate()

	public int getNext(){
		String sql = "select boardID from board order by boardID desc";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1)+1;
			}//if
			return 1; //ù��° �Խù��� ���
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //�����ͺ��̽� ����
	}//getNext()
	
	public int write(String boardTitle, String userID, String boardContent){
		String sql = "insert into board values(?,?,?,?,?,?)";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, boardContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //�����ͺ��̽� ����
	}//write()
	
}//class
