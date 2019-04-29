package club.anlan.sKill.service;

import club.anlan.sKill.domain.User;

public interface UserService {

    User getById(int id);

    boolean tx();
}
