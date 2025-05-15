package hello.typeconverter.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("formatter/edit")
    public String formatterForm(Model model){

        //Form 객체를 만들어서 초기값으로 number = 10000, dateTime = 현재 시간을 설정
        Form form = new Form();
        form.setNumber(10000);
        form.setDateTime(LocalDateTime.now());
        model.addAttribute("form", form);

        //이 데이터를 뷰(formatter-form.html)에 넘깁니다
        return "formatter-form";
    }

    @PostMapping("/formatter/edit")
    //@ModelAttribute는 사용자가 입력한 값을 Form 객체로 바인딩
    public String formmaterEdit(@ModelAttribute Form form) {
        return "formatter-form";
    }

    @Data // getter, setter, toString 자동 생성
    static class Form {

        //@NumberFormat: 숫자 입력하면 형식 변환
        @NumberFormat(pattern = "###,###")
        private Integer number;


        //@DateTimeFormat: 날짜/시간 입력하면 형식 변환
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dateTime;
    }
}
