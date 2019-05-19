package club.anlan.sKill.controller;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.rabbitmq.MQSender;
import club.anlan.sKill.redis.UserKey;
import club.anlan.sKill.result.Result;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/demo")
public class Sample {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTX(){
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        Boolean flag = redisService.set(UserKey.getById,"1",user);
        return Result.success(flag);
    }

}
