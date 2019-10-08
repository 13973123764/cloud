package com.cloud.search.pojo;

import java.util.Map;

/**
 * @author zf
 * @date 2019-10-08-00:10
 */
public class SearchRequest {

    // 搜索字段
    private String key;
    // 页码
    private Integer page;

    private Map<String, String> filter;

    // 页面大小
    private static final int DEFAULT_ROWS = 20;
    // 默认页
    private static final int DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return DEFAULT_ROWS;
    }

    public int getDefaultPage() {
        return DEFAULT_PAGE;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
