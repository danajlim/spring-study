package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
* HelloData 객체를 JSON으로 변환해서 클라이언트에게 응답하는 역할
* */
@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    //Jackson 라이브러리의 핵심 객체 : 자바 객체 ↔ JSON 간 변환(직렬화, 역직렬화) 을 담당
    private ObjectMapper objectMapper = new ObjectMapper();

    //모든 HTTP 요청(GET, POST 등)을 처리하는 서블릿 메서드
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/JSON");
        response.setCharacterEncoding("utf-8");

        //응답으로 보낼 데이터 객체 생성
        HelloData helloData = new HelloData(); //HelloData는 일반적인 DTO 클래스
        helloData.setUsername("dana");
        helloData.setAge(20);

        //HelloData 객체를 JSON 문자열로 변환
        String result = objectMapper.writeValueAsString(helloData);

        //변환된 JSON 문자열을 HTTP 응답 본문(body) 에 기록
        response.getWriter().write(result);
    }
}
