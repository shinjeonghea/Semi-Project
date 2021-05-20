package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.kosta.avg.age.model.BookMarkChannelVO;
import org.kosta.avg.age.model.BookMarkDAO;
import org.kosta.avg.age.model.BookMarkFolderVO;
import org.kosta.avg.age.model.MemberVO;
import org.kosta.avg.age.model.YoutubeVO;

public class AddChannelController implements Controller{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("mvo")==null||
				request.getMethod().equals("POST")==false){
			return "redirect:index.jsp";
		}
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		String id = mvo.getId();
		String addfolderName = request.getParameter("folder");
		String youtube_str = request.getParameter("checkChannel");
		String addChannelName=substringBetween(youtube_str, "title=", ",");
		String channelURL=substringBetween(youtube_str, "channelURL=", ",");
		//System.out.println("ㅁㅁㅁㅁㅁㅁ"+title+" " +channelURL);
		/*System.out.println(id);
		System.out.println(addfolderName);
		System.out.println(addChannelName);
		System.out.println(channelURL);*/
		BookMarkDAO.getInstance().addChannelMember(id, addfolderName, addChannelName, channelURL);
		
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
		ArrayList<BookMarkFolderVO> folderList = BookMarkDAO.getInstance().getFolderNameByMemberId(id);
		
		for(int i=0;i<folderList.size();i++) {
			channelList.addAll(BookMarkDAO.getInstance().getChannelByMemberId(folderList.get(i).getFolderNo()));
		}
		
		/*for(int k=0;k<channelList.size();k++) {
			System.out.println(channelList.get(k));
		}*/

		
		session.setAttribute("flist", folderList);
		session.setAttribute("clist", channelList);
		
		return "member/add-channel-ok.jsp";

	}
	
	private String substringBetween(String str, String open, String close) {
	    if (str == null || open == null || close == null) {
	       return null;
	    }
	    int start = str.indexOf(open);
	    if (start != -1) {
	       int end = str.indexOf(close, start + open.length());
	       if (end != -1) {
	          return str.substring(start + open.length(), end);
	       }
	    }
	    return null;
	}

}
