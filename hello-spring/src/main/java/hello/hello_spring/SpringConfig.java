package hello.hello_spring;

import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration //스프링 설정 클래스, 내부에 정의된 bean들 스프링 컨테이너에 등록
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    //스프링 컨테이너에 등록(= 스프링 빈으로 등록)
    @Bean
    public MemberService memberService(){
        // memberRepository()를 호출해 만든 객체를 MemberService 생성자에 주입
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        //@Bean이 붙은 memberRepository() 메서드 -> MemoryMemberRepository 객체 생성
        return new JdbcTemplateMemberRepository(dataSource);
    }

}
