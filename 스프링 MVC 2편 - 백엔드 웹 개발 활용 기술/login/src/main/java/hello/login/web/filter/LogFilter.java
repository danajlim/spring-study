package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

//로그인 체크 필터
@Slf4j
public class LogFilter implements Filter {
    //필터가 생성될때 한번 실행됨
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    //필터가 제거될때 한번 호출됨
    @Override
    public void destroy() {
        log.info("log filter destroy");
    }

    //요청이 올때마다 실행되는 핵심 메서드 doFilter -> 요청 전, 후 작업을 할 수 있음
    @Override
    public void doFilter(ServletRequest request, ServletResponse reponse, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request; //원래 ServletRequest는 기능이 제한되어 있기 때문에, 실제 HTTP 요청 정보를 얻기 위해 HttpServletRequest로 다운캐스팅
        String requestURI = httpRequest.getRequestURI(); //요청 URI 가져오기 (예: /login)

        String uuid = UUID.randomUUID().toString(); //요청별로 고유하게 추적하기 위한 UUID 생성

        try{
            //요청 전에: UUID와 URI 로그에 남김
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            //요청 처리 중: chain.doFilter로 다음 필터 또는 컨트롤러 호출
            chain.doFilter(request, reponse);
        } catch (Exception e){
            //예외가 발생하면 다시 던짐
            throw e;
        } finally {
            //요청후: 응답이 끝났을때 UUID와 URI를 다시 로그로 남김
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }
}
