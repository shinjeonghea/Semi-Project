package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosta.avg.age.model.MemberVO;
import org.kosta.avg.age.model.PagingBean;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class SearchPostController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("mvo") == null) {
			return "redirect:PostListController.do";
		}
		
		String searchOption = null;
		String keyword = null;
		if(request.getParameter("searchOption")==null) {
			searchOption = (String) session.getAttribute("searchOption");
		}else {
			searchOption = request.getParameter("searchOption");
			session.setAttribute("searchOption", searchOption);
		}
		
		if(request.getParameter("keyword")==null) {
			keyword = (String) session.getAttribute("keyword");
		}else {
			keyword = request.getParameter("keyword");
			session.setAttribute("keyword", keyword);
		}
		int totalPostCount=0;
		if(searchOption.equals("title") || searchOption.equals("content") || searchOption.equals("nick")) {
			totalPostCount = PostDAO.getInstance().getTotalPostCountByOption(searchOption, keyword);
		}else if(searchOption.equals("titleAndContent")) {
			totalPostCount = PostDAO.getInstance().getTotalPostCountByTitleAndContent(keyword);
		}
		
		String pageNo=request.getParameter("pageNo");
		PagingBean pagingBean=null;
		if(pageNo==null) {
			pagingBean=new PagingBean(totalPostCount);
		}else {
			pagingBean=new PagingBean(totalPostCount,Integer.parseInt(pageNo));
		}
		//list.jsp 에서 페이징 처리를 위해 pagingBean을 request 영역에 공유한다 
		request.setAttribute("pagingBean", pagingBean);
		
		
		ArrayList<PostVO> postList = null;
		if(searchOption.equals("title") || searchOption.equals("content") ){
			postList = PostDAO.getInstance().getPostingListByOption(searchOption, keyword, pagingBean);
		}else if(searchOption.equals("titleAndContent")) {
			postList = PostDAO.getInstance().getPostingListByTitleAndContent(keyword, keyword, pagingBean);
		}else if(searchOption.equals("nick")) {
			postList = PostDAO.getInstance().getPostingListByNick(searchOption, keyword, pagingBean);
		}
		for(int i=0; i<postList.size(); i++) {
			System.out.println(postList.get(i));
		}
		
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("postList", postList);
		request.setAttribute("url", "/board/search-list.jsp");
		return "/template/layout.jsp";
	}

}
