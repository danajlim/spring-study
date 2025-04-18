package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//LogDemoController – 웹 요청 처리 컨트롤러: HTTP 요청 처리 + 로그 호출 + 서비스 호출
@Controller
@RequiredArgsConstructor //lombok 라이브러리 제공 -> final이나 @NonNull 필드에 대해 생성자를 자동 생성

public class LogDemoController {
    private final LogDemoService logDemoService;
    //지연 조회(지연 주입)을 위한 기능 ObjectProvider
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        //getObject 호출 시 현재 요청 컨텍스트에 맞는 MyLogger을 찾아서 주입
        MyLogger myLogger = myLoggerProvider.getObject();
        //현재 요청의 URL을 얻어서
        String requestURL = request.getRequestURL().toString();
        //MyLogger에 전달 (setRequestURL)
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        //서비스 로직 호출
        logDemoService.logic("testId");
        return "OK";
    }
}
