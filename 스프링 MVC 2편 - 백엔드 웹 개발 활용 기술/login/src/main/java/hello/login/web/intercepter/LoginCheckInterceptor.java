package hello.login.web.intercepter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    //컨트롤러가 실행되기 전 요청을 가로채서 로그인 상태인지 확인함
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI(); //요청 경로 얻어오기
        log.info("인증 체크 인터셉터 실행: [{}]", requestURI);

        HttpSession session = request.getSession(false); //세션을 가져오고 없으면 새로 생성됨

        //세션이 없거나 로그인 정보가 없으면 로그인 실패
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL="+requestURI); //로그인되지 않은 사용자면 로그인 페이지로 리다이렉트 -> 로그인 후 원래 요청 페이지로 이동 가능
            return false; //요청 처리 중단 (컨트롤러까지 가지 않음)
        }

        return true;
    }
}
