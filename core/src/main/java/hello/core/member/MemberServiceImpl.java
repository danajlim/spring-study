package hello.core.member;

public class MemberServiceImpl implements MemberService{

    //추상화에만 의존 -> 구체적인 구현 repository에 의존하지 않음
    private final MemberRepository memberRepository;

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
