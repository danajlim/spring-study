package hello.login.web.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    //컨트롤러 호출 전에 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI(); //사용자가 요청한 URI 가져옴 (ex: "/members/1/edit")
        String uuid = UUID.randomUUID().toString(); //이 요청을 식별할 수 있는 고유한 아이디 생성

        request.setAttribute(LOG_ID,uuid); //생성한 UUID를 요청 객체에 저장 ->저장해두면 나중에 afterCompletion()에서도 이 값을 꺼내서 응답 로그에 사용할 수 있음

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        //handler: 이 요청을 처리할 대상
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true; //반환값이 true면 다음 단계로 진행, false면 요청 중단

    }

    // 컨트롤러 실행 후, 뷰 렌더링 전에 실행
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView); //뷰에 데이터 전달하기 직전에 실행됨
    }

    //요청 처리 완료 후 (뷰 렌더링까지 끝난 후)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        Object uuid = request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}]", uuid, requestURI, handler);

        if(ex != null){
            log.error("afterCompletion error!!", ex);
        }


    }
}
