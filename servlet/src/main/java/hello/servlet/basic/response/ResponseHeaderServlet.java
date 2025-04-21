package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/*HTTP 응답 헤더와 본문을 수동으로 설정해서 전송
* HTTP 응답 메시지를 구성하는 웹 계층(Controller 역할) 클래스
* */
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    //모든 HTTP 요청(GET, POST 등 포함)이 들어왔을 때 처리할 로직
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        //상태 코드 설정 (정상)
        response.setStatus(HttpServletResponse.SC_OK);

        //[response-headers]
        //응답 헤더 설정

        //응답의 content-type을 plain text로
        response.setHeader("Content-Type", "text/plain");
        //클라이언트가 응답을 캐시하지 않도록 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma","no-cache");
        //사용자 정의 헤더
        response.setHeader("my-header", "hello");

        //[Header 편의 메서드]
        content(response);
        cookie(response);
        redirect(response);

        //[Message Body]
        PrintWriter writer = response.getWriter();
        writer.println("ok");
    }

    /*Content 편의 메서드 : 응답의 Content-Type과 인코딩 설정
    Content-Type과 Charset을 분리해서 지정
    Content-Length는 생략 시 자동 계산됨
    */
    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        // Content-Length: 2
        // response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    /*쿠키 편의 메서드 : HTTP 쿠키를 설정해서 클라이언트에 전달
    Set-Cookie 헤더를 자동 생성
    쿠키 이름은 "myCookie", 값은 "good"이고, 10분간 유효
    */
    private void cookie(HttpServletResponse response) {
    //Set-Cookie: myCookie=good; Max-Age=600;
    //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    /*redirect 편의 메서드 : 브라우저에게 다른 페이지로 이동하라고 지시
    HTTP 302 Found 상태 코드 + Location 헤더를 자동으로 설정하여 리다이렉트 실행
    */
    private void redirect(HttpServletResponse response) throws IOException {
    //Status Code 302
    //Location: /basic/hello-form.html
    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
