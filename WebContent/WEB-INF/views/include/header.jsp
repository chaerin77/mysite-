<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");//"authUser" 주소를 authUser에 담는거니까 얘의 형은 따라가보면 UserVo라서 UserVo authUser씀
	//헤더에서만 쓰는거라 index.jsp에서 삭제함 //Scope 이거쓰면 값 다 가져올수있어서 이코드필요없음 vo import도 필요없고
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wrap">
	
		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite/main">MySite</a>
			</h1>

			<%/*
			if(세션영역에 값이 없으면){
				로그인 실패
			}else {
				로그인 성공
			}*/
			%>
			
			<%
			if(authUser == null){%> <!-- 입력한 정보를 만족하는 db의 데이터가 없으면(로그인실패) -->
				<!-- 로그인실패, 로그인전 -->
				<ul>
					<li><a href="/mysite/user?action=loginForm" class="btn_s">로그인</a></li><!-- 로그인 실패하거나 안했을때 -->
					<li><a href="/mysite/user?action=joinForm" class="btn_s">회원가입</a></li>
				</ul>
			<%}else {%>
				<!-- 로그인성공 -->
				<ul>
					<li>${sessionScope.authUser.name} 님 안녕하세요^^</li>
					<!-- <li> 자바코드값/*authUser.getName()*/ 님 안녕하세요^^ </li>-->
					<li><a href="/mysite/user?action=logout" class="btn_s">로그아웃</a></li>
					<li><a href="/mysite/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
				</ul>
			<%}%>
			<!-- sessionScope인지 어떻게알지..? -->
			<c:choose>
				<c:when test="${empty sessionScope.authUser}">
					<ul>
						<li><a href="/mysite/user?action=loginForm" class="btn_s">로그인</a></li><!-- 로그인 실패하거나 안했을때 -->
						<li><a href="/mysite/user?action=joinForm" class="btn_s">회원가입</a></li>
					</ul>
				</c:when>
				
				<c:otherwise>
						<!-- 로그인성공 -->
					<ul>
						<li>${sessionScope.authUser.name} 님 안녕하세요^^</li>
						<li>${authUser.name} 님 안녕하세요^^</li>
						<!-- <li> 자바코드값/*authUser.getName()*/ 님 안녕하세요^^ </li>-->
						<li><a href="/mysite/user?action=logout" class="btn_s">로그아웃</a></li>
						<li><a href="/mysite/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
			
			
			<!-- 
			<ul>
				<li>황일영 님 안녕하세요^^</li>
				<li><a href="" class="btn_s">로그아웃</a></li>
				<li><a href="" class="btn_s">회원정보수정</a></li>
			</ul>
			-->	
			<!--  
			<ul>
				<li><a href="/mysite/user?action=loginForm" class="btn_s">로그인</a></li><!-- 로그인 실패하거나 안했을때 
				<li><a href="/mysite/user?action=joinForm" class="btn_s">회원가입</a></li>
			</ul> -->
			
		</div>
		<!-- //header -->
		
		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite/guest?action=addList">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->
	</div>	

</body>
</html>