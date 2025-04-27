package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //추상화에만 의존 -> 구체적인 구현 repository에 의존하지 않음
    private final MemberRepository memberRepository;

    @Autowired //이걸 생성자에 붙여주면 멤버 리포지토리 타입에 맞는 애를 찾아와서 의존관계를 자동 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberid) {
        return memberRepository.findById(memberid);
    }
}
