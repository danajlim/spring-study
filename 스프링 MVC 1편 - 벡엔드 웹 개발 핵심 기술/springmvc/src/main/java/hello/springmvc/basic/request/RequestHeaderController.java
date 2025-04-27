package hello.springmvc.basic.request;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    //HTTP 요청 정보를 다양한 방식으로 매개변수로 전달받고 있는 구조
    public String headers(HttpServletRequest request, //서블릿에서 제공하는 객체로, 요청과 응답의 원본 객체
                          HttpServletResponse response,
                          HttpMethod httpMethod, //GET, POST, PUT 등 HTTP 요청의 메서드 타입
                          Locale locale, //클라이언트의 지역/언어 설정 정보를 담은 객체
                          @RequestHeader MultiValueMap<String, String> headerMap, //모든 요청 헤더를 Map 형태로, 하나의 헤더 키에 여러 값이 있을 수 있어서 MultiValueMap 사용
                          @RequestHeader("host") String host, //요청 헤더 중 "host" 값을 따로 변수로 받음
                          @CookieValue(value = "myCookie", required = false) String cookie){ //쿠키 중 이름이 "myCookie"인 값을 받아옴

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";

    }
}
