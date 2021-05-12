package org.kosta.avg.age.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.avg.age.model.MemberDAO;
import org.kosta.avg.age.model.MemberVO;

public class JoinController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		boolean check = MemberDAO.getInstance().join(id,password,nickname);
		if(check==true) {
			return  "redirect:index.jsp";
		} else {
			return "redirect:member/join-fail.jsp";
	}

}
}
