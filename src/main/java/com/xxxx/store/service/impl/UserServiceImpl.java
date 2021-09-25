package com.xxxx.store.service.impl;


import com.xxxx.store.entity.User;
import com.xxxx.store.mapper.UserMapper;
import com.xxxx.store.service.IUserService;
import com.xxxx.store.service.exception.*;
import com.xxxx.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private JsonResult jsonResult;

    @Override
    public void reg(User user) {
//        System.out.println(user.getUser_Id());
        String User_Id=user.getUser_Id();
        if (user.getUser_Id()==null || "".equals(user.getUser_Id()) && user.getUser_Pwd()==null || "".equals(user.getUser_Pwd())){
            throw new UserException("用户名或密码为空！");
        }

        User result = userMapper.findByUserName(User_Id);

        if (result!=null){
            throw new UserException("用户名已被占用！");
        }
        Date date = new Date();
        //盐值加密
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getUser_Pwd(), salt);
        user.setUser_Pwd(md5Password);
        user.setSalt(salt);
        // 补全数据：4项日志属性
        user.setCreated_User(User_Id);
        user.setCreated_Time(date);
        user.setModified_User(User_Id);
        user.setModified_Time(date);
        System.out.println(user.toString());
        Integer rows = userMapper.insert(user);


        if (rows!=1){
            throw new UnknownException("注册时出现未知错误！请联系管理员！");
        }

    }

    //密码加密方法
    private String getMd5Password(String password, String salt)
    {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
    @Override
    public User login(String User_Id, String User_Pwd) {
        User result=userMapper.findByUserName(User_Id);
        if (result==null){
            throw new UserException("用户不存在！");
        }
        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(User_Pwd, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!result.getUser_Pwd().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PassWordException("密码验证失败的错误");
        }
        return result;
    }

    @Override
    public User update(String oldUser_Pwd, String newUser_Pwd, String Id) {
        //通过id查找用户
//    try {
        User user = userMapper.findId(Integer.valueOf(Id));
        if(user == null){
            throw new TokenException("token验证失败！");
        }

        //判断旧密码是否正确,旧密码进行加密比较
        String md5Password = getMd5Password(oldUser_Pwd, user.getSalt());
        if (!(user.getUser_Pwd().equals(md5Password))) {
            throw new PassWordException("旧密码错误！");
        }

        //修改密码
        //新密码加密
        String newMd5Password = getMd5Password(newUser_Pwd, user.getSalt());
        Date date = new Date();

        userMapper.UpdatePassword(newMd5Password, Id, user.getUser_Id(), date);
        User result = userMapper.findId(Integer.valueOf(Id));
        //新密码与修改后的密码对比，不一样抛异常
        if (!(result.getUser_Pwd().equals(newMd5Password))) {
            throw new UserException("修改密码失败！");
        }
        return result;
//    }catch (Exception e){
//        finishException("服务器错误,联系管理员！");
//    }
//    return null;
    }

    @Override
    public void cancellation(int Id) {
        userMapper.cancellation(Id);

        User user = userMapper.findId(Id);
        if (!(user == null)){
            throw new UserException("注销失败！");
        }
    }


    public JsonResult finishException(String message){
        throw new UnknownException(message);
    }


    public User changeAvatar(String id, String username, String avatar) {
            User user = userMapper.findId(Integer.valueOf(id));
            if (user == null){
                throw new UserException("token验证错误！");
            }
            Date date = new Date();
            Date Modified_Time = date;
            userMapper.changeAvatar(Integer.valueOf(id), avatar, username, Modified_Time);
            User result = userMapper.findId(Integer.valueOf(id));
            if (!(result.getAvatar().equals(avatar))){
                System.out.println(result.getAvatar());
                System.out.println(avatar);
                throw new FileException("上传错误！");
            }
            return result;
    }
}
