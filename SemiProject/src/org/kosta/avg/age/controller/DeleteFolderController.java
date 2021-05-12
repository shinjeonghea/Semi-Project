package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.BookMarkFolderVO;
import org.kosta.avg.age.model.MemberVO;

public class DeleteFolderController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}
		
		//DB에서 해당 아이디에 저장된 폴더이름 지우고 지워진 DB를 세션에 업데이트 해주기
		
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		String id = mvo.getId();
		String delfolderName=request.getParameter("delfolderName");
		BookMarkDAO.getInstance().deleteFolderMember(id, delfolderName);
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
		
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		session.setAttribute("flist", folderList);
		session.setAttribute("clist", channelList);
		return "index.jsp";
	}

}
