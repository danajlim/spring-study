package hello.exception.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    //예외 핸들러 1 - IllegalArgumentException
    @ResponseStatus(HttpStatus.BAD_REQUEST) //예외가 발생했을 때 HTTP 상태 코드를 400으로
    @ExceptionHandler(IllegalArgumentException.class) //IllegalArgumentException이 발생했을 때 실행됨
    public ErrorResult illegalArgumentException(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); //에러 응답 객체 생성
    }

    //예외 핸들러 2 - UserException
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    //예외 핸들러 3 - 그 외 모든 예외
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //상태코드를 500으로
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex");
        return new ErrorResult("EX", "내부 오류");
    }

}
