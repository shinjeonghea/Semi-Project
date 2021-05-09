package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class PostDetailController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 기능 추가 후 다시 !
//		HttpSession session=request.getSession(false);
//		if(session==null||session.getAttribute("mvo")==null){
//			return "redirect:index.jsp";
//		}
		// 개별 게시물 조회  
		String postNo =request.getParameter("postNo");
		
		PostDAO.getInstance().updateHit(postNo);
		
		PostVO pvo = PostDAO.getInstance().getPostingByNo(postNo);		
		request.setAttribute("pvo", pvo);
		request.setAttribute("url", "/board/post-detail.jsp");
		return "/template/layout.jsp";
	}

}
