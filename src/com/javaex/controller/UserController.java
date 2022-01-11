package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(/user);
		
		String act = request.getParameter("action");
		
		if("joinForm".equals(act)) {
			System.out.println("action=joinForm");//"user >joinForm"
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(act)) {
			System.out.println("action=join");
			//사용자가 입력한 값이 와야함. -> action=join&id=aaa&pw=bbb 이런식으로 작동하게끔
			
			//파라미터값 가져오기
			String id = request.getParameter("id");
			String pw = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//생성자 사용 UserVo 메모리 올리기  파라미터-->vo로 만들기
			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);//[no=0, id=eee...]이거 여기서찍히는거
			
			//UserDao 저장하기(회원가입하기) Dao의 insert메소드 사용
			UserDao userDao = new UserDao();
			userDao.insert(userVo); 
			
			//이번 페이지는 리스트도 없고 "메시지" html코드로 써진 화면만 나오면 됨 포워드해주기
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(act)) {
			System.out.println("action=loginForm");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
