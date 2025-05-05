package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    /*
    *  HTTP 요청의 본문(body) 내용을 JSON으로 받아서 ItemSaveForm에 바인딩
    * ItemSaveForm 클래스에 지정한 Bean Validation(@NotNull, @Min 등)을 자동으로 적용해줌
    * BindingResult: 검증 결과를 담는 객체. 유효성 실패 시 이 안에 에러 정보가 담김
    * */
    @PostMapping("/add")
    public Object addItem(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult) {

        log.info("API 컨트롤러 호출");

        //검증 실패 여부 확인
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        //요청으로 받은 form 객체 그대로 JSON으로 응답
        return form;

    }
}
