package com.hp.controller;

import com.hp.auth.entity.UserInfo;
import com.hp.interceptors.LoginInterceptor;
import com.hp.pojo.Cart;
import com.hp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        UserInfo userInfo= LoginInterceptor.getUserInfo();
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
@GetMapping
    public ResponseEntity<List<Cart>> querycarts(){
       List<Cart> carts=cartService.querycarts();
       return  ResponseEntity.ok(carts);

}
@PutMapping("increment")
    public ResponseEntity<Void>increment(@RequestBody Cart cart){
        cartService.increment(cart);
        return  ResponseEntity.ok().build();
}
@DeleteMapping("{id}")
    public  ResponseEntity<Void> delete(@PathVariable("id")String i){
    cartService.delete(i);
    return  ResponseEntity.ok().build();
}

}
