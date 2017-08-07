package com.wujunshen.config;

import com.wujunshen.ConsumerBookApplication;
import com.wujunshen.vo.security.LoginParameter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/7 <br>
 * Time:下午2:35 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerBookApplication.class)
@EnableAutoConfiguration
public class RedisConfigTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, LoginParameter> redisTemplate;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetStringValue() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");

        assertThat(stringRedisTemplate.opsForValue().get("aaa"), equalTo("111"));
    }

    @Test
    public void testSetObjectValue() throws Exception {
        LoginParameter loginParameter = new LoginParameter("098f6bcd4621d373cade4e832627b4f6", "wujunshen", "wujunshen");

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        operations.set("com.neox", loginParameter);
        operations.set("com.neo.f", loginParameter, 1, TimeUnit.SECONDS);

        Thread.sleep(1000);

        assertThat(redisTemplate.hasKey("com.neo.f"), equalTo(false));
        assertThat(redisTemplate.hasKey("com.neox"), equalTo(true));
        assertThat(operations.get("com.neo.f"), equalTo(null));
        assertThat(operations.get("com.neox").getUserName(), equalTo("wujunshen"));
    }
}