package club.anlan.sKill.service;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.result.CodeMsg;
import club.anlan.sKill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User getById(long id);

    boolean login(HttpServletResponse response, LoginVo loginVo);

    User getByToken(String token);
}
