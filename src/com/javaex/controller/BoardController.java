package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board")
public class BoardController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String act = request.getParameter("action");
	
		if("list".equals(act)) {
			System.out.println("action=list");
			
			
			
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
