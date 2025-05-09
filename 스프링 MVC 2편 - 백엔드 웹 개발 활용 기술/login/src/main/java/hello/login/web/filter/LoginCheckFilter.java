package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//사용자가 로그인하지 않은 상태에서 보호된 페이지에 접근할 경우, 로그인 페이지로 redirect 시키는 역할
@Slf4j
public class LoginCheckFilter implements Filter {

    //로그인하지 않아도 접근 가능한 URL 경로 목록 -> 필터 체크 대상에서 제외
    private static final String[] whiteList = {"/","/members/add" };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; //request 다운캐스팅
        HttpServletResponse httpResponse = (HttpServletResponse) response; //response 다운캐스팅
        String requestURI = httpRequest.getRequestURI(); //현재 요청의 URI를 가져옵니다 (예: /mypage)


        try {
            log.info("인증 체크 필터 시작{}", requestURI);
            //화이트리스트에 없는 경로인지 검사
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                //기존 세션을 가져옴
                HttpSession session = httpRequest.getSession(false);
                //세션이 없거나 로그인 정보(LOGIN_MEMBER)가 없으면 (로그인 x 상태)
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청{}", requestURI);
                    //로그인페이지로 redirect시킴
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response); //다음 필터 또는 컨트롤러로 요청 넘김
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료{}", requestURI);
        }
    }

    //whitelist와 요청 URI를 비교해서 일치하면 true 반환
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
