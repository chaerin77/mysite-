package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			
		}else if("login".equals(act)){
			System.out.println("action=login"); //user > login
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			System.out.println(id);
			System.out.println(password);
			
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);//내가입력한 아이디/비번과 일치하는 사람의 정보를 db에서 갖고온게 담겨진것이 authVoS
			//System.out.println(uservo);
			
			if(authVo == null) {//로그인실패
				System.out.println("로그인실패");
				//로그인 실패해서 여기로 온건지 그냥 로그인폼으로 오고싶어서 주소창에 user?action=loginForm 친건지 구분해줘야함
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");//result변수에 fail이라는값이 담겨있으면 로그인실패해서 loginForm으로 다시온거라는표시
				
			}else {//로그인성공
				System.out.println("로그인성공");
				
				HttpSession session = request.getSession(); //session1111에 접속하게
				session.setAttribute("authUser", authVo); //로그인 성공하면 세션공간만들고 정보넣기
				System.out.println(session);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
			
		}else if("logout".equals(act)) {
			System.out.println("로그아웃");
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			//session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
		
		}else if("modifyForm".equals(act)) {
			System.out.println("action=modifyForm 회원정보 수정폼");
			
			//user?action=modifyForm&no=7 이런식이어야 몇번의 정보를 바꿔달라하는지알테니까 번호를 넣어야할텐데 어떻게?--modifyForm.jsp에서 session.getAttribute
			//System.out.println(session);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		}else if("modify".equals(act)) {
			System.out.println("action=modify 회원정보 수정");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
