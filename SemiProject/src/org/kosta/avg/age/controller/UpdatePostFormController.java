package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class UpdatePostFormController implements Controller {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}
		
		String postNo = request.getParameter("postNo");
		PostVO pvo = PostDAO.getInstance().getPostingByNo(postNo);	
		
		request.setAttribute("folderName", PostDAO.getInstance().getFolderNameByPostNo(postNo));
		ArrayList<BookMarkChannelVO> channelList = PostDAO.getInstance().showUploadedChannelList(postNo);
		request.setAttribute("channelList", channelList);
		request.setAttribute("folderName", PostDAO.getInstance().getFolderNameByPostNo(postNo));
		request.setAttribute("pvo", pvo);
		request.setAttribute("url", "/board/update.jsp");
		return "/template/layout.jsp";
	}
}






