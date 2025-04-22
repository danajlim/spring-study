package hello.servlet.domain.member;


import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//회원 저장소
public class MemberRepository {

    //static으로 만들어놓는 이유는 모든 인스턴스가 같은 store와 sequence를 사용, 싱글톤처럼 공유 저장소 역할
    private static Map<Long, Member> store = new HashMap<>(); //회원 정보를 저장하는 공간
    private static long sequence = 0L; //회원의 ID를 자동 생성하기 위한 변수

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
