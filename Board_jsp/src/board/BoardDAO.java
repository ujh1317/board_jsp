package board;

import java.sql.*;
import java.util.*;

public class BoardDAO {

	private Connection conn;
	private ResultSet rs;
	
	public BoardDAO(){
		
		try{ //DB접속
			String URL = "jdbc:mysql://localhost:3306/bbs";
			String USER = "root";
			String PWD = "12345";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(URL,USER,PWD);
		}catch(Exception e){
			e.printStackTrace(); //오류 출력
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
		return ""; //데이터베이스 오류
	}//getDate()

	public int getNext(){
		String sql = "select boardID from board order by boardID desc";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1)+1;
			}//if
			return 1; //첫번째 게시물인 경우
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //데이터베이스 오류
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
		return -1; //데이터베이스 오류
	}//write()
	
	public ArrayList<Board> getList(int pageNumber){
		String sql = "select * from board where boardID < ? and boardAvailable = 1 order by boardID desc limit 10";
		ArrayList<Board> list = new ArrayList<Board>();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Board board = new Board();
				board.setBoardID(rs.getInt(1));
				board.setBoardTitle(rs.getString(2));
				board.setUserID(rs.getString(3));
				board.setBoardDate(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardAvailable(rs.getInt(6));
				list.add(board);
			}//while
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return list;
	}//getList()
	
	public boolean nextPage(int pageNumber){
		String sql = "select * from board where boardID < ? and boardAvailable = 1";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return false;
	}//nextPage
	
	public Board getBoard(int boardID){
		String sql = "select * from board where boardID = ?";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				Board board = new Board();
				board.setBoardID(rs.getInt(1));
				board.setBoardTitle(rs.getString(2));
				board.setUserID(rs.getString(3));
				board.setBoardDate(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardAvailable(rs.getInt(6));
				return board;
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return null;
	}//getBoard()
	
	public int update(int boardID, String boardTitle, String boardContent){
		String sql = "update board set boardTitle=?, boardContent=? where boardID=?";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardID);
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //데이터베이스 오류
	}//update()
	
	public int delete(int boardID){
		String sql = "update board set boardAvailable=0 where boardID=?";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardID);
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		return -1; //데이터베이스 오류
	}//delete()
	
}//class

