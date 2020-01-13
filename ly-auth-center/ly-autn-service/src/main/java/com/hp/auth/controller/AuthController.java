package com.hp.auth.controller;

import com.hp.auth.client.UserClient;
import com.hp.auth.config.JwtProperties;
import com.hp.auth.entity.UserInfo;
import com.hp.auth.service.AuthService;
import com.hp.auth.utils.JwtUtils;
import com.leyou.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = authService.accredit(username, password);
        if (null == token) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //写入cookie
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getCookieMaxAge());
        return  ResponseEntity.ok().build();

    }
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN")String s, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo userInfo=JwtUtils.getInfoFromToken(s,jwtProperties.getPublicKey());
        if (userInfo!=null){
            //刷新cookie里面字符串
            String token = JwtUtils.generateToken(userInfo,jwtProperties.getPrivateKey(),jwtProperties.getExpire());
            //写入cookie
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getCookieMaxAge());
            return  ResponseEntity.ok(userInfo);
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}