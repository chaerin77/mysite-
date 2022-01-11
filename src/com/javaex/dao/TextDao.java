package com.javaex.dao;
import com.javaex.vo.UserVo;

public class TextDao {

	public static void main(String[] args) {

		//UserDao의 insert메소드 잘 돌아가는지 테스트해보려고 만든것
		UserDao uDao = new UserDao();
		
		//바인딩에서 id~gender 까지 4개의 데이터 필요한데(no는 sql에서 시퀀스번호써서 자동증가되니까) UserVo에서 생성자4개짜리를 만들거나 set으로넣거나
		UserVo uvo = new UserVo("ccc", "1234", "강호동", "male");
		
		uDao.insert(uvo);
	}

}
