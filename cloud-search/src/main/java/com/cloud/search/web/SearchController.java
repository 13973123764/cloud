package com.cloud.search.web;

import com.cloud.result.PageResult;
import com.cloud.search.pojo.Goods;
import com.cloud.search.pojo.SearchRequest;
import com.cloud.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zf
 * @date 2019-10-08-00:16
 */
@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * 搜索功能
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }

}
