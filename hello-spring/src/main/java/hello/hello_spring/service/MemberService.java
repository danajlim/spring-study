package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

//“회원 가입”, “중복 검증”, “회원 조회” 등 비즈니스 로직을 담당하는 서비스 계층
public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //회원가입
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //같은 이름 중복 회원 x
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
