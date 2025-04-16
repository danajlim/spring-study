package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//lombok : geter, setter, 생성자도 자동으로 생성해줌
@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args){
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setAge("dana");

        String name = helloLombok.getName();
        System.out.println("name = " + name);
    }
}
