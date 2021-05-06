package org.kosta.avg.age.model;

import java.util.ArrayList;

public class MemberVO {
	private String id;
	private String password;
	private String nick;
	private ArrayList<BookMarkVO> list;
	public MemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberVO(String id, String password, String nick) {
		super();
		this.id = id;
		this.password = password;
		this.nick = nick;
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
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public ArrayList<BookMarkVO> getList() {
		return list;
	}
	public void setList(ArrayList<BookMarkVO> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", nick=" + nick + ", list=" + list + "]";
	}
	
}
