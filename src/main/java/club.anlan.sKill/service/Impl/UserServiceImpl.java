package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.User;
import club.anlan.sKill.mapper.UserMapper;
import club.anlan.sKill.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(3);
        u1.setName("wtf");
        userMapper.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("wtf");
        userMapper.insert(u2);

        return true;
    }
}
