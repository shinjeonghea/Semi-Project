package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class PostListController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PostVO> postList =  PostDAO.getInstance().getPostingList();
		
		request.setAttribute("postList", postList);
		request.setAttribute("url", "/board/board-list.jsp");		
		return "/template/layout.jsp";
	}

}
