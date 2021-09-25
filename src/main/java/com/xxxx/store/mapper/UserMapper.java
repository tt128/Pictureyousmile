package com.xxxx.store.mapper;


import com.xxxx.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface UserMapper {

    Integer insert(User user);

    User findByUserName(String User_Name);

    void UpdatePassword(String newUser_Pwd, String Id, String User_Id, Date Modified_Time);

    User findId(int Id);

    void changeAvatar(@Param("Id") int Id ,
                      @Param("avatar") String avatar,
                      @Param("User_Id") String User_Id,
                      @Param("Modified_Time") Date Modified_Time);


    void cancellation(@Param("Id") int Id);

    List<User> findAll();

}
