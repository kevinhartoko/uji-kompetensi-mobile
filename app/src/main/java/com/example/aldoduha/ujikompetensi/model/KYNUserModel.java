package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aldoduha on 11/5/2017.
 */

public class KYNUserModel implements Serializable{
    private Long id;
    private String serverId;
    private String nama;
    private String username;
    private String password;
    private String role;
    private String token;

    public KYNUserModel(){

    }

    public KYNUserModel(JSONObject object){
        try {
            if(object.has(KYNJSONKey.KEY_USER_NAME))
                setNama(object.getString(KYNJSONKey.KEY_USER_NAME));
            if(object.has(KYNJSONKey.KEY_USER_USERNAME))
                setUsername(object.getString(KYNJSONKey.KEY_USER_USERNAME));
            if(object.has(KYNJSONKey.KEY_USER_PASSWORD))
                setPassword(object.getString(KYNJSONKey.KEY_USER_PASSWORD));
            if(object.has(KYNJSONKey.KEY_USER_ROLE))
                setRole(object.getString(KYNJSONKey.KEY_USER_ROLE));
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
