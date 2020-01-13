package com.hp.gateway.filter;

import com.hp.auth.entity.UserInfo;
import com.hp.auth.utils.JwtUtils;
import com.hp.gateway.config.FilterProperties;
import com.hp.gateway.config.JwtProperties;
import com.leyou.util.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.FileEncodingApplicationListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginFilter extends ZuulFilter {
    @Autowired
    private  JwtProperties jwtProperties;
    @Autowired
    private FilterProperties filterProperties;
    @Override
    public String filterType() {
        //请求在被路由之前执行
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //请求头之前，查看请求参数
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;

    }

    @Override
    public boolean shouldFilter() {
        //获取请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request请求
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);///api/auth/verify
        //读取配置获取白名单
        List<String> allowPaths = filterProperties.getAllowPaths();
        for(String url:allowPaths){
            //请求地址包含白名单
            if(requestURI.startsWith(url)){///api/auth/verify  /api/auth
                return  false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
      //第一步 获取cookie
        //获取请求的上下文
       RequestContext context=RequestContext.getCurrentContext();
       //获取reque请求
        HttpServletRequest request=context.getRequest();

       //解密
        try {
            //拿出cookie
            String s= CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(s, jwtProperties.getPublicKey());
        } catch (Exception e) {
            //用户没有登录
            //用户登录，token假的
            //  //不存在，未登陆，则拦截
            context.setSendZuulResponse(false);
            //返回403
            context.setResponseStatusCode(403);

        }
        return null;
    }
}
