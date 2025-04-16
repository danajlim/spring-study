package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {
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

    //여기서부터 진짜 유효한 url 기반으로 연결 시작 : 객체 생성 후 초기화
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 메세지");
    }

    //연결종료 : 객체 소멸 전 정리
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
}
