package org.kosta.avg.age.model;

import java.util.ArrayList;

public class PostVO {
	private String postNo;
	private String title;
	private String content;
	private String timePosted;
	private int hits;
	private MemberVO mvo;
	private ArrayList<BookMarkChannelVO> list;
	public PostVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PostVO(String postNo, String title, String content,
			String timePosted, int hits, MemberVO mvo) {
		super();
		this.postNo = postNo;
		this.title = title;
		this.content = content;
		this.timePosted = timePosted;
		this.hits = hits;
		this.mvo = mvo;
	}
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(String timePosted) {
		this.timePosted = timePosted;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public MemberVO getMvo() {
		return mvo;
	}
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	public ArrayList<BookMarkChannelVO> getList() {
		return list;
	}
	public void setList(ArrayList<BookMarkChannelVO> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PostVO [postNo=" + postNo + ", title=" + title
				+ ", content=" + content + ", timePosted=" + timePosted + ", hits=" + hits + ", mvo=" + mvo+ "]";
	}
	
}