package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

//Formatter<T>: 타입 변환 인터페이스, T: 변환 대상 타입
//문자열 <-> 숫자 변환 기능
@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    //문자열 -> 숫자
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);

        /*
        * NumberFormat.getInstance(locale): 해당 국가에 맞는 숫자 포맷 적용
        * parse(): Number 객체로 변환
        * */
        //"1,000"->1000
        return NumberFormat.getInstance(locale).parse(text);
    }

    //숫자 -> 문자열
    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);

        /*
        * 숫자(Number 객체)를 문자열로 포맷해서 반환*/
        return NumberFormat.getInstance(locale).format(object);
    }
}
