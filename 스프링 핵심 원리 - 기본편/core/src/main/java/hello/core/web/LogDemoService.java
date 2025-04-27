package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

//LogDemoService – 비즈니스 로직 계층
@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    //로그 찍는 메서드
    public void logic(String id){
        myLogger.log("service id = " + id);
    }
}
