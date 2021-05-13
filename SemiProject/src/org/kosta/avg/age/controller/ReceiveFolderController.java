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
		

		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
		//폴더 값 가져오기
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(request.getParameter("id"));
		//폴더 값 확인 코드
		/*for(int i=0;i<folderNameList.size();i++) {
			System.out.println(folderNameList.get(i));
		}*/ 
		session.setAttribute("flist", folderList);
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}
		session.setAttribute("clist", channelList);
		
		
		BookMarkDAO.getInstance().receiveChannelFromPost(mvo.getId(), postNo);
		return "redirect:PostDetailNoHitsController.do?postNo="+postNo;
	}

}
