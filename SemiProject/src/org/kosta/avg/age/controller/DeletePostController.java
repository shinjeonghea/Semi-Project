package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.PostDAO;

public class DeletePostController implements Controller {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:PostListController.do";
		}
		String postNo=request.getParameter("postNo");
		PostDAO.getInstance().deletePosting(Integer.parseInt(postNo));
		// 게시물 목록을 보여주기 위해
		// 리다이렉트 방식으로 이동시킨다. 
		return "redirect:PostListController.do";
	}
 
}






