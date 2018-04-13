package com.dcits.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/*
 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
	pre：路由之前
	routing：路由之时
	post： 路由之后
	error：发送错误调用
	filterOrder：过滤的顺序
	shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
	run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
 */


/*
 * 可以做些接入渠道端的数据校验,比如token等
 */
@Component
public class AccessFilter extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }else{
            //TODO 根据token获取相应的登录信息，进行校验（略）
        
            //灰度示例
//            RibbonFilterContextHolder.clearCurrentContext();
//            if (token.equals("1234567890")) {//可以把参数值，换成自己的version-版本号，shopId-商家Id之类)
//                RibbonFilterContextHolder.getCurrentContext().add("gated-launch", "true");
//            } else {
//                RibbonFilterContextHolder.getCurrentContext().add("gated-launch", "false");
//            }
        }
        //添加Basic Auth认证信息
        ctx.addZuulRequestHeader("Authorization", "Basic " + getBase64Credentials("app01", "*****"));
 
        log.info("ok");
        return null;
    }
    
    private String getBase64Credentials(String username, String password) {
        String plainCreds = username + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }
}
