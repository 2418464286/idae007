package com.hp.service;


import com.hp.auth.entity.UserInfo;
import com.hp.interceptors.LoginInterceptor;
import com.hp.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    public void addCart(Cart cart) {
        //获取用户消息
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //放入购物车
        System.out.println(userInfo);

    }
}
