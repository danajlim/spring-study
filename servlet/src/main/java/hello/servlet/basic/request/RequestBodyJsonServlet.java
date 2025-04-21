package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet (name = "requestBodyJsonServley", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    //Jackson 라이브러리의 핵심 클래스 : JSON 문자열 <-> Java 객체 간 직렬화/역직렬화 담당
    private ObjectMapper objectMapper = new ObjectMapper();

    //모든 HTTP 요청에 대해 처리하는 메서드 (GET/POST 구분 없이)
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HTTP 요청의 body(raw 데이터)를 바이트 스트림으로 읽어옴
        ServletInputStream inputStream = request.getInputStream();
        //copyToString()로 문자열로 변환
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        //readvalue()로 JSON 문자열 -> 자바 객체로 변환
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());
    }
}
