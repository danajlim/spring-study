package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*HTML를 출력하는데 특화된 서블릿
* 브라우저는 HTTP 응답을 웹페이지처럼 보여줌
*/
@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Content-type: text/html; charset = utf-8
        response.setContentType("text/html"); //응답의 content-type을 HTML로 지정
        response.setCharacterEncoding("utf-8"); //브라우저는 이 응답을 HTML 문서로 해석함

        //응답 바디에 HTML 태그를 직접 작성하여 출력
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println(" <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
