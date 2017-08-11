package com.groceryapp.model;

/**
 * Created by Natasha Mendis on 25-May-17.
 */

public class UserModel {
    private int user_id;
    private String user_name;
    private String user_email;
    private int user_tp;
    private String user_fn;
    private String user_ln;
    private String user_nic;
    private String user_city;


    public UserModel(int user_id, String user_name, String user_email, int user_tp, String user_fn, String user_ln, String user_nic, String user_city) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_tp = user_tp;
        this.user_fn = user_fn;
        this.user_ln = user_ln;
        this.user_nic = user_nic;
        this.user_city = user_city;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_tp() {
        return user_tp;
    }

    public void setUser_tp(int user_tp) {
        this.user_tp = user_tp;
    }

    public String getUser_fn() {
        return user_fn;
    }

    public void setUser_fn(String user_fn) {
        this.user_fn = user_fn;
    }

    public String getUser_ln() {
        return user_ln;
    }

    public void setUser_ln(String user_ln) {
        this.user_ln = user_ln;
    }

    public String getUser_nic() {
        return user_nic;
    }

    public void setUser_nic(String user_nic) {
        this.user_nic = user_nic;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }
}
