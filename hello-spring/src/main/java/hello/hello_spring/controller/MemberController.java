package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;


    @Autowired //자동으로 Memberservice (빈 객체)를 넣어줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
