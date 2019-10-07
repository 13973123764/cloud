package com.cloud.advice;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.result.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zf
 * @date 2019-09-27-15:32
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = CloudException.class)
    public ResponseEntity<ExceptionResult> handlerException(CloudException exception){
        ExceptionEnum ex = exception.getExceptionEnum();
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionResult(ex));
    }


}
