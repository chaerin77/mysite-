package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
	//필드
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//생성자
	
	//메소드 g/s
	
	//메소드 일반
	
	public void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			
		}
	}
	
	public void close() {
		// 5. 자원정리
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
	
	
	//리스트 가져오기 메소드
	public List<GuestbookVo> getList(){ //이메소드 쓰면 db에있는 데이터 갖고와서 addlist.jsp에 줄겨 리턴형 뭘로할지 고민. -list로 오면 좋겠지. 
		//번호 이름 날짜 본문을 담고있는 데이터들갖고와야하는데 <> 요안에 있는애들은 vo..Vo클래스 만들러갔다옴
		
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//문자열 만들기
			String query = "";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc ";
			
			//문자열 쿼리문으로 바꾸기
			pstmt = conn.prepareStatement(query);
			
			//바인딩 생략 - 쿼리문에 ? 없어서
			
			//실행
			rs = pstmt.executeQuery(); //결과값 rs에 담김 no1 name정우성 password 1234 ... 
			
			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no"); //해당 값들 변수에 담아주기
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date"); //" " 안의 컬럼명에 해당하는 값 가져온것 
				
				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate); //필드에 regDate라구 써놔서 생성자쓸때 regDate라고쓰는게맞음 위에껀 db컬럼명이구
				//낱개로 돌아다니던 값들 넣어줬음 그리고 이거 만든거는 리스트로 관리하려고 한거임
				//리스트 맨위에 미리 만들어주기
				guestbookList.add(guestbookVo);
				
				//데이터 한줄씩 읽어와서 생성자 순서맞춰 메모리에 올리고 그것의 주소를 리스트에 넣어줬음
			}
			 
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
			
		close();

		return guestbookList;
	}//getList 메소드 끝나기 전에 return 해줘야함
	
	
	//add 추가하기 메소드
	public void addGuest(GuestbookVo gvo) {
		getConnection();
		
		try {
			//3.문자열 만들기
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
			//문자열 쿼리문으로 바꾸기
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setString(1, gvo.getName());
			pstmt.setString(2, gvo.getPassword());
			pstmt.setString(3, gvo.getContent());
			
			//실행
			pstmt.executeUpdate();
			
			//결과처리	
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
				
		close();	
	}
	
	//delete 삭제하기 메소드
	public void deleteGuest(GuestbookVo gvo) {
		getConnection();
		
		try {
			//3.문자열 만들기
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
				
			//문자열 쿼리문으로 바꾸기
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, gvo.getNo());
			pstmt.setString(2, gvo.getPassword());
			
			//실행
			pstmt.executeUpdate();
				
			//결과처리	
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
			
		close();
	}
	
	//delete 삭제하기 메소드-만들었던것
		public void deleteGuest1(int no) {
			getConnection();
			
			try {
				//3.문자열 만들기
				String query = "";
				query += " delete from guestbook ";
				query += " where no = ? ";
					
				//문자열 쿼리문으로 바꾸기
				pstmt = conn.prepareStatement(query);
				
				//바인딩
				pstmt.setInt(1, no);
				
				//실행
				pstmt.executeUpdate();
					
				//결과처리	
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
				
			close();
		}
	
	
	//getGuest --번호가 _인 사람의 정보 가져오기 메소드
	public GuestbookVo getGuest(int no) {
		GuestbookVo gvo = null;
		
		getConnection();
		try {
			
			//3.문자열 만들기
			String query = "";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " where no = ? ";
			
			//문자열 쿼리문으로 바꾸기
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setInt(1, no);
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				
				int num = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				gvo = new GuestbookVo(num, name, password, content, regDate);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return gvo;
	}
	
}
