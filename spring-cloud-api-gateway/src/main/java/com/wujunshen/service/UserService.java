package com.wujunshen.service;

import com.wujunshen.dao.UserMapper;
import com.wujunshen.entity.User;
import com.wujunshen.entity.UserCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:17:41 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Service
@Slf4j
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User findUserInfoByName(String userName) {
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userCriteria);

        return (users != null) ? !users.isEmpty() ? users.get(0) : null : null;
    }
}