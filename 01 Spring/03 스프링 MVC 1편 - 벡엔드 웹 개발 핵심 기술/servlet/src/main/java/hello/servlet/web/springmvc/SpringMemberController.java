package hello.servlet.web.springmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller //웹 요청을 처리하는 controller
@RequestMapping("/springmvc/members") //이 클래스의 모든 메서드는 이 URL 경로로 시작함
public class SpringMemberController {

    //회원 정보 저장소 불러오기
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm() {
        return "new-form"; //뷰 이름 "new-form"을 반환
    }

    @PostMapping("/save")
    public String save(
            //폼에 입력된 "username"이라는 이름의 값을 자바 변수 username에 바인딩
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            //뷰에 데이터를 넘기기 위한 객체
            Model model
            ) {

        //새로운 멤버 저장
        Member member = new Member(username, age);
        memberRepository.save(member);

        //컨트롤러 -> 뷰로 데이터 전달
        model.addAttribute("member", member);
        return "save-result"; //뷰 이름 "save-result"을 반환
    }

    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members"; //뷰 이름 "members"를 반환
    }
}
