package hello.itemservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    /*
    * DefaultMessageCodesResolver의 기본 메세지 생성 규칙
    *
    * [객체 오류]
    * 1. code + "." + object name
    * 2. code
    *
    * [필드 오류]
    * 1. code + "." + object name
    * 2. code + "." + field
    * 3. code + "." + field type
    * 4. code
    *
    * */

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item","itemName", String.class);
        Assertions.assertThat(messageCodes).containsExactly(
                "required.item.itemName", "required.itemName", "required.java.lang.String", "required"
        );

    }



}
