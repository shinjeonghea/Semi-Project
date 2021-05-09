package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.BookMarkDAO;

public class AddChannelController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookMarkDAO.getInstance().addChannelMember("kgs", "요리", "백종원의 요리비책", "url");
		return "index.jsp";
	}

}
