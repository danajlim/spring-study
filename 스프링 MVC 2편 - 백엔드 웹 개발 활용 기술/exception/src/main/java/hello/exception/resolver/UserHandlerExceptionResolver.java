package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Spring MVC에서 발생한 예외 중 UserException을 감지해서 커스터마이징된 응답을 보냄
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    //예외가 발생했을 때 실행
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            //예외가 UserException일 경우
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                //클라이언트가 Accept: application/json을 보냈는지 확인
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                //JSON을 원하는 요청이면
                if ("application/json".equals(acceptHeader)) {
                    //에러 정보를 Map에 담고 → Jackson ObjectMapper로 JSON 문자열로 변환
                    Map<String,Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult);

                    //JSON 응답으로 내려줌
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(result);

                    //예외 처리 완료
                    return new ModelAndView();

                } else {
                    //HTML 요청이면 error/500이라는 뷰로 이동
                    //TEXT/HTML
                    return new ModelAndView("error/500");
                }
            }

        } catch (IOException e) {
            log.error("resolver ex",e);
        }

        return null;
    }
}
