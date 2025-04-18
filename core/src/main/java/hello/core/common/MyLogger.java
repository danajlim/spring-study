package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

//MyLogger – 요청 스코프 로깅 Bean: HTTP 요청마다 새로운 인스턴스를 생성해서 로그 출력하는 클래스
//uuid와 requestURL을 내부에 저장하고, 로그를 남김
@Component //Spring Bean으로 자동 등록됨
@Scope(value = "request") //HTTP 요청마다 새로 인스턴스를 만든다는 의미, 웹 요청마다 새로 생성
public class MyLogger {
    private String uuid; //Logger 인스턴스에 대한 고유 ID
    private String requestURL; //어떤 요청인지 식별하기 위한 URL

    //requestURL은 이 빈이 생성되는 시점에는 알 Log수 없으므로, 외부에서 setter로 입력 받는다
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    //로그 출력 메서드
    public void log(String message){
        System.out.println("[" + uuid + "[" + requestURL + "]" + message);
    }

    //Bean이 생성되고 의존성 주입이 끝난 뒤 자동 호출, uuid를 생성해서 로깅에 활용
    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this );
    }

    //HTTP 요청이 끝나고 이 Bean이 소멸되기 직전에 자동 호출
    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close:" + this );
    }
}
