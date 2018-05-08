package top.renote.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Joder_X on 2018/2/27.
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(/*default:Exception.class*/)
    public void exceptionHandler(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Exception ex){
        log.info(ex.getMessage());
    }
}
