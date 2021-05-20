package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.BookMarkFolderVO;
import org.kosta.avg.age.model.MemberVO;

public class ReceiveFolderController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}	
		
		MemberVO mvo = (MemberVO)session.getAttribute("mvo");
		String postNo = request.getParameter("postNo");
		
		// 게시글에서 채널을 추가함.
		BookMarkDAO.getInstance().receiveChannelFromPost(mvo.getId(), postNo);

		// 세션을 새로 갱신해줌.
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
		//폴더 값 가져오기
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(request.getParameter("id"));
		
		session.setAttribute("flist", folderList);
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}
		session.setAttribute("clist", channelList);
		
		return "redirect:PostDetailNoHitsController.do?postNo="+postNo;
	}

}