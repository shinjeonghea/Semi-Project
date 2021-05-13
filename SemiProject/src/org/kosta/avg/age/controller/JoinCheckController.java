package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.MemberDAO;

public class JoinCheckController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = request.getParameter("id");
		int check = MemberDAO.getInstance().joinIdCheck(userID);
		System.out.println("여기까지 됨");
		if(check==0) {
			request.setAttribute("checknum", 0);
		}else if(check==1) {
			request.setAttribute("checknum", 1);
		}
		return "AjaxView";
	}

}
