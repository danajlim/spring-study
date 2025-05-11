package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //log 함수 쓸 수 있는 애노테이션
@RestController
public class LogTestController {

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);
        log.info("info log = {}", name);

        return "ok";
    }
}
