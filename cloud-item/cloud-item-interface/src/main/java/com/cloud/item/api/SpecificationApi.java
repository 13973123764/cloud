package com.cloud.item.api;

import com.cloud.item.pojo.SpecGroup;
import com.cloud.item.pojo.SpecParam;
import com.cloud.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-03-14:46
 */
public interface SpecificationApi {

    @GetMapping("spec/queryParamByGid")
    public List<SpecParam> queryParamList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching);

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("spec/group")
    public List<SpecGroup> queryGroupByCid(@RequestParam("cid") Long cid);

}
