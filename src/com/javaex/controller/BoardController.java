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
			BoardList = bDao.addList();
			System.out.println(BoardList);
			
			
		}else if("modifyForm".equals(act)) {
			System.out.println("action=modifyForm");
		
			
		
		}else if("read".equals(act)) {
			System.out.println("action=read");
			
			
		}else if("writeForm".equals(act)) {
			System.out.println("action=writeForm");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
