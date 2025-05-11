package hello.core.singleton;

public class SingletonService {
    //싱글톤 : 애플리케이션 전체에서 단 하나의 인스턴스만 존재하도록

    //클래스에 하나만 존재, 정해진 값 변경 x
    private static final SingletonService instance = new SingletonService();

    //외부에서는 new SingletonService()로 객체를 만들 수 없고, 이 메서드를 통해서만 객체를 가져올 수 있음
    public static SingletonService getInstance() {
        return instance;
    }

    //외부에서 갹체 생성 차단
    private SingletonService(){}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
