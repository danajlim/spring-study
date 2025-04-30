package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    /*
    * 상품 등록 폼을 제출했을 때 실행되는 메서드
    * BindingResult: 폼 입력값을 객체에 바인딩할 때 발생하는 오류를 보관하는 용도로 사용되는 객체
    * 검증 결과를 view에 그대로 넘김
     * 반드시 @ModelAttribute 뒤에 와야함
    * */
    //@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다"));
        }
        if (item.getPrice() == null || item.getPrice()< 10000 || item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000~1,000,000원까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >=9999){
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999개까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice()!= null && item.getQuantity()!=null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if (resultPrice<1000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야합니다. 현재값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        /*검증 로직 7개 인자 버전
        * rejectedValue: item.getItemName() -> 사용자가 입력한 값을 지정
        * bindingFailure: False -> 바인딩 실패가 아니라 검증 실패임
        * codes: null -> 국제화 메세지 코드 없음
        * arguments: null -> 메시지 포맷에 들어갈 인자 없음
        * */
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,"상품 이름은 필수입니다"));
        }
        if (item.getPrice() == null || item.getPrice()< 10000 || item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000~1,000,000원까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >=9999){
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999개까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice()!= null && item.getQuantity()!=null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if (resultPrice<1000) {
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야합니다. 현재값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

