package com.xxh.start1.service;

import com.xxh.start1.mapper.UserMapper;
import com.xxh.start1.model.User;
import com.xxh.start1.model.UserExample;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
private  UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users= userMapper.selectByExample(userExample);
         if(users.size()==0)
         {
             //插入
             user.setGmtCreate(System.currentTimeMillis());
             user.setGmtModified(user.getGmtCreate());
             userMapper.insert(user);
         }
         else{
             User dbuser = users.get(0);
             User updateUser=new User();
             updateUser.setGmtModified(System.currentTimeMillis());
             updateUser.setAvatarUrl(user.getAvatarUrl());
             updateUser.setToken(user.getToken());
             updateUser.setName(user.getName());
             UserExample example=new UserExample();
             example.createCriteria()
                     .andIdEqualTo(dbuser.getId());
             userMapper.updateByExampleSelective(updateUser,example);
         }
    }
}
