package com.xxxx.store.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class UserEntity implements Serializable {
    private String Created_User;
    private Date Created_Time;
    private String Modified_User;
    private Date Modified_Time;

    public String getCreated_User() {
        return Created_User;
    }

    public void setCreated_User(String created_User) {
        Created_User = created_User;
    }

    public Date getCreated_Time() {
        return Created_Time;
    }

    public void setCreated_Time(Date created_Time) {
        Created_Time = created_Time;
    }

    public String getModified_User() {
        return Modified_User;
    }

    public void setModified_User(String modified_User) {
        Modified_User = modified_User;
    }

    public Date getModified_Time() {
        return Modified_Time;
    }

    public void setModified_Time(Date modified_Time) {
        Modified_Time = modified_Time;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "Created_User='" + Created_User + '\'' +
                ", Created_Time=" + Created_Time +
                ", Modified_User='" + Modified_User + '\'' +
                ", Modified_Time=" + Modified_Time +
                '}';
    }
}
