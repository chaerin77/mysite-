package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
		
		if("list".equals(act)) {
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
			
		}else if("writeForm".equals(act)) {
			System.out.println("action=writeForm");	
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("write".equals(act)) {
			System.out.println("action=write");
			
			//String name = request.getParameter("name");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int user_no = Integer.parseInt(request.getParameter("user_no"));
			int hit = 0;
			//System.out.println(user_no); 확인용
			
			BoardVo bVo = new BoardVo(title, content, hit, user_no);
			
			BoardDao bDao = new BoardDao();
			bDao.insert(bVo);

			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
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
			//int hit = Integer.parseInt(request.getParameter("hit"));
			/*
			int hitplus = hit++;//db에 조회수 증가 업데이트 하기 위해 dao에 uphit메소드 만들었음 -안먹음
			
			BoardVo bVo = new BoardVo(no,hitplus);
			System.out.println(bVo);*/
			
			//hit++;
			//System.out.println(hit);
			
			BoardDao bDao = new BoardDao();
			//bDao.uphit(bVo);
			BoardVo boardVo = bDao.read(no);
			System.out.println(boardVo);
			
			int hit = boardVo.getHit();
			hit++;
			boardVo.setHit(hit);
			System.out.println(boardVo);
			
			bDao.uphit(boardVo);	
			
			/*
			int count = boardVo.getHit();
			count=0;
			int hit = count++;
			System.out.println(hit);//0 등록만들기*/
			
			request.setAttribute("bVo", boardVo);
			//request.setAttribute("Hit", hitplus);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
			
		}else if("modifyForm".equals(act)) {
			System.out.println("action=modifyForm");	
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			BoardVo boardVo = bDao.read(no);

			request.setAttribute("bVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		}else if("modify".equals(act)) {
			System.out.println("action=modify");
			
			int no =Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo bVo = new BoardVo(no, title, content);
			
			BoardDao bDao = new BoardDao();
			bDao.update(bVo);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");		
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
