package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.BookMarkVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.MemberDAO;
import org.kosta.avg.age.model.MemberVO;


public class LoginController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<BookMarkVO> channelList = new ArrayList<BookMarkVO>();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		MemberVO mvo = MemberDAO.getInstance().login(id, password);
		//System.out.println(mvo);
		if (mvo != null) {
			HttpSession session = request.getSession();
			session.setAttribute("mvo", mvo);
			//폴더 값 가져오기
			ArrayList<Integer> folderNameList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
			//폴더 값 확인 코드
			/*for(int i=0;i<folderNameList.size();i++) {
				System.out.println(folderNameList.get(i));
			}*/ 
			session.setAttribute("flist", folderNameList);
			for(int i=0;i<folderNameList.size();i++) {
				channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderNameList.get(i)));
			}
			for(int k=0;k<channelList.size();k++) {
				System.out.println(channelList.get(k));
			}
			session.setAttribute("clist", channelList);
			return "redirect:index.jsp";
		} else {
			return "redirect:member/login-fail.jsp";
		}
	}

}