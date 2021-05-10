package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.BookMarkDAO;

public class UpdateFolderNameController implements Controller{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookMarkDAO.getInstance().updateFolderNameMember("kgs", "맛집", "쿠킹");
		return "index.jsp";
	}

}
