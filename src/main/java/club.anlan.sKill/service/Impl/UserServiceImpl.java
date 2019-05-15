package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.exception.GlobalException;
import club.anlan.sKill.mapper.UserMapper;
import club.anlan.sKill.redis.UserKey;
import club.anlan.sKill.result.CodeMsg;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.UserService;
import club.anlan.sKill.util.MD5Util;
import club.anlan.sKill.util.UUIDUtil;
import club.anlan.sKill.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    public static final String COOKIE_NAME_TOKEN="token";

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    public User getById(long id) {
        return userMapper.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo==null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = getById(Long.parseLong(mobile));
        if(user==null)
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password,salt);
        if(!calcPass.equals(dbPass))
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        //生成 Token
        String token = UUIDUtil.uuid();
        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    public User getByToken(String token) {
        if(StringUtils.isEmpty(token))
            return null;
        return redisService.get(UserKey.token,token,User.class);
    }


}
