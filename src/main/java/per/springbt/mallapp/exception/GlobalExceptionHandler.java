package per.springbt.mallapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import per.springbt.mallapp.common.APIRestResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception: ", e);
        return APIRestResponse.error(MallappExceptionEnum.SYSTEM_ERROR);
    }
    @ExceptionHandler
    @ResponseBody
    public Object handleMallException(MallappException e) {
        log.error("Mall Exception: ", e);
        return APIRestResponse.error(e.getCode(), e.getMsg());
    }
    @ExceptionHandler
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Argument Error: ", e);
        return handleBindingResult(e.getBindingResult());
    }
    public APIRestResponse handleBindingResult(BindingResult bindingResult) {
        //把异常处理为用户可见的提示
        List<String> list = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError e : allErrors) {
                list.add(e.getDefaultMessage());
            }
        }
        if (list.size() == 0) {
            return APIRestResponse.error(MallappExceptionEnum.PARAMETER_ERROR);
        }
        return APIRestResponse.error(MallappExceptionEnum.PARAMETER_ERROR.getCode(), list.toString());
    }
}