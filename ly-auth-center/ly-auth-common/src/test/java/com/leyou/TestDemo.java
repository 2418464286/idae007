package com.leyou;

import com.hp.auth.entity.UserInfo;
import com.hp.auth.utils.JwtUtils;
import com.hp.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TestDemo<pubPath> {
    //产生公钥和私钥
private  static  final String pubPath="D:\\tem\\rsa.pub";
private  static  final String  priPath="D:\\tem\\rsa.pri";
//@Test
//public void init(){
//    RsaUtils.generateKey(pubPath,priPath,"ty555");
//}
    private PublicKey publicKey;
    private PrivateKey privateKey;
    @Before
    public  void  loadDate() throws Exception{
        publicKey=RsaUtils.getPublicKey(pubPath);
        privateKey=RsaUtils.getPrivateKey(priPath);
    }
    //产生token
    @Test
    public void genToken() throws Exception {

        String token = JwtUtils.generateToken(new UserInfo(12L, "tom"), privateKey, 5);
        System.out.println(token);
        //eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNTc4NzEyNjIyfQ.Qd6DBWvE9gUJx6whBMluQLKEcKIguGYcW8cZ8IWAw8HdhLOuClRnvXRzBXsPR5P661oz8sYJRBY2hA_EzOdgvgBz9K3NCsppy5BgahSeeSj-5pDmvVh3AdC6AphjI1vWrui0LVSOFeE2y0CsJ2cOrABKpHny1sZZU5KLBHufMK8
    }

    @Test
    public void jiemi() throws Exception {
        //解密
        String s="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNTc4NzEyNjIyfQ.Qd6DBWvE9gUJx6whBMluQLKEcKIguGYcW8cZ8IWAw8HdhLOuClRnvXRzBXsPR5P661oz8sYJRBY2hA_EzOdgvgBz9K3NCsppy5BgahSeeSj-5pDmvVh3AdC6AphjI1vWrui0LVSOFeE2y0CsJ2cOrABKpHny1sZZU5KLBHufMK8";
        UserInfo userInfo = JwtUtils.getInfoFromToken(s, publicKey);
        System.out.println(userInfo);

    }
}
