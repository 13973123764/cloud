package com.cloud.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-28-18:07
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    /** 总条数 */
    private long total;
    /** 总页数 */
    private int pageTotal;
    private List<T> items;


    public PageResult(int pageTotal, List<T> items) {
        this.pageTotal = pageTotal;
        this.items = items;
    }

    public PageResult(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
