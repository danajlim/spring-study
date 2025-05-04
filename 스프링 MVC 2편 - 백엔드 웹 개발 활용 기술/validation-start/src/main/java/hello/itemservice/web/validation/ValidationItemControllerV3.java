package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    /*
    * @Validated: Bean Validation 자동 적용
    * SaveCheck.class : Item.java에서 savecheck 표시된 속성들만 확인
    * */
    @PostMapping("/add")
    public String addItem(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult
            bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외 (price, quantity 합쳐서 만들어야하는 예외)
        //둘 이상의 필드를 조합하는 에러는 Bean Validation에서 처리 불가
        //bindingResult.reject("errorCode", errorArgs, defaultMessage);
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice()*item.getQuantity();
            if (resultPrice < 10000){
                //totalPriceMin=전체 가격은 {0}원 이상이어야 합니다. 현재 값 = {1}
                //errors.properties 에서 totalPriceMin 찾음, {0},{1}에 들어갈 값 Object에 담음
                bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v3/addForm";
        }
        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }



    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    //UpdateCheck.class: Item.java에서 updatecheck 표시된것만 검증
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes bindingResult) {
        itemRepository.update(itemId, item);

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice()*item.getQuantity();
            if (resultPrice < 10000){
                //totalPriceMin=전체 가격은 {0}원 이상이어야 합니다. 현재 값 = {1}
                //errors.properties 에서 totalPriceMin 찾음, {0},{1}에 들어갈 값 Object에 담음
                bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v3/editForm";
        }

        return "redirect:/validation/v3/items/{itemId}";
    }

}


