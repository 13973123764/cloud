package com.cloud.result;

import com.cloud.enums.ExceptionEnum;
import lombok.Data;
import java.util.Date;

/**
 * @author zf
 * @date 2019-09-27-23:08
 */
@Data
public class ExceptionResult {

    private int status;
    private String msg;
    private long timestamp;


    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getStatus();
        this.msg = exceptionEnum.getMsg();
        this.timestamp = new Date().getTime();
    }
}
