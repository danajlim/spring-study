package hello.springmvc.basic.response;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//뷰 템플릿을 호출하는 컨트롤러
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    //ModelAndView: model+view 정보를 한꺼번에 담을 수 있는 객체
    public ModelAndView responseViewV1() {
        //"data"라는 이름으로 "hello!" 값을 뷰에 넘김
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    //Model: Controller -> View로 데이터를 전달할 때 사용하는 객체
    public String responseViewV2(Model model) {
        //모델에 데이터라는 이름에 hello!! 값을 추가
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }
}
