package com.hp.user.service;
import com.hp.user.Utils.CodecUtils;
import com.hp.user.mapper.UserMapper;
import com.hp.user.pojo.User;
import com.leyou.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    public Boolean check(String data, Integer type) {
        //1.用户名 2.手机
        User user=new User();
          switch (type){
              case  1:
                  user.setUsername(data);
                  break;
              case 2:
                  user .setPhone(data);
          }
          return  usermapper.selectCount(user)!=1;//可用
    }

    public Boolean sendVerifyCode(String phone) {
          //1.产生验证码
        //2.把验证码发到redis
        //发短信
       String url=NumberUtils.generateCode(5);//随机指定位数字
     //将验证码保存进redis
        stringRedisTemplate.opsForValue().set(KEY_PREFIX+phone,url,5, TimeUnit.MINUTES);
      //调用第三方接口来发短信

       return true;
    }

    public Boolean createUser(User user, String code) {

        //校验验证码
        String s = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        //取值没有
        if(null==s){
            return false;
        }
        //取到数据，填错了
        if(!code.equals(s)){
            return  false;
        }

        //校验把用户名拿到数据查询，返回，实现。
        //插入数据库
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //加密
        String newpassword = CodecUtils.md5Hex(user.getPassword(), salt);
        //设置密码
        user.setPassword(newpassword);

        user.setCreated(new Date());

        boolean flag=this.usermapper.insertSelective(user)==1;
        if(flag){
            //redis数据删除
            this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());

        }
        return  flag;
    }

    public User queryUser(String username, String password) {
        //根据盐查询用户
        User user=new User();
        user.setUsername(username);
        User user1=this.usermapper.selectOne(user);
        //用户为空
        if (user1==null){
            return  null;
        }
        //取出盐
        String salt=user1.getSalt();
        //加密
        String newpassword = CodecUtils.md5Hex(password, salt);
        if (!user1.getPassword().equals(newpassword)){
            return null;

        }
         return user1;
    }
}
