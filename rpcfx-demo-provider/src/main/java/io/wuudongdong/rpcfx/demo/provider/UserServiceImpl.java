package io.wuudongdong.rpcfx.demo.provider;

import io.wuudongdong.rpcfx.demo.api.User;
import io.wuudongdong.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
