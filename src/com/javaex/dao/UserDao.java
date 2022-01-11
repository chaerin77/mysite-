package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.javaex.vo.UserVo;

public class UserDao {
	
	//필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private	PreparedStatement pstmt = null;
	private	ResultSet rs = null;
	
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
	
	//저장 메소드
	public int insert(UserVo userVo) {
		
		int count=0; //count값 0으로 초기값 세팅 안하면 예외발생했을때 바로 뛰어넘어버려서 아무것도없는값 되어버려서 
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//문자열 -insert쿼리문 필요
			String query = "";
			query +=" insert into users ";
			//query +=" values (seq_users_no.nextval, 'bb', '1234', '이효리', 'female') ";
			query +=" values (seq_users_no.nextval, ?, ?, ?, ?) ";
			
			//쿼리문만들기
			pstmt= conn.prepareStatement(query);
			
			//바인딩
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			//실행
			count = pstmt.executeUpdate();
			
			// 4. 결과처리
			System.out.println(count + "건이 등록되었습니다(UserDao)");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);	
		}
		
		close();
		
		return count;
	}

}
