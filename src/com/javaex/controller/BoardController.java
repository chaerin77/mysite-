package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

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
			//로그인 하지 않은 사용자가 주소창에 글쓰기폼 주소를 쳐서 들어오려고 한다면
			//어떻게 막을 수 있을지? 만약 누군가 로그인 안한 상태에서 주소창에 글쓰기폼 주소치고 들어오려고 할때 메인페이지로 돌리기
			
			//로그인. 세션에 값이 있는지 없는지를 체크해야함 로그인 했냐안했냐.
			/*
			if(세션에 값이 있으면 --> 로그인 했으면, 로그인 성공하면){
				writeForm
			}else{
				메인으로
			}	
			*/
			HttpSession session = request.getSession(); //세션값 가져오기
			UserVo authUser = (UserVo)session.getAttribute("authUser"); //userController에서 넣어놓은 authUser 갖고오기
			
			if( authUser !=null) {
				System.out.println("로그인 했을 때");
				//포워드
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			}else {
				System.out.println("로그인 안했을 때");
				//리다이렉트 메인으로
				WebUtil.redirect(request, response, "/mysite/main");
			}
		
			
			//WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
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
			
			///////////////////////////////////////////
			
			/*
			int count = boardVo.getHit();
			count=0;
			int hit = count++;
			System.out.println(hit);//0 등록만들기*/
			
			///////////////////////////////////////////
			
			//이 방식은 사용자가 1명인 경우는 가능하지만 다수의 사용자가 사이트를 이용할 때 조회수가 맞지않는 문제가 발생함
			/*
			BoardDao bDao = new BoardDao();
			BoardVo boardVo = bDao.read(no);
			System.out.println(boardVo);
			
			int hit = boardVo.getHit();
			hit++;
			boardVo.setHit(hit);
			System.out.println(boardVo);
			
			bDao.uphit(boardVo);*/	
			
			/////////////////////////////////////////
			
			//no가 __인 사람의 정보를 읽어온 다음 uphit2메소드(쿼리문에 hit=hit+1)사용하여 쿼리문 자체에서 조회수 증가시키기
			BoardDao bDao = new BoardDao();
			BoardVo boardVo = bDao.read(no);
			bDao.uphit2(boardVo);
			
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
