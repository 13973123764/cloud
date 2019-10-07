package com.cloud.exception;

import com.cloud.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author zf
 * @date 2019-09-27-23:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CloudException extends RuntimeException{

    ExceptionEnum exceptionEnum;

}
