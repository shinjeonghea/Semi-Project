package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class UpdatePostController implements Controller {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}	
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		PostVO pvo=new PostVO();
		pvo.setPostNo(request.getParameter("postNo"));
		pvo.setTitle(title);
		pvo.setContent(content);
		// 해당 게시글에 저장되어있는 추천 폴더의 채널들 불러옴.
		ArrayList<BookMarkChannelVO> channelList = PostDAO.getInstance().showUploadedChannelList(request.getParameter("postNo"));
//		System.out.println(channelList);
		request.setAttribute("channelList", channelList);
		PostDAO.getInstance().updatePosting("폴더 이름", pvo);			
		String path="redirect:PostDetailNoHitsController.do?postNo="+pvo.getPostNo();
		return path;
	}

}







