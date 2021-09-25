package com.xxxx.store.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User extends UserEntity implements Serializable {

    private Integer Id;
    @NotNull()
    @JsonProperty(value = "User_Id")
    private String User_Id;
    @NotNull
    @JsonProperty(value = "User_Pwd")
    private String User_Pwd;
    private String Salt;
    private String User_Name;
    private String Email;
    private String Phone;
    @JsonProperty(value = "newUser_Pwd")
    private String newUser_Pwd;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOldUser_Pwd() {
        return oldUser_Pwd;
    }

    public void setOldUser_Pwd(String oldUser_Pwd) {
        this.oldUser_Pwd = oldUser_Pwd;
    }

    @JsonProperty(value = "oldUser_Pwd")
    private String oldUser_Pwd;

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", User_Id='" + User_Id + '\'' +
                ", User_Pwd='" + User_Pwd + '\'' +
                ", Salt='" + Salt + '\'' +
                ", User_Name='" + User_Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", newUser_Pwd='" + newUser_Pwd + '\'' +
                ", avatar='" + avatar + '\'' +
                ", oldUser_Pwd='" + oldUser_Pwd + '\'' +
                '}';
    }

    public String getNewUser_Pwd() {
        return newUser_Pwd;
    }

    public void setNewUser_Pwd(String newUser_Pwd) {
        this.newUser_Pwd = newUser_Pwd;
    }



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUser_Pwd() {
        return User_Pwd;
    }

    public void setUser_Pwd(String user_Pwd) {
        User_Pwd = user_Pwd;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}