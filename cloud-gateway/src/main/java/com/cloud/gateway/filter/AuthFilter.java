package com.cloud.gateway.filter;

import com.cloud.gateway.config.FilterProperties;
import com.cloud.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul 过滤器
 * @author zf
 * @date 2019-10-06-13:44
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    FilterProperties properties;

    @Override
    public String filterType() {
        // 前置过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = context.getRequest();
        System.out.println("properties = " + properties.getAllowPaths());
        return isAllowPath(request.getRequestURI());
    }

    private boolean isAllowPath(String requestURL) {
        return properties.getAllowPaths().stream().anyMatch(path -> StringUtils.contains(requestURL, path));
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = context.getRequest();
        // 获取cookie 中的 token
        System.out.println("running .....");
        return null;
    }
}
