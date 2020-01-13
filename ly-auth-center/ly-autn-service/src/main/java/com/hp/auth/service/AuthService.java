package com.hp.auth.service;

import com.hp.auth.client.UserClient;
import com.hp.auth.config.JwtProperties;
import com.hp.auth.entity.UserInfo;
import com.hp.auth.utils.JwtUtils;
import com.hp.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;
    public String accredit(String username, String password){

        try {
            User user=userClient.queryUser(username,password);
            if (user==null){
                return  null;
            }
            //产生token
            String token = JwtUtils.generateToken(new UserInfo(12L, "tom"), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
