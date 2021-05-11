package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.BookMarkFolderVO;
import org.kosta.avg.age.model.MemberVO;

public class DeleteChannelController implements Controller{

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
		// 폴더 넘버도 받아와야 할 것 같음
		String delchannelName = request.getParameter("delchannelName");
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
		
		BookMarkDAO.getInstance().deleteChannelMember(id, delchannelName);
		
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}
		session.setAttribute("clist", channelList);
		return "index.jsp";
	}

}
