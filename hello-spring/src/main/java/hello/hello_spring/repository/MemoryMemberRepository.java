package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    //회원 데이터 저장하는 store : Long=ID, Member=회원객체
    private static Map<Long, Member> store = new HashMap<>();
    //회원 ID를 자동 증가시키기 위한 변수
    private static long sequence = 0L; //long 타입 변수를 0으로 초기화

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //아이디 세팅 : ID 자동 증가
        store.put(member.getId(),member); //store에 저장
        return member;
    }

    //member 객체가 있을수도 있고 없을수도 있음
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //store에서 꺼냄
    }

    @Override
    public Optional<Member> findByName(String name) {
        //
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); //하나라도 찾으면 바로 반환
    }

    @Override
    public List<Member> findAll() {
        //현재 저장소에 있는 모든 회원 목록을 리스트로 반환
        return new ArrayList<>(store.values());
    }
}
