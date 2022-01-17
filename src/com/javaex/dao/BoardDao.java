package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	//필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//생성자
	
	//메소드 g/s
	
	//메소드 일반
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	private void close() {
		//5.자원정리
		try {
			if (rs != null) {
				rs.close();
			} 
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	
	public List<BoardVo> getList(){
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		
		try {
			//3.문자열 만들기
			String query ="";
			query +=" select bo.no, ";
			query +="        bo.title, ";
			query +="        bo.content, ";
			query +="        us.name, ";
			query +="        bo.hit, ";
			query +="        to_char(bo.reg_date, 'yy-mm-dd hh:mi') reg_date, ";
			query +="        bo.user_no ";
			query +=" from board bo, users us ";
			query +=" where bo.user_no = us.no ";
			query +=" order by reg_date desc";
			
			//쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);
			
			//바인딩 생략
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				
				BoardVo bVo = new BoardVo(no, title, content, name, hit, regDate, user_no);
				boardList.add(bVo);
			}
		
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return boardList;  
	}
	
	
	public void delete(int num) {
		int no=0;
		getConnection();
		
		try {
			//3.문자열 만들기
			String query ="";
			query +=" delete from board ";
			query +=" where no = ? ";
			
			//쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, num);
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		close();
		
	}
	
	public BoardVo read(int readnum) {
		BoardVo bVo = new BoardVo();
		getConnection();
		
		try {
			//3.문자열 만들기
			String query ="";
			query +=" select bo.no, ";
			query +="        us.name, ";
			query +="        bo.hit, ";
			query +="        to_char(bo.reg_date, 'yyyy-mm-dd hh:mi') reg_date, ";
			query +="        bo.title, ";
			query +="        bo.content, ";
			query +="        bo.user_no ";
			query +=" from board bo, users us ";
			query +=" where bo.user_no = us.no ";
			query +=" and bo.no = ? ";
			
			//쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, readnum);
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int user_no = rs.getInt("user_no");
			
				bVo.setNo(no);
				bVo.setName(name);
				bVo.setHit(hit);
				bVo.setReg_date(regDate);
				bVo.setTitle(title);
				bVo.setContent(content);
				bVo.setUser_no(user_no);
			}
			
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		return bVo;
	
	}
	
	//사용자 1명인 경우는 가능하지만 다수가 사이트를 사용할 경우 조회수가 맞지 않는 문제가 발생
	public void uphit(BoardVo bVo) {
		getConnection();
		try {
			//3.문자열 만들기
			String query ="";
			query +=" update board ";
			query +=" set hit =? ";
			query +=" where no=? ";
			
			//쿼리문 만들기
			pstmt=conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, bVo.getHit());
			pstmt.setInt(2, bVo.getNo());
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		
	}
	
	public void uphit2(BoardVo bVo) {
		getConnection();
		try {
			//3.문자열 만들기
			String query ="";
			query +=" update board ";
			query +=" set hit =hit+1 ";
			query +=" where no=? ";
			
			//쿼리문 만들기
			pstmt=conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, bVo.getNo());
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		
	}
	
	public void insert(BoardVo bVo) {
		getConnection();
		try {
			//3.문자열 만들기
			String query ="";
			query +=" insert into board ";
			query +=" values(seq_board_no.nextval, ?, ?, ?,  sysdate, ?) ";
			
			//쿼리문 만들기
			pstmt=conn.prepareStatement(query);
			
			//바인딩
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getHit());
			pstmt.setInt(4, bVo.getUser_no());
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		
	}
	
	public void update(BoardVo bVo) {
		getConnection();
		try {
			//3.문자열 만들기
			String query ="";
			query +=" update board ";
			query +=" set title = ?, ";
			query +="     content = ? ";
			query +=" where no = ? ";
			
			//쿼리문 만들기
			pstmt=conn.prepareStatement(query);
			
			//바인딩
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리
			
		}catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		
	}
	
	

}
