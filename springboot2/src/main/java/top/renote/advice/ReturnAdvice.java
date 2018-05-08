package top.renote.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by Joder_X on 2018/2/19.
 * 修改modelMap的status，转为http状态码
 *
 */
@ControllerAdvice
public class ReturnAdvice implements ResponseBodyAdvice {

    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof ModelMap){
            ModelMap modelMap = (ModelMap) body;
            response.setStatusCode(HttpStatus.valueOf((Integer) modelMap.get("status")));
            modelMap.remove("status");
        }
        return body;
    }
}
