package hello.springmvc.basic;

import lombok.Data;

@Data
//@Data 쓰면 자동으로 @Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 다 자동으로 적용해줌
public class HelloData {
    private String username;
    private int age;
}
