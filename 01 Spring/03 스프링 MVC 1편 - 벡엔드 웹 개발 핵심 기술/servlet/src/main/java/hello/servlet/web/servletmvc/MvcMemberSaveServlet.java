package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name= "mvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //POST 요청으로 들어온 form-data(username, age) 추출
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        //Member 객체 생성
        Member member = new Member(username, age);
        //저장
        memberRepository.save(member);

        //Model에 데이터를 보관: JSP 뷰로 데이터를 전달하기 위해 request 객체에 member를 담음
        request.setAttribute("member", member);

        //요청을 save-result.jsp로 전달
        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
