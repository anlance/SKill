package club.anlan.sKill.controller;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
    public String toList(Model model,User user){
        model.addAttribute(user);
        return "goods_list";
    }

}
