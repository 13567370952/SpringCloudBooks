package com.wujunshen.filter;

import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.service.BookConsumerService;
import com.wujunshen.util.Constants;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.Audience;
import com.wujunshen.web.vo.security.LoginParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/15 <br>
 * Time:下午2:52 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Order(1)
@WebFilter(filterName = "redisFilter", urlPatterns = {"/consumer/*"})
@Slf4j
public class RedisFilter implements Filter {
    @Resource
    private Audience audience;
    @Resource
    private BookConsumerService bookConsumerService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, LoginParameter> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=====================RedisFilter init======================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("======================Start to execute RedisFilter======================");
        log.info("redisFilter");

        Map<String, String[]> test = servletRequest.getParameterMap();
        //从redis缓存取token
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {//若token不为空，则执行controller方法
            filterChain.doFilter(servletRequest, servletResponse);
        }

        //token为空，则向API网关申请新的token
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {//若API网关成功返回token，则放置在redis缓存中,然后继续执行controller方法
            Map tokenMap = (HashMap) baseResponse.getData();
            log.info(Constants.TOKEN_TYPE + " {}", tokenMap.get(Constants.TOKEN_TYPE));
            log.info(Constants.ACCESS_TOKEN + " {}", tokenMap.get(Constants.ACCESS_TOKEN));
            log.info(Constants.EXPIRES_IN + " {}", tokenMap.get(Constants.EXPIRES_IN));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get(Constants.ACCESS_TOKEN), ((Integer) tokenMap.get(Constants.EXPIRES_IN)).longValue(), TimeUnit.SECONDS);
            filterChain.doFilter(servletRequest, servletResponse);
        }

        log.info("======================End to execute RedisFilter======================");
    }

    @Override
    public void destroy() {
        log.info("=====================RedisFilter destroy======================");
    }
}