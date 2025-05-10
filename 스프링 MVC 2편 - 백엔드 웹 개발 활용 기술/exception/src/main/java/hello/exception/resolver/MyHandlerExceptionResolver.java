package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver { //스프링 MVC의 예외 처리 인터페이스

    //예외가 발생했을 때 호출되는 메서드
    /*
    * request: HTTP 요청 객체
    * response: HTTP 응답 객체
    * handler: 예외가 발생한 컨트롤러 객체, 핸들러
    * ex: 실제로 발생한 예외 객체*/
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            //IllegalArgumentException 타입의 예외가 발생했는지 확인
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400"); //콘솔에 로그를 남깁니다
                //HTTP 응답 코드 400(Bad Request)**를 클라이언트에 보냅니다
                // 예외 메시지를 HTTP 에러 응답에 함께 담습니다
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView(); //빈 ModelAndView 객체를 반환 -> 별도 뷰로 이동하지 않고 응답이 끝나게 됨
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
