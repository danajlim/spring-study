package hello.core.scan.filter;

import java.lang.annotation.*;

//제외 필터용 커스텀 애노테이션
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {

}
