package com.mysign.service.Exception;

import com.mysign.service.vo.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MySignException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

}
