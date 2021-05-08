package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.BookMarkDAO;

public class DeleteFolderController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("1");
		//String folderName=request.getParameter("folderName");
		BookMarkDAO.getInstance().deleteFolderMember("kgs", "요리");
		return "index.jsp";
	}

}
