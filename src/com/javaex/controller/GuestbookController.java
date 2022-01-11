package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String act = request.getParameter("action");
	
		/*if("addList".equals(act)) {
			
			System.out.println("action=addLIst");
			//리스트 목록 가져오기
			GuestbookDao gDao = new GuestbookDao();
			List<GuestbookVo> Lgvo = gDao.getList();
			
			request.setAttribute("gbList", Lgvo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");*/
		
		if("add".equals(act)) {
			System.out.println("action=add");
			//파라미터 값 꺼내오기
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			String content = request.getParameter("content");
			
			//꺼내온 파라미터값으로 guestbookvo 만들기 
			GuestbookVo gvo = new GuestbookVo(name, pass, content);
			System.out.println(gvo);
			
			//dao의 addGuest메소드 사용해서 db에 데이터 추가하기
			GuestbookDao gDao = new GuestbookDao();
			gDao.addGuest(gvo);
			
			WebUtil.redirect(request, response, "/mysite/guest");
		
		}else if("deleteForm".equals(act)) {
			System.out.println("action=deleteForm");
			
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/deleteForm.jsp");//경로 제대로 쓰기
			
		}else if("delete".equals(act)) {
			System.out.println("action=delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pass");
			
			GuestbookVo gbVo = new GuestbookVo(no, pw);
			
			GuestbookDao gbDao = new GuestbookDao();
			gbDao.deleteGuest(gbVo);
			
			WebUtil.redirect(request, response, "/mysite/guest");
		
		}else {
			
			GuestbookDao gDao = new GuestbookDao();
			List<GuestbookVo> Lgvo = gDao.getList();
		
			request.setAttribute("gbList", Lgvo);
		
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
