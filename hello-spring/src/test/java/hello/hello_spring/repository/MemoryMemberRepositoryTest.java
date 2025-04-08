package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemberRepository repository = new MemoryMemberRepository();

    //각 테스트 실행 후 실행됨 : 저장소 초기화
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        //새로운 회원 생성
        Member member = new Member();
        member.setName("dana");
        repository.save(member);

        //저장한 ID로 회원 조회
        Member result = repository.findById(member.getId()).get();
        // 저장한 객체와 조회된 객체가 동일한지 검증
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("dana");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jelly");
        repository.save(member2);

        Member result = repository.findByName("dana").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("dana");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jelly");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
