package com.javaex.vo;

public class GuestbookVo {
	
	//필드 - 자바는대소문자로 두단어 표시 - 카멜 / 오라클은 언더바 _ 로 두단어 표시 - 스네이크
	private int no;
	private String name;
	private String password;
	private String content;
	private String regDate;
	//오라클에는 date형이 있는거고 자바에선 String으로 처리해줘야함
	
	//생성자 파라미터 개수대로 위에서부터 쓰기
	public GuestbookVo() {
		
	}
	
	
	public GuestbookVo(int no, String password) {
		super();
		this.no = no;
		this.password = password;
	}


	public GuestbookVo(String name, String password, String content) {
		super();
		this.name = name;
		this.password = password;
		this.content = content;
	}


	public GuestbookVo(int no, String name, String password, String content, String regDate) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.content = content;
		this.regDate = regDate;
	}

	
	//메소드 g/s
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	
	//메소드 일반
	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
	
}
