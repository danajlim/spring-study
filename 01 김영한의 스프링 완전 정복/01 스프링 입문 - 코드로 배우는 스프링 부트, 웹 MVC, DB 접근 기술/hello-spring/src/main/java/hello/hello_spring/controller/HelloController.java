package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //ResponseBody 문자 반환
    @GetMapping("hello-string")
    @ResponseBody //반환값을 응답 body에 그대로 넣어서 보냄
    public String helloString(@RequestParam("name")  String name){
    return "hello " + name; //"hello spring"
    }

    //예 : http://localhost:8080/hello-api?name=Dana
    //ResponseBody 객체 반환
    @GetMapping("hello-api")
    @ResponseBody
    //(@RequestParam("name") String name) : ?name=xxx 읽어와서 name 변수에 담음
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello(); //빈 객체 생성
        hello.setName(name); //전달받은 이름으로
        return hello; //응답으로 반환
    }

    static class Hello {
        private String name;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }

    }
}
