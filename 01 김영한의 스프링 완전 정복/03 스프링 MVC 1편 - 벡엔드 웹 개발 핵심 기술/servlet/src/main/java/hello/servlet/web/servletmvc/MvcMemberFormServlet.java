package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/*
* HttpServlet을 상속받아 HTTP 요청을 처리하는 서블릿 클래스
* 이름에서 알 수 있듯 MVC 패턴의 Controller 역할
*/
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //보여줄 JSP 뷰 파일 경로 : servlet을 통해 forward
        String viewPath = "/WEB-INF/views/new-form.jsp";
        //JSP로 서버 내부에서 요청을 전달(forward) : 브라우저 주소는 그대로 유지된채 내부적으로 new-form.jsp가 렌더링됨
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
