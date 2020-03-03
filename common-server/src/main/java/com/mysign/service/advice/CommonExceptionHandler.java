package com.mysign.service.advice;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import com.mysign.service.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //增强Controller将一些公共的数据定义在添加了 @ControllerAdvice 注解的类中，这样，在每一个 Controller 的接口中，就都能够访问到这些数据。 常用于全局异常处理，全局数据统计，数据预处理
public class CommonExceptionHandler {
    @ExceptionHandler(MySignException.class) //@ExceptionHandler 注解用来指明异常的处理类型
    public ResponseEntity<ExceptionResult> handleException(MySignException e){
        ExceptionEnum em=e.getExceptionEnum();
        return ResponseEntity.status(em.getStatus()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}