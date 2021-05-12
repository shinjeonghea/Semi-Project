package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.PagingBean;
import org.kosta.avg.age.model.PostDAO;
import org.kosta.avg.age.model.PostVO;

public class PostListController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int totalPostCount=PostDAO.getInstance().getTotalPostCount();
		String pageNo=request.getParameter("pageNo");
		PagingBean pagingBean=null;
		if(pageNo==null) {
			pagingBean=new PagingBean(totalPostCount);
		}else {
			pagingBean=new PagingBean(totalPostCount,Integer.parseInt(pageNo));
		}
		//list.jsp 에서 페이징 처리를 위해 pagingBean을 request 영역에 공유한다 
		request.setAttribute("pagingBean", pagingBean);
		
		ArrayList<PostVO> postList =  PostDAO.getInstance().getPostingList(pagingBean);
		
		request.setAttribute("postList", postList);
		request.setAttribute("url", "/board/board-list.jsp");		
		return "/template/layout.jsp";
	}

}
