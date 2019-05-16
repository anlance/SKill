package club.anlan.sKill.controller;

import club.anlan.sKill.result.Result;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.UserService;
import club.anlan.sKill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    private static Logger logger =  LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin(Model model){
        return "login";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        userService.login(response,loginVo);
        return Result.success(true);
    }

}
