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
		// 채널에 대해 받아온 정보
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		String id = mvo.getId();
		String folderNo = request.getParameter("folderNo");
		String channelName = request.getParameter("channelName");
		System.out.println(id);	
		System.out.println(folderNo);		
		System.out.println(channelName);
		
		// DB애서 채널에 대한 정보 지우는 메서드 실행
		BookMarkDAO.getInstance().deleteChannelMember(id, folderNo, channelName);
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();

		for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		session.setAttribute("flist", folderList);
		session.setAttribute("clist", channelList);
		return "index.jsp";
	}

}
