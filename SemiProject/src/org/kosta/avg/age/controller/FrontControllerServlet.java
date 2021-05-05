package org.kosta.avg.age.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("*.do")
public class FrontControllerServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;    
    public FrontControllerServlet() {
        super();       
    }
    /*
     *  step0 예외 발생시 기록을 남기고 error.jsp로 redirect 처리한다 
     *  step1 클라이언트의 요청 uri 로부터 컨트롤러명을 추출 
     *  step2 HandlerMapping 을 이용해 해당 컨트롤러 객체를 반환 
     *  step3 반환받은 컨트롤러 객체를 실행 후 리턴값(view정보)을 반환 
     *  step4 응답할 View를 forward 또는 rediect 방식으로 이동해서 response 하게 한다 
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
       /////// step1 ///////
       String uri=request.getRequestURI();
       //System.out.println(uri);//  /webstudy18-ex/MockController.do
       String contextPath=request.getContextPath();
       //System.out.println(contextPath);// /webstudy18-ex
       String command=uri.substring(contextPath.length()+1,uri.length()-".do".length());
       //System.out.println(command);// MockController
       /////// step2 /////////
       Controller controller=HandlerMapping.getInstance().create(command);
       ////// step3 /////////
       String view=controller.execute(request, response);
       ///// step4 /////////
       if(view.startsWith("redirect:")) {
          response.sendRedirect(view.substring("redirect:".length()));
       }else {
          request.getRequestDispatcher(view).forward(request, response);
       }
       
       }catch (Exception e) {
         e.printStackTrace();
         response.sendRedirect("error.jsp");
      }
    }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //System.out.println("front get");
      handleRequest(request, response);
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      handleRequest(request, response);
   }
}





