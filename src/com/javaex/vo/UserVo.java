package com.javaex.vo;

public class UserVo {
	
	//필드
	private int no;//똑같은 이름이 잘어울리니까 해놓은거지 다른이름(n) 한다고 안들어가고 하진않음 db에서의 이름과 꼭 똑같아야하는건아님
	private String id;
	private String password;
	private String name;
	private String gender;
	
	//생성자
	public UserVo() {
		
	}
	
	
	public UserVo(String id, String password, String name, String gender) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.gender = gender;
	}


	public UserVo(int no, String id, String password, String name, String gender) {
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.gender = gender;
	}


	//메소드 g/s
	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	//메소드 일반
	@Override
	public String toString() {
		return "UserVo [no=" + no + ", id=" + id + ", password=" + password + ", name=" + name + ", gender=" + gender
				+ "]";
	}
	
}
