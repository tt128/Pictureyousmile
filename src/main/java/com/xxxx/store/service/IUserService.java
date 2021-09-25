package com.xxxx.store.service;

import com.xxxx.store.entity.User;

public interface IUserService {


    public void reg(User user);

    public User login(String User_Name, String User_Pwd);

    public User update(String oldUser_Pwd, String newUser_Pwd, String Id);

    public User changeAvatar(String Id, String username, String avatar);

    public void cancellation(int Id);
}
