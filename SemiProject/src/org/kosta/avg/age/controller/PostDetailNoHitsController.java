package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkVO;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class PostDetailNoHitsController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HttpSession session=request.getSession(false);
//		if(session==null||session.getAttribute("mvo")==null){
//			return "redirect:index.jsp";
//		}
		
		ArrayList<BookMarkVO> channelList = PostDAO.getInstance().showUploadedChannelList(request.getParameter("postNo"));
		// 나중에 session으로 바꿔주기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		request.setAttribute("channelList", channelList);
		
		// 개별 게시물 조회  
		PostVO vo = PostDAO.getInstance().getPostingByNo(request.getParameter("postNo"));	
		request.setAttribute("pvo", vo);
		request.setAttribute("url", "/board/post-detail.jsp");
		return "/template/layout.jsp";
	}
}







