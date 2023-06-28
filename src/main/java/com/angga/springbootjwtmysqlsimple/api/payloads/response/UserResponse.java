package com.angga.springbootjwtmysqlsimple.api.payloads.response;

import com.angga.springbootjwtmysqlsimple.api.entity.User;

public class UserResponse {

    public UserResponse(User user){
        super();
        this.phone = user.getUsername();
        this.name = user.getName();
    }
    
    private String name;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
