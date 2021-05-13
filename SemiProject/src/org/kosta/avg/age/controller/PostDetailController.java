package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class PostDetailController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null){
			return "redirect:index.jsp";
		}
		 
		// 해당 게시물 번호 가져옴.
		String postNo =request.getParameter("postNo");
		
		// 해당 게시글에 저장되어있는 추천 폴더의 채널들 불러옴.
		ArrayList<BookMarkChannelVO> channelList = PostDAO.getInstance().showUploadedChannelList(postNo);
		request.setAttribute("channelList", channelList);
		
		// 개별 게시물 조회 
		PostDAO.getInstance().updateHit(postNo);
		PostVO pvo = PostDAO.getInstance().getPostingByNo(postNo);
		request.setAttribute("folderName", PostDAO.getInstance().getFolderNameByPostNo(postNo));
		request.setAttribute("pvo", pvo);
		request.setAttribute("url", "/board/post-detail.jsp");
		return "/template/layout.jsp";
	}

}
