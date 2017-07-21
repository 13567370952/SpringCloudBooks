package com.wujunshen.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.security.Audience;
import com.wujunshen.util.Constants;
import com.wujunshen.util.JwtUtils;
import com.wujunshen.vo.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:17:02 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class HTTPBearerAuthorizeFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPBearerAuthorizeFilter.class);
    @Autowired
    private Audience audience;

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
        } catch (IOException | ServletException e) {
            LOGGER.error("exception message is:{}", e.getMessage());
        }
        LOGGER.info("request URI is:{}", request.getRequestURI());
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
        BaseResponse baseResponse = new BaseResponse();
        ObjectMapper mapper = new ObjectMapper();
        ctx.setSendZuulResponse(true);//zuul过滤请求，false为不路由，true为路由
        HttpServletResponse httpResponse = ctx.getResponse();
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        baseResponse.setCode(responseStatus.getCode());
        baseResponse.setMessage(responseStatus.getMessage());
        httpResponse.getWriter().write(mapper.writeValueAsString(baseResponse));
        ctx.setResponse(httpResponse);//返回response信息
    }

    /**
     * 判断JWT是否有效
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private boolean isValidJwt(HttpServletRequest request) throws IOException, ServletException {
        LOGGER.info("{} request to {}", request.getMethod(), request.getRequestURL());
        String auth = request.getHeader("Authorization");
        LOGGER.info("auth is :{}", auth);
        String headString = auth.substring(0, 6).toLowerCase();
        if (headString.compareTo(Constants.BEARER) == 0) {
            auth = auth.substring(7, auth.length());
            if (JwtUtils.parseJWT(auth, audience.getBase64Secret()) != null) {
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
        LOGGER.info("{} request to {}", request.getMethod(), request.getRequestURL());
        String auth = request.getHeader("Authorization");
        LOGGER.info("auth is :{}", auth);
        return !(auth == null || auth.length() <= 7);
    }
}