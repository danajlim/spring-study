package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    // "/hello"로 GET 요청이 오면
    @GetMapping("hello")
    public String hello(Model model){
        //모델에 "data"라는 이름으로 값 저장
        model.addAttribute("data",  "hello!!");
        return "hello"; // "hello.html" 템플릿을 찾아 렌더링하라는 의미
    }

    //"/hello-mvc"로 GET 요청이 오면
    @GetMapping("hello-mvc")
    //?name=xxx 파라미터 값을 name 변수에 매핑
    public String helloMvc(@RequestParam(value="name") String name, Model model){
        //name이라는 이름으로 템플릿에 데이터를 넘김
        model.addAttribute("name",name);
        return "hello-template";
    }
}
