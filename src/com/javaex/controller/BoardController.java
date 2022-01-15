package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String act = request.getParameter("action");
	
		if("writeForm".equals(act)) {
			System.out.println("action=writeForm");
		
		}else if("write".equals(act)) {
			System.out.println("action=write");
			
		}
		
		else if("list".equals(act)) {
			System.out.println("action=list");
			//db의 board에 있는 데이터 list로 갖고오기
			List<BoardVo> BoardList = new ArrayList<BoardVo>();
			
			//BoardList에 db의 board에 있는데이터 넣어줄 dao의 메소드 필요함-> 만들고 BoardList에 사용하기
			BoardDao bDao = new BoardDao();
			BoardList = bDao.getList();
			System.out.println(BoardList);
			
			//request의 어트리뷰트에 "boardList"라는 이름으로 내가 가져온 정보 목록의 주소 넣기
			request.setAttribute("boardList", BoardList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			
		}else if("delete".equals(act)) {
			System.out.println("action=delete");
			//dao에 번호가__면 해당하는 모든 정보를 지우는 쿼리문 짜고 메소드 만들기
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			bDao.delete(no);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			
		}else if("read".equals(act)) {
			System.out.println("action=read");
			
			int no = Integer.parseInt(request.getParameter("no")); //board?action=read&no=35
			
			BoardDao bDao = new BoardDao();
			BoardVo boardVo = bDao.read(no);

			int count = boardVo.getHit();
			count=0;
			int hit = count++;
			System.out.println(hit);//0 등록만들기
			
			request.setAttribute("bVo", boardVo);
			request.setAttribute("Hit", hit);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
			
		}else if("modifyForm".equals(act)) {
			System.out.println("action=modifyForm");	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
