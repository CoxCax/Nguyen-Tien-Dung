package com.example.test5.model;

public class UserModel {
    public int id;
    public String name;
    public String email;
    public String password;
    public String comperpassword;
    public String created_at;
    public String update_at;

    public UserModel(int id, String name, String email, String password, String comperpassword, String created_at, String update_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.comperpassword = comperpassword;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public UserModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComperpassword() {
        return comperpassword;
    }

    public void setComperpassword(String comperpassword) {
        this.comperpassword = comperpassword;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
