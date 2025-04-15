package hello.core.scan.filter;

import java.lang.annotation.*;

//등록 필터용 커스텀 애노테이션
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//커스텀 annotation 만들기 : Spring 컴포넌트 스캔 필터링 기능
public @interface MyIncludeComponent {

}
