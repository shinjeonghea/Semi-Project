package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.BookMarkDAO;

public class AddFolderController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookMarkDAO.getInstance().addFolderMember("kgs", "사랑");
		return "index.jsp";
	}

}
