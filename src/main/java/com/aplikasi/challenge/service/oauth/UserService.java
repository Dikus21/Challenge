package com.aplikasi.challenge.service.oauth;

import com.aplikasi.challenge.request.LoginModel;
import com.aplikasi.challenge.request.RegisterModel;

import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel) ;
    Map registerByGoogle(RegisterModel objModel);
    public Map login(LoginModel objLogin);
}

