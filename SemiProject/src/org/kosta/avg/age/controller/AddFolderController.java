package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.BookMarkFolderVO;
import org.kosta.avg.age.model.MemberVO;

public class AddFolderController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		String id = mvo.getId();
		String addfolderName = request.getParameter("addfolderName");
		BookMarkDAO.getInstance().addFolderMember(id, addfolderName);
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
		
		// 로그인 했을 때 추가한 폴더들이 해당 아이디세션과 함께 저장
		session.setAttribute("flist", folderList);
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}
		session.setAttribute("clist", channelList);
		return "redirect:index.jsp";
	}

}
