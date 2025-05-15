package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import hello.exception.resolver.MyHandlerExceptionResolver;
import hello.exception.resolver.UserHandlerExceptionResolver;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.logging.Filter;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                "/css/**", "/*.ico"
                , "/error", "/error-page/**" //오류 페이지 경로
        );
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean< Filter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new LogFilter());
        frb.setOrder(1);
        frb.addUrlPatterns("/*");
        frb.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return frb;
    }
}
