package com.wujunshen.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wujunshen.exception.ResponseHandler;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.util.Constants;
import com.wujunshen.util.JwtUtils;
import com.wujunshen.web.vo.security.Audience;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:17:02 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class HTTPBearerAuthorizeFilter extends ZuulFilter {
    @Resource
    private Audience audience;

    @Resource
    private ResponseHandler responseHandler;

    /**
     * 返回一个字符串代表过滤器的类型，zuul定义了4种不同生命周期的过滤器类型
     * <p>
     * pre：可以在请求被路由之前调用
     * routing：在路由请求时候被调用
     * post：在routing和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断过滤器是否要执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器具体逻辑
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        try {
            if (!isExistedAuthorization(request)) {//无Authorization时返回处理逻辑
                responseHandler(ctx, ResponseStatus.NO_AUTHORIZATION);
                return null;
            }
            if (!isValidJwt(request)) {//JWT无效时返回处理逻辑
                responseHandler(ctx, ResponseStatus.INVALID_TOKEN);
                return null;
            }
        } catch (IOException e) {
            log.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
        log.info("request URI is:{}", request.getRequestURI());
        return null;
    }

    /**
     * 返回逻辑统一处理
     *
     * @param ctx
     * @param responseStatus
     * @throws IOException
     */
    private void responseHandler(RequestContext ctx, ResponseStatus responseStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ctx.setSendZuulResponse(false);//zuul过滤请求，false为不路由，true为路由
        HttpServletResponse httpResponse = ctx.getResponse();
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write(mapper.writeValueAsString(responseHandler.getBaseResponse(responseStatus)));

        ctx.setResponse(httpResponse);//返回response信息
    }

    /**
     * 判断JWT是否有效
     *
     * @param request
     * @return
     */
    private boolean isValidJwt(HttpServletRequest request) {
        log.info("{} request to {}", request.getMethod(), request.getRequestURL());
        String authorization = request.getHeader("Authorization");
        log.info("authorization is :{}", authorization);
        String headString = authorization.substring(0, 6).toLowerCase();
        if (headString.compareTo(Constants.BEARER) == 0) {
            authorization = authorization.substring(7, authorization.length());
            if (JwtUtils.parseJWT(authorization, audience.getBase64Secret()) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Authorization是否存在
     *
     * @param request
     * @return
     */
    private boolean isExistedAuthorization(HttpServletRequest request) {
        log.info("{} request to {}", request.getMethod(), request.getRequestURL());
        String authorization = request.getHeader("Authorization");
        log.info("authorization is :{}", authorization);
        return !(authorization == null || authorization.length() <= 7);
    }
}