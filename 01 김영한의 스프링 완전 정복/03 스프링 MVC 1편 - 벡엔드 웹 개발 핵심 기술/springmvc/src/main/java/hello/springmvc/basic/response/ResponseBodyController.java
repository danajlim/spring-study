package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController // -> @Controller + @ResponseBody
//@Controller @ResponseBody
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //문자 처리
    //"ok"을 HTTP 응답 바디에 직접 넣어줌
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    //JSON 응답을 생성
    @GetMapping("/response-body-json-v1")
    //ResponseEntity: 응답바디, 응답상태코드, 응답헤더(선택) 을 모두 설정할 수 있는 응답 컨테이너
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        //응답에 보낼 데이터를 객체로 생성, 이 객체는 JSON으로 변환돼서 클라이언트에게 전달
        HelloData helloData = new HelloData();
        helloData.setUsername("dana");
        helloData.setAge(20);

        /*
        HTTP/1.1 200 OK
        Content-Type: application/json

        {
            "username": "dana",
            "age": 20
        }
        */
        return new ResponseEntity<>(helloData, HttpStatus.OK); //HTTP 상태 코드까지 명확하게 지정
    }

    @ResponseStatus
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
