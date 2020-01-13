package com.hp.user.controller;

import com.hp.user.pojo.User;
import com.hp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data")String data,@PathVariable("type") Integer type){

          Boolean bool=userService.check(data,type);
          if (bool==false){
              return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
          }
        return  ResponseEntity.ok(bool);
    }
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean bool= userService.sendVerifyCode(phone);
        if (bool!=null && bool){
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("register")
    public ResponseEntity<Void> createUser(@Valid User user, @RequestParam("code") String code){
        Boolean b=userService.createUser(user,code);
        if(null!=b&&b){
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    @GetMapping
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,@RequestParam("password") String password){
        User user=userService.queryUser(username,password);
        if (null!=user){
            return ResponseEntity.ok(user);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
