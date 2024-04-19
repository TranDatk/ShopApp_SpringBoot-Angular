package com.dat.shopapp.services;

import com.dat.shopapp.exceptions.DataNotFoundException;
import com.dat.shopapp.models.User;

public interface IUserService {
    User createUser(User user) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
