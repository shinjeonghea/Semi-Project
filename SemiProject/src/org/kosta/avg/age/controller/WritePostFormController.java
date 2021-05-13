package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.MemberVO;
import org.kosta.avg.age.model.PostDAO;

public class WritePostFormController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null){
			return "redirect:index.jsp";
		}

		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		String id = mvo.getId();
		ArrayList<BookMarkChannelVO> flist = PostDAO.getInstance().loadFolderList(id);
		
		request.setAttribute("flist", flist);
		request.setAttribute("url", "/board/write.jsp");
		return "/template/layout.jsp";
	}

}
