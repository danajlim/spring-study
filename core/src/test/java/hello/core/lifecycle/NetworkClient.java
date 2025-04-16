package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {
    private String url;

    public NetworkClient(){
        //객체를 생성하면서 실행됨
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메세지");
    }

    //의존성 주입 : url 값이 설정됨
    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect " + url);
    }

    public void call(String message){
        System.out.println("call " + url + "message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close: " + url);
    }

    //초기화 메서드
    //url이 주입된 후에 실행됨 (Spring이 Bean 초기화할 때 자동 호출)
    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메세지");
    }

    //종료 메서드
    //ApplicationContext가 닫힐때 자동으로 호출됨
    @PreDestroy
    //연결종료 : 객체 소멸 전 정리
    public void close(){
        disconnect();
    }
}
