package com.demon.service;

import com.demon.model.UserModel;

/**
 * Created by Demon on 2017/4/18.
 */
public interface UserService {
    UserModel login(String token);
}
