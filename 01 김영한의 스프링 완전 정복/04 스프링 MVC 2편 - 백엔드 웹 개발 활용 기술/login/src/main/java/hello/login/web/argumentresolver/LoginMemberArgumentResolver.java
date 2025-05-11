package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@Login이 붙은 파라미터에 어떤 값을 넣을지 결정해주는 Handler Method Argument Resolver
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {


    //어떤 파라미터에 대해 이 리졸버를 적용할지를 판단
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        /*
        * 1. @Login 애노테이션이 붙어 있어야 하고
        * 2. 파라미터 타입이 Member여야 함
        */
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType; //둘다 만족하면 (true) 실행
    }

    //실제로 값을 꺼내서 파라미터에 넣어주는 메서드
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest(); //세션을 가져옴
        HttpSession session = request.getSession(false); // 로그인 정보(SessionConst.LOGIN_MEMBER)를 꺼냄
        if(session == null) {
            return null;
        }

        //파라미터에 넣어줌
        Object member = session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member;
    }
}
