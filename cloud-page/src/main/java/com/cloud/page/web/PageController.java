package com.cloud.page.web;

import com.cloud.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author zf
 * @date 2019-10-03-20:27
 */
@Controller
public class PageController {

    @Autowired
    PageService pageService;

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model){
        Map<String, Object> attributes = pageService.loadModel(spuId);
        model.addAllAttributes(attributes);
        return "item";
    }


}
