package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.MemberVO;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class WritePostController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * HttpSession session=request.getSession(false);
		 * if(session==null||session.getAttribute("mvo")==null||
		 * request.getMethod().equals("POST")==false){ return "redirect:index.jsp"; }
		 */
		
		// 라디오 버튼으로 누른 폴더 명을 받아온다.
		String folderName = request.getParameter("addFolder");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		PostVO pvo = new PostVO();
		pvo.setTitle(title);
		pvo.setContent(content);
//        pvo.setMvo((MemberVO)session.getAttribute("mvo"));  
		

		// 임시 사용자 kgs
		MemberVO mvo = new MemberVO();
		mvo.setId("kgs");
		mvo.setNick("고보승");
		mvo.setPassword("1");
		pvo.setMvo(mvo);
		PostDAO.getInstance().posting(folderName, pvo);
		String path = "redirect:PostDetailNoHitsController.do?postNo=" + pvo.getPostNo();
		return path;
	}
}
