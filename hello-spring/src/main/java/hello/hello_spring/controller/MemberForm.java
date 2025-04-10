package hello.hello_spring.controller;

//form 데이터를 담기 위한 객체
//사용자 입력값을 자바 객체로 자동 변환해 컨트롤러에 넘김
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
